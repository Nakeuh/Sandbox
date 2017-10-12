package spark.modulary_data_processing_architecture.example.modules.paris_market;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import spark.modulary_data_processing_architecture.example.modules.utils.Constantes;

/**
 * Receiver that will get paris market datas from a file. (datas are supposed to
 * be refreshed by a simulator)
 * 
 * @author victor
 *
 */
public class CustomMarchesStream extends Receiver<String> implements Constantes {

	private static final long serialVersionUID = 1L;

	public CustomMarchesStream() {
		super(StorageLevel.MEMORY_AND_DISK_2());
	}

	@Override
	public void onStart() {
		new Thread() {
			@Override
			public void run() {
				receiveMarketDatas();
			}
		}.start();

	}

	@Override
	public void onStop() {

	}

	private void receiveMarketDatas() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(market_DATA_PATH)), "UTF-8");
			store(json);
			Thread.sleep(SPARK_BATCH_DURATION);
			restart("Getting Marches datas again");
		} catch (Exception e) {
			restart("Error while reading datas", e);
		}
	}

}
