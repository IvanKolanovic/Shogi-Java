package tvz.ikolanovic.shogi.models.utils;

import javafx.scene.control.Alert;

public class DialogUtils {

    public static void showWinningDialog (String player){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WINNER!!!");
        alert.setHeaderText(player+ " WON!");
        alert.setContentText("Game finished.");
        alert.show();
    }

    public static void showSuccessDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The action was successful!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
