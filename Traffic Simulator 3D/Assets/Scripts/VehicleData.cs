using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class VehicleData : MonoBehaviour
{
    float vision = 15f;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    List<GameObject> GetObstacles()
    {
        List<GameObject> obstacles = new List<GameObject>();
        Collider[] hitColliders = Physics.OverlapSphere(transform.position, vision);
        foreach (var hitCollider in hitColliders)
        {
            if ((hitCollider.gameObject.tag == "Semaphore") || (hitCollider.gameObject.tag == "Pedestrian") || (hitCollider.gameObject.tag == "Vehicle"))
            {
                obstacles.Add(hitCollider.gameObject);
            }
        }

        return obstacles;
    }

    void GenerateData()
    {
        //Create Json based on the data from gameObjects
    }
}
