package spark.modulary_data_processing_architecture.example;

import java.util.List;

import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.json.JSONArray;
import org.json.JSONObject;

import scala.Tuple2;
import spark.modulary_data_processing_architecture.core.DataProcessor;
import spark.modulary_data_processing_architecture.example.modules.paris_market.MarketModule;
import spark.modulary_data_processing_architecture.example.modules.twitter.TwitterModule;
import spark.modulary_data_processing_architecture.example.modules.utils.Constantes;
import spark.modulary_data_processing_architecture.example.modules.utils.SparkUtils;
import spark.modulary_data_processing_architecture.example.modules.utils.Utils;

public class Main implements Constantes {

	public static void main(String[] args) {

		JavaStreamingContext ssc = SparkUtils.initStreamingContext("SparkContext",
				Durations.seconds(SPARK_BATCH_DURATION));

		DataProcessor mm = new DataProcessor();

		mm.addModule(new TwitterModule(ssc, 1, true));
		mm.addModule(new MarketModule(ssc, 0.5));

		mm.getMeanedResultsByLabel(10, 10).foreachRDD(rdd -> {
			sendSensiblesAreaLayer(rdd.collect());
		});

		ssc.start();

		try {
			ssc.awaitTermination();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void sendSensiblesAreaLayer(List<Tuple2<String, Double>> list) {
		JSONArray array = new JSONArray();
		for (Tuple2<String, Double> e : list) {
			JSONObject data = new JSONObject();
			data.put("id", e._1);
			data.put("score", e._2);
			array.put(data);
		}
		Utils.putJson(URL_THREATS, array.toString());
	}

}
