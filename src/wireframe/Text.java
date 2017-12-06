package wireframe;

/**
 *A Text element represents a block of canned text in a wireframe. It can be aligned but not edited otherwise.
 *
 */
public class Text extends AbstractWireframeElement {

	private final String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. "
			+ "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque "
			+ "penatibus et magnis dis parturient montes, nascetur ridiculus mus. "
			+ "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. "
			+ "Nulla consequat massa quis enim.";
	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	/**Creates a Text element at specified location with default alignment LEFT.
	 * @param location the location to place the Text
	 */
	Text(Location location) {
		super(location);
		setType(Enums.ElementType.TEXT);
	}
	
	/**Returns the alignment of this Text's text.
	 * @return the alignment of this Text's text
	 */
	final Enums.Alignment getAlignment() {
		return alignment;
	}
	
	/**Aligns this Text's text.
	 * @param alignment the alignment to align the text with
	 */
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

	/**Returns the canned text of this Text.
	 * @return the canned text of this Text
	 */
	final String getText() {
		return text;
	}

}
