// Environment code for project traffic

import jason.asSyntax.*;
import jason.environment.*;
import jason.asSyntax.parser.*;

import java.util.Scanner;
import java.util.logging.*;
import java.net.ServerSocket;
import java.net.Socket;


public class IntegrationEnvironment extends Environment {

    private Logger logger = Logger.getLogger("traffic."+IntegrationEnvironment.class.getName());
    ServerSocket servidor;
    Socket cliente;
    

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        
        try {
			addPercept(ASSyntax.parseLiteral("percept(demo)"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        criaServerSocket();
        aguardaClientes();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+action+", but not implemented!");
        
        String acao = "";
        //veio a ação parar um carro
        if (action.getFunctor().equals("parar")) {
	        try {
	                Communicator.enviaMensagem(cliente, acao); //envia uma mensagem pela rede
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
    
    
    private void criaServerSocket() {
        try {
            servidor = new ServerSocket(1234);
            System.out.println("Server escutando na porta 1234");
        } catch (Exception ex) {
        }
    }

    private void aguardaClientes() {
        try {
            /*Bloqueia esperando por uma conexão através do accept()
             Ao receber a conexão, ele receberá como retorno uma referência do Socket do cliente*/
            cliente = servidor.accept();
            System.out.println("Recebi uma conexão de um cliente");
            new Thread() {
	            public void run() {
	                System.out.println("Iniciada a Thread para recebimento de dados");
	                try {
	                    while (true) {
	                        String mensagem = Communicator.recebeMensagem(cliente); //recebe uma string enviada pela rede
	                        
	                        System.out.println("Mensagem recebida: " + mensagem);
	                        
	                        //tratar a msg que chega do unity
	                        
	                        //addPercept(ASSyntax.parseLiteral(mensagem));
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
