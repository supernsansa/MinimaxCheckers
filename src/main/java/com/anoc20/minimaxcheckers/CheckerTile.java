package com.anoc20.minimaxcheckers;

public class CheckerTile {
    private boolean isBlack;
    private CheckerPiece activePiece;
    private int index;

    public CheckerTile(boolean isBlack, int index) {
        this.isBlack = isBlack;
        this.index = index;
    }

    public CheckerTile(boolean isBlack, CheckerPiece activePiece) {
        this.isBlack = isBlack;
        //Ensure only black tiles can hold pieces
        if(isBlack == true) {
            this.activePiece = activePiece;
        }
        else {
            System.err.println("Checker pieces cannot be placed on white tiles");
            return;
        }
    }

    public boolean isBlack() {
        return isBlack;
    }

    public int getIndex() {
        return index;
    }

    public CheckerPiece getActivePiece() {
        return activePiece;
    }

    public void setActivePiece(CheckerPiece activePiece) {
        this.activePiece = activePiece;
    }
}
