package com.anoc20.minimaxcheckers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

//TODO More clear dialogs that explain if the problem is an invalid move or forced capture
//TODO force player to make a move each turn
//TODO Close game or offer rematch when match ends
//TODO Main menu
//TODO Toggleable available move outlines
public class GameController {

    private CheckersGame checkersGame;
    private CheckerTile selectedTile;
    private Rectangle[][] tileShapes = new Rectangle[8][8];
    private ArrayList<Circle> pieceShapes = new ArrayList<Circle>();
    private int multiLegIndex;

    @FXML
    private GridPane boardPane;
    @FXML
    private TextArea logTextArea;
    @FXML
    private TextArea turnIndicator;

    public void initialize() {
        // controller available in initialize method
        logTextArea.appendText("Welcome to Minimax Checkers \n");
        checkersGame = new CheckersGame(true, Mode.EASY);
        //If player lets AI go first, AI should make the first move
        if(checkersGame.isPlayerTurn() == false) {
            try {
                takeTurn();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            changePlayerTurnText();
        }
        drawBoard(checkersGame.getPlayingBoard());
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
                                pieceShapes.add(checkerPiece);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.RED);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                pieceShapes.add(checkerPiece);
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
                                pieceShapes.add(checkerPiece);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.WHITE);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                pieceShapes.add(checkerPiece);
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

