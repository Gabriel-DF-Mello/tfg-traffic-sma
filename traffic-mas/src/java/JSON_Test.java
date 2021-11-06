import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSON_Test {
	public static void main(String a[]) {
		String jsonStringReceived;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//CONVERTENDO OBJETOS EM JSON
		Obstacle poste = new Obstacle(100, 10, 10, 5, "poste", "parado");
		Obstacle pedestre = new Obstacle(101, 40, 40, 5, "pedestre", "parado");
		
		LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
		obstacles.add(poste);
		obstacles.add(pedestre);
		
		Agent carro1 = new Agent(1, "car", 10, 10, 5, 30, obstacles);
		
		jsonStringReceived = gson.toJson(carro1);
		
		System.out.println(jsonStringReceived);
		
		//CONVERTENDO JSON EM OBJETOS
		Agent umObjetoQQ = gson.fromJson(jsonStringReceived, Agent.class);
        System.out.println(umObjetoQQ);
	}
}
