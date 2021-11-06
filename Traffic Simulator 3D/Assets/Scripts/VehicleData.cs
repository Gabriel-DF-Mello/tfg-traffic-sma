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

    List<Obstacle> GetObstacles()
    {
        List<Obstacle> obstacles = new List<Obstacle>();
        Collider[] hitColliders = Physics.OverlapSphere(transform.position, vision);
        foreach (var hitCollider in hitColliders)
        {
            if ((hitCollider.gameObject.tag == "Semaphore") || (hitCollider.gameObject.tag == "Pedestrian") || (hitCollider.gameObject.tag == "Vehicle"))
            {
                if(hitCollider.transform.position != transform.position){
                    //Debug.Log("I am in range of a " + hitCollider.gameObject.tag);
                    Vector3 obstacleRelative = hitCollider.transform.InverseTransformPoint(transform.position);
                    Obstacle ob = new Obstacle();
                    ob.id = hitCollider.gameObject.GetInstanceID();
                    ob.distance_x = obstacleRelative.x;
                    ob.distance_y = obstacleRelative.z;
                    ob.type = hitCollider.gameObject.tag;
                    if (hitCollider.gameObject.tag == "Semaphore")
                    {
                        //Debug.Log("Found a semaphore");
                        // get semaphore state
                        ob.state = hitCollider.gameObject.GetComponent<SemaphoreData>().state;
                    }
                    else
                    {
                        ob.state = "None";
                    }
                    obstacles.Add(ob);
                }
            }
        }
        //Debug.Log("------------------------------------");
        return obstacles;
    }

    string GenerateData(bool hasLog)
    {
        Info info = new Info();
        info.name = type;
        info.id = gameObject.GetInstanceID();
        info.position_x = transform.position.x;
        info.position_y = transform.position.z;
        info.speed = gameObject.GetComponent<VehicleMovement>().speed;
        info.facing = transform.rotation.eulerAngles.y;

        Obstacle[] obs = GetObstacles().ToArray();

        string obstaclesToJson = JsonHelper.ToJson(obs, false);
        info.obstacles = obstaclesToJson;

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
        for (; ; )
        {
            s.Send(GenerateData(false));
        }
    }
}
