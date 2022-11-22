package edu.ship.engr.presentation;

import edu.ship.engr.presentation.gameobjects.Apple;
import edu.ship.engr.presentation.gameobjects.Rectangle;
import edu.ship.engr.presentation.gameobjects.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class SnakeGame extends JPanel implements SnakeGameInterface, KeyListener, ActionListener {
    public static final int SCREEN_WIDTH = 550;
    public static final int SCREEN_HEIGHT = 550;
    public static final int UNIT_SIZE = 25;
    private static final Color BACKGROUND_COLOR = new Color(115,162,78);
    private static final boolean DRAW_GRID = false;
    private GameFrame window;
    protected Snake snake;
    protected Apple apple;

    /**
     * Creates a new JPanel to contain the snake game
     * @param window the JFrame window
     */
    public SnakeGame(GameFrame window) {
        this.window = window;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(BACKGROUND_COLOR);
        window.addKeyListener(this);
    }

    /**
     * Checks all possible collisions that may happen within the game
     */
    public void checkCollision() {
        Rectangle snakeHead = snake.getHead();
        ArrayList<Rectangle> snakeBody = snake.getBody();

        int currentXPosition = snakeHead.getXPosition();
        int currentYPosition = snakeHead.getYPosition();

        // Check borders
        if (currentXPosition < 0 || currentYPosition < 0) {
            endGame();
        }
        if (currentXPosition > SCREEN_WIDTH || currentYPosition > SCREEN_HEIGHT) {
            endGame();
        }

        // Check self intersect
        for (int i = 1; i < snakeBody.size(); i++) {
            Rectangle currentBodyPart = snakeBody.get(i);

            if (snakeHead.intersects(currentBodyPart)) {
                endGame();
            }
        }

        checkAppleCollision(snakeHead);
    }

    /**
     * Ends the game
     */
    private void endGame() {
        System.out.println("You lose!");
        window.setVisible(false);

        JFrame parent = new JFrame("Game over!");
        JOptionPane.showMessageDialog(parent, "Snake1's score: " + snake.getBody().size());

        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        System.exit(0);
    }

    /**
     * Draws the snake in the window
     * @param g graphics
     */
    private void drawSnake(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        updateSnake(g2D);
        checkCollision();
    }

    /**
     * Update the snakes position and draw the snakes body based on that
     * @param g2D graphics 2D
     */
    private void updateSnake(Graphics2D g2D) {
        snake.move();

        for (int i = 0; i < snake.getBody().size(); i++) {
            Rectangle currentBodyPart = snake.getBody().get(i);
            int currentBodyXPos = currentBodyPart.getXPosition();
            int currentBodyYPos = currentBodyPart.getYPosition();

            if (i == 0) {
                g2D.setPaint(snake.getHeadColor());
            } else {
                g2D.setPaint(snake.getBodyColor());
            }
            g2D.drawRect(currentBodyXPos, currentBodyYPos, UNIT_SIZE, UNIT_SIZE);
            g2D.fillRect(currentBodyXPos, currentBodyYPos, UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * Draws the apple in the window
     * @param g the graphics
     */
    private void drawApple(Graphics g) {
        if (apple.getInPlay()) {
            Graphics2D g2D = (Graphics2D) g;

            g2D.setPaint(Color.red);
            g2D.drawRect(apple.getXPosition(), apple.getYPosition(), UNIT_SIZE, UNIT_SIZE);
            g2D.fillRect(apple.getXPosition(), apple.getYPosition(), UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**
     * Adds an apple to the board
     * @param xPosition x position of the apple
     * @param yPosition y position of the apple
     */
    public void setApple(int xPosition, int yPosition) {
        apple.setXPosition(xPosition);
        apple.setYPosition(yPosition);
        apple.setInPlay(true);
    }

    /**
     * Draws a grid on the window for debugging
     * @param g the graphics
     */
    private void drawGrid(Graphics g) {
        int width = SCREEN_WIDTH;
        int height = SCREEN_HEIGHT;
        int rows = SCREEN_WIDTH / UNIT_SIZE;
        int cols = SCREEN_HEIGHT / UNIT_SIZE;

        // draw the rows
        int rowHt = height / (rows);
        for (int i = 0; i < rows; i++)
            g.drawLine(0, i * rowHt, width, i * rowHt);

        // draw the columns
        int rowWid = width / (cols);
        for (int i = 0; i < cols; i++)
            g.drawLine(i * rowWid, 0, i * rowWid, height);
    }

    /**
     * Update
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (DRAW_GRID) {
            drawGrid(g);
        }

        drawSnake(g);
        drawApple(g);
    }

    /**
     * Listens for a key press
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // TODO: incorporate messaging with this

        if (keyCode == 39 && !snake.getDirection().equals("left")) {
            snake.setDirection("right");

        } else if (keyCode == 37 && !snake.getDirection().equals("right")) {
            snake.setDirection("left");

        } else if (keyCode == 38 && !snake.getDirection().equals("down")) {
            snake.setDirection("up");

        } else if (keyCode == 40 && !snake.getDirection().equals("up")) {
            snake.setDirection("down");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    /**
     * Redraws the screen after an action
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
