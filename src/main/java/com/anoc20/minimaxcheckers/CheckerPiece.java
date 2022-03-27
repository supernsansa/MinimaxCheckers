package com.anoc20.minimaxcheckers;

public class CheckerPiece {

    private PieceColour pieceColour;
    private boolean isKing;
    private int pieceIndex;
    private int id;

    //Constructor only contains isDark parameter as pieces can never start as kings
    public CheckerPiece(PieceColour pieceColour, int pieceIndex, int id) {
        this.pieceColour = pieceColour;
        this.pieceIndex = pieceIndex;
        this.id = id;
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

    public int getPieceIndex() {
        return pieceIndex;
    }

    public void setPieceIndex(int pieceIndex) {
        this.pieceIndex = pieceIndex;
    }

    public int getId() {
        return id;
    }
}

enum PieceColour {
    DARK,
    WHITE
}