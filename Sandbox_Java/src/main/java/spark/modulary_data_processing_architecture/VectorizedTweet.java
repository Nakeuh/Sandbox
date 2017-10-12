package spark.modulary_data_processing_architecture;

import org.apache.spark.mllib.linalg.Vector;

public class VectorizedTweet<LabelType> {
	
	private LabelType label;
	private Vector features;
	
	public VectorizedTweet(LabelType label, Vector features) {
		super();
		this.label = label;
		this.features = features;
	}
	
	public LabelType label() {
		return label;
	}
	public void setLabel(LabelType label) {
		this.label = label;
	}
	public Vector features() {
		return features;
	}
	public void setFeatures(Vector features) {
		this.features = features;
	}
	
	

}
