package com.anoc20.minimaxcheckers;

import java.util.ArrayList;

public class CheckerBoard{

    private CheckerPiece[] board = new CheckerPiece[32];

    //Constructor for checkerboard objects, boolean parameter for filled or unfilled board
    public CheckerBoard(boolean filled) {
        //If filled == true, populate the board with pieces
        if(filled) {
            //First, the white pieces
            for(int index = 0; index < 12; index++) {
                board[index] = new CheckerPiece(false);
            }
            //Next, the dark pieces
            for(int index = 20; index < 32; index++) {
                board[index] = new CheckerPiece(true);
            }
        }
        //Otherwise, keep the board empty (i.e. do nothing)
        else {
            return;
        }
    }

    public void printBoard() {
        //Flag for whether row starts with a white square or not
        Boolean flip = false;

        for(int row = 0; row < 8; row++) {
            System.out.println();
            int newIndex = row*4;
            for(int index = newIndex; index < newIndex+4; index++) {
                if(flip == false) {
                    //Print - for white square
                    System.out.print("- ");
                }
                //Print - for empty black square
                if(board[index] == null) {
                    System.out.print(index+1 + " ");
                }
                //Print d or D for dark pieces
                else if(board[index].isDark()){
                    if(board[index].isKing()) {
                        System.out.print("D ");
                    }
                    else {
                        System.out.print("d ");
                    }
                }
                //Print w or W for white pieces
                else if(board[index].isDark() == false){
                    if(board[index].isKing()) {
                        System.out.print("W ");
                    }
                    else {
                        System.out.print("w ");
                    }
                }
                //Print E if error
                else {
                    System.out.print("E ");
                }
                if(flip == true) {
                    //Print - for white square
                    System.out.print("- ");
                }
            }
            flip = !flip;
        }
    }

    public static void main(String[] args) {
        CheckerBoard unfilledBoard = new CheckerBoard(false);
        unfilledBoard.printBoard();
        System.out.println(" ");
        CheckerBoard filledBoard = new CheckerBoard(true);
        filledBoard.printBoard();
    }

}
