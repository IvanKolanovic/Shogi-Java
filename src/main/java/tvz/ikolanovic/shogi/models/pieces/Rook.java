package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Rook.
 */
public class Rook extends Piece {

    /**
     * Instantiates a new Rook.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public Rook(int owner, boolean invert) {
        super("HI", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, Board board) {
        List<Square> squares = new ArrayList<>();
        // The Rook can move in four directions: up, down, left, right
        int[][] directions = {
                {1, 0},   // Move up
                {-1, 0},  // Move down
                {0, 1},   // Move right
                {0, -1}   // Move left
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int nextX = x + dx;
            int nextY = y + dy;

            // Continue in each direction until an obstacle is encountered
            while (nextX >= 0 && nextX < Board.SIZE && nextY >= 0 && nextY < Board.SIZE) {
                Square targetSquare = board.getSquare(nextX, nextY);
                if (targetSquare != null) {
                    Piece targetPiece = targetSquare.getPiece();

                    // If the square is empty, add as a possible move
                    if (targetPiece == null) {
                        squares.add(targetSquare);
                    } else {
                        // If it's an opponent's piece, add as a possible move and stop
                        if (targetPiece.getOwner() != this.getOwner()) {
                            squares.add(targetSquare);
                        }
                        break; // Stop moving in this direction upon encountering any piece
                    }
                }
                nextX += dx;
                nextY += dy;
            }
        }

        return squares;
    }

    public String getAcronym() {
        return "R";
    }

}

