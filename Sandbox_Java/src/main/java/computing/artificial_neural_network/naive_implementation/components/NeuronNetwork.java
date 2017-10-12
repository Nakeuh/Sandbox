
package computing.artificial_neural_network.naive_implementation.components;

import java.util.List;

public class NeuronNetwork {

    private List<NeuronLayer> layers;

    private NeuronLayer input;

    private List<Double> output;

    public NeuronNetwork(final NeuronLayer input, final List<NeuronLayer> layers) {
        this.input = input;
        this.layers = layers;
    }

    public void compute() {
        for (final NeuronLayer l : layers) {
            l.compute();
            output = l.getOutput();
        }
    }

    public List<Double> getOutput() {
        return output;
    }

    public NeuronLayer getInput() {
        return input;
    }

    public void setInput(final NeuronLayer input) {
        this.input = input;
    }

    public List<NeuronLayer> getLayers() {
        return layers;
    }

    public void setLayers(final List<NeuronLayer> layers) {
        this.layers = layers;
    }

}
