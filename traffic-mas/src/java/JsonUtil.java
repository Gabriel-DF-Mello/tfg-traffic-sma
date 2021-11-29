import java.util.LinkedList;
<<<<<<< HEAD
=======

import com.google.gson.*;
>>>>>>> b437da48e4d156002bd3c29343467ef591e766db
import com.google.gson.Gson;

public class JsonUtil {
	
	static Gson gson = new Gson();
	
	public static Agent convertJson(String jsonStringReceived) {
		
		return gson.fromJson(jsonStringReceived, Agent.class);
	}
	
	public static LinkedList<Agent> convertJson(String seen, java.lang.reflect.Type listType) {
		return gson.fromJson(seen, listType);
	}	
}
