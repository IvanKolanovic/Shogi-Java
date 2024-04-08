package tvz.ikolanovic.shogi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import tvz.ikolanovic.shogi.engine.ShogiGameEngine;

@Getter
@Setter
public class BoardController
{
    @FXML
    private GridPane boardGrid;

    @FXML
    public void initialize()
    {
        ShogiGameEngine.getInstance().getGameBoard().populateBoard(boardGrid);
    }

    @FXML
    public void mouseCellClicked(MouseEvent event)
    {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != boardGrid)
        {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            ShogiGameEngine.getInstance().getGameBoard().pieceClicked(rowIndex,colIndex,boardGrid);
            System.out.println("Row= " + rowIndex + " Column: " + colIndex);
        }
    }

    @FXML
    public void sendMessage()
    {
    }
}
