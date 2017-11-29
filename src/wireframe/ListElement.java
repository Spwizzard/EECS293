package wireframe;

public class ListElement extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	public ListElement(Location location) {
		super(location);
		this.type = Enums.ElementType.LIST;
	}

	final Enums.Alignment getAlignment() {
		return alignment;
	}

	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
