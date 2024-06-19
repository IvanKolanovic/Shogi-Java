package tvz.ikolanovic.shogi.models.utils;

import javafx.scene.control.Alert;

import java.io.Serializable;

public class DialogUtils implements Serializable {

    public static void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The action was successful!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWinningDialog(String player) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WINNER!!!");
        alert.setHeaderText(player + " WON!");
        alert.setContentText("Game finished.");
        alert.show();
    }

}
