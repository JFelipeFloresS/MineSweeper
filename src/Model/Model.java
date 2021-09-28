/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Model {

    private final BoardModel board;
    private final ArrayList<int[]> clickedPlaces;
    public static final ArrayList<int[]> FLAGGED = new ArrayList<>();

    /**
     * Standard constructor for Model.
     */
    public Model() {
        this.board = new BoardModel();
        this.clickedPlaces = new ArrayList<>();
    }
    
    /**
     * Constructor for model with number of bombs defined by user.
     * @param nBombs number of bombs.
     */
    public Model(int nBombs) {
        this.board = new BoardModel(nBombs);
        this.clickedPlaces = new ArrayList<>();
    }

    /**
     * Gets board from board model.
     * @return 2d array based on the position on the board.
     * -1 represents a bomb.
     * 0 represents an empty space.
     * 1 - 8 the number of bombs surrounding that place.
     */
    public int[][] getBoard() {
        return this.board.getBoard();
    }

    /**
     * Adds position to the ArrayList clickedPlaces and checks adjacent positions if the position is empty (0)
     * @param pos position to be checked
     */
    public void click(int[] pos) {
        if (pos[0] < 0 || pos[0] > BoardModel.BOARD_SIDE - 1 || pos[1] < 0 || pos[1] > BoardModel.BOARD_SIDE - 1) {
            return;
        }
        if (!isInList(this.clickedPlaces, pos)) {
            this.clickedPlaces.add(pos);
            if (isInList(FLAGGED, pos)) {
                removeFromFlag(pos);
            }
            if (this.board.getBoard()[pos[0]][pos[1]] == 0) {
                checkAdjacents(pos);
            }
        }
    }

    /**
     * If all buttons but the bombs are clicked and all bombs are flagged, the user won.
     * @return true if victory, false otherwise.
     */
    public boolean checkVictory() {
        return this.board.areAllBombsFlagged() && isAllBoardClicked();
    }

    /**
     * Checks if the ArrayList clickedPlaces has the same size as the board size decreased by the number of bombs.
     * @return boolean showing whether all places but the bombs are clicked.
     */
    private boolean isAllBoardClicked() {
        return this.clickedPlaces.size() == (BoardModel.BOARD_SIDE * BoardModel.BOARD_SIDE) - this.board.getNumberOfBombs();
    }
    
    /**
     * Gets number of bombs from board model.
     * @return number of bombs.
     */
    public int getNumberOfBombs() {
        return this.board.getNumberOfBombs();
    }

    /**
     * Removes position from the FLAGGED ArrayList.
     * @param pos position to be removed.
     */
    public static void removeFromFlag(int[] pos) {
        for (int i = 0; i < FLAGGED.size(); i++) {
            if (Arrays.equals(FLAGGED.get(i), pos)) {
                FLAGGED.remove(i);
                return;
            }
        }
    }

    /**
     * Clicks all places on the board to be displayed at the end of the game.
     */
    public void clickAll() {
        FLAGGED.removeAll(FLAGGED);
        for (int i = 0; i < BoardModel.BOARD_SIDE; i++) {
            for (int j = 0; j < BoardModel.BOARD_SIDE; j++) {
                int[] p = {i, j};
                if (!isInList(this.clickedPlaces, p)) {
                    this.clickedPlaces.add(p);
                }
            }
        }
    }

    /**
     * Gets clicked places.
     * @return ArrayList of positions that have been clicked.
     */
    public ArrayList<int[]> getClickedPlaces() {
        return this.clickedPlaces;
    }

    /**
     * Checks if an Integer array is in an ArrayList of Integer arrays.
     * @param list ArrayList to be checked against.
     * @param p Integer array to be checked.
     * @return whether the p is in list or not.
     */
    public static boolean isInList(ArrayList<int[]> list, int[] p) {
        return list.stream().anyMatch(a -> Arrays.equals(a, p));
    }

    /**
     * Checks all adjacent positions to a given position on the board and calls the method click on each of them.
     * @param pos position to check adjacent.
     */
    public void checkAdjacents(int[] pos) {

        for (int i = pos[0] - 1; i <= pos[0] + 1; i++) {
            for (int j = pos[1] - 1; j <= pos[1] + 1; j++) {
                try {

                    int[] p = {i, j};
                    
                    if (!isInList(this.clickedPlaces, p)) {
                        click(p);
                    }

                } catch (Exception e) {
                }
            }
        }

    }
}
