package players;

import neuralNetwork.NeuralNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by andes on 9/17/2017.
 */
public class AiPlayer extends Player {

    private NeuralNet neuralNetwork;


    public AiPlayer(NeuralNet neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    /**
     * Uses the AI players NeuralNet to get a move
     * This is done by feeding the neural net an array that represents the snakes vision and a potential move
     * If the output of the NeuralNet is greater greater than 0, then the move is made
     * If no moves yield an output greater than 0, the move is defaulted to the snakes previous move, i.e. a straight path
     * @return W, A, S, or D
     */
    public char getMove() {
        char move = 0;

        // Gets the array representing the vision of the snake
        //double[] input = this.aiVision();
        double[] input = new double[5];

        // Stores values from the snake so that we make compare them to the board
        int headX = this.snake.getSnakeBody().get(0)[0];
        int headY = this.snake.getSnakeBody().get(0)[1];
        int objX = this.game.getLocationOfObjective()[0];
        int objY = this.game.getLocationOfObjective()[1];

        char lastMove = this.snake.getLastMove();

        // Array of directions so that we may more easily call directions
        char[] directions = {'w', 'a', 's', 'd'};
        int indexOfLastMove = 10;
        for(int i = 0; i < directions.length; i++){
            if(directions[i] == lastMove){
                indexOfLastMove = i;
                break;
            }
        }
        if(indexOfLastMove == 10){
            System.out.println("ERROR");
            System.exit(12);
        }


        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(0); values.add(1); values.add(2);
        Collections.shuffle(values);
        for(int value: values){
            char returnValue = 'X';
            if(value == 0){
                input = this.aiVision2(directions[indexOfLastMove]);
                returnValue = this.testForwardMove(input, headX, headY, objY, objX);
            }
            else if(value == 1){
                input = this.aiVision2(directions[(indexOfLastMove + 1) % 4]);
                returnValue = this.testLeftMove(input, headX, headY, objY, objX);
            }
            else if(value == 2){
                input = this.aiVision2(directions[((indexOfLastMove - 1) + 4) % 4]);
                returnValue = this.testRightMove(input, headX, headY, objY, objX);
            }
            if(returnValue != 'X'){
                return returnValue;
            }
        }
        /*
        if(this.snake.getLastMove() == 'w') {
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        else if(this.snake.getLastMove() == 'a'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        else{
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        input[4] = 0;
        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printing
            this.updateNN(this.snake.getLastMove());
            return this.snake.getLastMove();
        }

        if(this.snake.getLastMove() == 'w') {
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else if(this.snake.getLastMove() == 'a') {
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] =Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        else{
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        input[4] = -1;
        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printing
            if(this.snake.getLastMove() == 'w'){
                this.updateNN('w');
                return 'a';
            }
            else if(this.snake.getLastMove() == 'a'){
                this.updateNN('s');
                return 's';
            }
            else if(this.snake.getLastMove() == 's'){
                this.updateNN('d');
                return 'd';
            }
            else if(this.snake.getLastMove() == 'd'){
                this.updateNN('w');
                return 'w';
            }
        }
        if(this.snake.getLastMove() == 'w'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        else if(this.snake.getLastMove() == 'a'){
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else{
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        input[4] = 1;

        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printings
            if(this.snake.getLastMove() == 'w'){
                this.updateNN('d');
                return 'd';
            }
            else if(this.snake.getLastMove() == 'a'){
                this.updateNN('w');
                return 'w';
            }
            else if(this.snake.getLastMove() == 's'){
                this.updateNN('a');
                return 'a';
            }
            else if(this.snake.getLastMove() == 'd'){
                this.updateNN('s');
                return 's';
            }
        }*/
        //else{

        ///////////////////////////////////////////
        //Defualting move
        //////////////////////////////////////////

        /*input = this.aiVision2(directions[indexOfLastMove]);
            if(this.snake.getLastMove() == 'w') {
                input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
            }
            else if(this.snake.getLastMove() == 'a'){
                input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
            }
            else if(this.snake.getLastMove() == 's'){
                input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
            }
            else{
                input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
            }
            input[4] = 0;
            if(this.neuralNetwork.output(input) != 0.010010203039393920){
                System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printing
                this.updateNN(this.snake.getLastMove());
                return this.snake.getLastMove();
            }*/


        for(int value: values){
            char returnValue = 'X';
            if(value == 1){
                input = this.aiVision2(directions[(indexOfLastMove + 1) % 4]);
                returnValue = this.testLeftMove(input, headX, headY, objY, objX);
                if(directions[indexOfLastMove] == 'w'){
                    if(this.board[headX][headY - 1] == ' ' || this.board[headX][headY - 1] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 'a'){
                    if(this.board[headX + 1][headY] == ' ' || this.board[headX + 1][headY] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 's'){
                    if(this.board[headX][headY + 1] == ' ' || this.board[headX][headY + 1] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 'd'){
                    if(this.board[headX - 1][headY] == ' ' || this.board[headX - 1][headY] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
            }
            else if(value == 2) {
                input = this.aiVision2(directions[((indexOfLastMove - 1) + 4) % 4]);
                returnValue = this.testRightMove(input, headX, headY, objY, objX);
                if(directions[indexOfLastMove] == 'w'){
                    if(this.board[headX][headY + 1] == ' ' || this.board[headX][headY + 1] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 'a'){
                    if(this.board[headX - 1][headY] == ' ' || this.board[headX - 1][headY] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 's'){
                    if(this.board[headX][headY - 1] == ' ' || this.board[headX][headY - 1] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
                else if(directions[indexOfLastMove] == 'd'){
                    if(this.board[headX + 1][headY] == ' ' || this.board[headX + 1][headY] == '#'){
                        this.neuralNetwork.backPropigate(1);
                        break;
                    }
                }
            }
        }
        return snake.getLastMove();

        /*System.out.println("error");
        System.exit(13);
        return move;*/
    }
    //
    private double[] aiVision() {
        // obstacles to the left, forward, right, angle of snakeHead from objective
        double[] view = new double[5];
        char lastMove = this.snake.getLastMove();
        int headX = this.snake.getSnakeBody().get(0)[0];
        int headY = this.snake.getSnakeBody().get(0)[1];

        if (lastMove == 'w') {
            if (this.board[headX][headY - 1] == ' ') {
                view[0] = 0.0;
            } else if (this.board[headX][headY - 1] == '#') {
                view[0] = 0.0;
            } else {
                view[0] = 1.0;
            }
            if (this.board[headX - 1][headY] == ' ') {
                view[1] = 0.0;
            } else if (this.board[headX - 1][headY] == '#') {
                view[1] = 0.0;
            } else {
                view[1] = 1.0;
            }
            if (this.board[headX][headY + 1] == ' ') {
                view[2] = 0.0;
            } else if (this.board[headX][headY + 1] == '#') {
                view[2] = 0.0;
            } else {
                view[2] = 1.0;
            }
            return view;
        } else if (lastMove == 'a') {
            if (this.board[headX + 1][headY] == ' ') {
                view[0] = 0.0;
            } else if (this.board[headX + 1][headY] == '#') {
                view[0] = 0.0;
            } else {
                view[0] = 1.0;
            }
            if (this.board[headX][headY - 1] == ' ') {
                view[1] = 0.0;
            } else if (this.board[headX][headY - 1] == '#') {
                view[1] = 0.0;
            } else {
                view[1] = 1.0;
            }
            if (this.board[headX - 1][headY] == ' ') {
                view[2] = 0.0;
            } else if (this.board[headX - 1][headY] == '#') {
                view[2] = 0.0;
            } else {
                view[2] = 1.0;
            }
            return view;
        } else if (lastMove == 's') {
            if (this.board[headX][headY + 1] == ' ') {
                view[0] = 0.0;
            } else if (this.board[headX][headY + 1] == '#') {
                view[0] = 0.0;
            } else {
                view[0] = 1.0;
            }
            if (this.board[headX + 1][headY] == ' ') {
                view[1] = 0.0;
            } else if (this.board[headX + 1][headY] == '#') {
                view[1] = 0.0;
            } else {
                view[1] = 1.0;
            }
            if (this.board[headX][headY - 1] == ' ') {
                view[2] = 0.0;
            } else if (this.board[headX][headY - 1] == '#') {
                view[2] = 0.0;
            } else {
                view[2] = 1.0;
            }
            return view;
        } else if (lastMove == 'd') {
            if (this.board[headX - 1][headY] == ' ') {
                view[0] = 0.0;
            } else if (this.board[headX - 1][headY] == '#') {
                view[0] = 0.0;
            } else {
                view[0] = 1.0;
            }
            if (this.board[headX][headY + 1] == ' ') {
                view[1] = 0.0;
            } else if (this.board[headX][headY + 1] == '#') {
                view[1] = 0.0;
            } else {
                view[1] = 1.0;
            }
            if (this.board[headX + 1][headY] == ' ') {
                view[2] = 0.0;
            } else if (this.board[headX + 1][headY] == '#') {
                view[2] = 0.0;
            } else {
                view[2] = 1.0;
            }
            return view;
        }

        return null;
    }

    @SuppressWarnings("Duplicates")
    private double[] aiVision2(char testDirection){
        // obstacles to the left, forward, right, angle of snakeHead from objective
        double[] view = new double[5];
        char lastMove = this.snake.getLastMove();
        int headX = this.snake.getSnakeBody().get(0)[0];
        int headY = this.snake.getSnakeBody().get(0)[1];
        if(testDirection == 'w'){
            // Forward obstacle
            if(this.board[headX - 1][headY] != ' ' && this.board[headX - 1][headY] != '#'){
                view[0] = 1.0;
            }
        }
        else if(testDirection == 'a'){
            if(this.board[headX][headY - 1] != ' ' && this.board[headX][headY - 1] != '#'){
                view[0] = 1.0;
            }
            /*if(headY - 2 > -1){
                if(this.board[headX][headY - 2] != ' ' && this.board[headX][headY - 2] != '#'){
                    view[1] = 1.0;
                }
            }
            else{ view[1] = 1.0; }
            if(headY - 3 > -1){
                if(this.board[headX][headY - 3] != ' ' && this.board[headX][headY - 3] != '#'){
                    view[2] = 1.0;
                }
            }
            else{ view[2] = 1.0; }*/


            //else{ view[0] = 1.0; }
            /*if(this.board[headX + 1][headY] != ' ' && this.board[headX + 1][headY] != '#'){
                view[1] = -1.0;
            }
            //else{ view[1] = 1.0; }
            if(this.board[headX - 1][headY] != ' ' && this.board[headX - 1][headY] != '#'){
                view[2] = -1.0;
            }
            //else{ view[2] = 1.0; }*/

        }
        else if(testDirection == 's'){
            if(this.board[headX + 1][headY] != ' ' && this.board[headX + 1][headY] != '#'){
                view[0] = 1.0;
            }
            /*if(headX + 2 < this.board.length){
                if(this.board[headX + 2][headY] != ' ' && this.board[headX + 2][headY] != '#'){
                    view[1] = 1.0;
                }
            }
            else{ view[1] = 1.0; }
            if(headX + 3 < this.board.length){
                if(this.board[headX + 3][headY] != ' ' && this.board[headX + 3][headY] != '#'){
                    view[2] = 1.0;
                }
            }
            else{ view[2] = 1.0; }*/



            //else{ view[0] = 1.0; }
            /*if(this.board[headX][headY + 1] != ' ' && this.board[headX][headY + 1] != '#'){
                view[1] = -1.0;
            }
            //else{ view[1] = 1.0; }
            if(this.board[headX][headY - 1] != ' ' && this.board[headX][headY - 1] != '#'){
                view[2] = -1.0;
            }
            //else{ view[2] = 1.0; }*/
        }
        else if(testDirection == 'd'){
            if(this.board[headX][headY + 1] != ' ' && this.board[headX][headY + 1] != '#'){
                view[0] = 1.0;
            }
            /*if(headY + 2 < this.board.length){
                if(this.board[headX][headY + 2] != ' ' && this.board[headX][headY + 2] != '#'){
                    view[1] = 1.0;
                }
            }
            else{ view[1] = 1.0; }
            if(headY + 3 < this.board.length){
                if(this.board[headX][headY + 3] != ' ' && this.board[headX][headY + 3] != '#'){
                    view[2] = 1.0;
                }
            }
            else{ view[2] = 1.0; }*/


            //else{ view[0] = 1.0; }
            /*if(this.board[headX - 1][headY] != ' ' && this.board[headX - 1][headY] != '#'){
                view[1] = -1.0;
            }
            //else { view[1] = 1.0; }
            if(this.board[headX + 1][headY] != ' ' && this.board[headX + 1][headY] != '#'){
                view[2] = -1.0;
            }
            //else{ view[2] = 1.0; }*/

        }
        return view;
    }

    private void updateNN(char move){
        int headX = this.snake.getSnakeBody().get(0)[0];
        int headY = this.snake.getSnakeBody().get(0)[1];
        int objX = this.game.getLocationOfObjective()[0];
        int objY = this.game.getLocationOfObjective()[1];
        if(move == 'w'){
            if(this.board[headX - 1][headY] != ' '){
                if(this.board[headX - 1][headY] != '#') {
                    this.neuralNetwork.backPropigate(-1);
                }
                else{
                    this.neuralNetwork.backPropigate(1);
                }
            }
            else{
                if(((headX - objX) * (headX - objX)) > (((headX - 1) - objX) * ((headX - 1) - objX))){
                    this.neuralNetwork.backPropigate(1);
                }
                else{ this.neuralNetwork.backPropigate(-1); }
            }
        }
        else if(move == 'a'){
            if(this.board[headX ][headY - 1] != ' ') {
                if (this.board[headX][headY - 1] != '#') {
                    this.neuralNetwork.backPropigate(-1);
                }
                else{
                    this.neuralNetwork.backPropigate(1);
                }
            }
            else{
                if(((headY - objY) * (headY - objY)) > (((headY - 1) - objY) * ((headY - 1) - objY))){
                    this.neuralNetwork.backPropigate(1);
                }
                else{ this.neuralNetwork.backPropigate(-1); }
            }
        }
        else if(move == 's'){
            if(this.board[headX + 1][headY] != ' '){
                if(this.board[headX + 1][headY] != '#') {
                    this.neuralNetwork.backPropigate(-1);
                }
                else{ this.neuralNetwork.backPropigate(1); }
            }
            else{
                if(((headX - objX) * (headX - objX)) > (((headX + 1) - objX) * ((headX + 1) - objX))){
                    this.neuralNetwork.backPropigate(1);
                }
                else{ this.neuralNetwork.backPropigate(-1); }
            }
        }
        else if(move == 'd'){
            if(this.board[headX][headY + 1] != ' '){
                if(this.board[headX][headY + 1] != '#') {
                    this.neuralNetwork.backPropigate(-1);
                }
                else{ this.neuralNetwork.backPropigate(1); }
            }
            else{
                if(((headY - objY) * (headY - objY)) > (((headY + 1) - objY) * ((headY + 1) - objY))){
                    this.neuralNetwork.backPropigate(1);
                }
                else{ this.neuralNetwork.backPropigate(-1); }
            }

        }
    }

    private char generateRandomMove(int seed, double[] input, int headX, int headY, int objY, int objX){
        if(seed == 0){
            return testForwardMove(input, headX, headY, objY, objX);
        }
        else if(seed == 1){
            return testLeftMove(input, headX, headY, objY, objX);
        }
        else{
            return testRightMove(input, headX, headY, objY, objX);
        }
    }
    private char testForwardMove(double[] input, int headX, int headY, int objY, int objX){
        if(this.snake.getLastMove() == 'w') {
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        else if(this.snake.getLastMove() == 'a'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        else{
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        input[4] = 0;
        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printing
            this.updateNN(this.snake.getLastMove());
            return this.snake.getLastMove();
        }
        return 'X';
    }
    private char testLeftMove(double[] input, int headX, int headY, int objY, int objX){
        if(this.snake.getLastMove() == 'w') {
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else if(this.snake.getLastMove() == 'a') {
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] =Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        else{
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        input[4] = 0;
        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printing
            if(this.snake.getLastMove() == 'w'){
                this.updateNN('w');
                return 'a';
            }
            else if(this.snake.getLastMove() == 'a'){
                this.updateNN('s');
                return 's';
            }
            else if(this.snake.getLastMove() == 's'){
                this.updateNN('d');
                return 'd';
            }
            else if(this.snake.getLastMove() == 'd'){
                this.updateNN('w');
                return 'w';
            }
        }
        return 'X';
    }
    private char testRightMove(double[] input, int headX, int headY, int objY, int objX){
        if(this.snake.getLastMove() == 'w'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY + 1));
        }
        else if(this.snake.getLastMove() == 'a'){
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX - 1));
        }
        else if(this.snake.getLastMove() == 's'){
            input[3] = Math.abs(objY - headY) - Math.abs(objY - (headY - 1));
        }
        else{
            input[3] = Math.abs(objX - headX) - Math.abs(objX - (headX + 1));
        }
        input[4] = 0;

        if(this.neuralNetwork.output(input) > 0){
            System.out.println("output: " + this.neuralNetwork.getOutputNeuron().getOutput()); // Debug printings
            if(this.snake.getLastMove() == 'w'){
                this.updateNN('d');
                return 'd';
            }
            else if(this.snake.getLastMove() == 'a'){
                this.updateNN('w');
                return 'w';
            }
            else if(this.snake.getLastMove() == 's'){
                this.updateNN('a');
                return 'a';
            }
            else if(this.snake.getLastMove() == 'd'){
                this.updateNN('s');
                return 's';
            }
        }
        return 'X';
    }

    public NeuralNet getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNet neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
































