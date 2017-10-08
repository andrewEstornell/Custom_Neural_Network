package neuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by andes on 9/22/2017.
 */
public class NeuralNet {




    /*
        This neuralnetowrk uses neurons with linear functions and RELU activation, i.e each neuraon N is
            N = max{ 0, w_1*x_1 + . . .  + w_n*x_n + b}
        It will have a single output neuron.
        Since there is only one output neuron the neuralnet works by essentialy saying 'yes' or no' to a given input
        if the output is 0, we say no, if the output is greater than 0, we say yes.

        To update the weights, we do gradient back propigation.
        Let O be the output neuron, then each weight is updated via

            w_i += (stepSize)*(error)* dO/dw_i

        Where stepSize is user given value that determines how large of a "step" we take when udpateing the weights
              error is the value of the decision (not nessisarily an error)
                    -if the output of the neuralnet was positive when it should have been 0, then the error is -1.
                    -if the output was psoitive and it should have been positive then error is 1.
              dO/dw_i  is the deriviative of the output neuron with respect to the weight w_i



        The model starts with random decision and then as the weights are updated slowly learns "good" decisions.
     */

    private Neuron[][] hiddenLayers;      private int numbOfHiddenLayers;
    private Neuron outputNeuron;          private int numbOfNodesInLayer;
    private Neuron[] inputLayer;

    private double[][] paritals;

    // Determines how much we change the weights in back propagation, to large and we may miss the mark, too small and we may have to train for too long
    private double stepSize;

    // Helper field: used to store the partial dNN/dw_ijk
    private double partial;

    public NeuralNet(double stepSize, int numbOfHiddenLayer, int numbOfNodesInLayer, double[] inputLayer){

        // Field initialization
        this.numbOfHiddenLayers = numbOfHiddenLayer;
        this.numbOfNodesInLayer = numbOfNodesInLayer;
        this.hiddenLayers = new Neuron[this.numbOfHiddenLayers + 1][];

        this.inputLayer = new Neuron[inputLayer.length];
        for(int i = 0; i < inputLayer.length; i++){
            this.inputLayer[i] = new Neuron();
            this.inputLayer[i].setOutput(inputLayer[i]);
        }
        this.stepSize = stepSize;

        // Sets up a new neural net of random weights, this can be over written later by pulling weights from a txt file
        Random random = new Random();

        int numbOfWeightsInNode = inputLayer.length + 1;
        for(int i = 0; i < this.numbOfHiddenLayers; i++){
            double[][] tempLayer = new double[this.numbOfNodesInLayer][];
            this.hiddenLayers[i] = new Neuron[this.numbOfNodesInLayer];
            for(int j = 0; j < this.numbOfNodesInLayer; j++){
                double[] tempNode = new double[numbOfWeightsInNode];
                for(int k = 0; k < numbOfWeightsInNode; k++){
                    tempNode[k] = 2 * random.nextDouble() - 1;
                }
                this.hiddenLayers[i][j] = new Neuron(tempNode);
            }
            numbOfWeightsInNode = tempLayer.length + 1;
        }
        numbOfWeightsInNode = this.hiddenLayers[this.hiddenLayers.length - 2].length + 1;
        double[] tempNode = new double[numbOfWeightsInNode];
        for(int i = 0; i < numbOfWeightsInNode; i++){
            tempNode[i] = 2 * random.nextDouble() - 1;
        }
        this.hiddenLayers[this.hiddenLayers.length - 1] = new Neuron[1];
        this.hiddenLayers[this.hiddenLayers.length - 1][0] = new Neuron(tempNode);
        this.outputNeuron = this.hiddenLayers[this.hiddenLayers.length - 1][0];
    }

    public void pullWeightsFromFile(String fileName){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int layer = 0;
        int node= 0;
        int weight = 0;
        double[] tempNode = new double[this.hiddenLayers[0][0].getWeights().length];
        while(scanner.hasNextLine()){

            String line = scanner.nextLine();
            if(line.compareTo("*") == 0){
                layer++;
                node = 0;
                tempNode = new double[this.hiddenLayers[layer][node].getWeights().length];
            }
            else if(line.compareTo(",") == 0){
                this.hiddenLayers[layer][node].setWeights(tempNode);
                weight = 0;
                tempNode = new double[this.hiddenLayers[layer][node].getWeights().length];
                node++;
            }
            else if(line.compareTo("__") == 0){
                weight = 0;
                tempNode = new double[this.outputNeuron.getWeights().length];
                while(scanner.hasNextLine()){
                    line = scanner.nextLine();
                    //System.out.println(tempNode.length + " " + layer + " " + node + " " + weight); Debug printout
                    tempNode[weight] = Double.parseDouble(line);
                    weight ++;
                }
                this.outputNeuron.setWeights(tempNode);
                break;
            }
            else{
                try {
                    //System.out.println(tempNode.length + " " + layer + " " + node + " " + weight); Debug printout
                    tempNode[weight] = Double.parseDouble(line);
                    weight++;
                }catch (NumberFormatException e){
                    System.out.println(line + " is not a double");
                }
            }
        }
        scanner.close();
    }

    public void pushWeightsToFile(String fileName){
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < this.hiddenLayers.length - 1; i++){
            for(int j = 0; j < this.hiddenLayers[i].length; j++){
                for(double weight: this.hiddenLayers[i][j].getWeights()){
                    printWriter.println(weight);
                }
                printWriter.println(",");
            }
            printWriter.println("*");
        }

