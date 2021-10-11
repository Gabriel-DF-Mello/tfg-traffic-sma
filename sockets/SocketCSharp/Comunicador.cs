using System;
using System.Net;  
using System.Net.Sockets;  
using System.Text; 

namespace SocketCSharp
{
    class Comunicador
    {
        public static String recebeMensagem(Socket s) 
        {
            try 
            {
                //Cria um objeto de fluxo de dados de entrada, para poder receber dados de um socket s
                //DataInputStream leitor = new DataInputStream(s.getInputStream());
                String mensagem = ""; // = leitor.readUTF();
                return mensagem;
            } catch (Exception e) {
                Console.WriteLine(e);
                return null;
            }
        }

        public static void enviaMensagem(Socket socket, String mensagem) 
        {
            try 
            {
                // Sending
                int toSendLen = System.Text.Encoding.ASCII.GetByteCount(mensagem);
                byte[] toSendBytes = System.Text.Encoding.ASCII.GetBytes(mensagem);
                byte[] toSendLenBytes = System.BitConverter.GetBytes(toSendLen);
                socket.Send(toSendLenBytes);
                socket.Send(toSendBytes);


            } catch (Exception e) {
                Console.WriteLine(e);
            }
        }
    }
}