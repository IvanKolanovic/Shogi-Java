package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Lance.
 */
public class Lance extends Piece {

    /**
     * Instantiates a new Lance.
     *
     * @param owner  the owner
     * @param invert the invert
     */
    public Lance(int owner, boolean invert) {
        super("KY", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        List<Square> squares = new ArrayList<>();
        // The Lance moves forward only
        int directionX = 1;  // Assuming the piece moves up the board. Flip the direction if it moves down.

        int nextX = x + directionX;

        // Continue moving forward until hitting the edge of the board or an obstacle
        while (nextX >= 0 && nextX < GameEngine.SIZE) {
            Square targetSquare = gameEngine.getSquare(nextX, y);
            if (targetSquare != null) {
                Piece targetPiece = targetSquare.getPiece();

                // Check if the square is empty or contains an opponent's piece
                if (targetPiece == null) {
                    squares.add(targetSquare);
                } else {
                    // Check if it's an opponent's piece to capture it, otherwise stop
                    if (targetPiece.getOwner() != this.getOwner()) {
                        squares.add(targetSquare);
                    }
                    break; // Stop moving after meeting any piece
                }
            }
            nextX += directionX;
        }

        return squares;
    }

    public String getAcronym() {
        return "L";
    }
}

