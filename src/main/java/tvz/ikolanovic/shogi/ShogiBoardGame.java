package tvz.ikolanovic.shogi;

import javafx.application.Application;
import javafx.stage.Stage;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;

import java.io.IOException;

public class ShogiBoardGame extends Application
{
    @Override
    public void start(Stage stage)
    {
        ShogiGameEngine gameEngine = new ShogiGameEngine(stage);
        gameEngine.gameLoop();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
