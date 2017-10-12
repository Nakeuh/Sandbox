
package computing.artificial_neural_network.naive_implementation.components;

import java.util.ArrayList;
import java.util.List;

public class NeuronLayer {

    public List< Neuron> neurons;

    private List<Double> output;

    public NeuronLayer() {
        this.neurons = new ArrayList<Neuron>();
        this.output = new ArrayList<Double>();
    }

    public NeuronLayer(final List<Neuron> neurons) {
        this.neurons = neurons;
        this.output = new ArrayList<Double>();
    }

    public List<Double> getOutput() {
        return output;
    }

    public void compute() {
        this.output = new ArrayList<Double>();
        for (final Neuron n : neurons) {
            n.compute();
            output.add(n.getOutput());
        }
    }

    public static NeuronLayer doubleListToInputLayer(final List<Double> inputs) {
        final NeuronLayer n = new NeuronLayer();

        for (final Double d : inputs) {
            n.neurons.add(new SigmoidNeuron(d));
        }
        return n;
    }
}
