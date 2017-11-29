package wireframe;

import java.awt.Point;

class Location {
	
	private final int x;
	private final int y;
	
	Location(Point point){
		x = point.x;
		y = point.y;
	}
	
	Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}
}
