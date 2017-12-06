package wireframe;

import java.awt.Point;

/**
 * A Location object is simply a wrapper for a x value and a y value. It specifies nothing else other than
 * the values be final integers. 
 */
class Location {
	
	private final int x;
	private final int y;
	
	/**Creates a new Location from a java.awt.Point, copying the x and y values from the specified point.
	 * @param point the point to transform into a Location
	 */
	Location(Point point){
		x = point.x;
		y = point.y;
	}
	
	/**Creates a new Location from the specified x and y integers
	 * @param x the x value
	 * @param y the y value
	 */
	Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**Returns the x value of this location
	 * @return the x value of this location
	 */
	public final int getX() {
		return x;
	}
	
	/**Returns the y value of this location
	 * @return the y value of this location
	 */
	public final int getY() {
		return y;
	}
}
