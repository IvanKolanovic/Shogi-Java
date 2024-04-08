package tvz.ikolanovic.shogi.models.pieces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Piece {
    private String symbol;
    private int owner;
    private boolean promoted;
    private boolean inverted;

    // Define a method to get possible moves, to be overridden by subclasses
    public List<Square> getPossibleMoves(int x, int y, Board board) {
        return null;
    }

    public void promote() {
    }

    public String getSymbol() {
        if (inverted)
            return 1 + symbol;
        else
            return 0 + symbol;
    }

    public void demote() {
    }
}
