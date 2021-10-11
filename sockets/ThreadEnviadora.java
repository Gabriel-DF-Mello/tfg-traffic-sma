import java.net.Socket;
import java.util.Scanner;

public class ThreadEnviadora extends Thread {

    private Socket socket;

    public ThreadEnviadora(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Digite: ");
                String mensagem = scanner.nextLine();
                Comunicador.enviaMensagemTCP(socket, mensagem); //envia uma mensagem pela rede
            }
        } catch (Exception e) {
            e.printStackTrace();
            scanner.close();
        }
    }
}
