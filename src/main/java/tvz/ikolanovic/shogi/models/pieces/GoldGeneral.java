package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Gold general.
 */
public class GoldGeneral extends Piece {

    /**
     * Instantiates a new Gold general.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public GoldGeneral(int owner, boolean invert) {
        super("KI", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        List<Square> squares = new ArrayList<>();
        // Define the valid movements for a Gold General in Shogi
        int[][] directions = {
                {1, 0},   // Up
                {-1, 0},  // Down
                {0, 1},   // Right
                {0, -1},  // Left
                {1, 1},   // Up-right
                {1, -1}   // Up-left
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
        return "G";
    }

}

