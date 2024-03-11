package tvz.ikolanovic.shogi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.controllers.GameController;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;
import tvz.ikolanovic.shogi.models.Board;

import java.net.ServerSocket;

public class ShogiBoardGame extends Application
{
    @Override
    public void start(Stage stage)
    {
        this.initialize(stage);
    }

    @SneakyThrows
    public void initialize(Stage stage)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(ShogiBoardGame.class.getResource("enter-username.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shogi - Enter username");
        stage.setScene(scene);
        stage.show();
        ShogiGameEngine.getInstance().setStage(stage);
    }

    public static void main(String[] args)
    {
        launch();
    }
}
