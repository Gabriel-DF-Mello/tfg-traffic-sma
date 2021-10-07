using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PedestrianMovement : MonoBehaviour
{

    float startSpeed = 5f;
    float startTurnSpeed = 10f;
    public float speed;
    public float turnSpeed;

    public int pathIndex = 0;
    private Transform[] path;
    private Transform target;
    private int pointIndex = 0;
    // Start is called before the first frame update
    void Start()
    {
        speed = startSpeed;
        turnSpeed = startTurnSpeed;
        int size = PedestrianPaths.pdtpaths[pathIndex].Length;
        path = new Transform[size];
        for (int i = 0; i < size; i++)
        {
            path[i] = PedestrianPaths.pdtpaths[pathIndex][i];
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


        if (Vector3.Distance(transform.position, target.position) <= 0.7f)
        {
            GetNextWaypoint();
        }
    }

    void GetNextWaypoint()
    {
        pointIndex += 1;
        if (pointIndex >= path.Length)
        {
            Destroy(gameObject);
        }
        else
        {
            target = path[pointIndex];
        }
    }
}
