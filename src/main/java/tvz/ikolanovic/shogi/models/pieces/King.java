package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

public class King extends Piece
{

    public King(int owner, boolean invert)
    {
        super("GY", owner, Boolean.FALSE, invert);
    }


    public boolean canMove(Square from, Square to, Board b)
    {
        if (to.getPiece() != null)
        {
            if (from.getPiece().getOwner() == to.getPiece().getOwner())
            {
                return false;
            }
        }

        return (Math.abs(from.getColumn() - to.getColumn()) <= 1) &&
                Math.abs(from.getRow() - to.getRow()) <= 1;
    }
}

