/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import javax.swing.JFrame;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Frame extends JFrame {
    
    private final Controller controller;
    
    /**
     * Standard constructor for Frame.
     * @param controller object controller.
     */
    public Frame(Controller controller) {
        this.controller = controller;
        
        this.setVisible(true);
        this.setSize(1200, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Mine Sweeper");
        this.setMenuBar(bar());
        
        updateFrame();
    }
    
    /**
     * Removes all content from the frame and adds a new BoardView.
     */
    public void updateFrame() {
        this.getContentPane().removeAll();
        this.add(new BoardView(this.controller));
        this.validate();
        this.repaint();
    }
    
    /**
     * Creates the menu bar for the frame.
     * @return menu bar.
     */
    private MenuBar bar() {
        MenuBar bar = new MenuBar();
        
        Menu file = new Menu("File");
        bar.add(file);
        
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(this.controller);
        file.add(exit);
        
        Menu m1 = new Menu("Settings");
        
        MenuItem bombNumber = new MenuItem("Number of bombs");
        bombNumber.addActionListener(this.controller);
        bombNumber.setActionCommand("bomb number");
        
        m1.add(bombNumber);
        bar.add(m1);
        
        Menu help = new Menu("Help");
        bar.add(help);
        
        MenuItem rules = new MenuItem("How to play");
        rules.addActionListener(this.controller);
        help.add(rules);
        
        return bar;
    }
    
    /**
     * Checks victory from the controller.
     */
    public void checkVictory() {
        this.controller.checkVictory();
    }
    
}
