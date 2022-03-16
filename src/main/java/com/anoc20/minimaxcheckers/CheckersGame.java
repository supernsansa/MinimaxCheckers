package com.anoc20.minimaxcheckers;

//Outlines a "match" object that represents a match of checkers
public class CheckersGame {

    private CheckerBoard playingBoard;
    private boolean playerTurn;
    private Mode difficulty;

    //Default constructor
    public CheckersGame(boolean playerTurn, Mode difficulty) {
        this.playingBoard = new CheckerBoard();
        this.playingBoard.placeStartingPieces();
        this.playerTurn = playerTurn;
        this.difficulty = difficulty;
    }

    //Change from player to AI turn and vice-versa
    public void takeTurn() {
        playerTurn = !playerTurn;
    }

    public boolean movePiece(int x1,int y1,int x2,int y2) {
        CheckerTile activeTile = playingBoard.getBoard()[x1][y1];
        CheckerPiece activePiece = activeTile.getActivePiece();
        if(activePiece == null) {
            System.err.println("There is no piece at tile x: " + x1 + " y: " + y1);
            return false;
        }
        CheckerTile newTile = playingBoard.getBoard()[x2][y2];
        if(newTile.setActivePiece(activePiece)) {
            activeTile.removePiece();
            return true;
        }
        else {
            return false;
        }
    }


}

enum Mode {
    EASY,
    MEDIUM,
    HARD
}