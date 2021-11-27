import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static Agent convertJson(String jsonStringReceived) {
		return gson.fromJson(jsonStringReceived, Agent.class);
	}
	
	
	public static LinkedList<Agent> convertJson(String seen, java.lang.reflect.Type listType) {
		return gson.fromJson(seen, listType);
	}	
}
