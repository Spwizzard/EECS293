package wireframe;

/**
 *A RoundedBox element is just a box with rounded edges in a wireframe. It does nothing else.
 *
 */
public class RoundedBox extends AbstractWireframeElement {

	/**Creates a RoundedBox at the specified location. 
	 * @param location the location at which to place the RoundedBox
	 */
	RoundedBox(Location location) {
		super(location);
		setType(Enums.ElementType.ROUNDED_BOX);
	}

}
