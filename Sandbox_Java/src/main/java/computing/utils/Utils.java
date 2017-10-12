
package computing.utils;

import java.util.List;

public class Utils {
    
    public static boolean equals(List<Double> list1, List<Double> list2) {
        Boolean equals = true;
        for (int i = 0; i < list1.size(); i++) {
            equals = equals && list1.get(i).equals(list2.get(i));
        }
        return equals;
    }
}
