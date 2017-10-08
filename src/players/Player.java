package players;

import game.Game;
import game.Snake;
import neuralNetwork.NeuralNet;

/**
 * Created by andes on 9/17/2017.
 */
public class Player {

    protected Snake snake;
    protected Game game;
    protected char[][] board;

    protected NeuralNet neuralNet;

    public char getMove() {
        return ' ';
    }

    public Snake getSnake() {return snake;}
    public void setSnake(Snake snake) {this.snake = snake;}

    public char[][] getBoard() {
        return board;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        this.board = game.getBoard();
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
    public NeuralNet getNeuralNetwork(){return this.neuralNet;}


}
