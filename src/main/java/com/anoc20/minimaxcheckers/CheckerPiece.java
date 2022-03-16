package com.anoc20.minimaxcheckers;

public class CheckerPiece {

    private PieceColour pieceColour;
    private boolean isKing;

    //Constructor only contains isDark parameter as pieces can never start as kings
    public CheckerPiece(PieceColour pieceColour) {
        this.pieceColour = pieceColour;
    }

    //No setter for isDark as pieces cannot change colour
    public PieceColour getPieceColour() {
        return pieceColour;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}

enum PieceColour {
    DARK,
    WHITE
}