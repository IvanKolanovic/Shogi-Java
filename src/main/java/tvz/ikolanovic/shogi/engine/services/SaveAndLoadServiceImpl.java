package tvz.ikolanovic.shogi.engine.services;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import tvz.ikolanovic.shogi.engine.GameEngine;
import tvz.ikolanovic.shogi.engine.services.interfaces.SaveAndLoadService;
import tvz.ikolanovic.shogi.models.GameData;
import tvz.ikolanovic.shogi.models.PlayerOutPieces;
import tvz.ikolanovic.shogi.models.PlayerTimer;
import tvz.ikolanovic.shogi.models.Square;
import tvz.ikolanovic.shogi.models.utils.DialogUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveAndLoadServiceImpl implements SaveAndLoadService {

    @Override
    public PlayerOutPieces getPlayerOutPieces() {
        Map<String, String> player1 = new HashMap<>();
        Map<String, String> player2 = new HashMap<>();

        for (String pieceAcronym : pieceAcronyms) {
            String id = "#" + pieceAcronym;

            Label p1 = (Label) GameEngine.getInstance().getStage().getScene().lookup(id + 0);
            Label p2 = (Label) GameEngine.getInstance().getStage().getScene().lookup(id + 1);

            if (p1 == null) {
                player1.put(id + 0, "0");
                continue;
            }
            if (p2 == null) {
                player2.put(id + 1, "0");
                continue;
            }
            player1.put(id + 0, p1.getText());
            player2.put(id + 1, p2.getText());
        }

        return new PlayerOutPieces(player1, player2);
    }

    @Override
    public void saveGame() {
        GameEngine.getInstance().getTimerService().stopTimers();
        GameData gameData = new GameData(GameEngine.getInstance().getSquares(), GameEngine.getInstance().getMoveHistory(), GameEngine.getInstance().getIsOpponentsTurn(),
                getPlayerOutPieces(), GameEngine.getInstance().getTimerService().getPlayer1Timer().getTimeLeft(),
                GameEngine.getInstance().getTimerService().getPlayer2Timer().getTimeLeft());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveGame/gameSave.dat"))) {
            oos.writeObject(gameData);
            DialogUtils.showSuccessDialog("Game was successfully saved!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void loadGame(GridPane boardGrid, TextArea statOutput, Label p1Timer, Label p2Timer) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveGame/gameSave.dat"))) {
            GameData gameData = (GameData) ois.readObject();
            Square[][] squares = gameData.getGameState();

            boardGrid.getChildren().clear();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    GameEngine.getInstance().getBoardManager().setEmptyPieceOnBoard(boardGrid, i, j
                    );
                    GameEngine.getInstance().getBoardManager().setPieceOnBoard(boardGrid, i, j, squares[i][j].getPiece()
                    );
                }
            }

            gameData.getMoveHistory().forEach(statOutput::appendText);
            GameEngine.getInstance().setMoveHistory(gameData.getMoveHistory());
            GameEngine.getInstance().setIsOpponentsTurn(gameData.getIsOpponentsTurn());
            GameEngine.getInstance().setGameStarted(true);
            PlayerOutPieces playerOutPieces = gameData.getPlayerOutPieces();

            playerOutPieces.getPlayer1().forEach((key, value) -> {
                Label label = (Label) GameEngine.getInstance().getStage().getScene().lookup(key);
                label.setText(value);
            });
            playerOutPieces.getPlayer2().forEach((key, value) -> {
                Label label = (Label) GameEngine.getInstance().getStage().getScene().lookup(key);
                label.setText(value);
            });

            GameEngine.getInstance().getTimerService().setPlayer1Timer(new PlayerTimer(gameData.getPlayer1Timer(), p1Timer, "Player 1 Timer"));
            GameEngine.getInstance().getTimerService().setPlayer2Timer(new PlayerTimer(gameData.getPlayer2Timer(), p2Timer, "Player 2 Timer"));
            GameEngine.getInstance().getTimerService().startTimer();
            GameEngine.getInstance().getTimerService().switchTurn();

            DialogUtils.showSuccessDialog("Game was successfully loaded!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
