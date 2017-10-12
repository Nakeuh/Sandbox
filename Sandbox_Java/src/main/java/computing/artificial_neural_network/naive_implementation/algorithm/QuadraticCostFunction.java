package computing.artificial_neural_network.naive_implementation.algorithm;

import java.util.List;

import computing.artificial_neural_network.naive_implementation.components.NeuronLayer;
import computing.artificial_neural_network.naive_implementation.components.NeuronNetwork;

public class QuadraticCostFunction {

    /**
     * Quadratic Cost Function or Mean squared error (MSE)
     *
     * @param network network we want to train
     * @param inputs all training inputs
     * @param expOutputs all expected ouputs for training inputs
     * @return
     */
    public static double compute(final NeuronNetwork network, final List<NeuronLayer> inputs, final List<NeuronLayer> expOutputs) {
        final double  n = inputs.size();

        double sum = 0;

        for (int i = 0; i < n; i++) {
            network.setInput(inputs.get(i));
            network.compute();

            final List<Double> expectedOutput = expOutputs.get(i).getOutput();
            final List<Double> realOutput = network.getOutput();

            // Square Difference between the two vectors
            for(int j=0; j< expectedOutput.size();j++){
                sum+=Math.pow(expectedOutput.get(j)-realOutput.get(j),2);
            }
        }

        final double cost = 1/(2*n) * sum;
        return cost;
    }

}
