import java.net.Socket;

public class ThreadRecebedora extends Thread {

    private Socket socket;

    public ThreadRecebedora(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println("Iniciada a Thread para recebimento de dados");
        try {
            while (true) {
                String mensagem = Comunicador.recebeMensagemTCP(socket); //recebe uma string enviada pela rede
                
                if (mensagem == null) {
                    System.exit(0);
                }
                System.out.println("Mensagem recebida: " + mensagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
