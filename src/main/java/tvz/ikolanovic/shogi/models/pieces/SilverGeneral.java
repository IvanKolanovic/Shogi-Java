package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Silver general.
 */
public class SilverGeneral extends Piece {

    /**
     * Instantiates a new Silver general.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public SilverGeneral(int owner, boolean invert) {
        super("GI", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        List<Square> squares = new ArrayList<>();
        // Define the valid movements for a Silver General in Shogi
        int[][] directions = {
                {1, 0},   // Up (forward)
                {1, 1},   // Up-right (forward-diagonal)
                {1, -1},  // Up-left (forward-diagonal)
                {-1, 1},  // Down-right (backward-diagonal)
                {-1, -1}  // Down-left (backward-diagonal)
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int nextX = x + dx;
            int nextY = y + dy;

            // Check board boundaries
            if (nextX >= 0 && nextX < GameEngine.SIZE && nextY >= 0 && nextY < GameEngine.SIZE) {
                Square targetSquare = gameEngine.getSquare(nextX, nextY);
                if (targetSquare != null) {
                    Piece targetPiece = targetSquare.getPiece();

                    // Check if the square is empty or contains an opponent's piece
                    if (targetPiece == null || targetPiece.getOwner() != this.getOwner()) {
                        squares.add(targetSquare);
                    }
                }
            }
        }

        return squares;
    }

    public String getAcronym() {
        return "S";
    }
}

