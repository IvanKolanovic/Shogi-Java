package tvz.ikolanovic.shogi.engine;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.services.BoardManagerImpl;
import tvz.ikolanovic.shogi.engine.services.SaveAndLoadServiceImpl;
import tvz.ikolanovic.shogi.engine.services.TimerServiceImpl;
import tvz.ikolanovic.shogi.engine.services.interfaces.BoardManager;
import tvz.ikolanovic.shogi.engine.services.interfaces.SaveAndLoadService;
import tvz.ikolanovic.shogi.engine.services.interfaces.TimerService;
import tvz.ikolanovic.shogi.models.PlayerTimer;
import tvz.ikolanovic.shogi.models.Square;
import tvz.ikolanovic.shogi.models.pieces.King;
import tvz.ikolanovic.shogi.models.pieces.Piece;
import tvz.ikolanovic.shogi.models.utils.DialogUtils;
import tvz.ikolanovic.shogi.models.utils.HighlightedCoordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameEngine implements Serializable {
    public static final int SIZE = 9;
    private static GameEngine INSTANCE;
    private Stage stage;
    private Square[][] squares;
    private List<HighlightedCoordinate> highlightedCoordinates;
    private Square selectedSquarePiece;
    private List<String> moveHistory = new ArrayList<>();
    private Boolean gameStarted;
    private Boolean isOpponentsTurn;
    private BoardManager boardManager;
    private TimerService timerService;
    private SaveAndLoadService saveAndLoadService;


    private GameEngine() {
        squares = new Square[9][9];
        this.selectedSquarePiece = new Square(-1, -1, null); // default non-existing square
        this.highlightedCoordinates = new ArrayList<>();
        isOpponentsTurn = Boolean.FALSE;
        gameStarted = false;
        this.boardManager = new BoardManagerImpl();
        this.timerService = new TimerServiceImpl();
        this.saveAndLoadService = new SaveAndLoadServiceImpl();
    }

    public static GameEngine getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameEngine();
        }

        return INSTANCE;
    }

    public void gameLogic(Node clickedNode, GridPane boardGrid, TextArea statOutput, Label p1Timer, Label p2Timer) {
        if (!gameStarted) {
            if (timerService.getPlayer1Timer() != null && timerService.getPlayer2Timer() != null) {
                if (timerService.getPlayer1Timer().isRunning() || timerService.getPlayer2Timer().isRunning()) {
                    timerService.stopTimers();
                }
                return;
            }

            gameStarted = true;
            timerService.setPlayer1Timer(new PlayerTimer(300, p1Timer, "Player 1 Timer"));
            timerService.setPlayer2Timer(new PlayerTimer(300, p2Timer, "Player 2 Timer"));
            timerService.startTimer();
        }

        // click on descendant node
        Integer row = GridPane.getRowIndex(clickedNode);
        Integer column = GridPane.getColumnIndex(clickedNode);
        System.out.println("Row: " + row + " Column: " + column);

        if (canMovePiece(row, column)) {
            if (this.checkIfMyTurn())
                movePieceToSquare(row, column, boardGrid, statOutput);

        } else {
            pieceClicked(row, column, boardGrid);
        }
        if (isCheck()) {
            System.out.println("King is in Check!");
            DialogUtils.showWinningDialog(isOpponentsTurn ? "Player 1" : "Player 2");
            timerService.stopTimers();
        }
    }

    public boolean checkIfMyTurn() {
        if (isOpponentsTurn && this.selectedSquarePiece.getPiece().getOwner() == 0)
            return Boolean.FALSE;
        else if (!isOpponentsTurn && this.selectedSquarePiece.getPiece().getOwner() == 1)
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

    public boolean canMovePiece(int row, int col) {
        boolean isOnHighlighted = this.highlightedCoordinates.stream().anyMatch(highlightedCoordinate ->
                highlightedCoordinate.row() == row && highlightedCoordinate.column() == col);

        return isOnHighlighted
                && selectedSquarePiece.getRow() != -1
                && selectedSquarePiece.getColumn() != -1;
    }

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }


    public void pieceClicked(int x, int y, GridPane boardGrid) {
        Square selectedSquare = this.getSquare(x, y);
        Piece piece = selectedSquare.getPiece();
        if (piece != null) {
            if (piece != selectedSquarePiece.getPiece()) {
                boardManager.clearHighlighting(boardGrid);
            }

            List<Square> possibleMoves = piece.getPossibleMoves(x, y, this);

            for (Square square : possibleMoves) {
                int targetX = square.getRow();
                int targetY = square.getColumn();

                ImageView imageView = (ImageView) getBoardManager().getNodeByRowColumnIndex(targetX, targetY, boardGrid);
                if (imageView == null) {
                    System.out.println("ImageView is null at position: (" + targetX + ", " + targetY + ")");
                    continue;
                }

                // Apply drop shadow effect to the image view
                imageView.setEffect(new DropShadow(20, Color.RED));
                imageView.setImage(new Image("File:pieces/yellow.png"));


                // Add the highlighted coordinate
                highlightedCoordinates.add(new HighlightedCoordinate(targetX, targetY));
            }
            this.setSelectedSquarePiece(selectedSquare);
        }
    }

    public void movePieceToSquare(final int row, final int column, GridPane boardGrid, TextArea statOutput) {
        if (!boardManager.isSquareHighlighted(row, column)) return;

        this.boardManager.updateBoard(row, column, boardGrid, statOutput);
        this.selectedSquarePiece = new Square(-1, -1, null); // default non-existing square
    }

    public boolean isCheck() {
        // Find the king first
        Square kingSquare = findKingSquare();
        if (kingSquare == null) return false; // No king found, something went wrong

        int kingRow = kingSquare.getRow();
        int kingCol = kingSquare.getColumn();
        // Check threats from all pieces of the opposing side
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = squares[row][col].getPiece();
                if (piece != null && piece.isInverted() != isOpponentsTurn) { // Check opposing pieces
                    if (piece.canMoveTo(piece.getPossibleMoves(row, col, this), kingRow, kingCol)) {
                        return true; // King is in check

                    }
                }
            }
        }
        return false;
    }

    private Square findKingSquare() {
        // Assuming king isBlack value matches the turn, modify as per your setup
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Piece piece = squares[row][col].getPiece();
                if (piece instanceof King && piece.isInverted() == isOpponentsTurn) {
                    return squares[row][col];
                }
            }
        }
        return null;
    }

}
