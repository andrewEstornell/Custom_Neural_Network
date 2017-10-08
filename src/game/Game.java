package game;

import gui.GUI;
import players.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andes on 9/17/2017.
 */
public class Game {

    private char[][] board;
    private int boardSize;
    private Snake snake;
    private int[] locationOfObjective; //apples
    private Player player; // Ai or human
    private int turn;
    private GUI gui;

    private int turnLimit = 10000; // Prevents AI from running in circles


    public Game(Player player) {
        this.boardSize = 20;
        this.board = new char[this.boardSize][this.boardSize];
        this.player = player;
        this.turn = 0;
        // Initalzing the board
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (i == 0 || i == this.boardSize - 1) {
                    this.board[i][j] = '_';
                } else if (j == 0 || j == this.boardSize - 1) {
                    this.board[i][j] = '|';
                } else {
                    this.board[i][j] = ' ';
                }
            }
        }
        // Spawn a snake in the center of the of the board, then draws it on the board
        this.board[this.boardSize / 2][this.boardSize / 2] = '*';
        this.board[(this.boardSize / 2) + 1][this.boardSize / 2] = '*';
        this.board[(this.boardSize / 2) + 2][this.boardSize / 2] = '*';
        this.snake = new Snake(this.boardSize / 2, this.boardSize / 2);
        ArrayList<int[]> temp = new ArrayList<int[]>();
        temp.add(this.snake.getSnakeBody().get(0));
        temp.add(new int[]{(this.boardSize / 2) + 1, this.boardSize / 2});
        temp.add(new int[]{(this.boardSize / 2) + 2, this.boardSize / 2});
        this.snake.setSnakeBody(temp);

        //Stores the snake with the current player
        this.player.setSnake(this.snake);

        // Spawns new objctive
        this.locationOfObjective = new int[2];
        this.spawnNewObjective();
    }

    /**
     * Takes in a char; W, A, S, or, D then moves the snake one unit in that direction
     * @param direction
     * @return False if the move kills the snake, True otherwise
     */
    private boolean move(char direction){
        if(this.turn > this.turnLimit){
            return false;
        }
        this.turn ++;
        // Stores atributes of the snake so that we may check them against the board
        ArrayList<int[]> snakeBody = this.snake.getSnakeBody();
        int[] snakeHead = snakeBody.get(0);
        int[] snakeTip = snakeBody.get(snakeBody.size() - 1);
        int headX = snakeHead[0];
        int headY = snakeHead[1];
        int tipX = snakeTip[0];
        int tipY = snakeTip[1];

        /*
            Checks what type of square is 1 unit in the specified direction
            If the square is empty then we move the head one unit in the direction and delete the last tail element
            If the square holds and objective then we move the head one unit the direction with no tail deletion
            If the square has an obstacle then we return false ending the game
         */
        if(direction == 'w'){
            if(this.board[headX - 1][headY] ==' '){
                this.board[headX -1][headY] = '*';
                this.board[tipX][tipY] = ' ';
                this.snake.updateSnake('w', headX - 1, headY, ' ');
                return true;
            }
            else if(this.board[headX - 1][headY] == '#'){
                this.board[headX - 1][headY] = '*';
                this.snake.updateSnake('w', headX - 1, headY, '#');
                this.spawnNewObjective();
                return true;
            }
            else{ return false; }
        }
        else if(direction == 'a'){
            if(this.board[headX][headY - 1] == ' '){
                this.board[headX][headY - 1] = '*';
                this.board[tipX][tipY] = ' ';
                this.snake.updateSnake('a', headX, headY - 1, ' ');
                return true;
            }
            else if(this.board[headX][headY - 1] == '#'){
                this.board[headX][headY - 1] = '*';
                this.snake.updateSnake('a', headX, headY - 1, '#');
                this.spawnNewObjective();
                return true;
            }
            else{ return false; }
        }
        else if(direction == 's'){
            if(this.board[headX + 1][headY] == ' '){
                this.board[headX + 1][headY] ='*';
                this.board[tipX][tipY] = ' ';
                this.snake.updateSnake('s', headX + 1, headY, ' ');
                return true;
            }
            else if(this.board[headX + 1][headY] == '#'){
                this.board[headX + 1][headY] = '*';
                this.snake.updateSnake('s', headX + 1, headY, '#');
                this.spawnNewObjective();
                return true;
            }
            else{ return false; }
        }
        else if(direction == 'd'){
            if(this.board[headX][headY + 1] == ' '){
                this.board[headX][headY + 1] = '*';
                this.board[tipX][tipY] = ' ';
                this.snake.updateSnake('d', headX, headY + 1, ' ');
                return true;
            }
            else if(this.board[headX][headY + 1] == '#'){
                this.board[headX][headY + 1] = '*';
                this.snake.updateSnake('d', headX, headY + 1, '#');
                this.spawnNewObjective();
                return true;
            }
            else{ return false; }
        }
        return false;
    }

    /**
     * Uses a random into between 0 and boardsize -1 to chose a square where the next objective will spawn
     * The function then stores the location of the spawned objective
     * Is called after the snake eats one objective
     */
    private void spawnNewObjective(){
        int randX = (int)(Math.random() * this.boardSize - 1);
        int randY = (int)(Math.random() * this.boardSize - 1);
        while(this.board[randX][randY] != ' '){
            randX = (int)(Math.random() * this.boardSize - 1);
            randY = (int)(Math.random() * this.boardSize - 1);
        }
        this.board[randX][randY] = '#';
        this.locationOfObjective[0] = randX;
        this.locationOfObjective[1] = randY;
    }

    /**
     * Plays through an entire game
     * Making moves, spawning objectives
     * @param displayOnOrOff if 1 then a GUI is used to visualize the game, if 0 then no display is used
     */
    public void playGame(int displayOnOrOff){
        if(displayOnOrOff == 1){
            this.gui = new GUI(this.boardSize, this.boardSize);
            while(true) {
                this.displayBoard();
                char playersMove = this.player.getMove();
                // Checks if the move is false, false moves are yielded when the snake dies as a result of the move, this ends the game
                if(!this.move(playersMove)){
                    break;
                }
            }
            this.displayBoard();
        }
        else if(displayOnOrOff == 0){
                while(true){
                    char playersMove = this.player.getMove();
                    // Checks if the move is false, false moves are yielded when the snake dies as a result of the move, this ends the game
                    if(!this.move(playersMove)){
                        break;
                    }
                }
            }

    }

    /**
     * Uses a GUI to display the snakes movments
     */
    public void displayBoard(){
        // Pause th program so that a human viewer can understand the game
        try {
            Thread.sleep(120);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Changes the colors on the GUI so that they reflect the snakes change in position and any new objectives
        gui.updateeGui(this);

    }

    public char[][] getBoard() {return board;}
    public void setBoard(char[][] board) {this.board = board;}

    public int getBoardSize() {return boardSize;}
    public void setBoardSize(int boardSize) {this.boardSize = boardSize;}

    public Snake getSnake() {return snake;}
    public void setSnake(Snake snake) {this.snake = snake;}

    public int[] getLocationOfObjective() {return locationOfObjective;}
    public void setLocationOfObjective(int[] locationOfObjective) {this.locationOfObjective = locationOfObjective;}
}
