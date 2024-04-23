package tvz.ikolanovic.shogi.models;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;
import tvz.ikolanovic.shogi.models.pieces.*;
import tvz.ikolanovic.shogi.models.utils.HighlightedCoordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The type Board.
 */
@Getter
@Setter
public class Board {
    /**
     * The constant SIZE.
     */
    public static final int SIZE = 9;
    private Square[][] squares;
    private List<HighlightedCoordinate> highlightedCoordinates;
    private Square selectedSquarePiece;
    private Vector<String> moveHistory = new Vector<>();

    /**
     * Instantiates a new Board.
     */
    public Board() {
        this.squares = new Square[9][9];
        this.selectedSquarePiece = new Square(-1, -1, null); // default non-existing square
        this.highlightedCoordinates = new ArrayList<>();
    }

    /**
     * Populate board.
     *
     * @param boardGrid the board grid
     */
    public void populateBoard(GridPane boardGrid) {
        boardGrid.setAlignment(Pos.CENTER);
        for (int row = 0; row < SIZE; row++) {
            this.checkAndBuildMiddleRow(boardGrid, row);
            for (int col = 0; col < SIZE; col++) {
                this.checkAndBuildBackRow(boardGrid, row, col);
                this.checkAndBuildFrontRow(boardGrid, row, col);
            }
        }
        this.populateAllEmptySquares(boardGrid);
    }

    private void checkAndBuildMiddleRow(GridPane boardGrid, int row) {
        if (row == 1) {
            this.setPieceOnBoard(boardGrid, row, 1, new Rook(1, Boolean.TRUE));
            this.setPieceOnBoard(boardGrid, row, 7, new Bishop(1, Boolean.TRUE));
        } else if (row == 7) {
            this.setPieceOnBoard(boardGrid, row, 1, new Bishop(0, Boolean.FALSE));
            this.setPieceOnBoard(boardGrid, row, 7, new Rook(0, Boolean.FALSE));
        }
    }

    private void checkAndBuildFrontRow(GridPane boardGrid, int row, int col) {
        if (row == 2 || row == 6) {
            boolean isOppositePlayer = row == 2 ? Boolean.TRUE : Boolean.FALSE;
            int owner = isOppositePlayer ? 1 : 0;
            this.setPieceOnBoard(boardGrid, row, col, new Pawn(owner, isOppositePlayer));
        }
    }

    private void checkAndBuildBackRow(GridPane boardGrid, int row, int col) {
        if (row == 0 || row == 8) {
            boolean isOppositePlayer = row == 0 ? Boolean.TRUE : Boolean.FALSE;
            int owner = isOppositePlayer ? 1 : 0;

            if (col == 0 || col == 8) this.setPieceOnBoard(boardGrid, row, col, new Lance(owner, isOppositePlayer));
            else if (col == 1 || col == 7)
                this.setPieceOnBoard(boardGrid, row, col, new Knight(owner, isOppositePlayer));
            else if (col == 2 || col == 6)
                this.setPieceOnBoard(boardGrid, row, col, new SilverGeneral(owner, isOppositePlayer));
            else if (col == 3 || col == 5)
                this.setPieceOnBoard(boardGrid, row, col, new GoldGeneral(owner, isOppositePlayer));
            else this.setPieceOnBoard(boardGrid, row, col, new King(owner, isOppositePlayer));
        }
    }

    /**
     * Can move piece boolean.
     *
     * @param row the row
     * @param col the col
     * @return the boolean
     */
    public boolean canMovePiece(int row, int col) {
        Square square = squares[row][col];
        boolean isOnHighlighted = this.highlightedCoordinates.stream().anyMatch(highlightedCoordinate ->
                highlightedCoordinate.row() == row && highlightedCoordinate.column() == col);

//        if (square.getPiece() != null && square.getPiece().getOwner() == 0)
//            return Boolean.FALSE;

        return isOnHighlighted
                && selectedSquarePiece.getRow() != -1
                && selectedSquarePiece.getColumn() != -1;
    }

