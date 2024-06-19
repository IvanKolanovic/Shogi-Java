package tvz.ikolanovic.shogi.engine.services.interfaces;

import tvz.ikolanovic.shogi.models.PlayerTimer;

public interface TimerService {
    void startTimer();

    void switchTurn();

    void stopTimers();

    PlayerTimer getPlayer1Timer();

    void setPlayer1Timer(PlayerTimer player1Timer);

    PlayerTimer getPlayer2Timer();

    void setPlayer2Timer(PlayerTimer player2Timer);
}
