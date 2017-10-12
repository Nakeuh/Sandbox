package spark.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import scala.Tuple2;


public class MapUtils {

	public static <K, V> List<Tuple2<K, V>> mapToPairList(Map<K, V> map) {
		List<Tuple2<K, V>> list = new ArrayList<Tuple2<K, V>>();
		for (Entry<K, V> e : map.entrySet()) {
			list.add(new Tuple2<K, V>(e.getKey(), e.getValue()));
		}
		return list;
	}
}
 