package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;

@Getter
@Setter
public class GameController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextArea chatInputField;

    private ShogiGameEngine gameEngine;

    @FXML
    private void onConfirmUsername()
    {
        String username = usernameField.getText();

        if (username == null || username.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Username invalid");
            alert.setHeaderText("Please enter a valid username");
            alert.setContentText("The username must not be empty.");
            alert.showAndWait();
        } else
        {
            //this.gameEngine.connectClientToGameServer(username);
            this.gameEngine.setUpEmptyBoard();
        }
    }

    @FXML
    private void sendMessage()
    {
        String message = chatInputField.getText();

        if (message != null || !message.isEmpty())
            this.gameEngine.getMyClient().sendMessage(message);
    }
}
