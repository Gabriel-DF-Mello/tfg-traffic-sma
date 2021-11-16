
public class Obstacle {
	public int id;
	public String type;
	public String state;
	public float distance_x;
	public float distance_y;
	public float facing;
	
	
	public Obstacle(int id, String type, String state, float distance_x, float distance_y, float facing) {
		super();
		this.id = id;
		this.type = type;
		this.state = state;
		this.distance_x = distance_x;
		this.distance_y = distance_y;
		this.facing = facing;
	}


	@Override
	public String toString() {
		return "Obstacle [id=" + id + ", type=" + type + ", state=" + state + ", distance_x=" + distance_x
				+ ", distance_y=" + distance_y + ", facing=" + facing + "]";
	}
	
	
}
