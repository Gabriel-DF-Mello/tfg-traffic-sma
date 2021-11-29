import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;

import java.util.LinkedList;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class IntegrationEnvironment extends Environment {
	private Logger logger = Logger.getLogger("traffic." + IntegrationEnvironment.class.getName());

	ServerSocket server;
	Socket client;
	DatagramSocket socket;	
	Gson gson;

	/** Called before the MAS execution with the args informed in .mas2j */
	@Override
	public void init(String[] args) {
		super.init(args);
		gson = new Gson();

		try {
			addPercept(ASSyntax.parseLiteral("simulation(on)"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.createServerSocketTCP(1234);
		this.waitClientsTCP();
	}

	@Override
	public boolean executeAction(String agName, Structure action) {
		if (agName.equals("vehicle")) {
			try {
				String message = action.getFunctor() + ":"+ action.getTerm(0).toString();
				Communicator.sendMessageTCP(client, message);
				//logger.info(message);
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true; // the action was executed with success
	}

	/** Called before the end of MAS execution */
	@Override
	public void stop() {
		super.stop();
	}

	private void createServerSocketTCP(int port) {
		try {
			server = new ServerSocket(port, 10);
			logger.info("Server MAS waiting Unity Simulator at port " + port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void waitClientsTCP() {
		try {
			client = server.accept();
			logger.info("Server MAS received a conection with Unity Simulator");
			new Thread() {
				public void run() {
					String jsonStringReceived = new String();
					logger.info("All threads to handle percepts are online!!");
					try {
						while (true) {
							jsonStringReceived = Communicator.receiveMessageTCP(client);
							if (jsonStringReceived == null || jsonStringReceived.equals("")) {
								logger.info("Unity simulator did not send anything!");
								server.close();
								break;
								
							} else {
								logger.info("A socket json received: " + jsonStringReceived);
								//convert json into object
								Agent agent = gson.fromJson(jsonStringReceived, Agent.class);
						        java.lang.reflect.Type listType = new TypeToken<LinkedList<Agent>>(){}.getType();
						        
						        int stin = agent.seen.indexOf('[');
						        int enin = agent.seen.indexOf(']');
						        
						        String subst = agent.seen.substring(stin, enin + 1);
						        subst = subst.replace("\\", "");
						        
						        LinkedList<Agent> listObstaclesSeen = gson.fromJson(subst, listType);
								//build perceptions and send to all agents
						        //id, name, position_x, position_y, facing, speed, distance, state, seen, around
						        StringBuffer agentPercept = new StringBuffer();
						        agentPercept.append(agent.name + "(");
						        agentPercept.append(agent.id);
						        agentPercept.append(",");
						        agentPercept.append(agent.position_x);
						        agentPercept.append(",");
						        agentPercept.append(agent.position_y);
						        agentPercept.append(",");
						        agentPercept.append(agent.facing);
						        agentPercept.append(",");
						        agentPercept.append(agent.speed);
						        agentPercept.append(")");					        
						        addPercept(ASSyntax.parseLiteral(agentPercept.toString()));
						        
						        if (listObstaclesSeen.isEmpty()) {
						        	logger.info("No obstacles seen by the agent.");
						        } else {
						        	//id, name, position_x, position_y, facing, speed, distance, state, seen, around
						        	StringBuffer perceptListObstaclesSeen = new StringBuffer();
							        for (Agent i : listObstaclesSeen) {
							        	perceptListObstaclesSeen.append("seen(");
							        	perceptListObstaclesSeen.append(agent.id);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.name);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.position_x);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.position_y);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.facing);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.speed);
							            perceptListObstaclesSeen.append(",");
							            perceptListObstaclesSeen.append(i.distance);
							            perceptListObstaclesSeen.append(",");
							            if (i.state.equals("")) perceptListObstaclesSeen.append("_");
							            else perceptListObstaclesSeen.append(i.state);
							            perceptListObstaclesSeen.append(")");
							            System.out.println(perceptListObstaclesSeen);
							            addPercept(ASSyntax.parseLiteral(perceptListObstaclesSeen.toString()));
							            perceptListObstaclesSeen = new StringBuffer();
							        }
						        }						        
								try {
									Thread.sleep(1000);
									// remove percepts added before 1000 ms
									//removePercept(ASSyntax.parseLiteral(agentPercept.toString()));		
									//removePercept(ASSyntax.parseLiteral(perceptListObstaclesSeen.toString()));
								} catch (Exception e) {
									logger.info("Some problems to synchronize!!");
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
