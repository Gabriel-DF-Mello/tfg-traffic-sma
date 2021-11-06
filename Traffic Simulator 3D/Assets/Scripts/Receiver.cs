using System.Collections;
using System.Collections.Generic;
using WebSocketSharp;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System;

public class Receiver : MonoBehaviour
{
    public static Socket socket;
    private static byte[] _receiveBuffer = new byte[8142];
    private void Awake()
    {
        ClientSocket();
    }
    private void Start()
    {
        socket.BeginReceive(_receiveBuffer, 0, _receiveBuffer.Length, SocketFlags.None, new AsyncCallback(ReceiveCallback), null);
    }

    static void ClientSocket()
    {
        try
        {
            // Establish the remote endpoint for the socket.  

            Debug.Log("Iniciando conexão");

            IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint remoteEP = new IPEndPoint(ipAddress, 1234);

            //cria um socket TCP ou UDP para se conectar ao servidor de ip "localhost" porta
            socket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

            socket.Connect(remoteEP);

            Debug.Log("Cliente conectado em {0}" + socket.RemoteEndPoint.ToString());
        }
        catch (ArgumentNullException ane)
        {
            Debug.Log("ArgumentNullException : {0}" + ane.ToString());
        }
    }

    void ReceiveCallback(IAsyncResult AR)
    {
        //Check how much bytes are recieved and call EndRecieve to finalize handshake
        int received = socket.EndReceive(AR);

        if (received <= 0)
            return;

        byte[] recData = new byte[received];
        Buffer.BlockCopy(_receiveBuffer, 0, recData, 0, received);

        //Process data here the way you want , all your bytes will be stored in recData
        String rcv = System.Text.Encoding.ASCII.GetString(recData);
        Debug.Log("SMA mandou: " + rcv);


        //Start receiving again
        socket.BeginReceive(_receiveBuffer, 0, _receiveBuffer.Length, SocketFlags.None, new AsyncCallback(ReceiveCallback), null);
    }

    public static String receiveMessage(Socket s)
    {
        try
        {
            byte[] rcvLenBytes = new byte[4];
            s.Receive(rcvLenBytes);
            int rcvLen = System.BitConverter.ToInt32(rcvLenBytes, 0);
            byte[] rcvBytes = new byte[rcvLen];
            s.Receive(rcvBytes);
            String rcv = System.Text.Encoding.ASCII.GetString(rcvBytes);
            return rcv;
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
