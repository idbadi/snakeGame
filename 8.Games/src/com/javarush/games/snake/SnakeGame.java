package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;

    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }

        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        else if (isGameStopped && key == Key.SPACE){
            createGame();
        }
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 300;
        score = 0;
        setScore(score);
        setTurnTimer(turnDelay);
        createNewApple();
        isGameStopped = false;
        drawScene();


    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i, j, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }

    private void gameOver() {
        if (!isGameStopped) {
            stopTurnTimer();
            isGameStopped = true;
            showMessageDialog(Color.RED, "GAME OVER", Color.AQUAMARINE, 100);
        }
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "YOU WIN", Color.AQUAMARINE, 100);

    }

}
