package tvz.ikolanovic.shogi.models;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.utils.DialogUtils;

import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
public class PlayerTimer implements Serializable {
    private final int totalTime;
    private int timeLeft;
    private boolean running;
    private final Object lock = new Object();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> countdownTask;
    private final Label timerLabel;
    private String threadName;

    public PlayerTimer(int totalTimeInSeconds, Label timerLabel, String threadName) {
        this.totalTime = totalTimeInSeconds;
        this.timeLeft = totalTimeInSeconds;
        this.timerLabel = timerLabel;
        this.initTime();
        this.threadName = threadName;
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void start() {
        running = true;
        countdownTask = scheduler.scheduleAtFixedRate(() -> {
            synchronized (lock) {
                if (!running) {
                    System.out.println(threadName + " blocked. Waiting for turn");
                    return;
                }
            }
            timeLeft--;
            Platform.runLater(() -> timerLabel.setText(formatTime(timeLeft)));
            System.out.println(threadName + " Counting: " + formatTime(timeLeft));
            if (timeLeft <= 0) {
                running = false;
                System.out.println(threadName + " has reached 0!");
                Platform.runLater(() -> timerLabel.setText("Time's up!"));
                Platform.runLater(() -> DialogUtils.showWinningDialog(GameEngine.getInstance().getIsOpponentsTurn() ? "Player 1" : "Player 2"));
                countdownTask.cancel(true);
                GameEngine.getInstance().setGameStarted(false);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void pause() {
        synchronized (lock) {
            running = false;
        }
    }

    public void resume() {
        synchronized (lock) {
            running = true;
            lock.notifyAll();
        }
    }

    public void reset() {
        synchronized (lock) {
            timeLeft = totalTime;
            running = false;
            Platform.runLater(() -> timerLabel.setText(formatTime(timeLeft)));
        }
    }

    public void initTime() {
        synchronized (lock) {
            timeLeft = totalTime;
            Platform.runLater(() -> timerLabel.setText(formatTime(timeLeft)));
        }
    }

    public void stop() {
        if (countdownTask != null) {
            countdownTask.cancel(true);
        }
        scheduler.shutdown();
    }
}
