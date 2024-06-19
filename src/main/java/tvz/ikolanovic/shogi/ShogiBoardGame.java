package tvz.ikolanovic.shogi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.engine.GameEngine;

public class ShogiBoardGame extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.initialize(stage);
    }

    @SneakyThrows
    public void initialize(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ShogiBoardGame.class.getResource("board-game-dark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Shogi - Japanese Chess");
        stage.setScene(scene);
        stage.show();
        GameEngine.getInstance().setStage(stage);
    }
}
