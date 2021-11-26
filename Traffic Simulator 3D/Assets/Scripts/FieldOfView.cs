using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FieldOfView : MonoBehaviour
{
    public float viewRadius = 50;
    [Range(0, 360)]
    public float viewAngle = 40;

	[Range(0, 360)]
	public float semaphoreAngle = 160;

    [HideInInspector]
    public List<Info> seen = new List<Info>();

	[HideInInspector]
	public List<Info> around = new List<Info>();

	public void FindTargets()
	{
		seen.Clear();
		around.Clear();
		Collider[] targetsInViewRadius = Physics.OverlapSphere(transform.position, viewRadius);

		for (int i = 0; i < targetsInViewRadius.Length; i++)
		{
			Collider hitCollider = targetsInViewRadius[i];
			Transform target = hitCollider.transform;

			Vector3 dirToTarget = (target.position - transform.position).normalized;
			float dstToTarget = Vector3.Distance(transform.position, target.position);
			Vector3 obstacleRelative = target.InverseTransformPoint(transform.position);

			Info info = new Info();

			info.id = hitCollider.gameObject.GetInstanceID();
			info.name = hitCollider.gameObject.tag;
			info.position_x = obstacleRelative.x;
			info.position_y = obstacleRelative.z;
			info.facing = AngleToString(hitCollider.transform.rotation.eulerAngles.y);
			info.distance = dstToTarget;
			info.seen = "";
			info.around = "";

			if ((hitCollider.gameObject.tag == "Pedestrian") || (hitCollider.gameObject.tag == "Vehicle")) { //not semaphore
				info.state = "None";
				if(hitCollider.gameObject.tag == "Vehicle") {
					info.speed = hitCollider.gameObject.GetComponent<VehicleMovement>().speed;
                }
                else{
					info.speed = hitCollider.gameObject.GetComponent<PedestrianMovement>().speed;
				}

				if (Vector3.Angle(transform.forward, dirToTarget) < viewAngle / 2) {
					seen.Add(info);
				}
				else { // is in around
					around.Add(info);
				}
			} else if (hitCollider.gameObject.tag == "Semaphore") {
				info.state = hitCollider.gameObject.GetComponent<SemaphoreData>().state;
				info.speed = 0;
				// semaphore
				if (Vector3.Angle(transform.forward, dirToTarget) < semaphoreAngle / 2) {
					// is in seen
					seen.Add(info);
				}
				else { // is in around
					around.Add(info);
				}
			}
		}
	}

	string AngleToString(float angle)
	{
		if (((angle >= 315) && (angle <= 360)) || ((angle >= 0) && (angle < 45)))
		{
			return "Up";
		}
		else if ((angle >= 45) && (angle < 135))
		{
			return "Right";
		}
		else if ((angle >= 135) && (angle < 225))
		{
			return "Down";
		}
		else if ((angle >= 225) && (angle < 315))
		{
			return "Left";
		}
		return "Up";
	}

	public Vector3 DirFromAngle(float angleInDegrees, bool angleIsGlobal)
	{
		if (!angleIsGlobal)
		{
			angleInDegrees += transform.eulerAngles.y;
		}
		return new Vector3(Mathf.Sin(angleInDegrees * Mathf.Deg2Rad), 0, Mathf.Cos(angleInDegrees * Mathf.Deg2Rad));
	}
}
