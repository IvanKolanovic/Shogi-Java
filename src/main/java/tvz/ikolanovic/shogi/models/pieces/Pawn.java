package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

public class Pawn extends Piece
{

    public Pawn(int owner, boolean invert)
    {
        super("FU", owner, Boolean.FALSE, invert);
    }

    public boolean canMove(Square from, Square to, Board b)
    {
        if (isPromoted())
        {
            //Gold movement code
            if ((Math.abs(from.getRow() - to.getRow()) <= 1 &&
                    (Math.abs(from.getColumn() - to.getColumn()) <= 1)))
            {
                if (getOwner() == 1)
                {
                    //If Piece is moving backwards check for diagonal
                    if (from.getRow() - to.getRow() == 1)
                    {
                        if (from.getColumn() != to.getColumn())
                        {
                            return false;
                        }
                    }
                } else if (getOwner() == 2)
                {
                    //If Piece is moving backwards check for diagonal
                    if (from.getRow() - to.getRow() == -1)
                    {
                        if (from.getColumn() != to.getColumn())
                        {
                            return false;
                        }
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

        //Pawn Basic Move
        if (getOwner() == 1 && from.getRow() - to.getRow() == -1 ||
                getOwner() == 2 && from.getRow() - to.getRow() == 1)
        {
            if (from.getColumn() == to.getColumn())
            {
                if (to.getPiece() != null)
                {
                    return from.getPiece().getOwner() != to.getPiece().getOwner();
                }
                return true;
            }
        }

        return false;
    }
}
