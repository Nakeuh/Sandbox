package spark.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkUtils implements Constantes{
	
	public static JavaSparkContext initContext(String appName){
		// Dependency needed because of Windows
		System.setProperty("hadoop.home.dir", WINUTIL_PATH);

		// Cut some Spark logs
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		// Initialize Spark Streaming context
		SparkConf conf = new SparkConf().setAppName(appName).setMaster("local[*]");
		return new JavaSparkContext(conf);
	}
	
	public static JavaStreamingContext initStreamingContext(String appName, Duration bachDuration){
		return new JavaStreamingContext(initContext(appName), bachDuration);
	}

}
