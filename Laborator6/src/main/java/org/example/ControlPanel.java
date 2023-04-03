package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ControlPanel extends JPanel{
    final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton loadButton = new JButton("Load");
    JButton resetButton = new JButton("Reset");
    JButton saveButton = new JButton("Save");
//create all buttons (Load, Exit, etc.)

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
//change the default layout manager (just for fun)
        //frame.setLayout(new GridLayout(1, 4));
//add all buttons ...TODO
//configure listeners for all buttons
        exitButton.addActionListener(this::exitGame);
        saveButton.addActionListener(this::saveGame);
        resetButton.addActionListener(this::resetGame);
        loadButton.addActionListener(this::loadGame);

        this.add(loadButton);
        this.add(saveButton);
        this.add(resetButton);
        this.add(exitButton);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
    private void saveGame(ActionEvent e) {
        frame.canvas.exportGame();
        try{
            frame.canvas.saveGame();
        }
        catch (IOException exception){
            System.out.println("IO exception!");
        }
    }
    private void resetGame(ActionEvent e) {
        frame.canvas.resetGame();
    }
    private void loadGame(ActionEvent e) {
        try{
            frame.canvas.loadGame();
        }

        catch (IOException exception){
            System.out.println("IO exception!");
        }

        catch (ClassNotFoundException exception){
            System.out.println("ClassNotFound  exception!");
        }

    }
}