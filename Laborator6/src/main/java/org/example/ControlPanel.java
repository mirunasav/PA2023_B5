package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        this.add(loadButton);
        this.add(saveButton);
        this.add(resetButton);
        this.add(exitButton);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}