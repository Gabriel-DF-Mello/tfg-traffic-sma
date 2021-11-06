import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;
import java.util.logging.*;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;


public class IntegrationEnvironment extends Environment {
    private Logger logger = Logger.getLogger("traffic."+IntegrationEnvironment.class.getName());
    
    ServerSocket server;
    Socket client;
    DatagramSocket socket;

    
    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        try {
			addPercept(ASSyntax.parseLiteral("whoIsThere"));
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
	        	//converter acao do agente em json para unity
	        	
	        	String message = "Agente: " + agName + "Oi, mandando de volta";
	            Communicator.sendMessageTCP(client, message); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }   
        } else logger.info("Server MAS is trying an action: "+action+", but not implemented yet!");
        
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
            server = new ServerSocket(port,10);
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
	                        
	                        //convert json string  to an agent 
	                        //{"id":-25022,"name":"Car","speed":15.0,"facing":358.1144104003906,"obstacles":"{\"items\":[]}"}
	                        
	                       if(!jsonStringReceived.equals("hi")) {
	                        	Agent agt = JsonUtil.objectToJson(jsonStringReceived);
		                        logger.info("JSON was converted in Agente: " + agt);
	                        }
	                        
	                        //split agent information in percepts 	                        
	                        //....
	                        
	                        //add percepts to all agents in Environment 
	                        //addPercept(ASSyntax.parseLiteral(jsonStringReceived));
	                        //addPercept(ASSyntax.parseLiteral("oi"));
	                        //addPercept(ASSyntax.parseLiteral("oi"));
	                        try {
	                        	Thread.sleep(1000);
	                        	//removePercept(ASSyntax.parseLiteral(jsonStringReceived));
	                        	//removePercept(ASSyntax.parseLiteral("oi"));
	                        	//removePercept(ASSyntax.parseLiteral("oi"));
	                        } catch(Exception e) {
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
