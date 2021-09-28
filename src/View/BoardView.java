/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BoardModel;
import Model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author José Felipe Flores da Silva
 */
public class BoardView extends JPanel {

    private final Controller controller;
    private final ImageIcon bomb = new Images().getPNGImage("bomb");
    private final ImageIcon flag = new Images().getPNGImage("flag");

    /**
     * Constructor for BoardView.
     * @param controller object controller.
     */
    public BoardView(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        showTopPanel();

        showBoard();

    }

    /**
     * Adds top panel to display how many bombs there are left on the board.
     */
    private void showTopPanel() {
        JPanel topPanel = new JPanel();
        this.add(topPanel, BorderLayout.NORTH);
        
        JLabel b = new JLabel();
        b.setIcon(bomb);
        
        JLabel n = new JLabel("" + ((this.controller.getNumberOfBombs() - Model.FLAGGED.size())));
        
        topPanel.add(b);
        topPanel.add(n);
    }

    /**
     * Displays the playing board using a 2d array of JButtons on a JPanel with GridLayout.
     */
    private void showBoard() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BoardModel.BOARD_SIDE, BoardModel.BOARD_SIDE));
        this.add(boardPanel, BorderLayout.CENTER);

        int[][] board = this.controller.getBoard();

        JButton[][] buttons = new JButton[BoardModel.BOARD_SIDE][BoardModel.BOARD_SIDE];
        for (int i = 0; i < BoardModel.BOARD_SIDE; i++) {
            for (int j = 0; j < BoardModel.BOARD_SIDE; j++) {

                int[] pos = {i, j};
                buttons[i][j] = new JButton();
                buttons[i][j].setActionCommand("click " + i + " " + j);
                buttons[i][j].setPreferredSize(new Dimension((int) (60 - (BoardModel.BOARD_SIDE * 0.2)), 45 - (int) (BoardModel.BOARD_SIDE * 0.2)));
                buttons[i][j].setFont(new Font("Tahoma", Font.BOLD, 20 - (int) (BoardModel.BOARD_SIDE * 0.4)));

                if (Model.isInList(this.controller.getClickedPlaces(), pos)) {

                    drawPlace(buttons[i][j], board[i][j]);

                } else {

                    buttons[i][j].addActionListener(this.controller);
                    buttons[i][j].addMouseListener(new MouseAdapter() {
                        /**
                         * Checks if button was right clicked to flag or unflag the given button.
                         * @param e mouse event.
                         */
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                JButton b = (JButton) e.getComponent();
                                String[] c = b.getActionCommand().split(" ");
                                int[] pos = {Integer.parseInt(c[1]), Integer.parseInt(c[2])};
                                if (Model.isInList(Model.FLAGGED, pos)) {
                                    Model.removeFromFlag(pos);
                                } else {
                                    Model.FLAGGED.add(pos);
                                }
                                Frame f = (Frame) SwingUtilities.getRoot(b);
                                f.updateFrame();
                                f.checkVictory();
                            }
                        }
                    });

                }

                if (Model.isInList(Model.FLAGGED, pos)) {
                    drawPlace(buttons[i][j], -2);
                }

                boardPanel.add(buttons[i][j]);
            }
        }

    }

    /**
     * Draws all different types of buttons.
     * @param b button to be drawn.
     * @param value 
     * -1 represents a bomb.
     * 0 represents an empty space.
     * 1 - 8 the number of bombs surrounding that place.
     */
    private void drawPlace(JButton b, int value) {
        Color[] colours = {Color.BLACK, Color.LIGHT_GRAY, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK};
        
        switch (value) {
            case -2:
                b.setIcon(flag);
                break;
            case -1:
                b.setBackground(Color.WHITE);
                b.setIcon(bomb);
                break;
            case 0:
                b.setBackground(colours[1]);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                b.setText(value + "");
                b.setForeground(colours[value]);
                b.setBackground(Color.WHITE);
                break;

        }

    }
    
    /**
     * Images class to display images on the frame.
     */
    public class Images {
        
        /**
         * Gets PNG image.
         * @param s name of the image.
         * @return resized ImageIcon of the selected image.
         */
        public ImageIcon getPNGImage(String s) {
            try {
                Image img = ImageIO.read(getClass().getResource("../Assets/" + s + ".png"));
                img = img.getScaledInstance((int) (60 - (BoardModel.BOARD_SIDE * 0.2)) - 2, 45 - (int) (BoardModel.BOARD_SIDE * 0.2) - 2, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } catch (IOException ex) {
                Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
    }

}
