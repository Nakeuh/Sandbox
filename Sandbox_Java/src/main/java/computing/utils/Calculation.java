package computing.utils;

import java.util.List;

public class Calculation {

    public static double vectorEuclideanDistance(final List<Double> e1, final List<Double> e2) {
        double distance = 0;
        for (int i = 0; i < e1.size(); i++) {
            distance += Math.pow(e1.get(i) - e2.get(i), 2);
        }

        if (distance > 0)
            distance = Math.sqrt(distance);

        return distance;
    }

    /**
    * To maximize this function, take a large value for 'd' :
    * d > 2 => result > 0.9
    * And logically, to minimize this function, take a small value for 'd'
    * d < -2 => result < 0.1
    */
    public static double logisticFunction(final Double d) {
        return 1 / (1 + Math.exp(-d));
    }

}
