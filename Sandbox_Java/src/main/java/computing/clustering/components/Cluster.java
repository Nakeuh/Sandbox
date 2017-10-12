
package computing.clustering.components;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    public List<Element> datas;

    public List<Double> center;

    public Cluster() {
        datas = new ArrayList<Element>();
        center = new ArrayList<Double>();
    }

    public void affiche(int limit) {
        System.out.println("Centre : " + this.center);
        System.out.println(this.datas.size() + " elements");

        if (this.datas.size() <= limit) {
            for (Element dArr : this.datas) {
                System.out.println("Element :" + dArr.getName());
            }
        }
    }
}
