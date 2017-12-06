package wireframe;

/**
 * The AbstractElement class provides some of the basic implementation shared by its subclasses Annotation and AbstractWireframeElement.
 * In particular, it implements moving, resizing, and typing, as well as access to the needed fields for those operations. 
 *
 */
abstract class AbstractElement {
	
	private Location topLeftLocation;
	private int pixelWidth = 100; //Safety default width 
	private int pixelHeight = 100; //Safety default height
	private Enums.ElementType type;
	
	/**
	 * Constructs an element at the given location
	 * Defaults the height and width in pixels to be 100 each.
	 * @param location the location to place the element
	 */
	AbstractElement(Location location){
		this.topLeftLocation = location;
		//subclasses will determine height, width, and type
	}
	
	/**
	 * Moves this element to the specified location
	 * @param location the location to move to
	 * @throws LockedException if this element is locked
	 */
	void moveTo(Location location) throws LockedException{
		this.topLeftLocation = location;
	}
	
	/**
	 * Moves this element to a new location where
	 * 	x = current X + xOffset
	 *  y = current Y + yOffset
	 * @param xOffset the distance to move in the X direction (pixels)
	 * @param yOffset the distance to move in the Y direction (pixels)
	 * @throws LockedException if this element is locked
	 */
	void moveUsingOffset(int xOffset,int yOffset) throws LockedException{
		int newX = getTopLeftLocation().getX() + xOffset;
		int newY = getTopLeftLocation().getY() + yOffset;
		moveTo(new Location(newX, newY));
	}
	
	/**
	 * Resizes this element to a new width and height, specified by pixelWidth and pixelHeight, respectively.
	 * @param pixelWidth the new pixel width to resize this element to
	 * @param pixelHeight the new pixel height to resize this element to
	 * @throws LockedException if this element is locked
	 */
	void resizeTo(int pixelWidth, int pixelHeight) throws LockedException{
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
	}

	/**Deletes and dereferences this element from the wireframe.
	 * @throws LockedException if this element cannot be deleted due to a lock
	 */
	abstract void deleteElement() throws LockedException;
	
	/**Returns the location of the top left pixel of this element
	 * @return the location of the top left pixel of this element
	 */
	Location getTopLeftLocation() {
		return topLeftLocation;
	}

	/**Returns the width of this element in pixels
	 * @return the width of this element in pixels
	 */
	int getPixelWidth() {
		return pixelWidth;
	}

	/**Returns the height of this element in pixels
	 * @return the height of this element in pixels
	 */
	int getPixelHeight() {
		return pixelHeight;
	}

	/**Sets the type of this element
	 * @param type the type to set this element to
	 */
	void setType(Enums.ElementType type) {
		this.type = type;
	}
	
	/**Returns the type of this element
	 * @return the type of this element
	 */
	Enums.ElementType getType() {
		return type;
	}
}
