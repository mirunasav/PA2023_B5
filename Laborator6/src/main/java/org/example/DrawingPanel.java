package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;

    private BufferedImage image;
    private Graphics2D graphics;

    //game state
    private GameState game;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        initPanel();
    }

    private void initPanel() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // change state of game
                System.out.println("click");
                int x = e.getX();
                int y = e.getY();
                System.out.println(x + " " + y);
                if (game == null)
                    return;
                if (game.won) {
                    System.out.println("gata joc");
                    return;
                }

                for (int i = 0; i < game.numVertices; i++)
                    if (Math.abs(game.x[i] - x) < 10 && Math.abs(game.y[i] - y) < 10) {
                        //select
                        if (game.selected == -1) {
                            game.selected = i;
                            selectPoint(game.selected, 1);
                        } else {
                            //deselect
                            if (i == game.selected) {
                                selectPoint(game.selected, 0);
                                game.selected = -1;
                            } else
                                nextState(game.selected, i);
                        }
                        repaint();
                    }
            }
        });
    }

    private void selectPoint(int x, int state) {
        System.out.println("selectez punct");
        if (state == 1) {
            graphics.setColor(Color.YELLOW);
            graphics.fillOval(game.x[x] - 5, game.y[x] - 5, 10, 10);
        } else {
            graphics.setColor(Color.BLACK);
            graphics.fillOval(game.x[x] - 5, game.y[x] - 5, 10, 10);
        }
    }

    private void nextState(int x, int y) {
        if (game.addEdge(x, y) == false) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval(game.x[x] - 5, game.y[x] - 5, 10, 10);
            graphics.fillOval(game.x[y] - 5, game.y[y] - 5, 10, 10);
            game.selected = -1;
            System.out.println("aici?");
            return;
        }

        int color;
        if (game.turn == 0) {
            graphics.setColor(Color.BLUE);
            game.edge[x][y] = 2;
            game.edge[y][x] = 2;
        } else {
            graphics.setColor(Color.RED);
            game.edge[x][y] = 3;
            game.edge[y][x] = 3;
        }
        game.turn = 1 - game.turn;
        game.selected = -1;
        graphics.drawLine(game.x[x], game.y[x], game.x[y], game.y[y]);
        graphics.setColor(Color.BLACK);
        graphics.fillOval(game.x[x] - 5, game.y[x] - 5, 10, 10);
        graphics.fillOval(game.x[y] - 5, game.y[y] - 5, 10, 10);
        game.won = game.win();
    }

    public final void createBoard() {
        game = new GameState();
        game.numVertices = (Integer) frame.configPanel.dotsSpinner.getValue();
        game.edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();
        game.createVertices(W, H);
        game.createEdges();
        createOffscreenImage();
        drawGame();
        repaint();
    }

    public final void loadBoard() {
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawGame() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
        for (int i = 0; i < game.numVertices; i++)
            for (int j = i + 1; j < game.numVertices; j++)
                if (game.edge[i][j] == 1) {
                    graphics.setColor(Color.GRAY);
                    graphics.drawLine(game.x[i], game.y[i], game.x[j], game.y[j]);
                } else if (game.edge[i][j] == 2) {
                    graphics.setColor(Color.BLUE);
                    graphics.drawLine(game.x[i], game.y[i], game.x[j], game.y[j]);
                } else if (game.edge[i][j] == 3) {
                    graphics.setColor(Color.RED);
                    graphics.drawLine(game.x[i], game.y[i], game.x[j], game.y[j]);
                }

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < game.numVertices; i++)
            graphics.fillOval(game.x[i] - 5, game.y[i] - 5, 10, 10);

        for (int i = 0; i < game.numVertices; i++)
            if (i == game.selected) {
                graphics.setColor(Color.YELLOW);
                graphics.fillOval(game.x[i] - 5, game.y[i] - 5, 10, 10);
            }
    }

    public void exportGame() {
        File outputfile = new File("savedgame.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetGame() {
        for (int i = 0; i < game.numVertices; i++)
            for (int j = i + 1; j < game.numVertices; j++)
                if (game.edge[i][j] != 0)
                    game.edge[i][j] = 1;

        game.won = false;
        game.selected = -1;
        game.turn = 0;
        drawGame();
        repaint();
    }

    public void saveGame() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("data.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(game);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("data.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        game = (GameState) objectInputStream.readObject();
        objectInputStream.close();
        drawGame();
        repaint();
    }

    @Override
    public void update(Graphics graphics) {
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }

}
