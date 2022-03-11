package com.anoc20.minimaxcheckers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameController {


    @FXML
    private GridPane boardPane;

    public void initialize() {
        // controller available in initialize method
        setTiles();
    }

    @FXML
    private void setTiles() {

    }
}