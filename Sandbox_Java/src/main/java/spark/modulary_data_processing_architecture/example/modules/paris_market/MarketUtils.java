package spark.modulary_data_processing_architecture.example.modules.paris_market;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import scala.Tuple2;
import spark.modulary_data_processing_architecture.example.modules.utils.MapUtils;
import spark.modulary_data_processing_architecture.example.modules.utils.ZoneUtils;
import twitter4j.GeoLocation;

/**
 * Format paris market datas
 * @author victor
 *
 */
public class MarketUtils {
	public static List<Tuple2<String, Integer>> numberOfmarketOpenInEachZone(String json) {
		JSONArray markets = stringToJSONArrayMarches(json);

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String currentDayFrench = "";
		Map<String, Integer> map = new HashMap<String, Integer>();

		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			currentDayFrench = "dimanche";
			break;
		case Calendar.MONDAY:
			currentDayFrench = "lundi";
			break;
		case Calendar.TUESDAY:
			currentDayFrench = "mardi";
			break;
		case Calendar.WEDNESDAY:
			currentDayFrench = "mercredi";
			break;
		case Calendar.THURSDAY:
			currentDayFrench = "jeudi";
			break;
		case Calendar.FRIDAY:
			currentDayFrench = "vendredi";
			break;
		case Calendar.SATURDAY:
			currentDayFrench = "samedi";
			break;
		}

		for (int i = 0; i < markets.length(); i++) {
			JSONObject market = markets.getJSONObject(i);
			JSONObject fields = market.getJSONObject("fields");
			double longitude = fields.getJSONArray("geo_coordinates").getDouble(0);
			double latitude = fields.getJSONArray("geo_coordinates").getDouble(1);
			// market is open today ?
			if (fields.has(currentDayFrench)) {
				// market is open now ?
				String hours = fields.getString(currentDayFrench);
				if (currentTimeInHours(hours)) {
					int count = map.containsKey(ZoneUtils.getZoneID(new GeoLocation(longitude, latitude)).getID())
							? map.get(ZoneUtils.getZoneID(new GeoLocation(longitude, latitude)).getID()) : 0;
					map.put(ZoneUtils.getZoneID(new GeoLocation(longitude, latitude)).getID(), count + 1);
				}
			}

		}

		return MapUtils.mapToPairList(map);
	}
	
	public static boolean currentTimeInHours(String marketHours) {
		boolean test = false;
		// Hour formate : "07h00 - 14h30" or "12h - 20h"
		String[] tmp = marketHours.split(" - ");
		if (tmp.length == 2) {
			int startHour = Integer.parseInt(tmp[0].split("h")[0]);
			int endHour = Integer.parseInt(tmp[1].split("h")[0]);

			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int currentHour = cal.get(Calendar.HOUR_OF_DAY);

			if (currentHour >= startHour && currentHour <= endHour) {
				return true;
			}
		}
		return test;
	}

	public static JSONArray stringToJSONArrayMarches(String json) {
		return new JSONArray(json);
	}
}
