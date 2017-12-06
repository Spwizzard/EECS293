package wireframe;

/**
 * A Header element represents some blocked-out text that is larger than regular text, meant as a headline.
 * The blocked-out text can be aligned.
 *
 */
public class Header extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	/**Creates a Header element with default alignment LEFT at specified location.
	 * @param location the location to place the Header
	 */
	Header(Location location) {
		super(location);
		setType(Enums.ElementType.HEADER);
	}

	/**Returns the alignment of this Header's text.
	 * @return the alignment of this Header's text
	 */
	final Enums.Alignment getAlignment() {
		return alignment;
	}

	/**Aligns this Header's text.
	 * @param alignment the alignment to align the text with
	 */
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
