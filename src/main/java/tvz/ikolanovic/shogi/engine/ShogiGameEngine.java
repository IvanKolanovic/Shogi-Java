package tvz.ikolanovic.shogi.engine;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import tvz.ikolanovic.shogi.ShogiBoardGame;
import tvz.ikolanovic.shogi.controllers.GameController;
import tvz.ikolanovic.shogi.engine.sockets.Client;

import java.net.Socket;

@NoArgsConstructor
@Getter
@Setter
public class ShogiGameEngine
{
    private Stage stage;
    private Client myClient;

    public ShogiGameEngine(Stage stage)
    {
        this.stage = stage;
    }

    @SneakyThrows
    public void setUpEmptyBoard()
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
        Socket socket = new Socket("localhost",1234);
        this.myClient = new Client(socket,username);
        this.myClient.listenForMessage();
        this.setUpEmptyBoard();
    }
}
