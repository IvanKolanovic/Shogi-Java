package tvz.ikolanovic.shogi.engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.ShogiBoardGame;

@NoArgsConstructor
public class ShogiGameEngine
{
    private Stage stage;

    public ShogiGameEngine(Stage stage)
    {
        this.stage = stage;
    }

    public void gameLoop()
    {
        this.initView();
    }

    @SneakyThrows
    public void initView()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(ShogiBoardGame.class.getResource("board-game-dark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        this.stage.setTitle("Shogi - Japanese Chess");
        this.stage.setScene(scene);
        this.stage.show();
    }
}
