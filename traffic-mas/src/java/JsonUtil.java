import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
<<<<<<< HEAD
=======
	
	public static Agent objectToJson(String json) {
		return gson.fromJson(json, Agent.class);
	}
>>>>>>> 42ea75a74948c7c58f10a765be330235c5302d68
}
