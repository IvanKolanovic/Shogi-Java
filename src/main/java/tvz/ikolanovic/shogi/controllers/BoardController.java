package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.utils.DialogUtils;
import tvz.ikolanovic.shogi.models.utils.DocumentationUtils;

/**
 * The type Board controller.
 */
@Getter
@Setter
public class BoardController {
    @FXML
    private GridPane boardGrid;
    @FXML
    private TextArea statOutput;
    @FXML
    private Label p1Timer;
    @FXML
    private Label p2Timer;


    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        GameEngine.getInstance().getBoardManager().populateBoard(boardGrid);
    }

    @FXML
    public void mouseCellClicked(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != boardGrid) {
            GameEngine.getInstance().gameLogic(clickedNode, boardGrid, statOutput, p1Timer, p2Timer);
        }
    }

    public void generateHTMLDocumentation() {
        DocumentationUtils.generateDocumentation();
        DialogUtils.showSuccessDialog("Documentation was successfully generated!");
    }

    public void saveGame() {
        GameEngine.getInstance().getSaveAndLoadService().saveGame();
    }

    public void loadGame() {
        GameEngine.getInstance().getSaveAndLoadService().loadGame(boardGrid, statOutput, p1Timer, p2Timer);
    }

    public void restartGame() {
        boardGrid.getChildren().clear();
        GameEngine.getInstance().getBoardManager().populateBoard(boardGrid);
    }


    /**
     * Send message.
     */
    @FXML
    public void sendMessage() {
    }
}
