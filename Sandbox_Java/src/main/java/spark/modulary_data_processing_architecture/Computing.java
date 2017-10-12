package spark.modulary_data_processing_architecture;


import java.util.List;

import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.json.JSONArray;
import org.json.JSONObject;

import scala.Tuple2;
import spark.utils.Constantes;
import spark.utils.SparkUtils;
import spark.utils.Utils;

public class Computing implements Constantes{
	
	public static void main(String[] args) {

		JavaStreamingContext ssc = SparkUtils.initStreamingContext("Thales Software Contest 2017",
				Durations.seconds(SPARK_BATCH_DURATION));

		TwitterModule twitterModule = new TwitterModule(ssc, 1, true);
		JavaPairDStream<String, Double> tweetStream = twitterModule.compute().window(Durations.seconds(10), Durations.seconds(10));

		MarketModule marchesModule = new MarketModule(ssc, 0.5);
		JavaPairDStream<String, Double> marchesStream = marchesModule.compute().window(Durations.seconds(10), Durations.seconds(10));

		tweetStream
			.union(marchesStream)
			.mapToPair(t->new Tuple2<String,Tuple2<Double,Integer>>(t._1,new Tuple2<Double,Integer>(t._2,1)))
			.reduceByKey((a,b)-> new Tuple2<Double,Integer>(a._1+b._1,a._2+b._2))
			.mapToPair(t->new Tuple2<String,Double>(t._1,t._2._1/t._2._2))
			.foreachRDD(rdd -> {
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
