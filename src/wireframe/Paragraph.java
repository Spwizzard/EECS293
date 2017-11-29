package wireframe;

public class Paragraph extends AbstractWireframeElement {

	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	public Paragraph(Location location) {
		super(location);
		this.type = Enums.ElementType.PARAGRAPH;
	}

	final Enums.Alignment getAlignment() {
		return alignment;
	}

	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

}
