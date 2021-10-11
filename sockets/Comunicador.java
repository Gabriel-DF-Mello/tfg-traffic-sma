import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Comunicador {

    public static String recebeMensagemTCP(Socket s) {
        try {
            // Receiving
            InputStream is = s.getInputStream();
            byte[] lenBytes = new byte[4];
            is.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                    ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            is.read(receivedBytes, 0, len);
            String mensagem = new String(receivedBytes, 0, len);

            return mensagem;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public static void enviaMensagemTCP(Socket s, String mensagem) {
        try {
            //Cria um objeto de fluxo de dados de de saída, para poder enviar dados pelo socket s
            DataOutputStream escritor = new DataOutputStream(s.getOutputStream());
            escritor.writeUTF(mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static DatagramPacket montaMensagemUDP(String mensagem, String ip, int porta) {
        try {
            byte[] buffer = mensagem.getBytes();
            //monta um pacote datagrama com a mensagem, indicando, além dos dados, o endereço e a porta a ser enviado
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), porta);
            return pacote;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } 
    }

    public static DatagramPacket recebeMensagemUDP(DatagramSocket s) {
        try {
            //cria um pacote vazio de 512 bytes
            DatagramPacket pacote = new DatagramPacket(new byte[512], 512);
            //bloqueia aguardando um pacote datagrama do servidor
            s.receive(pacote);
            return pacote;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void enviaMensagemUDP(DatagramSocket s, DatagramPacket pacote) {
        try {
            //envia o pacote datagrama
            s.send(pacote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

