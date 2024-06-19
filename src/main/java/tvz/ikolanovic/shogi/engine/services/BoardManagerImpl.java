package tvz.ikolanovic.shogi.engine.services;

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
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.engine.services.interfaces.BoardManager;
import tvz.ikolanovic.shogi.models.Square;
import tvz.ikolanovic.shogi.models.pieces.*;

public class BoardManagerImpl implements BoardManager {

    @Override
    public void populateBoard(GridPane boardGrid) {
        boardGrid.setAlignment(Pos.CENTER);
        for (int row = 0; row < GameEngine.SIZE; row++) {
            checkAndBuildMiddleRow(boardGrid, row);
            for (int col = 0; col < GameEngine.SIZE; col++) {
                checkAndBuildBackRow(boardGrid, row, col);
                checkAndBuildFrontRow(boardGrid, row, col);
            }
        }
        populateAllEmptySquares(boardGrid);
    }

    @Override
    public void checkAndBuildMiddleRow(GridPane boardGrid, int row) {
        if (row == 1) {
            setPieceOnBoard(boardGrid, row, 1, new Rook(1, Boolean.TRUE));
            setPieceOnBoard(boardGrid, row, 7, new Bishop(1, Boolean.TRUE));
        } else if (row == 7) {
            setPieceOnBoard(boardGrid, row, 1, new Bishop(0, Boolean.FALSE));
            setPieceOnBoard(boardGrid, row, 7, new Rook(0, Boolean.FALSE));
        }
    }

    @Override
    public void checkAndBuildFrontRow(GridPane boardGrid, int row, int col) {
        if (row == 2 || row == 6) {
            boolean isOppositePlayer = row == 2 ? Boolean.TRUE : Boolean.FALSE;
            int owner = isOppositePlayer ? 1 : 0;
            setPieceOnBoard(boardGrid, row, col, new Pawn(owner, isOppositePlayer));
        }
    }

    @Override
    public void checkAndBuildBackRow(GridPane boardGrid, int row, int col) {
        if (row == 0 || row == 8) {
            boolean isOppositePlayer = row == 0 ? Boolean.TRUE : Boolean.FALSE;
            int owner = isOppositePlayer ? 1 : 0;

            if (col == 0 || col == 8) setPieceOnBoard(boardGrid, row, col, new Lance(owner, isOppositePlayer));
            else if (col == 1 || col == 7)
                setPieceOnBoard(boardGrid, row, col, new Knight(owner, isOppositePlayer));
            else if (col == 2 || col == 6)
                setPieceOnBoard(boardGrid, row, col, new SilverGeneral(owner, isOppositePlayer));
            else if (col == 3 || col == 5)
                setPieceOnBoard(boardGrid, row, col, new GoldGeneral(owner, isOppositePlayer));
            else setPieceOnBoard(boardGrid, row, col, new King(owner, isOppositePlayer));
        }
    }

    @Override
    public void populateAllEmptySquares(GridPane boardGrid) {
        for (int row = 0; row < GameEngine.SIZE; row++) {
            for (int col = 0; col < GameEngine.SIZE; col++) {
                if (GameEngine.getInstance().getSquares()[row][col] == null) {
                    setEmptyPieceOnBoard(boardGrid, row, col);
                }
            }
        }
    }

    @Override
    public void setPieceOnBoard(GridPane boardGrid, int row, int col, Piece piece) {
        GameEngine.getInstance().getSquares()[row][col] = new Square(row, col, piece);
        ImageView imageView = new ImageView();
        if (piece == null) {
            imageView.setImage(new Image("File:pieces/empty.png"));
        } else {
            imageView.setImage(new Image("File:pieces/literal/" + piece.getSymbol() + ".png"));
        }
        imageView.setFitWidth(70);
        imageView.setFitHeight(65);

        boardGrid.add(imageView, col, row);
    }

    @Override
    public void setEmptyPieceOnBoard(GridPane boardGrid, int row, int col) {
        GameEngine.getInstance().getSquares()[row][col] = new Square(row, col, null);
        ImageView imageView = new ImageView(new Image("File:pieces/empty.png"));
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(70);
        imageView.setFitHeight(65);
        boardGrid.add(imageView, col, row);
    }

