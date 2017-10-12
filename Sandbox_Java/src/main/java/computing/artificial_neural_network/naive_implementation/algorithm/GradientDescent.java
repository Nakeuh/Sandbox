package computing.artificial_neural_network.naive_implementation.algorithm;

import java.util.ArrayList;
import java.util.List;

import computing.artificial_neural_network.naive_implementation.components.Neuron;
import computing.artificial_neural_network.naive_implementation.components.NeuronLayer;
import computing.artificial_neural_network.naive_implementation.components.NeuronNetwork;

public class GradientDescent {

    // TODO
    public static void compute(final NeuronNetwork network, final List<NeuronLayer> inputs, final List<NeuronLayer> expOutputs){
        final double learningRate = 0.005;
        final double objectiveCost = 0.00001;
        final int maxIterationNumber = 50;

        double cost = 1;
        int iteration=0;
        //
        // init weights and biases ?
        //if yes :


        while(cost>objectiveCost &&(maxIterationNumber<0 || iteration<maxIterationNumber)){
            iteration++;

            cost = QuadraticCostFunction.compute(network, inputs, expOutputs);
            final List<Double> vectorV = new ArrayList<Double>();

            for(final NeuronLayer layer: network.getLayers()){
                for(final Neuron n: layer.neurons){
                    // Vector v :  Every bias and weights
                    vectorV.add(n.getBias());
                    for(final Double weight:n.getWeights()){
                       vectorV.add(weight);
                    }
                }
            }

            // TODO Vector v += Delta (v)
            // Delta(v) = -learningRate*Grad(Cost)
            //Grad(Cost) = vector (deriv(Cost,v(1)),deriv(Cost,v(2)),...)


            int j=0;
            for(final NeuronLayer layer: network.getLayers()){
                for(final Neuron n: layer.neurons){
                    // Vector v :  Every bias and weights
                    n.setBias(vectorV.get(j++));
                    for(int k=0;k<n.getWeights().size();k++){
                       n.getWeights().set(k, vectorV.get(j++));
                    }
                }
            }

            System.out.println("Iter : "+iteration+" Cost Function : "+cost+" (Result expected : Cost < "+objectiveCost+")");

        }

    }

}
