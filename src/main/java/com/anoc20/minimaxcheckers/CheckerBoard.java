package com.anoc20.minimaxcheckers;

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
                    board[x][y] = new CheckerTile(Colour.BLACK, index, x, y);
                    index++;
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y] = new CheckerTile(Colour.BLACK, index, x, y);
                    index++;
                } else {
                    board[x][y] = new CheckerTile(Colour.WHITE, 0, x, y);
                }
            }
        }
    }

    //This method populates the tiles on the board with the right pieces to start a match
    public void placeStartingPieces() {
        int id = 0;
        //First the white pieces
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x % 2 != 0) && (y % 2 == 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(PieceColour.WHITE, board[x][y].getIndex(),id));
                    id++;
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(PieceColour.WHITE, board[x][y].getIndex(),id));
                    id++;
                } else {
                    continue;
                }
            }
        }
        //Next, the dark pieces
        for (int y = 5; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x % 2 != 0) && (y % 2 == 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(PieceColour.DARK, board[x][y].getIndex(),id));
                    id++;
                } else if ((x % 2 == 0) && (y % 2 != 0)) {
                    board[x][y].setActivePiece(new CheckerPiece(PieceColour.DARK, board[x][y].getIndex(),id));
                    id++;
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

    //Returns all tiles as a 1D array
    public CheckerTile[] getAllTiles() {
        CheckerTile[] allTiles = new CheckerTile[64];
        int index = 0;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                allTiles[index] = board[x][y];
                index++;
            }
        }

        return allTiles;
    }

    //Return a CheckerTile object associated with a given index number (1 - 32)
    public CheckerTile getTileByIndex(int index) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(board[x][y].getIndex() == index) {
                    return board[x][y];
                }
            }
        }
        return null;
    }

    //Returns coordinates of a tile given its index
    public int[] getTileCoordsByIndex(int index) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(board[x][y].getIndex() == index) {
                    int[] returnValues = {x,y};
                    return returnValues;
                }
            }
        }
        return null;
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
                    if (board[x][y].getColour() == Colour.BLACK && board[x][y].getActivePiece() != null) {
                        if (board[x][y].getActivePiece().getPieceColour() == PieceColour.DARK) {
                            System.out.print("D ");
                        } else {
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
