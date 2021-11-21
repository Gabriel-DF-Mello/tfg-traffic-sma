import java.util.LinkedList;

public class Agent {
	public int id;
	public String name;
	public float position_x;
	public float position_y;
	public float speed;
	public String facing;
	public String state; //just for signals
    public String seen;
    public String around;
	
	public Agent(int id, String name,  float position_x, float position_y, float speed, String facing, String state, String seen, String around) {
		super();
		this.id = id;
		this.name = name;
		this.position_x = position_x;
		this.position_y = position_y;
		this.speed = speed;
		this.facing = facing;
		this.state = state; //just for signals - green, yellow, red
		this.seen = seen;  //list of agents in his view field
		this.around = around;  //list of agents out side his view field
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", position_x=" + position_x + ", position_y=" + position_y
				+ ", speed=" + speed + ", facing=" + facing + ", seen=" + seen + ", around=" + around + "]";
	}

	

	
}
