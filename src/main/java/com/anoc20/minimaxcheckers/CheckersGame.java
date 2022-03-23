package com.anoc20.minimaxcheckers;

import java.util.ArrayList;

//Outlines a "match" object that represents a match of checkers
public class CheckersGame {

    private CheckerBoard playingBoard;
    private boolean playerTurn;
    private Mode difficulty;
    private PieceColour playerColour;
    private int movesMade;

    //Default constructor
    public CheckersGame(boolean playerTurn, Mode difficulty) {
        this.playingBoard = new CheckerBoard();
        this.playingBoard.placeStartingPieces();
        this.playerTurn = playerTurn;
        this.difficulty = difficulty;

        if (playerTurn == true) {
            playerColour = PieceColour.DARK;
        } else {
            playerColour = PieceColour.WHITE;
        }
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
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex()));
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
        CheckerTile originTile = getPlayingBoard().getTileByIndex(move.getIndexO());
        CheckerTile destinationTile = getPlayingBoard().getTileByIndex(move.getIndexD());

        if(move.getMoveType() == MoveType.MOVEMENT) {
            movePiece(originTile.getX(), originTile.getY(), destinationTile.getX(), destinationTile.getY());
        }
        else if(move.getMoveType() == MoveType.CAPTURE) {
            //TODO Implement capture method
        }
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
}

enum Mode {
    EASY,
    MEDIUM,
    HARD
}