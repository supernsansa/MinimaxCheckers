package com.anoc20.minimaxcheckers;

import java.util.ArrayList;

//Outlines a "match" object that represents a match of checkers
public class CheckersGame {

    private CheckerBoard playingBoard;
    private boolean playerTurn;
    private Mode difficulty;
    private PieceColour playerColour;
    private int movesMade;
    private boolean multiCap;
    private boolean finished;

    //Default constructor
    public CheckersGame(boolean playerTurn, Mode difficulty) {
        this.playingBoard = new CheckerBoard();
        this.playingBoard.placeStartingPieces();
        this.playerTurn = playerTurn;
        this.difficulty = difficulty;
        this.movesMade = 0;
        this.multiCap = false;
        this.finished = false;

        if (playerTurn == true) {
            playerColour = PieceColour.DARK;
        } else {
            playerColour = PieceColour.WHITE;
        }
    }

    //Change from player to AI turn and vice-versa
    public void takeTurn() {
        movesMade = 0;
        multiCap = false;
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

    //This method handles capture manoeuvres
    public void capturePiece(int indexO, int indexD, int indexC) {
        //Get the offensive piece
        CheckerPiece activePiece = getPlayingBoard().getTileByIndex(indexO).getActivePiece();
        //Move the piece to where it needs to go
        getPlayingBoard().getTileByIndex(indexD).setActivePiece(activePiece);
        getPlayingBoard().getTileByIndex(indexO).removePiece();
        //Check if capture piece is king
        CheckerPiece capPiece = getPlayingBoard().getTileByIndex(indexC).getActivePiece();
        if(capPiece.isKing()) {
            //If so, the active piece becomes a king
            activePiece.setKing(true);
        }
        //Remove the captured piece
        getPlayingBoard().getTileByIndex(indexC).removePiece();
        movesMade++;
    }

    //Scans the board to find all possible moves for a particular player (Dark or White)
    public ArrayList<Move> availableMoves(PieceColour pieceColour) {
        ArrayList<Move> availableMoves = new ArrayList<Move>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                CheckerTile potentialTile = playingBoard.getBoard()[x][y];
                CheckerPiece potentialPiece = potentialTile.getActivePiece();

                if (pieceColour == PieceColour.DARK) {
                    //For each dark piece...
                    if (potentialPiece != null && potentialPiece.getPieceColour() == PieceColour.DARK) {
                        //Check if diagonals in front of piece are empty
                        CheckerTile diag1;
                        CheckerTile diag2;
                        try {
                            diag1 = playingBoard.getBoard()[x - 1][y + 1];
                            if (diag1.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag1.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                CheckerTile diag3 = playingBoard.getBoard()[x - 2][y + 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag1.getIndex()));
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException indexException) {
                        }

                        try {
                            diag2 = playingBoard.getBoard()[x + 1][y + 1];
                            if (diag2.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag2.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag2.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                CheckerTile diag3 = playingBoard.getBoard()[x + 2][y + 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag2.getIndex()));
                                }

                            }
                        } catch (ArrayIndexOutOfBoundsException indexException) {
                        }

                        //If the piece is a king, check the backwards diagonals as well
                        if (potentialPiece.isKing()) {
                            try {
                                diag1 = playingBoard.getBoard()[x - 1][y - 1];
                                if (diag1.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                                }
                                //Otherwise if piece is in the way, check if it can be captured
                                else if (diag1.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x - 2][y + 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag1.getIndex()));
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }

                            try {
                                diag2 = playingBoard.getBoard()[x + 1][y - 1];
                                if (diag2.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag2.getIndex()));
                                }
                                //Otherwise if piece is in the way, check if it can be captured
                                else if (diag2.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x + 2][y + 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag2.getIndex()));
                                    }

                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }
                        }
                    }

                } else if (pieceColour == PieceColour.WHITE) {
                    //For each white piece...
                    if (potentialPiece != null && potentialPiece.getPieceColour() == PieceColour.WHITE) {
                        //Check if diagonals in front of piece are empty
                        CheckerTile diag1;
                        CheckerTile diag2;
                        try {
                            diag1 = playingBoard.getBoard()[x - 1][y - 1];
                            if (diag1.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag1.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                CheckerTile diag3 = playingBoard.getBoard()[x - 2][y - 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag1.getIndex()));
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException indexException) {
                        }

                        try {
                            diag2 = playingBoard.getBoard()[x + 1][y - 1];
                            if (diag2.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag2.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag2.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                CheckerTile diag3 = playingBoard.getBoard()[x + 2][y - 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag2.getIndex()));
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException indexException) {
                        }

                        //If piece is a king, check backwards diagonals as well
                        if (potentialPiece.isKing()) {
                            try {
                                diag1 = playingBoard.getBoard()[x - 1][y + 1];
                                if (diag1.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                                }
                                //Otherwise if piece is in the way, check if it can be captured
                                else if (diag1.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x - 2][y - 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag1.getIndex()));
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }

                            try {
                                diag2 = playingBoard.getBoard()[x + 1][y + 1];
                                if (diag2.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag2.getIndex()));
                                }
                                //Otherwise if piece is in the way, check if it can be captured
                                else if (diag2.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x + 2][y - 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(), diag2.getIndex()));
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }
                        }
                    }
                }
            }
        }

        return availableMoves;
    }

    //Method for executing a movement or capture manoeuvre
    public void executeMove(Move move) {
        CheckerTile originTile = getPlayingBoard().getTileByIndex(move.getIndexOrigin());
        CheckerTile destinationTile = getPlayingBoard().getTileByIndex(move.getIndexDest());

        if(move.getMoveType() == MoveType.MOVEMENT) {
            System.out.println("Movement made");
            movePiece(originTile.getX(), originTile.getY(), destinationTile.getX(), destinationTile.getY());
        }
        else if(move.getMoveType() == MoveType.CAPTURE) {
            System.out.println("Capture made");
            capturePiece(move.getIndexOrigin(), move.getIndexDest(), move.getIndexCapture());
        }
    }

    //Returns the amount of dark or white pieces on the board, sets finished variable to true if 0
    public int pieceCount(PieceColour pieceColour) {
        int pieceCount = 0;
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(playingBoard.getBoard()[x][y].getActivePiece().getPieceColour() == pieceColour) {
                    pieceCount++;
                }
            }
        }
        if(pieceCount == 0) {
            finished = true;
        }
        return pieceCount;
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

    public PieceColour getPlayerColour() {
        return playerColour;
    }

    public int getMovesMade() { return movesMade; }

    public void setMovesMade(int movesMade) { this.movesMade = movesMade; }

    public boolean isMultiCap() { return multiCap; }

    public boolean isFinished() { return finished; }
}

enum Mode {
    EASY,
    MEDIUM,
    HARD
}