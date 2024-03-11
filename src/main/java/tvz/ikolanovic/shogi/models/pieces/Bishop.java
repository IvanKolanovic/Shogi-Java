package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

public class Bishop extends Piece
{
    public Bishop(int owner, boolean invert)
    {
        super("KA", owner, Boolean.FALSE, invert);
    }


    public boolean canMove(Square from, Square to, Board b)
    {

        if (this.isPromoted())
        {
            if ((Math.abs(from.getColumn() - to.getColumn()) <= 1) &&
                    Math.abs(from.getRow() - to.getRow()) <= 1)
            {
                return true;
            }
        }

        if (Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getColumn() - to.getColumn()))
        {
            //Check if moving left or right, up or down
            int dirC = to.getColumn() > from.getColumn() ? 1 : -1;
            int dirR = to.getRow() > from.getRow() ? 1 : -1;
            for (int i = 1; i < Math.abs(to.getColumn() - from.getColumn()); i++)
            {
                if (b.getSquare(from.getRow() + i * dirR, from.getColumn() + i * dirC).getPiece() != null)
                {
                    return false;
                }
            }

            if (to.getPiece() != null)
            {
                return from.getPiece().getOwner() != to.getPiece().getOwner();
            }
            return true;
        }
        return false;
    }

}