    /**
     * Gets square.
     *
     * @param row    the row
     * @param column the column
     * @return the square
     */
    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    private void populateAllEmptySquares(GridPane boardGrid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (squares[row][col] == null) {
                    this.setEmptyPieceOnBoard(boardGrid, row, col);
                }
            }
        }
    }

    /**
     * Piece clicked.
     *
     * @param x         the x
     * @param y         the y
     * @param boardGrid the board grid
     */
    public void pieceClicked(int x, int y, GridPane boardGrid) {
        Square selectedSquare = this.getSquare(x, y);
        Piece piece = selectedSquare.getPiece();
        if (piece != null) {
            if (piece != selectedSquarePiece.getPiece()) this.clearHighlighting(boardGrid);

            List<Square> possibleMoves = piece.getPossibleMoves(x, y, this); // You need to implement getPossibleMoves

            for (Square square : possibleMoves) {
                int targetX = square.getRow();
                int targetY = square.getColumn();

                // Find the button or cell in your GridPane corresponding to this position
                ImageView imageView = (ImageView) this.getNodeByRowColumnIndex(targetX, targetY, boardGrid);
                if (square.getPiece() == null)
                    imageView.setImage(new Image("File:pieces/yellow.png"));
                else
                    imageView.setEffect(new DropShadow(20, Color.RED));
                highlightedCoordinates.add(new HighlightedCoordinate(targetX, targetY));
            }
            this.setSelectedSquarePiece(selectedSquare);
        }
    }

    /**
     * Move piece to square.
     *
     * @param row        the row
     * @param column     the column
     * @param boardGrid  the board grid
     * @param statOutput the stat output
     */
    public void movePieceToSquare(final int row, final int column, GridPane boardGrid, TextArea statOutput) {
        if (!isSquareHighlighted(row, column)) return;

        this.updateBoard(row, column, boardGrid, statOutput);
        this.selectedSquarePiece = new Square(-1, -1, null); // default non-existing square
    }

    private void updateBoard(final int newRow, final int newColumn, GridPane boardGrid, TextArea statOutput) {
        int oldRow = this.selectedSquarePiece.getRow();
        int oldColumn = this.selectedSquarePiece.getColumn();

        this.clearHighlighting(boardGrid);
        Square oldSquare = squares[oldRow][oldColumn];
        ImageView imageView = (ImageView) this.getNodeByRowColumnIndex(oldRow,
                oldColumn, boardGrid);
        // remove old
        boardGrid.getChildren().removeIf(node -> !(node instanceof Group)
                && GridPane.getColumnIndex(node) == oldColumn
                && GridPane.getRowIndex(node) == oldRow);
        // write stat
        this.checkMoveType(boardGrid, oldSquare, newRow, newColumn, statOutput);

        if (squares[newRow][newColumn].getPiece() == null) {
            // place empty on ol
            this.setEmptyPieceOnBoard(boardGrid, oldRow, oldColumn);
        } else if (squares[newRow][newColumn].getPiece() != null) {
            // remove opponent
            boardGrid.getChildren().removeIf(node -> !(node instanceof Group)
                    && GridPane.getColumnIndex(node) == newColumn
                    && GridPane.getRowIndex(node) == newRow);
        }
        // add new
        boardGrid.add(imageView, newColumn, newRow);
        this.setPieceOnBoard(boardGrid, newRow, newColumn, oldSquare.getPiece());
    }

    /**
     * Check move type.
     *
     * @param boardGrid  the board grid
     * @param oldSquare  the old square
     * @param newRow     the new row
     * @param newColumn  the new column
     * @param statOutput the stat output
     */
    public void checkMoveType(GridPane boardGrid, Square oldSquare, final int newRow,
                              final int newColumn, TextArea statOutput) {
        ImageView imageView = (ImageView) this.getNodeByRowColumnIndex(oldSquare.getRow(),
                oldSquare.getColumn(), boardGrid);
        Square target = squares[newRow][newColumn];
        if (target.getPiece() == null) {
            String txt = String.format("%s%d%d-%d%d%n", oldSquare.getPiece().getAcronym(), oldSquare.getRow(),
                    oldSquare.getColumn(), newRow, newColumn);
            moveHistory.add(txt);
            statOutput.appendText(txt);
        } else if (target.getPiece() != null) {
            String txt = String.format("%s%d%dx%s%d%d%n", oldSquare.getPiece().getAcronym(), oldSquare.getRow(),
                    oldSquare.getColumn(), target.getPiece().getAcronym(), newRow, newColumn);
            moveHistory.add(txt);
            statOutput.appendText(txt);
            Label label = (Label) ShogiGameEngine.getInstance().getStage().getScene().lookup("#" + target.getPiece().getAcronym() + target.getPiece().getOwner());
            label.setText(String.valueOf(Integer.parseInt(label.getText()) + 1));
        }
    }

    private boolean isSquareHighlighted(final int row, final int column) {
        return this.highlightedCoordinates.stream().anyMatch(highlightedCoordinate ->
                highlightedCoordinate.row() == row && highlightedCoordinate.column() == column);
    }

    // Helper method to get a node by row and column
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (!(node instanceof Group) && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private void clearHighlighting(GridPane boardGrid) {
        highlightedCoordinates.forEach(highlightedCoordinate -> {
            ImageView imageView = (ImageView) getNodeByRowColumnIndex(highlightedCoordinate.row(), highlightedCoordinate.column(), boardGrid);
            imageView.setEffect(new DropShadow(0, Color.RED));
            if (squares[highlightedCoordinate.row()][highlightedCoordinate.column()].getPiece() == null)
                imageView.setImage(new Image("File:pieces/empty.png"));
        });
        highlightedCoordinates.clear();
    }

    private void setPieceOnBoard(GridPane boardGrid, int row, int col, Piece piece) {
        squares[row][col] = new Square(row, col, piece);
        ImageView imageView = new ImageView(new Image("File:pieces/literal/" + piece.getSymbol() + ".png"));
        imageView.setFitWidth(70);
        imageView.setFitHeight(65);
        boardGrid.add(imageView, col, row);
    }

    private void setEmptyPieceOnBoard(GridPane boardGrid, int row, int col) {
        squares[row][col] = new Square(row, col, null);
        ImageView imageView = new ImageView(new Image("File:pieces/empty.png"));
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(70);
        imageView.setFitHeight(65);
        boardGrid.add(imageView, col, row);
    }
}
