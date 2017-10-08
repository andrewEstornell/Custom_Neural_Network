package game;

import java.util.ArrayList;

/**
 * Created by andes on 9/17/2017.
 */
public class Snake {

    // List of x, y coordinates that represent the snake
    private ArrayList<int[]> snakeBody;
    private char lastMove;

    public Snake(int x, int y) {
        this.snakeBody = new ArrayList<int[]>();
        int[] snakeHead = new int[2];
        snakeHead[0] = x;
        snakeHead[1] = y;
        this.snakeBody.add(snakeHead);
        this.lastMove = 'w';
    }

    public void updateSnake(char move, int x, int y, char squareMovedTo){
        if(squareMovedTo == '#'){
            this.snakeBody.add(0, new int[]{x, y});
            this.lastMove = move;
        }
        else{
            this.snakeBody.add(0, new int[]{x, y});
            this.snakeBody.remove(this.snakeBody.size() - 1);
            this.lastMove = move;
        }
    }

    public ArrayList<int[]> getSnakeBody() {return snakeBody;}
    public void setSnakeBody(ArrayList<int[]> snakeBody) {this.snakeBody = snakeBody;}

    public char getLastMove() {return lastMove;}
    public void setLastMove(char lastMove) {this.lastMove = lastMove;}


}
