package com.anoc20.minimaxcheckers;

public class CheckerBoard{

    /**
     * 2D CheckerPiece array representing a 8x8 English Draughts board
     * null slots represent empty tiles on the checkerboard
     */
    private CheckerPiece[][] board = new CheckerPiece[8][8];

    public CheckerBoard() {
        //TODO Populate board with starting positions
        //First, do dark positions
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 3; y++) {
                if( (x % 2 != 0) && (y % 2 == 0) ) {
                    board[x][y] = new CheckerPiece(true);
                }
                else if( (x % 2 == 0) && (y % 2 != 0) ) {
                    board[x][y] = new CheckerPiece(true);
                }
                else {
                    board[x][y] = null;
                }
            }
        }
        //Then, do white positions
        for(int x = 0; x < 8; x++) {
            for(int y = 5; y < 8; y++) {
                if( (x % 2 != 0) && (y % 2 == 0) ) {
                    board[x][y] = new CheckerPiece(false);
                }
                else if( (x % 2 == 0) && (y % 2 != 0) ) {
                    board[x][y] = new CheckerPiece(false);
                }
                else {
                    board[x][y] = null;
                }
            }
        }
    }

    public CheckerPiece[][] getBoard() {
        return board;
    }

    //Move piece from one position to another (not validated)
    public void movePiece(int x, int y, int newX, int newY) {
        board[newX][newY] = board[x][y];
        board[x][y] = null;
    }

    //Remove piece from the board
    public void removePiece(int x, int y) {
        board[x][y] = null;
    }

    //Capture a piece (i.e. remove it from the board and have the attacking piece take its place)
    public void capturePiece(int x, int y, int enemyX, int enemyY) {
        removePiece(enemyX, enemyY);
        movePiece(x,y,enemyX,enemyY);
    }

    public void printBoard() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if(board[x][y] != null) {
                    if(board[x][y].isDark()) {
                        System.out.print("D ");
                    }
                    else {
                        System.out.print("W ");
                    }
                }
                else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        CheckerBoard board = new CheckerBoard();
        board.printBoard();
        System.out.println();

        //Move white piece to new spot
        board.movePiece(0,5,1,4);
        board.printBoard();
    }

}
