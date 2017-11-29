package wireframe;

public class Annotation extends AbstractElement {
	
	private AbstractWireframeElement linkedElement;
	private String text = "Annotation";
	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	private boolean hidden = false;
	
	Annotation(AbstractWireframeElement linkedElement, Location location) {
		super(location);
		this.linkedElement = linkedElement;
		this.type = Enums.ElementType.ANNOTATION;
	}
	
	void display(){
		hidden = false;
	}
	
	void hide(){
		hidden = true;
	}
	
	final void setText(String text) {
		this.text = text;
	}
	
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}
	
	final String getText() {
		return text;
	}

	final Enums.Alignment getAlignment() {
		return alignment;
	}

	final AbstractWireframeElement getLinkedElement() {
		return linkedElement;
	}
	
	final void setLinkedElement(AbstractWireframeElement linkedElement) {
		this.linkedElement = linkedElement;
	}

	boolean isHidden() {
		return hidden;
	}
}
