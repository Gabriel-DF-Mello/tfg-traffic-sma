using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Paths : MonoBehaviour
{

    public static List<Transform[]> paths = new List<Transform[]>();

    // Start is called before the first frame update
    void Awake()
    {
        for (int i = 0; i < transform.childCount; i++)
        {
            Transform child = transform.GetChild(i);

            Transform[] points = new Transform[child.childCount];
            for (int ii = 0; ii < points.Length; ii++)
            {
                points[ii] = child.GetChild(ii);
            }

            paths.Add(points);
        }
    }
}
