package com.anoc20.minimaxcheckers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

//TODO MinimaxABP algorithm, figure out way to represent multi-capture moves
//Outlines a "match" object that represents a match of checkers
public class CheckersGame {

    private CheckerBoard playingBoard;
    private boolean playerTurn;
    private Mode difficulty;
    private PieceColour playerColour;
    private int movesMade;
    private boolean multiCap;
    private boolean finished;
    private PlayerType victor;
    private boolean maximise;
    private ArrayList<CheckerPiece> removedPieces;
    private ArrayList<CheckerPiece> kings;

    //Debug
    int movesExecuted = 0;
    int movesReversed = 0;

    //Default constructor
    public CheckersGame(boolean playerTurn, Mode difficulty) {
        this.playingBoard = new CheckerBoard();
        this.playingBoard.placeStartingPieces();
        this.playerTurn = playerTurn;
        this.difficulty = difficulty;
        this.movesMade = 0;
        this.multiCap = false;
        this.finished = false;
        this.removedPieces = new ArrayList<CheckerPiece>();
        this.kings = new ArrayList<CheckerPiece>();

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

    //This method handles non-capture movements
    public void movePiece(int x1, int y1, int x2, int y2, boolean permanent) {
        CheckerTile activeTile = playingBoard.getBoard()[x1][y1];
        CheckerPiece activePiece = activeTile.getActivePiece();
        if (activePiece == null) {
            System.err.println("There is no piece at tile x: " + x1 + " y: " + y1);
            return;
        }
        CheckerTile newTile = playingBoard.getBoard()[x2][y2];
        if (newTile.setActivePiece(activePiece)) {
            activeTile.removePiece();
            //Update piece's internal index
            activePiece.setPieceIndex(newTile.getIndex());
            //Check if piece has landed on king's row
            if ((activePiece.getPieceColour() == PieceColour.WHITE && y2 == 7) ||
                    (activePiece.getPieceColour() == PieceColour.DARK && y2 == 0)) {
                activePiece.setKing(true);
                if(permanent) {
                    kings.add(activePiece);
                }
            }
            movesMade++;
        }
    }

    //This method handles capture manoeuvres
    public void capturePiece(int indexO, int indexD, int indexC, boolean permanent) {
        //Get the offensive piece
        CheckerPiece activePiece = getPlayingBoard().getTileByIndex(indexO).getActivePiece();
        //Move the piece to where it needs to go
        CheckerTile destTile = getPlayingBoard().getTileByIndex(indexD);
        getPlayingBoard().getTileByIndex(indexD).setActivePiece(activePiece);
        getPlayingBoard().getTileByIndex(indexO).removePiece();
        //Update piece's internal index
        activePiece.setPieceIndex(destTile.getIndex());

        //Check if captured piece is king
        CheckerPiece capPiece = getPlayingBoard().getTileByIndex(indexC).getActivePiece();
        if (capPiece.isKing()) {
            //If so, the active piece becomes a king
            activePiece.setKing(true);
            if(permanent) {
                kings.add(activePiece);
            }
        }

        //Remove the captured piece
        if (permanent) {
            capPiece.setPieceIndex(0);
        }
        removedPieces.add(capPiece);
        getPlayingBoard().getTileByIndex(indexC).removePiece();
        movesMade++;
        multiCap = true;

        //Check if piece has landed on king's row
        if ((activePiece.getPieceColour() == PieceColour.WHITE && destTile.getY() == 7) ||
                (activePiece.getPieceColour() == PieceColour.DARK && destTile.getY() == 0)) {
            activePiece.setKing(true);
            if(permanent) {
                kings.add(activePiece);
            }
            multiCap = false;
        }
    }

    //Scans the board to find all possible moves for a particular player (Dark or White)
    public ArrayList<Move> availableMoves(PieceColour pieceColour) {
        ArrayList<Move> availableMoves = new ArrayList<Move>();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                CheckerTile potentialTile = playingBoard.getBoard()[x][y];
                CheckerPiece potentialPiece = potentialTile.getActivePiece();

                if (pieceColour == PieceColour.WHITE) {
                    //For each white piece...
                    if (potentialPiece != null && potentialPiece.getPieceColour() == PieceColour.WHITE) {
                        //Check if diagonals in front of piece are empty
                        CheckerTile diag1;
                        CheckerTile diag2;
                        try {
                            diag1 = playingBoard.getBoard()[x - 1][y + 1];
                            if (diag1.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag1.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                CheckerTile diag3 = playingBoard.getBoard()[x - 2][y + 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                            diag1.getIndex(),diag1.getActivePiece().getId()));
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
                                CheckerTile diag3 = playingBoard.getBoard()[x + 2][y + 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                            diag2.getIndex(), diag2.getActivePiece().getId()));
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
                                else if (diag1.getActivePiece().getPieceColour() == PieceColour.DARK) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x - 2][y - 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                                diag1.getIndex(), diag1.getActivePiece().getId()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                                diag2.getIndex(), diag2.getActivePiece().getId()));
                                    }

                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }
                        }
                    }

                } else if (pieceColour == PieceColour.DARK) {
                    //For each dark piece...
                    if (potentialPiece != null && potentialPiece.getPieceColour() == PieceColour.DARK) {
                        //Check if diagonals in front of piece are empty
                        CheckerTile diag1;
                        CheckerTile diag2;
                        try {
                            diag1 = playingBoard.getBoard()[x - 1][y - 1];
                            if (diag1.getActivePiece() == null) {
                                availableMoves.add(new Move(MoveType.MOVEMENT, potentialTile.getIndex(), diag1.getIndex()));
                            }
                            //Otherwise if piece is in the way, check if it can be captured
                            else if (diag1.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                CheckerTile diag3 = playingBoard.getBoard()[x - 2][y - 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                            diag1.getIndex(), diag1.getActivePiece().getId()));
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
                                CheckerTile diag3 = playingBoard.getBoard()[x + 2][y - 2];
                                if (diag3.getActivePiece() == null) {
                                    availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                            diag2.getIndex(), diag2.getActivePiece().getId()));
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
                                else if (diag1.getActivePiece().getPieceColour() == PieceColour.WHITE) {
                                    CheckerTile diag3 = playingBoard.getBoard()[x - 2][y + 2];
                                    if (diag3.getActivePiece() == null) {
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                                diag1.getIndex(), diag1.getActivePiece().getId()));
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
                                        availableMoves.add(new Move(MoveType.CAPTURE, potentialTile.getIndex(), diag3.getIndex(),
                                                diag2.getIndex(), diag2.getActivePiece().getId()));
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException indexException) {
                            }
                        }
                    }
                }
            }
        }

        //If no moves are available, insert a pass move into the available moves list
        if (availableMoves.size() == 0) {
            availableMoves.add(new Move(MoveType.FORFEIT, 0, 0));
        }

        return availableMoves;
    }

    //Return an ArrayList of available capture moves (used to enforce forced capture)
    public ArrayList<Move> availableCaptures(PieceColour pieceColour) {
        ArrayList<Move> moves = availableMoves(pieceColour);
        ArrayList<Move> captures = new ArrayList<Move>();
        for (Move move : moves) {
            if (move.getMoveType() == MoveType.CAPTURE) {
                captures.add(move);
            }
        }
        return captures;
    }

    //Return captures possible for a specific piece at a specific index (Used to check if multi-leg captures are possible)
    public ArrayList<Move> getCapturesByIndex(PieceColour pieceColour, int index) {
        ArrayList<Move> moves = availableCaptures(pieceColour);
        ArrayList<Move> captures = new ArrayList<Move>();
        for (Move move : moves) {
            if (move.getIndexOrigin() == index) {
                captures.add(move);
            }
        }
        return captures;
    }

    //Method for executing a movement or capture manoeuvre
    public void executeMove(Move move, boolean permanent) {
        CheckerTile originTile = getPlayingBoard().getTileByIndex(move.getIndexOrigin());
        CheckerTile destinationTile = getPlayingBoard().getTileByIndex(move.getIndexDest());

        if (move.getMoveType() == MoveType.MOVEMENT) {
            //System.out.println("Movement made");
            movesExecuted++;
            movePiece(originTile.getX(), originTile.getY(), destinationTile.getX(), destinationTile.getY(),permanent);
        } else if (move.getMoveType() == MoveType.CAPTURE) {
            //System.out.println("Capture made");
            movesExecuted++;
            capturePiece(move.getIndexOrigin(), move.getIndexDest(), move.getIndexCapture(),permanent);
        } else if (move.getMoveType() == MoveType.FORFEIT) {
            System.out.println("No moves possible, forfeiting game");
            finished = true;
            if(playerTurn) {
                victor = PlayerType.BOT;
            }
            else {
                victor = PlayerType.HUMAN;
            }
        }
    }

    //Method to unmake a move
    public void unmakeMove(Move move) {
        CheckerTile originTile = getPlayingBoard().getTileByIndex(move.getIndexOrigin());
        CheckerTile destinationTile = getPlayingBoard().getTileByIndex(move.getIndexDest());

        if (move.getMoveType() == MoveType.MOVEMENT) {
            //System.out.println("Movement unmade");
            movesReversed++;
            movePiece(destinationTile.getX(), destinationTile.getY(), originTile.getX(), originTile.getY(),false);
        } else if (move.getMoveType() == MoveType.CAPTURE) {
            //System.out.println("Capture unmade");
            movesReversed++;
            CheckerPiece restoredPiece = null;
            for(CheckerPiece removedPiece : removedPieces) {
                if(removedPiece.getId() == move.getCapturedPieceID()) {
                    restoredPiece = removedPiece;
                    break;
                }
            }
            playingBoard.getTileByIndex(move.getIndexCapture()).setActivePiece(restoredPiece);
            movePiece(destinationTile.getX(), destinationTile.getY(), originTile.getX(), originTile.getY(), false);
        }
        //Reset all king statuses
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                CheckerPiece piece = playingBoard.getBoard()[x][y].getActivePiece();
                if(piece != null) {
                    piece.setKing(false);
                }
            }
        }
        //Restore king status for all pieces in the kings arraylist
        for(CheckerPiece piece : kings) {
            if(piece.getPieceIndex() != 0) {
                piece.setKing(true);
            }
        }
    }

    //Returns the amount of dark or white pieces on the board, sets finished variable to true if 0
    public int pieceCount(PieceColour pieceColour) {
        int pieceCount = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (playingBoard.getBoard()[x][y].getActivePiece() != null) {
                    if (playingBoard.getBoard()[x][y].getActivePiece().getPieceColour() == pieceColour) {
                        pieceCount++;
                    }
                }
            }
        }
        if (pieceCount == 0) {
            finished = true;
        }
        return pieceCount;
    }

    //Returns the quantity of a certain colour's pieces, this time with increased weightings for king pieces
    public double weightedPieceCount(PieceColour pieceColour) {
        double pieceCount = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (playingBoard.getBoard()[x][y].getActivePiece() != null) {
                    if (playingBoard.getBoard()[x][y].getActivePiece().getPieceColour() == pieceColour) {
                        //Add 2 to the piece count if the piece is a king
                        if(playingBoard.getBoard()[x][y].getActivePiece().isKing()) {
                            pieceCount = pieceCount + 1.5;
                        }
                        else {
                            pieceCount = pieceCount + 1;
                        }
                    }
                }
            }
        }
        if (pieceCount == 0) {
            finished = true;
        }
        return pieceCount;
    }

    public void AIMove() {
        if(difficulty == Mode.EASY) {
            easyAIMove();
        }
        else {
            minimaxAIMove();
        }
    }

    //For easy mode, have the AI pick a move randomly (no multi-leg)
    public void easyAIMove() {
        ArrayList<Move> possibleMoves;
        //If player is white, ai is dark
        if (playerColour == PieceColour.WHITE) {
            possibleMoves = availableCaptures(PieceColour.DARK);
            if(possibleMoves.size() == 0) {
                possibleMoves = availableMoves(PieceColour.DARK);
            }
        //Otherwise, if player is dark, ai must be white
        } else {
            possibleMoves = availableCaptures(PieceColour.WHITE);
            if(possibleMoves.size() == 0) {
                possibleMoves = availableMoves(PieceColour.WHITE);
            }
        }

        Random random = new Random();
        //System.out.println(possibleMoves);
        //System.out.println(possibleMoves.size());
        int randomIndex = random.nextInt(possibleMoves.size());

        //System.out.println((possibleMoves.get(randomIndex)));
        executeMove(possibleMoves.get(randomIndex),true);
        takeTurn();
    }

    //For medium and hard mode, have the AI pick the best move as determined by the Minimax algorithm with Alpha Beta pruning
    public void minimaxAIMove() {
        movesExecuted = 0;
        movesReversed = 0;
        ArrayList<Move> possibleMoves;
        double eval;
        //If player is white, ai is dark
        if (playerColour == PieceColour.WHITE) {
            maximise = false;
            possibleMoves = availableCaptures(PieceColour.DARK);
            if(possibleMoves.size() == 0) {
                possibleMoves = availableMoves(PieceColour.DARK);
            }
            eval = 1000;
            //Otherwise, if player is dark, ai must be white
        } else {
            maximise = true;
            possibleMoves = availableCaptures(PieceColour.WHITE);
            if(possibleMoves.size() == 0) {
                possibleMoves = availableMoves(PieceColour.WHITE);
            }
            eval = -1000;
        }

        Move bestMove = null;
        for(Move move : possibleMoves) {
            //Change depth based on chosen difficulty
            double tempEval = 0;
            if(difficulty == Mode.MEDIUM) {
                tempEval = minimaxABP(this, 6, -1000, 1000, maximise, move);
            }
            else {
                tempEval = minimaxABP(this, 10, -1000, 1000, maximise, move);
            }

            System.out.println(tempEval);
            //System.out.println(tempEval);
            if (playerColour == PieceColour.DARK) {
                System.out.println(tempEval + " > " + eval);
                if(tempEval > eval) {
                    eval = tempEval;
                    bestMove = move;
                }
            }
            else {
                System.out.println(tempEval + " < " + eval);
                if(tempEval < eval) {
                    eval = tempEval;
                    bestMove = move;
                }
            }
        }

        System.out.println("Possible Moves:");
        System.out.println(possibleMoves);
        System.out.println(eval);
        System.out.println(bestMove.toString());
        System.out.println("Minimax moves made: " + movesExecuted);
        System.out.println("Minimax moves unmade: " + movesReversed);

        executeMove(bestMove,true);
        takeTurn();
    }

    //This method calculates the number of white pieces minus the number of dark pieces
    public double simpleHeuristic() {
        return pieceCount(PieceColour.WHITE) - pieceCount(PieceColour.DARK);
    }

    //This method calculates the number of white pieces minus the number of dark pieces, with king weightings factored in
    public double betterHeuristic() {
        return weightedPieceCount(PieceColour.WHITE) - weightedPieceCount(PieceColour.DARK);
    }

    //Method that implements the Minimax search algorithm with alpha-beta pruning
    private double minimaxABP(CheckersGame state, int depth, double alpha, double beta, boolean maximise, Move moveToMake) {

        state.executeMove(moveToMake,false);

       if(depth == 0 || state.gameEnded()) {
           double returnVal;
           if(difficulty == Mode.MEDIUM) {
               returnVal = state.simpleHeuristic();
           }
           else {
               returnVal = betterHeuristic();
           }

           state.unmakeMove(moveToMake);

           return returnVal;
       }
       if(maximise) {
           double maxEvaluation = -1000;
           ArrayList<Move> moves;
           moves = state.availableCaptures(PieceColour.WHITE);
           if(moves.size() == 0) {
               moves = state.availableMoves(PieceColour.WHITE);
           }

           /**
           if(state.playerTurn) {
               moves = state.availableCaptures(state.getPlayerColour());
               if(moves.size() == 0) {
                   moves = state.availableMoves(state.getPlayerColour());
               }
           }
           else {
               if(state.playerColour == PieceColour.WHITE) {
                   moves = state.availableCaptures(PieceColour.DARK);
                   if(moves.size() == 0) {
                       moves = state.availableMoves(PieceColour.DARK);
                   }
               }
               else {
                   moves = state.availableCaptures(PieceColour.WHITE);
                   if(moves.size() == 0) {
                       moves = state.availableMoves(PieceColour.WHITE);
                   }
               }
           }
            */

           for(Move move : moves) {
               if(move.getMoveType() != MoveType.FORFEIT) {
                   //state.executeMove(move,false);
                   double eval = minimaxABP(state, depth-1, alpha, beta, false, move);
                   maxEvaluation = Math.max(maxEvaluation,eval);
                   alpha = Math.max(alpha, eval);
                   if(beta <= alpha) {
                       break;
                   }
               }
           }
           state.unmakeMove(moveToMake);
           return maxEvaluation;
       }
       else {
           double minEvaluation = 1000;
           ArrayList<Move> moves;

           moves = state.availableCaptures(PieceColour.DARK);
           if(moves.size() == 0) {
               moves = state.availableMoves(PieceColour.DARK);
           }

           /**
           if(state.playerTurn) {
               moves = state.availableCaptures(state.getPlayerColour());
               if(moves.size() == 0) {
                   moves = state.availableMoves(state.getPlayerColour());
               }
           }
           else {
               if(state.playerColour == PieceColour.WHITE) {
                   moves = state.availableCaptures(PieceColour.DARK);
                   if(moves.size() == 0) {
                       moves = state.availableMoves(PieceColour.DARK);
                   }
               }
               else {
                   moves = state.availableCaptures(PieceColour.WHITE);
                   if(moves.size() == 0) {
                       moves = state.availableMoves(PieceColour.WHITE);
                   }
               }
           }
            */

           for(Move move : moves) {
               if(move.getMoveType() != MoveType.FORFEIT) {
                   //state.executeMove(move,false);
                   double eval = minimaxABP(state, depth-1, alpha, beta, true, move);
                   minEvaluation = Math.min(minEvaluation, eval);
                   beta = Math.min(beta, eval);
                   if(beta <= alpha) {
                       break;
                   }
               }
           }
           state.unmakeMove(moveToMake);
           return minEvaluation;
       }
    }

    public boolean gameEnded() {
        if(pieceCount(PieceColour.WHITE) == 0) {
            return true;
        }
        else if(pieceCount(PieceColour.DARK) == 0) {
            return true;
        }
        return false;
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

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }

    public boolean isMultiCap() {
        return multiCap;
    }

    public boolean isFinished() {
        return finished;
    }

    public PlayerType getVictor() {
        return victor;
    }
}

enum Mode {
    EASY,
    MEDIUM,
    HARD
}

enum PlayerType {
    HUMAN,
    BOT
}