using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class VehicleSpawner : MonoBehaviour
{
    // Start is called before the first frame update

    public int pathIndex = 0;
    public GameObject vehicle;
    public int spawnNumber = 1;
    public bool makeKillable;
    public float rate = 5f;
    public float timeBetweenWaves = 5f;
    private float countdown = 2f;
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (countdown <= 0f)
        {
            StartCoroutine(SpawnWave());
            countdown = timeBetweenWaves;
            return;
        }

        countdown -= Time.deltaTime;

        countdown = Mathf.Clamp(countdown, 0f, Mathf.Infinity);
    }

    IEnumerator SpawnWave()
    {

        for (int i = 0; i < spawnNumber; i++)
        {
            Spawn(vehicle);
            yield return new WaitForSeconds(1f / rate);
        }
    }

    void Spawn(GameObject vehicle)
    {
        GameObject instance = Instantiate(vehicle, transform.position, transform.rotation) as GameObject;
        instance.GetComponent<VehicleMovement>().pathIndex = pathIndex;
        instance.GetComponent<VehicleMovement>().canDie = makeKillable;
    }
}
