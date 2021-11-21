import java.util.LinkedList;

public class Agent {
	public int id;
	public String name;
	public float position_x;
	public float position_y;
	public float facing;
	public float speed;
	public String obstacles;
	
	public Agent(int id, String name,  float position_x, float position_y, float facing, float speed, String obstacles) {
		super();
		this.id = id;
		this.name = name;
		this.position_x = position_x;
		this.position_y = position_y;
		this.facing = facing;
		this.speed = speed;
		this.obstacles = obstacles; //"{itens:[]}"
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", position_x=" + position_x + ", position_y=" + position_y + ", facing=" + facing
				+ ", speed=" + speed + ", obstacles=" + obstacles + "]";
	}	
}
