package wireframe;

/**
 * A wireframe element that can be attached to another element for the purpose of displaying useful information
 * about that element. Can have its text edited, moved, resized, and hidden. Must be linked to an existing wireframe element.
 * 
 * If its linked element is deleted, the annotation will be dereferenced and deleted as well.
 */
public class Annotation extends AbstractElement {
	
	private AbstractWireframeElement linkedElement;
	private String text = "Annotation";
	private Enums.Alignment alignment = Enums.Alignment.LEFT;
	private boolean hidden = false;
	
	/**Constructs an Annotation linked to specified linkedElement at specified location
	 * Defaults the text to "Annotation" and displays it.
	 * @param linkedElement the element to link this Annotation to
	 * @param location the location to create this element at
	 */
	Annotation(AbstractWireframeElement linkedElement, Location location) {
		super(location);
		this.linkedElement = linkedElement;
		setType(Enums.ElementType.ANNOTATION);
	}
	
	/**Displays this element.
	 * 
	 * Display behavior is implemented by GUI using the wireframe package.
	 */
	void display(){
		hidden = false;
	}
	
	/**Hides this element.
	 * 
	 * Display behavior is implemented by GUI using the wireframe package.
	 */
	void hide(){
		hidden = true;
	}
	
	//Uses the linked element to dereference itself. 
	@Override
	void deleteElement() throws LockedException {
		assert linkedElement != null : "Linked element doesn't exist!";
		linkedElement.removeAnnotation(this);
	}
	
	
	/**Sets the text of this Annotation to the given text.
	 * @param text the new text for this Annotation
	 */
	final void setText(String text) {
		this.text = text;
	}
	
	/**Changes the alignment of the text of this Annotation to the specified alignment.
	 * @param alignment the new alignment of the text
	 */
	final void setAlignment(Enums.Alignment alignment) {
		this.alignment = alignment;
	}
	
	/**Returns the text of this Annotation.
	 * @return the text of this Annotation
	 */
	final String getText() {
		return text;
	}

	/**Returns the alignment of this Annotation.
	 * @return the alignment of this Annotation
	 */
	final Enums.Alignment getAlignment() {
		return alignment;
	}

	/**Returns the element that this Annotation is linked to.
	 * @return the element that this Annotation is linked to
	 */
	final AbstractWireframeElement getLinkedElement() {
		return linkedElement;
	}
	
	
	/**Sets the element that this Annotation is linked to to the specified element.
	 * @param linkedElement the new element to link to
	 */
	final void setLinkedElement(AbstractWireframeElement linkedElement) {
		this.linkedElement = linkedElement;
	}

	/**Returns whether thhis Annotation is hidden or not.
	 * @return true if this Annotation is hidden, false if not.
	 */
	boolean isHidden() {
		return hidden;
	}


}
