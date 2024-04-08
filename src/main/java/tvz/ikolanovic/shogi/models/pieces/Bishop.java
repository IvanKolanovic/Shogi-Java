package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(int owner, boolean invert)
    {
        super("KA", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, Board board) {
        List<Square> squares = new ArrayList<>();
        // Define the directions: up-right, up-left, down-right, down-left
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int nextX = x + dx;
            int nextY = y + dy;

            while (nextX >= 0 && nextX < 9 && nextY >= 0 && nextY < 9) { // Check board boundaries
                Square targetSquare = board.getSquare(nextX, nextY);
                Piece targetPiece = targetSquare == null ? null : targetSquare.getPiece();

                // If there's no piece, it's a valid move
                if (targetPiece == null) {
                    squares.add(new Square(nextX, nextY, targetPiece));
                } else {
                    // If there's a piece, we can capture it if it's the opponent's piece, but then must stop
                    if (targetPiece.getOwner() != this.getOwner()) {
                        squares.add(new Square(nextX, nextY, targetPiece));
                    }
                    break; // Stop either way: can't jump over pieces
                }

                nextX += dx;
                nextY += dy;
            }
        }

        return squares;
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

