package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel implements ActionListener {
    final MainFrame frame;
    JLabel dotsLabel, linesLabel;
    JSpinner dotsSpinner;
    JComboBox linesCombo;
    JButton createButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
//create the label and the spinner
        dotsLabel = new JLabel("Number of dots:");
        linesLabel = new JLabel("Line probability:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(6, 3, 100, 1));
//create the rest of the components

        Double[] s1 = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};

        linesCombo = new JComboBox(s1);
        linesCombo.addActionListener(this);


        createButton = new JButton("Create");
        createButton.addActionListener(frame::listenForCreate);

        this.add(dotsLabel); //JPanel uses FlowLayout by default
        this.add(dotsSpinner);
        this.add(linesLabel);
        this.add(linesCombo);
        this.add(createButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox comboBox = (JComboBox) e.getSource();
        Double probability = (Double) comboBox.getSelectedItem();
    }
}
