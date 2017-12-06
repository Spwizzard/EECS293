package wireframe;

/**
 *A Paragraph element represents a blocked-out block of text in a wireframe. The text can be aligned.
 *
 */
public class Paragraph extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	/**Creates a Paragraph at specified location with default alignment LEFT.
	 * @param location the location to place the Paragraph
	 */
	Paragraph(Location location) {
		super(location);
		setType(Enums.ElementType.PARAGRAPH);
	}

	/**Returns the alignment of this Paragraph's text.
	 * @return the alignment of this Paragraph's text
	 */
	final Enums.Alignment getAlignment() {
		return alignment;
	}
	
	/**Aligns this Paragraph's text.
	 * @param alignment the alignment to align the text with
	 */
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
