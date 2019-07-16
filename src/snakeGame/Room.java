package snakeGame;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Room {
    public static Room game;

    public static void main(String[] args) {
        game = new Room(50,18, new Snake(10, 10));
        game.snake.setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();

    }

    private int width;
    private int height;
    private Snake snake;
    private Mouse mouse;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Snake getSnake() {
        return snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    private void run(){
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (snake.isAlive()){
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                if (event.getKeyChar() == 'q'){
                    return;
                }

                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        snake.setDirection(SnakeDirection.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.setDirection(SnakeDirection.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        snake.setDirection(SnakeDirection.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.setDirection(SnakeDirection.DOWN);
                        break;
                }
            }
            snake.move();
            print();
            sleep();
        }
        System.out.println("Game over");
    }

    private void print(){
        int[][] matrix = new int[height][width];

        ArrayList<SnakeSection> sections = new ArrayList<>(snake.getSections());
        for (SnakeSection snakeSection : sections){
            matrix[snakeSection.getY()][snakeSection.getX()]= 1;
        }
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 2 : 4;
        matrix[mouse.getY()][mouse.getX()] = 3;

        String[] symbols = {".", "x", "X", "Q", "RIP"};
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private void createMouse(){
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        mouse = new Mouse(x, y);
    }

    void eatMouse(){
        createMouse();
    }

    private void sleep(){
        try {
            int lengtSnake = snake.getSections().size();
            int delay = 500;
            while (delay>=200){
                delay -= 10 * lengtSnake;
            }
            Thread.sleep(delay);
        }catch (InterruptedException ignored){

        }
    }
}
