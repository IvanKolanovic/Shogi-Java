package tvz.ikolanovic.shogi.engine.services.interfaces;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import tvz.ikolanovic.shogi.models.pieces.Piece;

public interface BoardManager {
    void populateBoard(GridPane boardGrid);

    void checkAndBuildMiddleRow(GridPane boardGrid, int row);

    void checkAndBuildFrontRow(GridPane boardGrid, int row, int col);

    void checkAndBuildBackRow(GridPane boardGrid, int row, int col);

    void populateAllEmptySquares(GridPane boardGrid);

    void setPieceOnBoard(GridPane boardGrid, int row, int col, Piece piece);

    void setEmptyPieceOnBoard(GridPane boardGrid, int row, int col);

    void clearHighlighting(GridPane boardGrid);

    // Helper method to get a node by row and column

    Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane);

    boolean isSquareHighlighted(int row, int column);

    void updateBoard(int newRow, int newColumn, GridPane boardGrid, TextArea statOutput);
}
