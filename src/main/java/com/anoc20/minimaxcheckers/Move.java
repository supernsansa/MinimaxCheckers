package com.anoc20.minimaxcheckers;

public class Move {
    private MoveType moveType;
    private int indexOrigin, indexDest, indexCapture;

    public Move(MoveType moveType, int indexOrigin, int indexDest) {
        this.moveType = moveType;
        this.indexOrigin = indexOrigin;
        this.indexDest = indexDest;
    }

    public Move(MoveType moveType, int indexOrigin, int indexDest, int indexCapture) {
        this.moveType = moveType;
        this.indexOrigin = indexOrigin;
        this.indexDest = indexDest;
        this.indexCapture = indexCapture;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public int getIndexOrigin() {
        return indexOrigin;
    }

    public int getIndexDest() {
        return indexDest;
    }

    public int getIndexCapture() {
        return indexCapture;
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveType=" + moveType +
                ", indexO=" + indexOrigin +
                ", indexD=" + indexDest +
                '}';
    }
}
enum MoveType {
    MOVEMENT,
    CAPTURE
}
