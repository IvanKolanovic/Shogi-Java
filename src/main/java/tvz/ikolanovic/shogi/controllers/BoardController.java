package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;

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

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        ShogiGameEngine.getInstance().getGameBoard().populateBoard(boardGrid);
    }

    /**
     * Mouse cell clicked.
     *
     * @param event the event
     */
    @FXML
    public void mouseCellClicked(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != boardGrid) {
            // click on descendant node
            Integer row = GridPane.getRowIndex(clickedNode);
            Integer column = GridPane.getColumnIndex(clickedNode);

            if (ShogiGameEngine.getInstance().getGameBoard().canMovePiece(row, column)) {
                ShogiGameEngine.getInstance().getGameBoard().movePieceToSquare(row, column, boardGrid, statOutput);
            } else {
                ShogiGameEngine.getInstance().getGameBoard().pieceClicked(row, column, boardGrid);
            }
            System.out.println("Row: " + row + " Column: " + column);
        }
    }

    /**
     * Send message.
     */
    @FXML
    public void sendMessage() {
    }
}
