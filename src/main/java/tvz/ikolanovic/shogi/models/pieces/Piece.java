package tvz.ikolanovic.shogi.models.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

import java.util.List;

/**
 * The type Piece.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Piece {
    private String symbol;
    private int owner;
    private boolean promoted;
    private boolean inverted;

    /**
     * Gets possible moves.
     *
     * @param x     the x
     * @param y     the y
     * @param board the board
     * @return the possible moves
     */
// Define a method to get possible moves, to be overridden by subclasses
    public List<Square> getPossibleMoves(int x, int y, Board board) {
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
    public String getAcronym(){
        return null;
    }

    /**
     * Demote.
     */
    public void demote() {
    }
}
