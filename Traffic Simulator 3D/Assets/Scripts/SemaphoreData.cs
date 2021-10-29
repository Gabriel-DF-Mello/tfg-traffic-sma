using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SemaphoreData : MonoBehaviour
{
    // Start is called before the first frame update
    public string state = "Green";
    private float countdown;
    public float rate = 6f;

    private void Start()
    {
        state = "Green";
        countdown = 2f * rate;
    }

    // Update is called once per frame
    void Update()
    {
        if (countdown <= 0f)
        {
            StartCoroutine(ChangeState());
            if(state == "Yellow")
            {
                countdown = rate;
            } else
            {
                countdown = rate * 1.5f;
            }
            return;
        }

        countdown -= Time.deltaTime;
        countdown = Mathf.Clamp(countdown, 0f, Mathf.Infinity);
    }

    IEnumerator ChangeState()
    {
        Light light = transform.Find("PointLight").GetComponent<Light>();
        if (state == "Green")
        {
            //Debug.Log("Changing to Yellow");
            state = "Yellow";
            light.color = Color.yellow;
        }
        else if(state == "Yellow")
        {
            //Debug.Log("Changing to Red");
            state = "Red";
            light.color = Color.red;
        }
        else if(state == "Red")
        {
            //Debug.Log("Changing to Green");
            state = "Green";
            light.color = Color.green;
        }
        yield return new WaitForSeconds(1f / 4f);
    }
}
