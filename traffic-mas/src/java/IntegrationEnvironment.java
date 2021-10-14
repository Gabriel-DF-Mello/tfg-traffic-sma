// Environment code for project traffic

import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;





public class IntegrationEnvironment extends Environment {

    private Logger logger = Logger.getLogger("traffic."+IntegrationEnvironment.class.getName());
    ServerSocket servidor;
    Socket cliente;
    DatagramSocket socket;
    

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        try {
			addPercept(ASSyntax.parseLiteral("quemEstaAi"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        criaServerSocketTCP(1234);
        aguardaClientesTCP();
    	//ou
        //criaServerSocketUDP(porta);
        //aguardaClientesUDP();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        if (action.getFunctor().equals("ola")) {
	        try {
	        	System.out.println("executando uma acao....");
	            Communicator.sendMessageTCP(cliente, action.toString()); 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }   
        } else logger.info("tentando executar : "+action+", mas não foi implementada!");
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
    

    private void criaServerSocketTCP(int porta) {
        try {
            servidor = new ServerSocket(porta,10);
            System.out.println("Aguardando simulador Unity na porta " + porta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aguardaClientesTCP() {  	
        try {
            /*Bloqueia esperando por uma conexão através do accept()
             Ao receber a conexão, ele receberá como retorno uma referência do Socket do cliente*/
            cliente = servidor.accept();
            System.out.println("Recebi a conexão com simulador Unity");
            new Thread() {
	            public void run() {
	            	String jsonString = new String();
	            
	                System.out.println("Iniciada a Thread para recebimento percepções");
	                try {
	                    while (true) {
	                        jsonString = Communicator.receiveMessageTCP(cliente); 	                        
	                        System.out.println("Mensagem recebida: " + jsonString);
	                        
//	                        jsonParser = new JSONParser();
	                        
	                        //tratar a msg que chega do unity
	                        //identificação do agente
	                        //obstaculo (veículo, semáforo, pedestre)
	                        //detalhes do obstaculo (semáforo = cores; pedestre; veículo = velocidade)
	                        //distância (dentro da quadra)
	                        //velocidade do agente
	                        //{"agente":"car1","obstaculo":"pedestre","cor":"sem,"velocidadeOutro":0,"distancia":3,"velocidadePropria":3}
	                        	                        
	                        		
//	                        Object obj = parser.parse(json);
//	                        JSONObject jsonObject = (JSONObject) obj;
//	                        String agente = (String)jsonObject.get("agente");
//	                        System.out.println(agente);
	                        
	                        
	                        addPercept(ASSyntax.parseLiteral(jsonString.toString()));
	                        try {
	                        	Thread.sleep(1000);
	                        	removePercept(ASSyntax.parseLiteral(jsonString.toString()));
	                        } catch(Exception e) {
	                        	System.out.println("Problemas de sincronismo");
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
