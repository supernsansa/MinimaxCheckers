package com.anoc20.minimaxcheckers;

public class Move {
    private MoveType moveType;
    private int indexO,indexD;

    public Move(MoveType moveType, int indexO, int indexD) {
        this.moveType = moveType;
        this.indexO = indexO;
        this.indexD = indexD;
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveType=" + moveType +
                ", indexO=" + indexO +
                ", indexD=" + indexD +
                '}';
    }
}
enum MoveType {
    MOVEMENT,
    CAPTURE
}
