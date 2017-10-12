package computing.artificial_neural_network.naive_implementation.components;

import java.util.List;

public abstract class Neuron {

    protected List<Neuron> inputNeurons;

    private List<Double> weights;

    private Double bias;

    private Double output;

    public Neuron(final Double bias, final List<Neuron> inputNeurons, final List<Double> weights){
        this.bias = bias;
        this.inputNeurons = inputNeurons;
        this.weights = weights;
        this.output=null;
    }

    public Neuron(final Double inputValue){
        this(null,null,null);
        this.output=inputValue;
    }

    public abstract void compute();

    public Double getOutput() {
        return output;
    }

    public void setOutput(final Double output) {
        this.output = output;
    }


    public List<Double> getWeights() {
        return weights;
    }


    public void setWeights(final List<Double> weights) {
        this.weights = weights;
    }


    public Double getBias() {
        return bias;
    }


    public void setBias(final Double bias) {
        this.bias = bias;
    }



}
