import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSON_Test {
	public static void main(String a[]) {
		
		Gson gson = new Gson();//GsonBuilder().setPrettyPrinting().create();
		
		//CONVERTENDO OBJETOS EM JSON	
		LinkedList<Agent> listObstaclesSeen = new LinkedList<Agent>();
		//id, name, x, y, speed, facing, state, seen, around
		listObstaclesSeen.add(new Agent(100, "signal_1", 100, 10, 0, "left", "green", "", ""));
		listObstaclesSeen.add(new Agent(500,"alexandre", 56, 12, 0, "up", "", "", ""));
		listObstaclesSeen.add(new Agent(501,"gabriel", 546, 120, 2, "left", "", "", ""));
		String listObstaclesSeenString = gson.toJson(listObstaclesSeen);
		//System.out.println(listObstaclesSeenString);
		
		//um objeto carro com seus obstaculos vistos
		//id, name, x, y, speed, facing, state, seen, around
		Agent car = new Agent(1,"civic", 300, 12, 5, "left", "", listObstaclesSeenString,"");
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
        agentPercept.append(agent.name);
        agentPercept.append("(");
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
        
        //id, name, x, y, speed, facing, state, seen, around
        for (Agent i : listObstaclesSeen2) {
        	percepcaoListObstaclesSeen = new StringBuffer();
        	percepcaoListObstaclesSeen.append("seen(");
            percepcaoListObstaclesSeen.append(agent.name);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.name);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.position_x);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.position_y);
            percepcaoListObstaclesSeen.append(",");
            percepcaoListObstaclesSeen.append(i.facing);
            percepcaoListObstaclesSeen.append(")");
            System.out.println(percepcaoListObstaclesSeen);
        }
        
        
        		
        		
        		
        
        
        
	}
}