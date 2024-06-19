package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.GameData;
import tvz.ikolanovic.shogi.models.PlayerOutPieces;
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
    @FXML
    private Label p1Timer;
    @FXML
    private Label p2Timer;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        Board.populateBoard(boardGrid);
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
            ShogiGameEngine.getInstance().getGameBoard().gameLogic(clickedNode, boardGrid, statOutput, p1Timer, p2Timer);
        }
    }

    public void generateHTMLDocumentation() {
        DocumentationUtils.generateDocumentation();
        DialogUtils.showSuccessDialog("Documentation was successfully generated!");
    }

    public void saveGame() {
        GameData gameData = new GameData(Board.squares, ShogiGameEngine.getInstance().getGameBoard().getMoveHistory(), Board.isOpponentsTurn,
                ShogiGameEngine.getInstance().getGameBoard().getPlayerOutPieces());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveGame/gameSave.dat"))) {
            oos.writeObject(gameData);
            DialogUtils.showSuccessDialog("Game was successfully saved!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveGame/gameSave.dat"))) {
            GameData gameData = (GameData) ois.readObject();
            Square[][] squares = gameData.getGameState();

            boardGrid.getChildren().clear();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Board.setEmptyPieceOnBoard(boardGrid, i, j
                    );
                    Board.setPieceOnBoard(boardGrid, i, j, squares[i][j].getPiece()
                    );
                }
            }

            gameData.getMoveHistory().forEach(move -> statOutput.appendText(move));
            ShogiGameEngine.getInstance().getGameBoard().setMoveHistory(gameData.getMoveHistory());
            Board.isOpponentsTurn = gameData.getIsOpponentsTurn();

            PlayerOutPieces playerOutPieces = gameData.getPlayerOutPieces();

            playerOutPieces.getPlayer1().forEach((key, value) -> {
                Label label = (Label) ShogiGameEngine.getInstance().getStage().getScene().lookup(key);
                label.setText(value);
            });
            playerOutPieces.getPlayer2().forEach((key, value) -> {
                Label label = (Label) ShogiGameEngine.getInstance().getStage().getScene().lookup(key);
                label.setText(value);
            });

            DialogUtils.showSuccessDialog("Game was successfully loaded!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void restartGame() {
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
