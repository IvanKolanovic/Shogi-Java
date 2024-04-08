package tvz.ikolanovic.shogi.models.pieces;

import tvz.ikolanovic.shogi.models.Board;
import tvz.ikolanovic.shogi.models.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(int owner, boolean invert) {
        super("FU", owner, Boolean.FALSE, invert);
    }

    @Override
    public List<Square> getPossibleMoves(int x, int y, Board board) {
        List<Square> squares = new ArrayList<>();

        // Determine the direction based on the piece's side
        int direction = this.isInverted() ? 1 : -1; // Assuming white pieces move "up" and black pieces move "down"

        int targetX = x + direction;

        // Check if the move is within the board and the target cell is empty or contains an opponent's piece
        if (targetX >= 0 && targetX < 9) { // Assuming 0-indexed board
            Square targetSquare = board.getSquare(targetX, y);
            Piece targetPiece = targetSquare == null ? null : targetSquare.getPiece();
            if (targetPiece == null || targetPiece.getOwner() != this.getOwner()) { // Can move or capture
                squares.add(new Square(targetX, y, targetPiece));
            }
        }

        return squares;
    }
}
