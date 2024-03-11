package tvz.ikolanovic.shogi.engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.ShogiBoardGame;
import tvz.ikolanovic.shogi.engine.sockets.Client;
import tvz.ikolanovic.shogi.models.Board;

import java.net.Socket;

@Getter
@Setter
public class ShogiGameEngine
{
    private static ShogiGameEngine INSTANCE;
    private Stage stage;
    private Client myClient;
    private Board gameBoard;


    private ShogiGameEngine()
    {
        this.gameBoard = new Board();
    }

    public static ShogiGameEngine getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ShogiGameEngine();
        }

        return INSTANCE;
    }

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

    @SneakyThrows
    public void connectClientToGameServer(String username)
    {
        Socket socket = new Socket("localhost", 1234);
        this.myClient = new Client(socket, username);
        this.myClient.listenForMessage();
        this.setUpBoard();
    }
}
