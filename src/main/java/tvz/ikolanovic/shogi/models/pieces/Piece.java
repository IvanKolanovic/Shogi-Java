package tvz.ikolanovic.shogi.models.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Piece
{
    private String symbol;
    private int owner;
    private boolean promoted;
    private boolean inverted;


    public boolean canMove(Square from, Square to, Board b)
    {
        return false;
    }

    public void promote()
    {
    }

    public String getSymbol()
    {
        if (inverted)
            return 1 + symbol;
        else
            return 0 + symbol;
    }

    public void demote()
    {
    }
}
