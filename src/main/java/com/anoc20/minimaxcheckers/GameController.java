package com.anoc20.minimaxcheckers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameController {

    private CheckerBoard board = new CheckerBoard();
    @FXML
    private GridPane boardPane;

    public void initialize() {
        // controller available in initialize method
        drawBoard();
    }

    @FXML
    private void drawBoard() {
        CheckerTile[][] tiles = board.getBoard();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if(tiles[x][y] != null) {
                    if(tiles[x][y].isBlack()) {
                        //TODO put black rectangle into gridpane
                        if(tiles[x][y].getActivePiece() != null) {
                            //If piece is dark
                            if(tiles[x][y].getActivePiece().isDark()) {
                                if(tiles[x][y].getActivePiece().isKing()) {
                                    //TODO place king marker
                                }
                                else {
                                    Circle checkerPiece = new Circle(x,y,20);
                                    checkerPiece.setFill(Color.RED);
                                    checkerPiece.toFront();
                                    boardPane.add(checkerPiece,x,y);
                                }
                            }
                            //Otherwise, if piece is white
                            else {
                                if(tiles[x][y].getActivePiece().isKing()) {
                                    //TODO place king marker
                                }
                                else {
                                    Circle checkerPiece = new Circle(x,y,20);
                                    checkerPiece.setFill(Color.WHITE);
                                    checkerPiece.toFront();
                                    boardPane.add(checkerPiece,x,y);
                                }
                            }
                        }
                    }
                    else {
                        //TODO place white rectangle into gridpane
                    }
                }
                else {
                    //System.out.print("- ");
                    continue;
                }
            }
        }
    }

}