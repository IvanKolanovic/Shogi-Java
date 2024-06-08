package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;
import tvz.ikolanovic.shogi.models.utils.DialogUtils;
import tvz.ikolanovic.shogi.models.utils.DocumentationUtils;

import java.io.*;

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
            ShogiGameEngine.getInstance().getGameBoard().gameLogic(clickedNode, boardGrid, statOutput);
        }
    }

    public void generateHTMLDocumentation() {
        DocumentationUtils.generateDocumentation();
        DialogUtils.showSuccessDialog("Documentation was successfully generated!");
    }

    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveGame/gameSave.dat"))) {
            oos.writeObject(Board.squares);
            DialogUtils.showSuccessDialog("Game was successfully saved!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadGame() {

      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveGame/gameSave.dat"))) {
            Square[][] gameStateToLoad = (Square[][]) ois.readObject();

          boardGrid.getChildren().clear();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Board.setEmptyPieceOnBoard(
                            boardGrid, i, j
                    );
                    Board.setPieceOnBoard(
                            boardGrid, i, j, gameStateToLoad[i][j].getPiece()
                    );
                }
            }


            DialogUtils.showSuccessDialog("Game was successfully loaded!");

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void restartGame(){
        boardGrid.getChildren().clear();
        Board.populateBoard(boardGrid);
    }


    /**
     * Send message.
     */
    @FXML
    public void sendMessage() {
    }
}
