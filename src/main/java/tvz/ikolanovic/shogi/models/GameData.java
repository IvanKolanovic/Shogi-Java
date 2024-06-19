package tvz.ikolanovic.shogi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GameData implements Serializable {
    @Serial
    private static final long serialVersionUID = 123L;
    private Square[][] gameState;
    private List<String> moveHistory = new ArrayList<>();
    private Boolean isOpponentsTurn;
    private PlayerOutPieces playerOutPieces;
    private int player1Timer;
    private int player2Timer;
}
