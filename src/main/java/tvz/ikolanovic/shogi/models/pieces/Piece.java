package tvz.ikolanovic.shogi.models.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.BoardSquare;

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


    public boolean canMove(BoardSquare from, BoardSquare to, Board b)
    {
        return false;
    }

    public void promote()
    {
        this.promoted = true;
        //If a piece is "double promoted," don't add another "!" mark.
        if (!this.symbol.substring(this.symbol.length() - 1).equals("!"))
        {
            this.symbol = this.symbol += "!";
        }
    }

    public void demote()
    {
        this.promoted = false;
        this.symbol = this.symbol.split("!")[0];
    }
}
