package tvz.ikolanovic.shogi.models;

import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.pieces.Piece;

import java.io.Serializable;

/**
 * The type Square.
 */
@Getter
@Setter
public class Square implements Serializable
{

    private int row;
    private int column;
    private Piece piece;

    /**
     * Instantiates a new Square.
     *
     * @param row    the row
     * @param column the column
     * @param piece  the piece
     */
    public Square(int row, int column, Piece piece)
    {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }
}
