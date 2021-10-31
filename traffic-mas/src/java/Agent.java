import java.util.LinkedList;

public class Agent {
	public int id;
	public float position_x;
	public float position_y;
	public float facing;
	public float speed;
	public LinkedList obstacles;
	
	public Agent(int id, float position_x, float position_y, float facing, float speed, LinkedList obstacles) {
		super();
		this.id = id;
		this.position_x = position_x;
		this.position_y = position_y;
		this.facing = facing;
		this.speed = speed;
		this.obstacles = obstacles;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", position_x=" + position_x + ", position_y=" + position_y + ", facing=" + facing
				+ ", speed=" + speed + ", obstacles=" + obstacles + "]";
	}	
}
