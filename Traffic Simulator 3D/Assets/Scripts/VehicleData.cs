using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class VehicleData : MonoBehaviour
{
    public float vision = 60f;
    public string type;
    Sender s;

    // Start is called before the first frame update
    void Start()
    {
        GameObject simManager = GameObject.Find("SimManager");
        s = simManager.GetComponent<Sender>();
    }

    // Update is called once per frame
    void Update()
    {
        GenerateData(true);
    }

    string GenerateData(bool hasLog)
    {
        Info info = new Info();
        info.id = gameObject.GetInstanceID();
        info.name = type;
        info.position_x = transform.position.x;
        info.position_y = transform.position.z;
        info.speed = gameObject.GetComponent<VehicleMovement>().speed;
        info.facing = transform.rotation.eulerAngles.y;
        info.distance = 0;
        info.state = "None";

        gameObject.GetComponent<FieldOfView>().FindTargets();
        Info[] seenObstacles = gameObject.GetComponent<FieldOfView>().seen.ToArray();
        Info[] aroundObstacles = gameObject.GetComponent<FieldOfView>().around.ToArray();

        string seenToJson = JsonHelper.ToJson(seenObstacles, false);
        string aroundToJson = JsonHelper.ToJson(aroundObstacles, false);

        // set around and seen
        info.seen = seenToJson;
        info.around = aroundToJson;

        //Create Json based on the data from gameObjects
        string json = JsonUtility.ToJson(info);
        if (hasLog)
        {
            Debug.Log(json);
        }
        return json;
    }

    void SendData()
    {
        s.Send(GenerateData(false));
    }
}
