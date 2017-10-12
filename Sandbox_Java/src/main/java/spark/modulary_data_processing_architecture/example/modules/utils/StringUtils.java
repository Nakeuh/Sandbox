package spark.modulary_data_processing_architecture.example.modules.utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {
	public static List<String> customSplit(String message) {
		return Arrays.asList(message.toLowerCase().replaceAll("[^a-zA-Z ]", "")
				.replaceAll("( of | a | the | be | to | and | in | that | have | it | for | not | at )", " ")
				.split(" "));
	}
}
