
package computing.testing_zone;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import computing.artificial_neural_network.compact_implementation.NeuralNetwork;
import computing.artificial_neural_network.mnist.BinaryImage;
import computing.artificial_neural_network.mnist.MnistDataExtract;
import computing.clustering.components.Element;
import computing.clustering.kmeans.KMeans;

public class Main {

    public static void main(final String[] args) {

        testNeuralNetwork();
    }

    public static void testDisplayHandwrittenNumbers() {
        final List<BinaryImage> images =
            MnistDataExtract.extract("ressources/test/t10k-images-idx3-ubyte", "ressources/test/t10k-labels-idx1-ubyte");
        for (final BinaryImage i : images) {
            i.displayImage(1, 2);
        }
    }

    public static void testKmeans() {
        final List<Element> datas = new ArrayList<Element>();
        final int nbElement = 3000;

        for (int i = 0; i < nbElement; i++) {
            datas.add(Element.randomDoubleElement(i, 50));
        }

        KMeans.clusterize(2, datas);
    }

    public static void testNeuralNetwork() {
        final int[] sizes = { 784, 30, 10 };
        final NeuralNetwork network = new NeuralNetwork(sizes);

        final List<Pair<double[], double[]>> imagesTest = MnistDataExtract
                        .extractInPair("ressources/test/t10k-images-idx3-ubyte", "ressources/test/t10k-labels-idx1-ubyte");
        final List<Pair<double[], double[]>> imagesTraining = MnistDataExtract
                        .extractInPair("ressources/training/train-images-idx3-ubyte", "ressources/training/train-labels-idx1-ubyte");

        final int epoch = 30;
        final int batchSize = 10;
        final double learningRate = 3;

        network.stochasticGradientDescent(imagesTraining, epoch, batchSize, learningRate,imagesTest);
    }
}
