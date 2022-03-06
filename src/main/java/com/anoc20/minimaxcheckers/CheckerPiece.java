package com.anoc20.minimaxcheckers;

public class CheckerPiece {

    private boolean isDark;
    private boolean isKing;

    //Constructor only contains isDark parameter as pieces can never start as kings
    public CheckerPiece(boolean isDark) {
        this.isDark = isDark;
    }

    //No setter for isDark as pieces cannot change colour
    public boolean isDark() {
        return isDark;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}
