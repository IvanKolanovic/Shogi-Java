package tvz.ikolanovic.shogi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PlayerOutPieces implements Serializable {
    @Serial
    private static final long serialVersionUID = 321L;
    private Map<String, String> player1 = new HashMap<>();
    private Map<String, String> player2 = new HashMap<>();
}
