import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSON_Test {
	public static void main(String a[]) {
		
		Gson gson = new Gson();//GsonBuilder().setPrettyPrinting().create();
		
		//CONVERTENDO OBJETOS EM JSON
		Obstacle signal = new Obstacle(100, "signal", "stopped", 100, 10, 500);
		Obstacle pedestrian = new Obstacle(110, "pedestrian", "walking", 10, 10, 300);
		
		LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
		obstacles.add(signal);
		obstacles.add(pedestrian);
		String obstaclesString = gson.toJson(obstacles);
		
		//um objeto carro com seus obstaculos
		Agent car = new Agent(-25022,"Car", 3, 3, 10, 0, obstaclesString);
		String jsonStringObject2Json = gson.toJson(car);
		System.out.println("O objeto car com seus dois obstaculos\n"+jsonStringObject2Json);
		
		
		//CONVERTENDO JSON EM OBJETOS
		String jsonStringJson2Object = jsonStringObject2Json;
		Agent car2 = gson.fromJson(jsonStringJson2Object, Agent.class);
        System.out.println("Objeto car recebido\n\n" + car2);
        
        System.out.println("String de obstaculos: " +  car2.obstacles);
        java.lang.reflect.Type tipoLista = new TypeToken<LinkedList<Obstacle>>(){}.getType();
        LinkedList<Obstacle> obstacles2 = gson.fromJson(car2.obstacles, tipoLista);
        System.out.println(obstacles2);        
	}
}