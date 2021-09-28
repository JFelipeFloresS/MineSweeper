/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class Rules extends JFrame{
    
    public Rules() {
        this.setVisible(true);
        this.setSize(1200, 500);
        this.setTitle("How to play");
        this.setLayout(new BorderLayout());
        
        showRules();
        
        this.validate();
        this.repaint();
    }
    
    private void showRules() {
        JPanel top = new JPanel();
        this.add(top, BorderLayout.NORTH);
        
        JLabel title = new JLabel("HOW TO PLAY");
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        top.add(title);
        
        JPanel r = new JPanel();
        this.add(r, BorderLayout.CENTER);
        
        JLabel rules = new JLabel("<html>");
        String text = "- Left click to show what lies on a given square.<br>"
                + "- The numbers on each square represent how many mines surround the given square.<br>"
                + "- Right click to flag a place that has a mine on.<br>"
                + "- Flag all bombs and clear all spaces to win!";
        rules.setText(rules.getText() + text + "</html>");
        rules.setFont(new Font("Tahoma", Font.BOLD, 20));
        r.add(rules);
    }
    
}