    @Override
    public void clearHighlighting(GridPane boardGrid) {
        GameEngine.getInstance().getHighlightedCoordinates().forEach(highlightedCoordinate -> {
            ImageView imageView = (ImageView) getNodeByRowColumnIndex(highlightedCoordinate.row(), highlightedCoordinate.column(), boardGrid);
            imageView.setEffect(new DropShadow(0, Color.RED));
            if (GameEngine.getInstance().getSquares()[highlightedCoordinate.row()][highlightedCoordinate.column()].getPiece() == null)
                imageView.setImage(new Image("File:pieces/empty.png"));
        });
        GameEngine.getInstance().getHighlightedCoordinates().clear();
    }

    @Override
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

    @Override
    public boolean isSquareHighlighted(final int row, final int column) {
        return GameEngine.getInstance().getHighlightedCoordinates().stream().anyMatch(highlightedCoordinate ->
                highlightedCoordinate.row() == row && highlightedCoordinate.column() == column);
    }

    @Override
    public void updateBoard(final int newRow, final int newColumn, GridPane boardGrid, TextArea statOutput) {
        int oldRow = GameEngine.getInstance().getSelectedSquarePiece().getRow();
        int oldColumn = GameEngine.getInstance().getSelectedSquarePiece().getColumn();

        clearHighlighting(boardGrid);
        Square oldSquare = GameEngine.getInstance().getSquares()[oldRow][oldColumn];
        ImageView imageView = (ImageView) getNodeByRowColumnIndex(oldRow,
                oldColumn, boardGrid);
        // remove old
        boardGrid.getChildren().removeIf(node -> !(node instanceof Group)
                && GridPane.getColumnIndex(node) == oldColumn
                && GridPane.getRowIndex(node) == oldRow);
        setEmptyPieceOnBoard(boardGrid, oldRow, oldColumn);
        // write stat
        this.checkMoveType(oldSquare, newRow, newColumn, statOutput);

        if (GameEngine.getInstance().getSquares()[newRow][newColumn].getPiece() == null) {
            // place empty on ol
            setEmptyPieceOnBoard(boardGrid, oldRow, oldColumn);
        } else if (GameEngine.getInstance().getSquares()[newRow][newColumn].getPiece() != null) {
            // remove opponent
            boardGrid.getChildren().removeIf(node -> !(node instanceof Group)
                    && GridPane.getColumnIndex(node) == newColumn
                    && GridPane.getRowIndex(node) == newRow);
        }
        // add new
        boardGrid.add(imageView, newColumn, newRow);
        setPieceOnBoard(boardGrid, newRow, newColumn, oldSquare.getPiece());
        GameEngine.getInstance().setIsOpponentsTurn(!GameEngine.getInstance().getIsOpponentsTurn());
        GameEngine.getInstance().getTimerService().switchTurn();
    }

    private void checkMoveType(Square oldSquare, final int newRow,
                               final int newColumn, TextArea statOutput) {
        Square target = GameEngine.getInstance().getSquares()[newRow][newColumn];
        if (target.getPiece() == null) {
            String txt = String.format("%s%d%d-%d%d%n", oldSquare.getPiece().getAcronym(), oldSquare.getRow(),
                    oldSquare.getColumn(), newRow, newColumn);
            GameEngine.getInstance().getMoveHistory().add(txt);
            statOutput.appendText(txt);
        } else {
            String txt = String.format("%s%d%dx%s%d%d%n", oldSquare.getPiece().getAcronym(), oldSquare.getRow(),
                    oldSquare.getColumn(), target.getPiece().getAcronym(), newRow, newColumn);
            GameEngine.getInstance().getMoveHistory().add(txt);
            statOutput.appendText(txt);
            Label label = (Label) GameEngine.getInstance().getStage().getScene().lookup("#" + target.getPiece().getAcronym() + target.getPiece().getOwner());
            label.setText(String.valueOf(Integer.parseInt(label.getText()) + 1));
        }
    }
}
