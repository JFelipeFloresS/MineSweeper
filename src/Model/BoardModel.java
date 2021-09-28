/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class BoardModel {

    public static final int BOARD_SIDE = 20;
    private final int[][] board;
    private final ArrayList<int[]> BOMBS;
    private int numberOfBombs;
    private final Random RD = new Random();

    /**
     * Standard constructor for BoardModel.
     * Initial number of bombs of 20.
     */
    public BoardModel() {
        this.numberOfBombs = 20;
        this.board = new int[BOARD_SIDE][BOARD_SIDE];
        this.BOMBS = new ArrayList<>();
        initialiseBoard();

        placeBombs();

        setNumbers();
    }
    
    /**
     * Constructor of BoardModel with customised number of bombs.
     * @param nBombs number of bombs to be created on the board.
     */
    public BoardModel(int nBombs) {
        this.numberOfBombs = nBombs;
        this.board = new int[BOARD_SIDE][BOARD_SIDE];
        this.BOMBS = new ArrayList<>();
        initialiseBoard();

        placeBombs();

        setNumbers();
    }

    /**
     * Gets 2d array board.
     * @return 2d array based on the position on the board.
     * -1 represents a bomb.
     * 0 represents an empty space.
     * 1 - 8 the number of bombs surrounding that place.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Sets all positions on the board to 0.
     */
    private void initialiseBoard() {
        for (int i = 0; i < BOARD_SIDE; i++) {
            for (int j = 0; j < BOARD_SIDE; j++) {
                this.board[i][j] = 0;
            }
        }
    }

    /**
     * Sets random positions on the board to -1, representing the bombs.
     */
    private void placeBombs() {

        for (int i = 0; i < numberOfBombs; i++) {
            int[] newBomb;
            do {
                newBomb = new int[2];
                newBomb[0] = RD.nextInt(BOARD_SIDE);
                newBomb[1] = RD.nextInt(BOARD_SIDE);

            } while (BOMBS.contains(newBomb));
            BOMBS.add(newBomb);
            this.board[newBomb[0]][newBomb[1]] = -1;
        }

    }

    /**
     * Checks the board and changes the zeros to the number of bombs surrounding them.
     */
    private void setNumbers() {
        for (int i = 0; i < BOARD_SIDE; i++) {
            for (int j = 0; j < BOARD_SIDE; j++) {
                if (this.board[i][j] == 0) {

                    int c = 0;

                    if (this.board[i][j] == 0) {

                        for (int k = i - 1; k <= i + 1; k++) {
                            for (int l = j - 1; l <= j + 1; l++) {
                                try {
                                    if (this.board[k][l] == - 1) {
                                        c++;
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }

                        this.board[i][j] = c;
                    }

                }
            }
        }
    }

    /**
     * Gets number of bombs.
     * @return number of bombs.
     */
    public int getNumberOfBombs() {
        return this.numberOfBombs;
    }

    /**
     * Checks if all bombs are flagged and that only bombs are flagged.
     * @return whether or not all bombs are flagged.
     */
    public boolean areAllBombsFlagged() {
        for (int i = 0; i < this.BOMBS.size(); i++) {
            if (!Model.isInList(Model.FLAGGED, this.BOMBS.get(i))) {
                return false;
            }
        }
        return this.BOMBS.size() == Model.FLAGGED.size();
    }

}
