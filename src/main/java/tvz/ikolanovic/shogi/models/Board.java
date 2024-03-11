package tvz.ikolanovic.shogi.models;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.pieces.*;

@Getter
@Setter
public class Board
{
    private static final int SIZE = 9;
    private Square[][] squares;

    public Board()
    {
        this.squares = new Square[9][9];
    }

    public void populateBoard(GridPane boardGrid)
    {
        boardGrid.setAlignment(Pos.CENTER);
        for (int row = 0; row < SIZE; row++)
        {
            this.checkAndBuildMiddleRow(boardGrid, row);
            for (int col = 0; col < SIZE; col++)
            {
                this.checkAndBuildBackRow(boardGrid, row, col);
                this.checkAndBuildFrontRow(boardGrid, row, col);
            }
        }
    }

    private void checkAndBuildMiddleRow(GridPane boardGrid, int row)
    {
        if (row == 1)
        {
            this.setPieceOnBoard(boardGrid, row, 1, new Rook(0, Boolean.TRUE));
            this.setPieceOnBoard(boardGrid, row, 7, new Bishop(0, Boolean.TRUE));
        } else if (row == 7)
        {
            this.setPieceOnBoard(boardGrid, row, 1, new Bishop(0, Boolean.FALSE));
            this.setPieceOnBoard(boardGrid, row, 7, new Rook(0, Boolean.FALSE));
        }
    }

    private void checkAndBuildFrontRow(GridPane boardGrid, int row, int col)
    {
        if (row == 2 || row == 6)
        {
            boolean isOppositePlayer = row == 2 ? Boolean.TRUE : Boolean.FALSE;
            this.setPieceOnBoard(boardGrid, row, col, new Pawn(0, isOppositePlayer));
        }
    }

    private void checkAndBuildBackRow(GridPane boardGrid, int row, int col)
    {
        if (row == 0 || row == 8)
        {
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

    private void setPieceOnBoard(GridPane boardGrid, int row, int col, Piece piece)
    {
        squares[row][col] = new Square(row, col, piece);
        ImageView imageView = new ImageView(new Image("File:pieces/literal/" + piece.getSymbol() + ".png"));
        imageView.setFitWidth(70);
        imageView.setFitHeight(65);
        boardGrid.add(imageView, col, row);
    }


    public Square getSquare(int row, int column) {
        return squares[row][column];
    }
}
