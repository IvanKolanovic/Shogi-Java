package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

public class Lance extends Piece
{

    public Lance(int owner, boolean invert)
    {
        super("KY", owner, Boolean.FALSE, invert);
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

        if (from.getColumn() == to.getColumn())
        {
            if (getOwner() == 2)
            {
                //Check for collision and row violation if moving up
                if (from.getRow() - to.getRow() > 0)
                {
                    //Check squares between old and new position for pieces
                    for (int i = from.getRow() - 1; i > to.getRow(); i--)
                    {
                        if (b.getSquare(i, from.getColumn()).getPiece() != null)
                        {
                            return false;
                        }
                    }
                } else
                {
                    return false;
                }
            }
            if (getOwner() == 1)
            {
                //Check for collision and row violation if moving down
                if (from.getRow() - to.getRow() < 0 && from.getColumn() == to.getColumn())
                {
                    //Check squares between old and new position for pieces
                    for (int i = from.getRow() + 1; i < to.getRow(); i++)
                    {
                        if (b.getSquare(i, from.getColumn()).getPiece() != null)
                        {
                            return false;
                        }
                    }
                } else
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

