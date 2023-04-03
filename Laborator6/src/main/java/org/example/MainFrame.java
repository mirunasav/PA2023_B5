package org.example;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.SwingConstants.CENTER;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;

    DrawingPanel canvas;

    public MainFrame() {
        super("Laborator 6");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel(this);
        controlPanel = new ControlPanel(this);
        canvas = new DrawingPanel(this);

        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(configPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);

    }

    public  void listenForCreate(ActionEvent actionEvent) {
       canvas.createBoard();
    }
}
