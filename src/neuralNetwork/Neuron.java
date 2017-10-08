package neuralNetwork;

/**
 * Created by andes on 9/21/2017.
 */
public class Neuron {
    /*
        neurons are a linear function, composed with an activation function
        i.e, for each neuron in our netwokr, N, we can compute the output of N via
            N = Max { 0  ,   w_1*x_1 + . . .  + w_n*x_n + b }
        where, w's are the neurons weights, x's are the input, and b is the bias (just another weight that isnt multiplied by an input)

        The Max{} function just allows us to 'not fire' the nueron if its output is below zero
        This will cuase neurons with low outputs to have no affect on the neurons in the next layer.


        Each neuron will have its weights updated, w_i, via backporpigation

    */
    private double[] weights;

    //Derived fields
    private double output;
    private Neuron[] input;
    private Neuron[] forwardConnection;
    private double[] partials;

    private double nonMaxedOutput;

    public Neuron(double[] weights){
        this.weights = weights;
    }

    // Used to create input neurons that do not have weights.
    public Neuron(){}

    /**
     *  Used relu activation on a linear function representing the neuron
     *  Saves output and input layer
     * @param input pervious layer of neurons in the network
     * @return max(0, output)
     */
    public double fireNeuron(Neuron[] input){
        double output = 0.0;
        this.input = input;
        for(int i = 0; i < this.input.length; i ++){
            output += this.input[i].getOutput() * this.weights[i];
        }
        this.nonMaxedOutput = output + this.weights[this.weights.length - 1];
        //this.output = Math.max(0, output + this.weights[this.weights.length - 1]);
        if(this.nonMaxedOutput >= 0) {
            this.output = this.nonMaxedOutput;
        }
        else{ this.output = this.nonMaxedOutput * 0.01; }
        return this.output;
    }

    /**
     * updates the weights of the neuron using a given step size and a derived error function
     * The neural network can be represented by a function, call it NN,
     * then each weights, w_i, is updated by dNN/dw_i the partial derivative of NN with respect to w_i;
     * @param stepSize given step size
     * @param errorValue difference between NN output and the optimal value
     */
    public void updateWeights(double stepSize, double errorValue){
        for(int i = 0; i < this.weights.length; i++){
            this.weights[i] += (this.partials[i] * stepSize * errorValue);
        }
    }

    public void incramentWeights(double value){
        for(double weight: this.weights){
            weight += value;
        }
    }

    public double[] getWeights() {return weights;}
    public void setWeights(double[] weights) {this.weights = weights;}

    public double getOutput() {return output;}
    public void setOutput(double output) {this.output = output;}

    public Neuron[] getInput() {return input;}
    public void setInput(Neuron[] input) {this.input = input;}

    public double[] getPartials() {return partials;}
    public void setPartials(double[] partials) {this.partials = partials;}

    public Neuron[] getForwardConnection() {
        return forwardConnection;
    }

    public void setForwardConnection(Neuron[] forwardConnection) {
        this.forwardConnection = forwardConnection;
    }

    public double getNonMaxedOutput() {
        return nonMaxedOutput;
    }

    public void setNonMaxedOutput(double nonMaxedOutput) {
        this.nonMaxedOutput = nonMaxedOutput;
    }
}
