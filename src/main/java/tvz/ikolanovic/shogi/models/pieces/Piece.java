package tvz.ikolanovic.shogi.models.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.models.Square;

import java.io.Serializable;
import java.util.List;

/**
 * The type Piece.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Piece implements Serializable {
    private String symbol;
    private int owner;
    private boolean promoted;
    private boolean inverted;

    /**
     * Gets possible moves.
     *
     * @param x     the x
     * @param y     the y
     * @param gameEngine the board
     * @return the possible moves
     */
// Define a method to get possible moves, to be overridden by subclasses
    public List<Square> getPossibleMoves(int x, int y, GameEngine gameEngine) {
        return null;
    }

    /**
     * Promote.
     */
    public void promote() {
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        if (inverted)
            return 1 + symbol;
        else
            return 0 + symbol;
    }

    /**
     * Get acronym string.
     *
     * @return the string
     */
    public String getAcronym() {
        return null;
    }

    /**
     * Demote.
     */
    public void demote() {
    }

    public boolean canMoveTo(List<Square> possibleMoves, int kingRow, int kingCol) {
        return possibleMoves.stream().anyMatch(move -> move.getRow() == kingRow && move.getColumn() == kingCol);
    }
}
