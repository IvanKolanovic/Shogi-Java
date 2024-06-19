package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Bishop.
 */
public class Bishop extends Piece {
    /**
     * Instantiates a new Bishop.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public Bishop(int owner, boolean invert) {
        super("KA", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        List<Square> squares = new ArrayList<>();
        // Define the directions: up-right, up-left, down-right, down-left
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int nextX = x + dx;
            int nextY = y + dy;

            while (nextX >= 0 && nextX < 9 && nextY >= 0 && nextY < 9) { // Check board boundaries
                Square targetSquare = gameEngine.getSquare(nextX, nextY);
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

    public String getAcronym() {
        return "B";
    }

}

