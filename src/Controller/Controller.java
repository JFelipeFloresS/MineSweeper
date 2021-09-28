/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Model;
import View.Frame;
import View.Rules;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Controller implements ActionListener {

    private final Frame frame;
    private Model model;

    /**
     * Standard constructor.
     */
    public Controller() {
        this.model = new Model();
        this.frame = new Frame(this);
    }

    /**
     * Retrieves board from model.
     * @return 2d array based on the position on the board.
     * -1 represents a bomb.
     * 0 represents an empty space.
     * 1 - 8 the number of bombs surrounding that place.
     */
    public int[][] getBoard() {
        return this.model.getBoard();
    }

    /**
     * Shows what lies on the clicked button. If it's a bomb, the player lost the game.
     * @param pos position clicked by the user.
     */
    public void click(int[] pos) {
        this.model.click(pos);
        if (this.model.getBoard()[pos[0]][pos[1]] == -1) {
            this.model.clickAll();
            this.frame.updateFrame();
            int ans = JOptionPane.showConfirmDialog(this.frame, "You stepped on a bomb!!\r\nWould you like to try again?", "Oops...", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                reset();
            } else {
                System.exit(0);
            }
        }
        this.frame.updateFrame();
        checkVictory();
    }

    /**
     * Reset the board, creating a new model object.
     */
    private void reset() {
        this.model = new Model(this.getNumberOfBombs());
        Model.FLAGGED.removeAll(Model.FLAGGED);
        this.frame.updateFrame();
    }
    
    /**
     * Reset the board, creating a new model object.
     * @param nBombs number of bombs to be created.
     */
    private void reset(int nBombs) {
        this.model = new Model(nBombs);
        Model.FLAGGED.removeAll(Model.FLAGGED);
        this.frame.updateFrame();
    }

    /**
     * Gets clicked places from the model.
     * @return ArrayList of positions.
     */
    public ArrayList<int[]> getClickedPlaces() {
        return this.model.getClickedPlaces();
    }

    /**
     * Gets number of bombs from the model.
     * @return number of bombs on the board.
     */
    public int getNumberOfBombs() {
        return this.model.getNumberOfBombs();
    }

    /**
     * Checks if player won the game through the model and displays a message in case of victory.
     */
    public void checkVictory() {
        if (this.model.checkVictory()) {
            int ans = JOptionPane.showConfirmDialog(this.frame, "YOU WON!\r\nWOULD YOU LIKE TO PLAY AGAIN?", "CONGRATULATIONS", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                reset();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Asks the user how many bombs they want and reset the model based on the answer.
     */
    private void changeNumberOfBombs() {
        String ans = "";
        try {
            ans = JOptionPane.showInputDialog(this.frame, "Please select number of bombs.", this.model.getNumberOfBombs());
            if (ans.equals(JOptionPane.CANCEL_OPTION)) {
                return;
            }
            int nBombs = Integer.parseInt(ans);
            if (nBombs <= 0) {
                changeNumberOfBombs();
            }
            
            reset(nBombs);
        } catch (NumberFormatException e) {
            changeNumberOfBombs();
        }

    }
    
    /**
     * Creates new window Rules to show the user how to play the game.
     */
    private void showRules() {
        new Rules();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().startsWith("click")) {
            String[] c = e.getActionCommand().split(" ");
            int[] pos = {Integer.parseInt(c[1]), Integer.parseInt(c[2])};
            click(pos);
        }
        switch (e.getActionCommand().toLowerCase()) {
            case "bomb number":
                changeNumberOfBombs();
                break;
            case "exit":
                System.exit(0);
                break;
            case "how to play":
                showRules();
                break;
        }
    }

}
