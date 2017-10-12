package computing.artificial_neural_network.naive_implementation.exercises;

import java.util.ArrayList;
import java.util.List;

import computing.artificial_neural_network.mnist.BinaryImage;
import computing.artificial_neural_network.mnist.MnistDataExtract;
import computing.artificial_neural_network.naive_implementation.algorithm.GradientDescent;
import computing.artificial_neural_network.naive_implementation.components.Neuron;
import computing.artificial_neural_network.naive_implementation.components.NeuronLayer;
import computing.artificial_neural_network.naive_implementation.components.NeuronNetwork;
import computing.artificial_neural_network.naive_implementation.components.SigmoidNeuron;

/**
 * Exercise from Michael A. Nielsen, "Neural Networks and Deep Learning", Determination Press, 2015
 *  http://neuralnetworksanddeeplearning.com/chap1.html
 */

/**
 *
 *
 *
 */

public class Exercise3 {

    private static NeuronNetwork initNetwork(final int nbInputsNeuron){
        NeuronNetwork n=null ;
        NeuronLayer NeuronLayer;

        final List<NeuronLayer> layers = new ArrayList<NeuronLayer>();
        final double b = -5; // bias TODO remove

        final List<Neuron> inputList = new ArrayList<Neuron>();
        List<Double>weights = new ArrayList<Double>();
        for(int i=0;i<nbInputsNeuron;i++){
            inputList.add(new SigmoidNeuron(0d));
            weights.add(0d);
        }
        NeuronLayer = new NeuronLayer(inputList);

        // Hidden layer 1
        List<Neuron> neurons = new ArrayList<Neuron>();

        neurons.add(new SigmoidNeuron(b,NeuronLayer.neurons,weights));
        neurons.add(new SigmoidNeuron(b,NeuronLayer.neurons,weights));
        neurons.add(new SigmoidNeuron(b,NeuronLayer.neurons,weights));
        neurons.add(new SigmoidNeuron(b,NeuronLayer.neurons,weights));
        neurons.add(new SigmoidNeuron(b,NeuronLayer.neurons,weights));

        layers.add(new NeuronLayer(neurons));

        // Hidden layer 2
        weights = new ArrayList<Double>();
        for(int i=0;i<5;i++){
            weights.add(0d);
        }

         neurons = new ArrayList<Neuron>();

        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));
        neurons.add(new SigmoidNeuron(b,layers.get(0).neurons,weights));

        layers.add(new NeuronLayer(neurons));

        n = new NeuronNetwork(NeuronLayer,layers);

        return n;
    }

    private static NeuronLayer labelToNeuronLayer(final int label){

        final List<Double> list = new ArrayList<Double>();
        for(int i=0;i<10;i++){
            if(i==label){
                list.add(1d);
            }else{
                list.add(0d);
            }
        }

        final NeuronLayer layer = NeuronLayer.doubleListToInputLayer(list);

        return layer;

    }

    public static void main(final String[] args){
        final List<BinaryImage> list = MnistDataExtract.extract("ressources/test/t10k-images-idx3-ubyte","ressources/test/t10k-labels-idx1-ubyte");
        final NeuronNetwork n = initNetwork(list.get(0).getHeight()*list.get(0).getWidth());

        final List<NeuronLayer> inputs = new ArrayList<NeuronLayer>();
        final List<NeuronLayer> outputs = new ArrayList<NeuronLayer>();

         for(final BinaryImage image:list){
             final int i=0;

      //      image.displayImage(1, 2);
            final List<Neuron> neuron = new ArrayList<Neuron>();
            for(final double pixel: image.getImageAsList() ){
                neuron.add(new SigmoidNeuron(pixel));
            }
            inputs.add(new NeuronLayer(neuron));
            outputs.add(labelToNeuronLayer(image.getLabel()));
        }
        GradientDescent.compute(n,inputs,outputs);
    }


}
