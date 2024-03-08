package tvz.ikolanovic.shogi.models;

import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.pieces.Piece;

@Getter
@Setter
public class BoardSquare
{

    private int r;
    private int c;
    private Piece p;

    public BoardSquare(int r, int c)
    {
        this.r = r;
        this.c = c;
    }
}
