using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class VehicleMovement : MonoBehaviour
{
    float startSpeed = 15f;
    float startTurnSpeed = 5.2f;
    public float speed;
    public float turnSpeed;
    public bool canDie;

    public int pathIndex = 0;
    private Transform[] path;
    private Transform target;
    private int pointIndex = 0;

    // Start is called before the first frame update
    void Start()
    {
        speed = startSpeed;
        turnSpeed = startTurnSpeed;
        int size = Paths.paths[pathIndex].Length;
        path = new Transform[size];
        for(int i = 0; i < size; i++)
        {
            path[i] = Paths.paths[pathIndex][i];
        }
        target = path[0];

    }

    // Update is called once per frame
    void Update()
    {
        Vector3 dir = (target.position - transform.position).normalized;
        transform.Translate(dir * speed * Time.deltaTime, Space.World);

        Quaternion _lookRotation = Quaternion.LookRotation(dir);

        //rotate us over time according to speed until we are in the required rotation
        transform.rotation = Quaternion.Slerp(transform.rotation, _lookRotation, Time.deltaTime * turnSpeed);


        if (Vector3.Distance(transform.position, target.position) <= 0.5f)
        {
            GetNextWaypoint();
        }
    }

    void GetNextWaypoint()
    {
        pointIndex += 1;
        if (pointIndex >= path.Length)
        {
            if (canDie)
            {
                Destroy(gameObject);
            }
            else
            {
                pointIndex = 0;
                target = path[pointIndex];
            }
        }
        else
        {
            target = path[pointIndex];
        }
    }
}
