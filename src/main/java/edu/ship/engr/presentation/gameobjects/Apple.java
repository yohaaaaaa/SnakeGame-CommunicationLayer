package edu.ship.engr.presentation.gameobjects;

import edu.ship.engr.presentation.SnakeGame;

import java.util.Random;

public class Apple {
    private int xPosition;
    private int yPosition;
    private boolean inPlay;

    /**
     * Creates a new apple object
     */
    public Apple() {}

    /**
     * Create an apple at a random position and sets it to be in play
     */
    public void spawnApple() {
        inPlay = true;

        xPosition = new Random().nextInt(SnakeGame.SCREEN_WIDTH/ SnakeGame.UNIT_SIZE) * SnakeGame.UNIT_SIZE;
        yPosition = new Random().nextInt(SnakeGame.SCREEN_HEIGHT/ SnakeGame.UNIT_SIZE) * SnakeGame.UNIT_SIZE;
    }

    /**
     * Removes an apple from being in play
     */
    public void despawnApple() {
        inPlay = false;

        xPosition = 0;
        yPosition = 0;
    }

    /**
     * Sets the apples x position
     * @param newXPosition the new x position
     */
    public void setXPosition(int newXPosition) {
        xPosition = newXPosition;
    }

    /**
     * Sets the apples y position
     * @param newYPosition the new y position
     */
    public void setYPosition(int newYPosition) {
        yPosition = newYPosition;
    }

    /**
     * sets the apples state in the game
     * @param isInPlay should the apple be currently in play
     */
    public void setInPlay(boolean isInPlay) {
        inPlay = isInPlay;
    }

    /**
     * @return true if the apple is eatable in game
     */
    public boolean getInPlay() {
        return inPlay;
    }

    /**
     * @return the x position of the apple
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * @return the y position of the apple
     */
    public int getYPosition() {
        return yPosition;
    }
}