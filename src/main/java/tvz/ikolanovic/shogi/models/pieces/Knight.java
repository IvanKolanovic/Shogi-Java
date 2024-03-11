package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

public class Knight extends Piece
{

    public Knight(int owner, boolean invert)
    {
        super("KE", owner, Boolean.FALSE, invert);
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

        if (getOwner() == 2)
        {
            //Check if moving up two squares
            if (from.getRow() - to.getRow() != 2 || Math.abs(from.getColumn() - to.getColumn()) != 1)
            {
                return false;
            }
        }
        if (getOwner() == 1)
        {
            //Check if moving down two squares
            if (from.getRow() - to.getRow() != -2 || Math.abs(from.getColumn() - to.getColumn()) != 1)
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

}

