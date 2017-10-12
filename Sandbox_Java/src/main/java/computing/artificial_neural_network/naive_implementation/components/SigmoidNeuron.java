
package computing.artificial_neural_network.naive_implementation.components;

import java.util.List;

import computing.utils.Calculation;

public class SigmoidNeuron extends Neuron {

    public SigmoidNeuron(final Double bias, final List<Neuron> inputNeurons, final List<Double> weights) {
        super(bias, inputNeurons, weights);
    }

    public SigmoidNeuron(final Double inputValue) {
        super(inputValue);
    }

    @Override
    public void compute() {
        if (inputNeurons != null && getWeights() != null) {

            double sum = 0;
            int i = 0;

            for (final Neuron n : inputNeurons) {
                sum += n.getOutput() * getWeights().get(i);
                i++;
            }

            setOutput(Calculation.logisticFunction(sum + getBias()));
        }
    }

}
