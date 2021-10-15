using System.Collections;
using System.Collections.Generic;
using WebSocketSharp;
using UnityEngine;

public class Socket : MonoBehaviour
{
    static WebSocket ws;
    private void Start()
    {
        ws = new WebSocket("ws://localhost:7020");
        ws.Connect();
        ws.OnMessage += (sender, e) =>
        {
            Debug.Log("Message Received from " + ((WebSocket)sender).Url + ", Data : " + e.Data);
        };
    }
    private void Update()
    {
        if (ws == null)
        {
            return;
        }
        if (Input.GetKeyDown(KeyCode.Space))
        {
            ws.Send("Hello");
        }
    }

    public static void Send(string obj)
    {
        ws.Send(obj);
    }
}
