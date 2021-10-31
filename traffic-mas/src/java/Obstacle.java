
public class Obstacle {
	public int id;
	public float position_x;
	public float position_y;
	public float facing;
	public String type;
	public String state;
	
	public Obstacle(int id, float position_x, float position_y, float facing, String type, String state) {
		super();
		this.id = id;
		this.position_x = position_x;
		this.position_y = position_y;
		this.facing = facing;
		this.type = type;
		this.state = state;
	}
}
