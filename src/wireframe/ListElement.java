package wireframe;

/**
 * A ListElement element represents a blocked-out text list in a wireframe. The text can be aligned.
 *
 */
public class ListElement extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	/**Creates a ListElement at specified location with default alignment LEFT.
	 * @param location the location to place the ListElement
	 */
	ListElement(Location location) {
		super(location);
		setType(Enums.ElementType.LIST);
	}

	/**Returns the alignment of this ListElement's text.
	 * @return the alignment of this ListElement's text
	 */
	final Enums.Alignment getAlignment() {
		return alignment;
	}
	
	/**Aligns this ListElement's text.
	 * @param alignment the alignment to align the text with
	 */
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
