package tvz.ikolanovic.shogi.engine.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.engine.services.interfaces.TimerService;
import tvz.ikolanovic.shogi.models.PlayerTimer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimerServiceImpl implements TimerService {
    private PlayerTimer player1Timer;
    private PlayerTimer player2Timer;




    @Override
    public void startTimer() {
        player1Timer.start();
        player2Timer.start();
        player2Timer.pause();
        player1Timer.resume(); // Start with player 1
    }

    @Override
    public void switchTurn() {
        if (GameEngine.getInstance().getIsOpponentsTurn()) {
            player1Timer.pause();
            player2Timer.resume();
        } else {
            player2Timer.pause();
            player1Timer.resume();
        }
    }

    @Override
    public void stopTimers() {
        player1Timer.stop();
        player2Timer.stop();
    }
}
