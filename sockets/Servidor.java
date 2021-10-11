import java.net.ServerSocket;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {

    ServerSocket servidor;
    DatagramSocket socket;

    private void criaServerSocketTCP(int porta) {
        try {
            servidor = new ServerSocket(porta,10);
            System.out.println("Server TCP escutando na porta " + porta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void criaServerSocketUDP(int porta) {
        try {
            socket = new DatagramSocket(porta);
            System.out.println("Server UDP escutando na porta " + porta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aguardaClientesTCP() {
        try {
            /*Bloqueia esperando por uma conexão através do accept()
             Ao receber a conexão, ele receberá como retorno uma referência do Socket do cliente*/
            Socket cliente = servidor.accept();
            System.out.println("Recebi uma conexão de um cliente");
            ThreadRecebedora tr = new ThreadRecebedora(cliente);
            //ThreadEnviadora te = new ThreadEnviadora(cliente);
            tr.start();
            //te.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aguardaClientesUDP() {
        DatagramPacket pacote = Comunicador.recebeMensagemUDP(socket);//aguarda um pacote datagrama chegar de um cliente pela porta 1234
        String mensagem;
        while (pacote != null) {
            mensagem = new String(pacote.getData());//monta uma string com os dados (bytes) que vieram no pacote
            System.out.println("Recebi " + mensagem);
            System.out.println("De: "+pacote.getAddress().getHostName()+":"+pacote.getPort());
            pacote = Comunicador.recebeMensagemUDP(socket);//aguarda um pacote datagrama chegar de um cliente pela porta 1234
        }
    }

    public Servidor(String tipo, int porta) {
        if (tipo.equalsIgnoreCase("TCP")) {
            criaServerSocketTCP(porta);
            aguardaClientesTCP();
        } else if (tipo.equalsIgnoreCase("UDP")) {
            criaServerSocketUDP(porta);
            aguardaClientesUDP();
        } else {
            System.out.println("O protocolo de transporte não foi escolhido adequadamente");
        }
    }

    public static void main(String[] args) {
        Servidor s = new Servidor("TCP", 1234);
    }
}