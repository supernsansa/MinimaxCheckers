package com.anoc20.minimaxcheckers;

import java.util.ArrayList;

public class CheckerBoard {

    private CheckerTile[][] board = new CheckerTile[8][8];

    //Constructor for checkerboard objects
    public CheckerBoard() {
        //Fill board with black and white tiles
        //Index value for black tiles, ranges from 1 to 32. 0 represents a white tile.
        int index = 1;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x % 2 != 0) && (y % 2 == 0)) {
                    board[x][y] = new CheckerTile(true,index);
                    index++;
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y] = new CheckerTile(true,index);
                    index++;
                } else {
                    board[x][y] = new CheckerTile(false,0);
                }
            }
        }
    }

    //This method populates the tiles on the board with the right pieces to start a match
    public void placeStartingPieces() {
        //First the dark pieces
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x % 2 != 0) && (y % 2 == 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(true));
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(true));
                } else {
                    continue;
                }
            }
        }
        //Next, the white pieces
        for (int y = 5; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x % 2 != 0) && (y % 2 == 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(false));
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(false));
                } else {
                    continue;
                }
            }
        }
    }

    public CheckerTile[][] getBoard() {
        return board;
    }

    public void setBoard(CheckerTile[][] board) {
        this.board = board;
    }

    //Prints a text representation of the checkerboard where each black tile is represented by and index from 1 to 32, 0 represents white
    public void printBoardTiles() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][y] != null) {
                    System.out.print(board[x][y].getIndex() + " ");
                } else {
                    //System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    //Prints a text representation of the checkerboard where true = dark piece and false = white piece
    public void printBoardPieces() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][y] != null) {
                    if (board[x][y].isBlack() && board[x][y].getActivePiece() != null) {
                        if(board[x][y].getActivePiece().isDark()) {
                            System.out.print("D ");
                        }
                        else {
                            System.out.print("W ");
                        }
                    } else {
                        System.out.print("- ");
                    }
                } else {
                    //System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CheckerBoard testBoard = new CheckerBoard();
        testBoard.printBoardTiles();
        System.out.println(" ");
        testBoard.placeStartingPieces();
        testBoard.printBoardPieces();
    }

}
