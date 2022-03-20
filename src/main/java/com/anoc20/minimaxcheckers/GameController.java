package com.anoc20.minimaxcheckers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameController {

    private CheckersGame checkersGame;
    private CheckerTile selectedTile;
    private Rectangle[][] tileShapes = new Rectangle[8][8];

    @FXML
    private GridPane boardPane;
    @FXML
    private TextArea logTextArea;
    @FXML
    private TextArea turnIndicator;

    public void initialize() {
        // controller available in initialize method
        logTextArea.appendText("Welcome to Minimax Checkers \n");
        checkersGame = new CheckersGame(true, Mode.MEDIUM);
        changePlayerTurnText();
        drawBoard(checkersGame.getPlayingBoard());
        checkersGame.movePiece(1, 2, 0, 3);
        drawBoard(checkersGame.getPlayingBoard());
        System.out.println("BEFORE CAPTURE IS POSSIBLE");
        System.out.println(checkersGame.availableMoves(PieceColour.DARK));
        System.out.println(checkersGame.availableMoves(PieceColour.WHITE));
        System.out.println("AFTER CAPTURE IS POSSIBLE");
        checkersGame.movePiece(0, 3, 1, 4);
        drawBoard(checkersGame.getPlayingBoard());
        System.out.println(checkersGame.availableMoves(PieceColour.DARK));
        System.out.println(checkersGame.availableMoves(PieceColour.WHITE));
        //checkersGame.takeTurn();
        //changePlayerTurnText();
    }

    @FXML
    private void drawBoard(CheckerBoard board) {

        boardPane.setStyle("-fx-padding: 0;" +
                "-fx-border-style: solid outside;" +
                "-fx-border-width: 20;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: brown;");

        CheckerTile[][] tiles = board.getBoard();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {

                if (tiles[x][y].getColour() == Colour.BLACK) {
                    Rectangle blackTile = new Rectangle(50, 50);
                    blackTile.setFill(Color.BLACK);
                    tileShapes[x][y] = blackTile;
                    boardPane.add(blackTile, x, y);
                    GridPane.setHalignment(blackTile, HPos.CENTER);
                    GridPane.setValignment(blackTile, VPos.CENTER);

                    if (tiles[x][y].getActivePiece() != null) {
                        //If piece is dark
                        if (tiles[x][y].getActivePiece().getPieceColour() == PieceColour.DARK) {
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

                } else {
                    Rectangle whiteTile = new Rectangle(50, 50);
                    whiteTile.setFill(Color.WHEAT);
                    tileShapes[x][y] = whiteTile;
                    boardPane.add(whiteTile, x, y);
                    GridPane.setHalignment(whiteTile, HPos.CENTER);
                    GridPane.setValignment(whiteTile, VPos.CENTER);
                }
            }
        }
    }

    @FXML
    private void changePlayerTurnText() {
        if (checkersGame.isPlayerTurn()) {
            turnIndicator.setText("It's Your Turn");
        } else {
            turnIndicator.setText("It's the AI's Turn");
        }
    }

    public void clickOnGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        //If user clicks on the checkers board...
        if (clickedNode != boardPane) {
            //Get the column and row index of what part of the board was clicked
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Clicked Cell: X = " + colIndex + ", Y = " + rowIndex);

            //If no tile is selected
            if (selectedTile == null && checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex].getActivePiece().getPieceColour() == checkersGame.getPlayerColour()
                    && checkersGame.isPlayerTurn() == true) {
                //The clicked tile becomes the selected tile (provided that it currently holds one of the player's pieces)
                selectedTile = checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex];
                //If it is the player's turn and they click on a tile containing one of their pieces, highlight the tile and the available tiles it can move to
                System.out.println("It is the player's turn");
                System.out.println("Valid tile selected");
                //Give selected tile a gold border
                tileShapes[colIndex][rowIndex].setStroke(Color.GOLD);
                tileShapes[colIndex][rowIndex].setStrokeWidth(5.0);
                ArrayList<Move> moves = checkersGame.availableMoves(checkersGame.getPlayerColour());

                for (Move move : moves) {
                    if (move.getIndexO() == selectedTile.getIndex()) {
                        //Highlight relevant tiles
                        CheckerTile tile = checkersGame.getPlayingBoard().getTileByIndex(move.getIndexD());
                        tileShapes[tile.getX()][tile.getY()].setStroke(Color.GOLD);
                        tileShapes[tile.getX()][tile.getY()].setStrokeWidth(5.0);
                    }
                }
            }
            //Alternatively, deselect tile if the user clicks the already selected tile
            else if (checkersGame.isPlayerTurn() && selectedTile != null) {
                if(selectedTile.getX() == colIndex && selectedTile.getY() == rowIndex) {
                    selectedTile = null;
                    //Remove the borders from formerly highlighted tiles
                    for (int y = 0; y < 8; y++) {
                        for (int x = 0; x < 8; x++) {
                            tileShapes[x][y].setStrokeWidth(0);
                        }
                    }
                    return;
                }
            }
            //Otherwise, assume the user is attempting to make a move
            else {
                //TODO attempt to make a move, then validate by checking if said move is possible
            }
        }
    }
}