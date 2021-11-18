import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;

import java.util.LinkedList;
import java.util.logging.*;

import com.google.gson.reflect.TypeToken;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class IntegrationEnvironment extends Environment {
	private Logger logger = Logger.getLogger("traffic." + IntegrationEnvironment.class.getName());

	ServerSocket server;
	Socket client;
	DatagramSocket socket;

	/** Called before the MAS execution with the args informed in .mas2j */
	@Override
	public void init(String[] args) {
		super.init(args);

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
		if (action.getFunctor().equals("acaoola")) {
			try {
				logger.info("an action in execution....");
				// converter acao do agente em json para unity

				String message = "Agent: " + agName + " " + action.getFunctor();
				Communicator.sendMessageTCP(client, message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			logger.info("Server MAS is trying an action: " + action + ", but not implemented yet!");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
							logger.info("A socket json received: " + jsonStringReceived);
							//convert json into object
							Agent agent = JsonUtil.gson.fromJson(jsonStringReceived, Agent.class);
					        java.lang.reflect.Type listType = new TypeToken<LinkedList<Agent>>(){}.getType();
					        LinkedList<Agent> listObstaclesSeen = JsonUtil.gson.fromJson(agent.seen, listType);
							//build perceptions and send to all agents
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
					        addPercept(ASSyntax.parseLiteral(agentPercept.toString()));
					        
					        StringBuffer percepcaoListObstaclesSeen;
					        //id, name, x, y, speed, facing, state, seen, around
					        for (Agent i : listObstaclesSeen) {
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
					            addPercept(ASSyntax.parseLiteral(percepcaoListObstaclesSeen.toString()));
					        }
							try {
								Thread.sleep(1000);
								// remove percepts added before 1000 ms
							} catch (Exception e) {
								logger.info("Some problems to synchronize!!");
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
