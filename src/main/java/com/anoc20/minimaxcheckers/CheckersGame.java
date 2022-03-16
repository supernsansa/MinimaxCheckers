package com.anoc20.minimaxcheckers;

import java.util.ArrayList;

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

    public boolean movePiece(int x1, int y1, int x2, int y2) {
        CheckerTile activeTile = playingBoard.getBoard()[x1][y1];
        CheckerPiece activePiece = activeTile.getActivePiece();
        if (activePiece == null) {
            System.err.println("There is no piece at tile x: " + x1 + " y: " + y1);
            return false;
        }
        CheckerTile newTile = playingBoard.getBoard()[x2][y2];
        if (newTile.setActivePiece(activePiece)) {
            activeTile.removePiece();
            return true;
        } else {
            return false;
        }
    }

    //Scans the board to find all possible moves for a particular player (Dark or White)
    public ArrayList<Move> availableMoves(PieceColour pieceColour) {
        ArrayList<Move> availableMoves = new ArrayList<Move>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                CheckerTile potentialTile = playingBoard.getBoard()[x][y];
                CheckerPiece potentialPiece = potentialTile.getActivePiece();
                //For each dark piece...
                if (potentialPiece != null && potentialPiece.getPieceColour() == PieceColour.DARK) {
                    if(potentialPiece.isKing()) {
                        //TODO Check forward and backward diagonals
                    }
                    else {
                        //Check if diagonals in front of piece are empty
                        CheckerTile diag1;
                        CheckerTile diag2;
                        try {
                            diag1 = playingBoard.getBoard()[x-1][y+1];
                            if(diag1.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                            }
                            //TODO if piece is in the way, check if it can be captured
                            else {

                            }
                        }
                        catch(ArrayIndexOutOfBoundsException indexException) {}

                        try {
                            diag2 = playingBoard.getBoard()[x+1][y+1];
                            if(diag2.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag2.getIndex()));
                            }
                            //TODO if piece is in the way, check if it can be captured
                            else {

                            }
                        }
                        catch(ArrayIndexOutOfBoundsException indexException) {}
                    }
                }
            }
        }

        return availableMoves;
    }

    //Getters and Setters
    public CheckerBoard getPlayingBoard() {
        return playingBoard;
    }

    public void setPlayingBoard(CheckerBoard playingBoard) {
        this.playingBoard = playingBoard;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Mode getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Mode difficulty) {
        this.difficulty = difficulty;
    }

}

enum Mode {
    EASY,
    MEDIUM,
    HARD
}