    //This method handles updating the GUI when a checker piece is moved
    @FXML
    private void updatePieceLocations() {
        //First, remove all checker pieces from the board
        boardPane.getChildren().removeAll(pieceShapes);

        //Now, redraw all the checker pieces
        CheckerTile[][] tiles = checkersGame.getPlayingBoard().getBoard();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[x][y].getColour() == Colour.BLACK) {
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
                                pieceShapes.add(checkerPiece);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.RED);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                pieceShapes.add(checkerPiece);
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
                                pieceShapes.add(checkerPiece);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            } else {
                                Circle checkerPiece = new Circle(x, y, 21);
                                checkerPiece.setFill(Color.WHITE);
                                checkerPiece.toFront();
                                boardPane.add(checkerPiece, x, y);
                                pieceShapes.add(checkerPiece);
                                GridPane.setHalignment(checkerPiece, HPos.CENTER);
                                GridPane.setValignment(checkerPiece, VPos.CENTER);
                            }
                        }
                    }
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

    //This method handles all game actions involving the user clicking on the checkers board
    public void clickOnGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        //If user clicks on the checkers board...
        if (clickedNode != boardPane) {
            //Get the column and row index of what part of the board was clicked
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Clicked Cell: X = " + colIndex + ", Y = " + rowIndex);

            try {
                //If no tile is selected
                if (selectedTile == null && checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex].getActivePiece().getPieceColour() == checkersGame.getPlayerColour()
                        && checkersGame.isPlayerTurn() == true) {
                    //The clicked tile becomes the selected tile (provided that it currently holds one of the player's pieces)
                    selectedTile = checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex];
                    //If it is the player's turn and they click on a tile containing one of their pieces, highlight the tile and the available tiles it can move to
                    System.out.println("Selected Tile: " + selectedTile.getIndex());
                    //Give selected tile a gold border
                    tileShapes[colIndex][rowIndex].setStroke(Color.LIME);
                    tileShapes[colIndex][rowIndex].setStrokeWidth(5.0);
                    ArrayList<Move> moves = checkersGame.availableCaptures(checkersGame.getPlayerColour());

                    if(moves.size() == 0) {
                        moves = checkersGame.availableMoves(checkersGame.getPlayerColour());
                    }

                    for (Move move : moves) {
                        if (move.getIndexOrigin() == selectedTile.getIndex()) {
                            //Highlight relevant tiles
                            CheckerTile tile = checkersGame.getPlayingBoard().getTileByIndex(move.getIndexDest());
                            tileShapes[tile.getX()][tile.getY()].setStroke(Color.LIME);
                            tileShapes[tile.getX()][tile.getY()].setStrokeWidth(5.0);
                        }
                    }
                }
                //Alternatively, deselect tile if the user clicks the already selected tile
                else if (checkersGame.isPlayerTurn() && selectedTile != null && selectedTile.getX() == colIndex
                        && selectedTile.getY() == rowIndex) {
                    selectedTile = null;
                    //Remove the borders from formerly highlighted tiles
                    for (int y = 0; y < 8; y++) {
                        for (int x = 0; x < 8; x++) {
                            tileShapes[x][y].setStrokeWidth(0);
                        }
                    }
                    return;
                }
                //Otherwise, assume the user is attempting to make a move
                else {
                    System.out.println("Attempting a move");

                    //If user has not made any moves yet
                    if (checkersGame.getMovesMade() == 0) {
                        //First check if there are any captures the user needs to make
                        ArrayList<Move> moves = checkersGame.availableCaptures(checkersGame.getPlayerColour());
                        //If no captures are possible, user is free to make other types of move
                        if(moves.size() == 0) {
                            moves = checkersGame.availableMoves(checkersGame.getPlayerColour());
                        }

                        //Compare the index of the selected tile and the next clicked tile
                        int destinationIndex = checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex].getIndex();
                        boolean moveFound = false;

                        //Find corresponding move
                        for (Move move : moves) {
                            if (move.getIndexOrigin() == selectedTile.getIndex() && move.getIndexDest() == destinationIndex) {
                                System.out.println("Move found");
                                //Get and execute move
                                checkersGame.executeMove(move);
                                //If it was a capture, save the new index of the capturing piece (needed for multi-leg capture moves)
                                if(move.getMoveType() == MoveType.CAPTURE) {
                                    multiLegIndex = move.getIndexDest();
                                }
                                clearTileBorders();
                                updatePieceLocations();
                                selectedTile = null;
                                moveFound = true;
                                break;
                            }
                        }
                        //If no valid move is found
                        if (moveFound == false) {
                            System.out.println("No valid move found");

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Move");
                            alert.setContentText("You have attempted an invalid move. Try something else.");
                            alert.show();
                            selectedTile = null;
                            clearTileBorders();
                        }
                    }
                    //If user has already made a move, check if multi leg is possible, otherwise throw alert telling user to finish their turn
                    else if(checkersGame.isMultiCap()) {
                        ArrayList<Move> multiLegCaps = checkersGame.getCapturesByIndex(checkersGame.getPlayerColour(),multiLegIndex);

                        //Compare the index of the selected tile and the next clicked tile
                        int destinationIndex = checkersGame.getPlayingBoard().getBoard()[colIndex][rowIndex].getIndex();
                        boolean moveFound = false;

                        //Find corresponding move
                        for (Move move : multiLegCaps) {
                            if (move.getIndexOrigin() == selectedTile.getIndex() && move.getIndexDest() == destinationIndex) {
                                System.out.println("Move found");
                                //Get and execute move
                                checkersGame.executeMove(move);
                                //If it was a capture, save the new index of the capturing piece (needed for multi-leg capture moves)
                                if(move.getMoveType() == MoveType.CAPTURE) {
                                    multiLegIndex = move.getIndexDest();
                                }
                                clearTileBorders();
                                updatePieceLocations();
                                selectedTile = null;
                                moveFound = true;
                                break;
                            }
                        }
                        //If no valid move is found
                        if (moveFound == false) {
                            System.out.println("No valid move found");

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Invalid Move");
                            alert.setContentText("You have attempted an invalid move. Try something else.");
                            alert.show();
                            selectedTile = null;
                            clearTileBorders();
                        }
                    }
                    //Otherwise, there is no move they can possibly make, tell user to finish their turn
                    else {
                        System.out.println("User has no more moves left this turn");

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Moves Left");
                        alert.setContentText("You have made all the moves you can possibly make in this turn.");
                        alert.show();
                        selectedTile = null;
                        clearTileBorders();
                    }

                    //Check if the player won
                    victoryCheck();

                }
            } catch (NullPointerException ne) {
                //Do nothing
                return;
            }
        }
    }

    @FXML
    private void clearTileBorders() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                tileShapes[x][y].setStrokeWidth(0);
            }
        }
    }

    @FXML
    public void takeTurn() throws InterruptedException {
        if (checkersGame.isPlayerTurn()) {
            checkersGame.takeTurn();
            multiLegIndex = 0;
            logTextArea.appendText("Player took their turn \n");
            logTextArea.appendText("AI is thinking \n");
            //checkersGame.easyAIMove();
            checkersGame.minimaxAIMove();
            logTextArea.appendText("AI took their turn \n");
            updatePieceLocations();
            playerStuckCheck();
            victoryCheck();
            changePlayerTurnText();
        }
        else {
            logTextArea.appendText("AI is thinking \n");
            //checkersGame.easyAIMove();
            checkersGame.minimaxAIMove();
            logTextArea.appendText("AI took their turn \n");
            multiLegIndex = 0;
            updatePieceLocations();
            playerStuckCheck();
            victoryCheck();
            changePlayerTurnText();
        }
    }

    @FXML
    private void victoryCheck() {
        //Check if someone has won. Display a fitting  alert if so
        if(checkersGame.isFinished()) {
            if(checkersGame.getVictor() == PlayerType.HUMAN) {
                System.out.println("You Won!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations");
                alert.setContentText("You won the game.");
                alert.show();
            }
            else {
                System.out.println("You Lost");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sorry, the AI won");
                alert.setContentText("Better luck next time");
                alert.show();
            }
        }
    }

    private void playerStuckCheck() {
        boolean stuck = false;
        System.out.println(checkersGame.availableMoves(checkersGame.getPlayerColour()));
        Move move = checkersGame.availableMoves(checkersGame.getPlayerColour()).get(0);
        if(move.getMoveType() == MoveType.FORFEIT) {
            System.out.println("Player cannot make a legal move");
            checkersGame.executeMove(move);
        }
    }
}