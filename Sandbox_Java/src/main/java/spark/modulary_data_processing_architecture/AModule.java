package spark.modulary_data_processing_architecture;

import java.io.Serializable;

import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public abstract class AModule implements Serializable{
	
	protected JavaStreamingContext ssc;
	protected double pertinence; 
	
	public AModule(JavaStreamingContext ssc, double pertinence){
		this.ssc = ssc;
		this.pertinence=pertinence;
	}
		
	/**
	 * A Module return a Stream with a score between 0 and 1 for each Zones
	 * @return
	 */
	protected abstract JavaPairDStream<String, Double> computeStream();
	
	public JavaPairDStream<String, Double> compute(){
		double pert = pertinence;
		return computeStream().mapValues(score -> score*pert);
	}

	public double getPertinence() {
		return pertinence;
	}
	
}
