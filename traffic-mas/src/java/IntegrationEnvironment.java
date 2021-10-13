// Environment code for project traffic

import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;

import java.util.Scanner;
import java.util.logging.*;
import java.net.DatagramPacket;
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
        logger.info("executing: "+action+", but not implemented!");
        
        String acao = "";
        //veio a ação parar um carro
        if (action.getFunctor().equals("parar")) {
	        try {
	            Communicator.sendMessageTCP(cliente, acao); //envia uma mensagem pela rede
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
	                System.out.println("Iniciada a Thread para recebimento percepções");
	                try {
	                    while (true) {
	                        String json = Communicator.receiveMessageTCP(cliente); 
	                        
	                        System.out.println("Mensagem recebida: " + json);
	                        
	                        //tratar a msg que chega do unity
	                        
	                        addPercept(ASSyntax.parseLiteral(json.toString()));
	                        //addPercept(ASSyntax.parseLiteral("quemEstaAi"));
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