        printWriter.println("__");
        for(double weight: outputNeuron.getWeights()){
            printWriter.println(weight);
        }
        printWriter.close();
    }

    public double output(double[] input){
        Neuron[] inputLayer = new Neuron[input.length];
        for(int i = 0; i < input.length; i++){
            inputLayer[i] = new Neuron();
            inputLayer[i].setOutput(input[i]);
        }

        for(int i = 0; i < this.hiddenLayers.length; i++){
            if(i > 0){
                inputLayer = this.hiddenLayers[i - 1];
            }
            for(Neuron neuron: this.hiddenLayers[i]){
                neuron.fireNeuron(inputLayer);
            }
        }
        return this.outputNeuron.getOutput();
    }

    /**
     * Calculates dO/dw_ijk and stores it in the respective neuron
     *      Where dO/dw_ijk is the derivative of the output neuron with respect w_ijk,
     *      w_ijk = k'th weight of j'th neuron in i'th hidden layer
     */
    private void storePartials(){
        for(int i = 0; i < this.hiddenLayers.length; i++){
            for(int j = 0; j < this.hiddenLayers[i].length; j++) {
                double[] weights = this.hiddenLayers[i][j].getWeights();
                double[] partials = new double[weights.length];
                for (int k = 0; k < weights.length - 1; k++) {
                    this.partial = 0.0;
                    this.calculatePartial(i, j, k);
                    partials[k] = this.partial;
                }
                if (i < this.hiddenLayers.length - 1) {
                    this.hiddenLayers[i][j].setPartials(partials);
                }
            }
        }
    }

    private void calculatePartial(int x, int y, int z){

        if(x == this.hiddenLayers.length - 1){
            double[] partials = new double[this.hiddenLayers[x][0].getWeights().length];
            for(int i = 0; i < partials.length - 1; i++) {
                if(this.hiddenLayers[x][0].getOutput() >= 0) {
                    partials[i] = this.hiddenLayers[x][0].getInput()[i].getOutput();
                }
                else{
                    partials[i] = this.hiddenLayers[x][0].getInput()[i].getOutput() * 0.01;
                }
            }
            partials[partials.length - 1] = 1;
            this.hiddenLayers[x][0].setPartials(partials);
        }
        else {
            for (int i = 0; i < this.hiddenLayers[x + 1].length; i++) {
                if (z < this.hiddenLayers[x][y].getWeights().length - 1) {
                    /*
                        Comment code can be included when switching neurons to ReLU activation
                     */
                    if (this.hiddenLayers[x][y].getNonMaxedOutput() >= 0) {
                        pathFromNodeToOutput(x + 1, i, y, this.hiddenLayers[x][y].getInput()[z].getOutput());
                    }
                    else{
                        pathFromNodeToOutput(x + 1, i, y, this.hiddenLayers[x][y].getInput()[z].getOutput() * 0.01);
                    }
                }
                else {
                    if (this.hiddenLayers[x][y].getNonMaxedOutput() >= 0) {
                        pathFromNodeToOutput(x + 1, i, y, 1.0);
                    }
                    else{
                        pathFromNodeToOutput(x + 1, i, y, 0.01);
                    }
                }
            }
        }

    }

    private void pathFromNodeToOutput(int x, int y, int z, double currentValue){

        if(x == this.hiddenLayers.length - 1){
            if(this.hiddenLayers[x][0].getOutput() >= 0) {
                this.partial += currentValue * this.outputNeuron.getWeights()[y];
            }
            else{
                this.partial += currentValue * this.outputNeuron.getWeights()[y] * 0.01;
            }
        }
        else {
            for (int j = 0; j < this.hiddenLayers[x].length; j++) {
                /*
                        Comment code can be included when switching neurons to ReLU activation
                     */
                if (this.hiddenLayers[x][y].getOutput() >= 0) {
                    pathFromNodeToOutput(x + 1, j, y, currentValue * this.hiddenLayers[x][y].getWeights()[z]);
                } else {
                    pathFromNodeToOutput(x + 1, j, y, currentValue * this.hiddenLayers[x][y].getWeights()[z] * 0.01);
                }
            }
        }
    }

    public void backPropigate(double error){
        this.storePartials();
        for(Neuron[] layer: this.hiddenLayers){
            for(Neuron neuron: layer){
                neuron.updateWeights(this.stepSize, error);
            }
        }

    }


    public Neuron[][] getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(Neuron[][] hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }

    public int getNumbOfHiddenLayers() {
        return numbOfHiddenLayers;
    }

    public void setNumbOfHiddenLayers(int numbOfHiddenLayers) {
        this.numbOfHiddenLayers = numbOfHiddenLayers;
    }

    public Neuron getOutputNeuron() {
        return outputNeuron;
    }

    public void setOutputNeuron(Neuron outputNeuron) {
        this.outputNeuron = outputNeuron;
    }

    public int getNumbOfNodesInLayer() {
        return numbOfNodesInLayer;
    }

    public void setNumbOfNodesInLayer(int numbOfNodesInLayer) {
        this.numbOfNodesInLayer = numbOfNodesInLayer;
    }

    public Neuron[] getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(Neuron[] inputLayer) {
        this.inputLayer = inputLayer;
    }

    public double[][] getParitals() {
        return paritals;
    }

    public void setParitals(double[][] paritals) {
        this.paritals = paritals;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    public double getPartial() {
        return partial;
    }

    public void setPartial(double partial) {
        this.partial = partial;
    }


}











