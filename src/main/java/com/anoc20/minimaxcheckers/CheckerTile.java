package com.anoc20.minimaxcheckers;

public class CheckerTile {
    private Colour colour;
    private CheckerPiece activePiece;
    private int index;

    public CheckerTile(Colour colour, int index) {
        this.colour = colour;
        this.index = index;
    }

    public CheckerTile(Colour colour, CheckerPiece activePiece) {
        this.colour = colour;
        //Ensure only black tiles can hold pieces
        if(colour == Colour.BLACK) {
            this.activePiece = activePiece;
        }
        else {
            System.err.println("Checker pieces cannot be placed on white tiles");
            return;
        }
    }

    public Colour getColour() {
        return colour;
    }

    public int getIndex() {
        return index;
    }

    public CheckerPiece getActivePiece() {
        return activePiece;
    }

    public boolean setActivePiece(CheckerPiece activePiece) {
        if(colour == Colour.BLACK) {
            this.activePiece = activePiece;
            return true;
        }
        else {
            System.err.println("Checker pieces cannot be placed on white tiles");
            return false;
        }
    }

    public void removePiece() {
        this.activePiece = null;
    }
}

enum Colour {
    BLACK,
    WHITE
}
