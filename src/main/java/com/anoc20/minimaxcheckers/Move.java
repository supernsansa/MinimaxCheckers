package com.anoc20.minimaxcheckers;

//Class defining Move objects.
public class Move {
    private MoveType moveType;
    private int indexOrigin, indexDest, indexCapture, capturedPieceID;

    public Move(MoveType moveType, int indexOrigin, int indexDest) {
        this.moveType = moveType;
        this.indexOrigin = indexOrigin;
        this.indexDest = indexDest;
    }

    public Move(MoveType moveType, int indexOrigin, int indexDest, int indexCapture, int capturedPieceID) {
        this.moveType = moveType;
        this.indexOrigin = indexOrigin;
        this.indexDest = indexDest;
        this.indexCapture = indexCapture;
        this.capturedPieceID = capturedPieceID;
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

    public int getCapturedPieceID() {
        return capturedPieceID;
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
    CAPTURE,
    FORFEIT
}
