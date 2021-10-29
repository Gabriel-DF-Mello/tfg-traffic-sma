using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System;

public class Sender : MonoBehaviour
{
    static Socket socket;
    // Start is called before the first frame update
    void Start()
    {
        socket = Receiver.socket;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
            Debug.Log("Sending Hello");
            Send("oi"); //envia uma mensagem pela rede
        }
        if (Input.GetKeyDown(KeyCode.K))
        {
            Debug.Log("Matando conexão");
            socket.Shutdown(SocketShutdown.Both);
            socket.Close();
        }
    }

    private void SendData(String message)
    {
        int toSendLen = System.Text.Encoding.ASCII.GetByteCount(message);
        byte[] toSendBytes = System.Text.Encoding.ASCII.GetBytes(message);

        SocketAsyncEventArgs socketAsyncData = new SocketAsyncEventArgs();
        socketAsyncData.SetBuffer(toSendBytes, 0, toSendBytes.Length);
        socket.SendAsync(socketAsyncData);
    }

    public static void Send(String message)
    {
        try
        {
            // Sending
            int toSendLen = System.Text.Encoding.ASCII.GetByteCount(message);
            byte[] toSendBytes = System.Text.Encoding.ASCII.GetBytes(message);
            byte[] toSendLenBytes = System.BitConverter.GetBytes(toSendLen);
            socket.Send(toSendLenBytes);
            socket.Send(toSendBytes);
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }
}
