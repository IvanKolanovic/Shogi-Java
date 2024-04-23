package tvz.ikolanovic.shogi.engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.ShogiBoardGame;
import tvz.ikolanovic.shogi.models.Board;


/**
 * The type Shogi game engine.
 */
@Getter
@Setter
public class ShogiGameEngine
{
    private static ShogiGameEngine INSTANCE;
    private Stage stage;
    private Board gameBoard;


    private ShogiGameEngine()
    {
        this.gameBoard = new Board();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ShogiGameEngine getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ShogiGameEngine();
        }

        return INSTANCE;
    }

    /**
     * Sets up board.
     */
    @SneakyThrows
    public void setUpBoard()
    {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(ShogiBoardGame.class.getResource("board-game-dark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        this.stage.setTitle("Shogi - Japanese Chess");
        this.stage.setScene(scene);
        this.stage.show();
    }
}
