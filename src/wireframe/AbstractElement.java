package wireframe;

abstract class AbstractElement {
	
	private Location topLeftLocation;
	private int pixelWidth = 100; //Safety default width 
	private int pixelHeight = 100; //Safety default height
	protected Enums.ElementType type;
	
	AbstractElement(Location location){
		this.topLeftLocation = location;
		//subclasses will determine height, width, and type
	}
	
	void moveTo(Location location) throws LockedException{
		this.topLeftLocation = location;
	}
	
	void moveUsingOffset(int xOffset,int yOffset) throws LockedException{
		int newX = getTopLeftLocation().getX() + xOffset;
		int newY = getTopLeftLocation().getY() + yOffset;
		moveTo(new Location(newX, newY));
	}
	
	void resizeTo(int pixelWidth, int pixelHeight) throws LockedException{
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
	}
	
	Location getTopLeftLocation() {
		return topLeftLocation;
	}

	int getPixelWidth() {
		return pixelWidth;
	}

	int getPixelHeight() {
		return pixelHeight;
	}

	Enums.ElementType getType() {
		return type;
	}
}
