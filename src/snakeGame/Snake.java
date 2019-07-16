package snakeGame;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public int getX(){
        return sections.get(0).getX();
    }

    public int getY(){
        return sections.get(0).getY();
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void move(){
        if (!isAlive){
            return;
        }

        switch (direction){
            case UP:
                move(0, -1);
                break;
            case RIGHT:
                move(1, 0);
                break;
            case DOWN:
                move(0, 1);
                break;
            case LEFT:
                move(-1, 0);
                break;
        }
    }

    void move(int dX, int dY){
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX()+dX, head.getY()+dY);

        checkBorders(head);
        if (!isAlive){
            return;
        }

        checkBody(head);
        if (!isAlive){
            return;
        }

        Mouse mouse = Room.game.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()){
            sections.add(0, head);
            Room.game.eatMouse();
        }else {
            sections.add(0, head);
            sections.remove(sections.size()-1);
        }
    }

    private void checkBorders(SnakeSection head){
        if (head.getX() < 0 || head.getX() >= Room.game.getWidth() || head.getY() < 0 || head.getY() >= Room.game.getHeight()){
            isAlive = false;
        }
    }

    private void checkBody(SnakeSection head){
        if(sections.contains(head)){
            isAlive = false;
        }
    }
}
