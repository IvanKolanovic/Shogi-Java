package tvz.ikolanovic.shogi.models;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.pieces.*;
import tvz.ikolanovic.shogi.models.utils.HighlightedCoordinate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Board {
    private static final int SIZE = 9;
    private Square[][] squares;
    private Square lastSelectedSquare;
    private List<HighlightedCoordinate> highlightedCoordinates;

    public Board() {
        this.squares = new Square[9][9];
        this.lastSelectedSquare = new Square(-1,-1,null); // default non-existing square
        this.highlightedCoordinates = new ArrayList<>();
    }

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
            this.setPieceOnBoard(boardGrid, row, 1, new Rook(0, Boolean.TRUE));
            this.setPieceOnBoard(boardGrid, row, 7, new Bishop(0, Boolean.TRUE));
        } else if (row == 7) {
            this.setPieceOnBoard(boardGrid, row, 1, new Bishop(0, Boolean.FALSE));
            this.setPieceOnBoard(boardGrid, row, 7, new Rook(0, Boolean.FALSE));
        }
    }

    private void checkAndBuildFrontRow(GridPane boardGrid, int row, int col) {
        if (row == 2 || row == 6) {
            boolean isOppositePlayer = row == 2 ? Boolean.TRUE : Boolean.FALSE;
            this.setPieceOnBoard(boardGrid, row, col, new Pawn(0, isOppositePlayer));
        }
    }

    private void checkAndBuildBackRow(GridPane boardGrid, int row, int col) {
        if (row == 0 || row == 8) {
            boolean isOppositePlayer = row == 0 ? Boolean.TRUE : Boolean.FALSE;

            if (col == 0 || col == 8)
                this.setPieceOnBoard(boardGrid, row, col, new Lance(0, isOppositePlayer));
            else if (col == 1 || col == 7)
                this.setPieceOnBoard(boardGrid, row, col, new Knight(0, isOppositePlayer));
            else if (col == 2 || col == 6)
                this.setPieceOnBoard(boardGrid, row, col, new SilverGeneral(0, isOppositePlayer));
            else if (col == 3 || col == 5)
                this.setPieceOnBoard(boardGrid, row, col, new GoldGeneral(0, isOppositePlayer));
            else
                this.setPieceOnBoard(boardGrid, row, col, new King(0, isOppositePlayer));
        }
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

    public void pieceClicked(int x, int y, GridPane boardGrid) {
        Piece piece = this.getSquare(x, y).getPiece();
        if (piece != null) {
            if (piece != lastSelectedSquare.getPiece())
                this.clearHighlighting(boardGrid);


            List<Square> possibleMoves = piece.getPossibleMoves(x, y, this); // You need to implement getPossibleMoves

            for (Square square : possibleMoves) {
                int targetX = square.getRow();
                int targetY = square.getColumn();

                // Find the button or cell in your GridPane corresponding to this position
                ImageView imageView = (ImageView) getNodeByRowColumnIndex(targetX, targetY, boardGrid);
                imageView.setImage(new Image("File:pieces/yellow.png"));
                highlightedCoordinates.add(new HighlightedCoordinate(targetX,targetY));
            }
        }
    }

    // Helper method to get a node by row and column
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
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

    public void clearHighlighting(GridPane boardGrid) {
        highlightedCoordinates.forEach(highlightedCoordinate -> {
            ImageView imageView = (ImageView) getNodeByRowColumnIndex(highlightedCoordinate.row(), highlightedCoordinate.column(), boardGrid);
            imageView.setImage(new Image("File:pieces/empty.png"));
        });
        highlightedCoordinates.clear();
    }


}
