package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.ShogiBoardGame;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;

@Getter
@Setter
public class GameController
{
    @FXML
    private TextField usernameField;
    @FXML
    private TextArea chatInputField;
    @FXML
    private BoardController boardController;


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
          ShogiGameEngine.getInstance().setUpBoard();
        }
    }

}
