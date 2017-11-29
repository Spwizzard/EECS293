package wireframe;

public class Text extends AbstractWireframeElement {

	private final String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. "
			+ "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque "
			+ "penatibus et magnis dis parturient montes, nascetur ridiculus mus. "
			+ "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. "
			+ "Nulla consequat massa quis enim.";
	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	
	public Text(Location location) {
		super(location);
		this.type = Enums.ElementType.TEXT;
	}
	
	final Enums.Alignment getAlignment() {
		return alignment;
	}

	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}

	final String getText() {
		return text;
	}

}
