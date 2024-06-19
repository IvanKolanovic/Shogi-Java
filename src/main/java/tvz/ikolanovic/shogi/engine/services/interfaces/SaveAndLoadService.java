package tvz.ikolanovic.shogi.engine.services.interfaces;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import tvz.ikolanovic.shogi.models.PlayerOutPieces;

import java.util.Arrays;
import java.util.List;

public interface SaveAndLoadService {
    List<String> pieceAcronyms = Arrays.asList(
            "B",
            "G",
            "N",
            "L",
            "P",
            "R",
            "S"
    );

    PlayerOutPieces getPlayerOutPieces();

    void saveGame();

    void loadGame(GridPane boardGrid, TextArea statOutput, Label p1Timer, Label p2Timer);
}
