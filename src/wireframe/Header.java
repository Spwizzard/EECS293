package wireframe;

public class Header extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	public Header(Location location) {
		super(location);
		this.type = Enums.ElementType.HEADER;
	}

	final Enums.Alignment getAlignment() {
		return alignment;
	}

	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
