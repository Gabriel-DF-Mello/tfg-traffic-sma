import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class JsonUtil {
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static Agent objectToJson(String json) {
		return gson.fromJson(json, Agent.class);
	}
}
