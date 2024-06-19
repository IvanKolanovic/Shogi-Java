package tvz.ikolanovic.shogi.models;

import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.pieces.Piece;

import java.io.Serializable;


@Getter
@Setter
public class Square implements Serializable {

    private int row;
    private int column;
    private Piece piece;

    public Square(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }
}
