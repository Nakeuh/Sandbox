package spark.modulary_data_processing_architecture.example.modules.utils;

import twitter4j.GeoLocation;

public interface Constantes {
	public static final int SPARK_BATCH_DURATION = 5;
	public static final int SIMULATOR_FREQUENCY = 10;
	public static final int SIMULATOR_TIME_FACTOR = 1;
	
	//Extent width: 2,436815 - 2,255140: 0,181675
	//Extent height: 48,911166 - 48,800928: 0,110238
	public static final GeoLocation PARIS_northWestLimit = new GeoLocation(48.911166, 2.255140);
	public static final GeoLocation PARIS_southEastLimit = new GeoLocation(48.77490975, 2.436815);
	//public static final GeoLocation PARIS_southEastLimit = new GeoLocation(48.800928, 2.436815);
	
	public static final int NB_CASE_PER_LINE = 20;
	
	//Taille d'une case, en degrés: 0,00908375
	//Si 15 cases de hauteur, nelle hauteur d'extent: 0,13625625
	//Borne Sud: 48,77490975

	public static final String TRAINING_DATA_PATH = "C:/Users/Public/SoftwareContest/trainingDatas";
	public static final String SIMULATION_DATA_PATH = "C:/Users/Public/SoftwareContest/simulatedDatas";
	public static final String SIMULATION_DATA_PATH_SPARK = "file:///Users/Public/SoftwareContest/simulatedDatas";
	public static final String TRAINING_DATA_PATH_SPARK = "file:///Users/Public/SoftwareContest/trainingDatas";

	public static final String SAMPLE_BASIC_TWEETS = "C:/Users/Public/SoftwareContest/sampleDatas/basicTweets.csv";
	public static final String SAMPLE_PANICK_TWEETS = "C:/Users/Public/SoftwareContest/sampleDatas/fearTweets.csv";
	
	public static final String market_DATA_PATH = "resources/marches-paris.json";
	
	public static final String URL_THREATS = "http://localhost:5001/api/threats";
	public static final String URL_INCIDENT = "http://localhost:5001/api/incidents";
	public static final String URL_UNIT = "http://localhost:5001/api/patrols";
	public static final String URL_ENDPOINT_ASSIGNMENT = "/assignment";
	
	// If we have more than 'maxTweets' for a zone, the score is reliable
	// at 100%
	public static final int maxTweets = 15;
	
	// If we have 'maxMarket' or more market open for a zone, the score is 1
	public static final int maxMarket = 5;
	

}
