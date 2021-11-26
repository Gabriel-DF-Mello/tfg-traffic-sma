import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSON_Test {
	public static void main(String a[]) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//CONVERTENDO OBJETOS EM JSON	
		LinkedList<Agent> listObstaclesSeen = new LinkedList<Agent>();
		//int id, String name, float position_x, float position_y, String facing, float speed, float distance, String state, String seen, String around
		listObstaclesSeen.add(new Agent(100, "semaphore", 100, 10, "left", 0, 25 ,"green", "", ""));
		listObstaclesSeen.add(new Agent(500,"pedestrian", 56, 12, "up", 0, 25, "", "", ""));
		listObstaclesSeen.add(new Agent(501,"pedestrian", 546, 120, "left", 2, 25, "", "", ""));
		String listObstaclesSeenString = gson.toJson(listObstaclesSeen);
		//System.out.println(listObstaclesSeenString);
		
		//um objeto carro com seus obstaculos vistos
		//id, name, x, y, speed, facing, state, seen, around
		Agent car = new Agent(1,"vehicle", 300, 12, "left", 5, 0, "", listObstaclesSeenString,"");
		String jsonStringObject2Json = gson.toJson(car);
		//System.out.println("O objeto car com seus obstaculos\n"+jsonStringObject2Json);

		
		//convert json into object
		String jsonStringJson2Object = jsonStringObject2Json;
		System.out.println("JSON received:\n " + jsonStringJson2Object);
		Agent agent = gson.fromJson(jsonStringJson2Object, Agent.class);
        java.lang.reflect.Type listType = new TypeToken<LinkedList<Agent>>(){}.getType();
        LinkedList<Agent> listObstaclesSeen2 = gson.fromJson(agent.seen, listType);
        
        
        //build perceptions
        StringBuffer agentPercept = new StringBuffer();
        agentPercept.append(agent.name + "(");
        agentPercept.append(agent.id);
        agentPercept.append(",");
        agentPercept.append(agent.position_x);
        agentPercept.append(",");
        agentPercept.append(agent.position_y);
        agentPercept.append(",");
        agentPercept.append(agent.speed);
        agentPercept.append(",");
        agentPercept.append(agent.facing);
        agentPercept.append(")");
        
        
        System.out.println("\nAgents sent:");
        System.out.println(agentPercept);
        
        StringBuffer percepcaoListObstaclesSeen;
        System.out.println("\nList of seen:");
        
        for (Agent i : listObstaclesSeen2) {
        	percepcaoListObstaclesSeen = new StringBuffer();
        	percepcaoListObstaclesSeen.append("seen(");
        	percepcaoListObstaclesSeen.append(agent.id);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.name);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.position_x);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.position_y);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.facing);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.speed);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.distance);
            percepcaoListObstaclesSeen.append(")");
            System.out.println(percepcaoListObstaclesSeen);
        }
        
        
        		
        		
        		
        
        
        
	}
}