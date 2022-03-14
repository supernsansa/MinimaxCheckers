package com.anoc20.minimaxcheckers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameController {

    private boolean playerTurn;
    private CheckerBoard board = new CheckerBoard();

    @FXML
    private GridPane boardPane;
    @FXML
    private TextArea logTextArea;

    public void initialize() {
        // controller available in initialize method
        board.placeStartingPieces();
        drawBoard(board);
    }

    @FXML
    private void drawBoard(CheckerBoard board) {
        logTextArea.appendText("Welcome to Minimax Checkers \n");
        logTextArea.appendText("Good luck! \n");

        boardPane.setStyle("-fx-padding: 0;" +
                "-fx-border-style: solid outside;" +
                "-fx-border-width: 20;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: brown;");

        CheckerTile[][] tiles = board.getBoard();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {

                if (tiles[x][y].isBlack()) {
                    Rectangle blackTile = new Rectangle(50,50);
                    blackTile.setFill(Color.BLACK);
                    boardPane.add(blackTile,x,y);
                    GridPane.setHalignment(blackTile, HPos.CENTER);
                    GridPane.setValignment(blackTile, VPos.CENTER);

                    if (tiles[x][y].getActivePiece() != null) {
                        //If piece is dark
                        if (tiles[x][y].getActivePiece().isDark()) {
                            if (tiles[x][y].getActivePiece().isKing()) {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.RED);
                                //Give king pieces a gold outline
                                checkerPiece.setStroke(Color.GOLD);
                                checkerPiece.setStrokeWidth(5.0);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.RED);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            }
                        }
                        //Otherwise, if piece is white
                        else {
                            if (tiles[x][y].getActivePiece().isKing()) {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.WHITE);
                                //Give king pieces a gold outline
                                checkerPiece.setStroke(Color.GOLD);
                                checkerPiece.setStrokeWidth(5.0);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.WHITE);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            }
                        }
                    }

                }
                else {
                    Rectangle whiteTile = new Rectangle(50,50);
                    whiteTile.setFill(Color.WHEAT);
                    boardPane.add(whiteTile,x,y);
                    GridPane.setHalignment(whiteTile, HPos.CENTER);
                    GridPane.setValignment(whiteTile, VPos.CENTER);
                }
            }
        }
    }
}