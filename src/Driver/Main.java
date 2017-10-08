package Driver;

import game.Game;
import neuralNetwork.NeuralNet;
import players.AiPlayer;
import players.Player;

import java.util.ArrayList;

/**
 * Created by andes on 9/17/2017.
 */
public class Main {
    public static void main(String[] args){

        /*
            Sets up neural net
            NeuralNet( double stepSize, int numberOfHiddenLayers, int numberOfNeuronsInLayer, double[] inputLayer)

            Less hidden layers and more neurons perlayer => less complex thought, but faster training
            For a simpel game such as snake 2x25 is good enough to reach

            Current tests with 4x10 have an average snake length of 17
                -sanke will often run into its own tail inorder to get closer to the objective
                -if the snake is has no possible way to get closer to the objective it will refuse to make moves
                    ** most likely both problems would be solved with more training or a more complex structure.


                To see the modle train much faster, increase the step size.
                This could however, cause the neuralnetwork to "step" past the optimal spot and cuase it it stop learning.
         */
        NeuralNet neuralNetwork = new NeuralNet(0.000001,4, 10, new double[]{ 1, 1, 1, 1, 1});

        /*
            ****** TO MAKE A NEW NEURAL NETWORK, COMMENT OUT THE LINE "neuralNetwork.pullWeightsFromFile("Weights4x10_17.txt");"*****
            ****** Then change the file name in the line "neuralNetwork.pushWeightsToFile("Weights4x10_17.txt");" at the bottom of this class, to the file you would like to create.

            File to pull from, these weights represent linear function of each neuron

            the neuron N will be
                N = Max{ 0,  w_1*x_1 + . . .  + w_n*x_n + b}
            where x_i are the inputs, w_i are the weights pulled, and b is the bias also from the weight file
            Max returns the maximum of 0 and the linear combination
                -i.e. if the neurons output is negative, it does not fire
         */
        neuralNetwork.pullWeightsFromFile("Weights4x10_17.txt");
        // Ensures the correct weights were pulled
        System.out.println(neuralNetwork.toString());

        // List of snake lengths durring training, for book keeping only
        ArrayList<Integer> snakeSizes = new ArrayList<Integer>();

        // Create a new AI player equiped the neuralnetwork we just created via the weights file.
        Player aiPlayer = new AiPlayer(neuralNetwork);

        // Amount of games to train the neuralnetwork on
        int numberOfGamesToPlay = 10;

        // Training loop, also stored the size of all snakes over 3 and reports the number of such snakes and their average length
        for(int i = 0; i < numberOfGamesToPlay; i++) {
            Game game = new Game(aiPlayer);
            aiPlayer.setGame(game);
            game.playGame(1);
            if(aiPlayer.getSnake().getSnakeBody().size() > 3){
                snakeSizes.add(aiPlayer.getSnake().getSnakeBody().size());
            }
            System.out.println(aiPlayer.getNeuralNetwork().getOutputNeuron().getOutput());
            System.out.println("games played " + i + "/" + numberOfGamesToPlay);
        }
        double average = 0;
        for(Integer snakeSize: snakeSizes){
            average += (double)snakeSize;
        }
        System.out.println("Number of snakes: " + snakeSizes.size());
        System.out.println("Average length: " + average/ (double)(snakeSizes.size()));

        // Stores the updated weights after training

        /*
            *********MAKE SURE THIS THE FILE YOU WANT TO WRITE TO********
            *********IT WILL OVERWRITE ANY STORED WEIGHTS IN THE FILE****
         */
        neuralNetwork.pushWeightsToFile("Weights4x10_17.txt");
    }
}
