package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Knight.
 */
public class Knight extends Piece {

    /**
     * Instantiates a new Knight.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public Knight(int owner, boolean invert) {
        super("KE", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        List<Square> squares = new ArrayList<>();

        // Determine the forward direction based on the piece's side
        int direction = this.isInverted() ? 1 : -1; // Assuming white pieces move "up" and black pieces move "down"

        // Calculate the two potential moves
        int[][] potentialMoves = {
                {2 * direction, -1}, // Two forward, one left
                {2 * direction, 1},  // Two forward, one right
        };

        for (int[] move : potentialMoves) {
            int nextX = x + move[0];
            int nextY = y + move[1];

            // Ensure the move is within the board boundaries
            if (nextX >= 0 && nextX < 9 && nextY >= 0 && nextY < 9) {
                Square targetSquare = gameEngine.getSquare(nextX, nextY);
                Piece targetPiece = targetSquare == null ? null : targetSquare.getPiece();

                // The square is either empty or contains an opponent's piece (capture)
                if (targetPiece == null || targetPiece.getOwner() != this.getOwner()) {
                    squares.add(new Square(nextX, nextY, targetPiece));
                }
            }
        }

        return squares;
    }

    public String getAcronym() {
        return "N";
    }
}

