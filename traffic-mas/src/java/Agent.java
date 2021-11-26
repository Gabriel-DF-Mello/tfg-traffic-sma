public class Agent {
	public int id;
	public String name;
	public float position_x;
	public float position_y;
	public String facing;
	public float speed;
	public float distance;
	public String state; //just for semaphore
    public String seen;
    public String around;
	
	public Agent(int id, String name, float position_x, float position_y, String facing, float speed, float distance, String state, String seen, String around) {
		super();
		this.id = id;
		this.name = name;
		this.position_x = position_x;
		this.position_y = position_y;
		this.facing = facing;
		this.speed = speed;
		this.distance = distance;
		this.state = state; //just for semaphore - green, yellow, red
		this.seen = seen;  //list of agents in his view field
		this.around = around;  //list of agents out side his view field
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", position_x=" + position_x + ", position_y=" + position_y
				+ ", facing=" + facing + ", speed=" + speed + ", distance=" + distance + ", state=" + state + ", seen="
				+ seen + ", around=" + around + "]";
	}

}
