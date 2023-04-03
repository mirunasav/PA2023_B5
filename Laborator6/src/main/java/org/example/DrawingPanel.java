package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawingPanel extends JPanel {

    final MainFrame frame;
    final static int W = 800, H = 600;
    private int numVertices;
    private double edgeProbability;
    private int[] x, y;

    BufferedImage image;
    Graphics2D graphics;


    private void init(){
     // createOffscreenImage();
        initPanel();
      //  createBoard();
    }
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        this.init();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//              repaint();
//            }
//        });
    }
//    final void createBoard(Graphics graphics) {
//        numVertices = (Integer) frame.configPanel.dotsSpinner.getValue();
//        edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();
//        createVertices();
//        drawLines();
//       // drawVertices();
//        //repaint();
//    }

    private void createVertices() {
        int x0 = W / 2;
        int y0 = H / 2; //middle of the board
        int radius = H / 2 - 10; //board radius
        double alpha = 2 * Math.PI / numVertices; // the angle
        x = new int[numVertices];
        y = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            x[i] = x0 + (int) (radius * Math.cos(alpha * i));
            y[i] = y0 + (int) (radius * Math.sin(alpha * i));
        }
    }

    private void drawLines() {

    }

    private void drawVertices() {
      int diameter = 50;
        for (int i = 0; i < numVertices; i++){
           graphics.drawOval(x[i]-diameter/2, y[i]-diameter/2, diameter, diameter);
        }
    }

    @Override
    public void update(Graphics g) {
    } //No need for update

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,W,H);

        //painting the vertexes
        graphics.setColor(Color.BLACK);
        numVertices = (Integer) frame.configPanel.dotsSpinner.getValue();
        edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();
        createVertices();
        int diameter = 20;
        for (int i = 0; i < numVertices; i++){
            graphics.fillOval(x[i]-diameter/2, y[i]-diameter/2, diameter, diameter);
        }

        //painting the lines
        for(int i = 0; i<numVertices-1; i++){
            for(int j = i+1; j<numVertices; j++){
                //verificam daca am muchie intre nodurile i si j
                Random random = new Random();
                double randomNumber = random.nextDouble();
                if(randomNumber <= edgeProbability){
                    graphics.drawLine(x[i], y[i], x[j],y[j]);
                }
            }
        }
    }

    private void drawVertex(int x, int y,Graphics graphics) {
        int diameter = 50;
    }
}