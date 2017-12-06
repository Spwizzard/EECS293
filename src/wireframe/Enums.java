package wireframe;

import java.util.Optional;

/**
 * The Enums class holds the enums that the Wireframe package uses.
 *
 */
public class Enums {
	
	/**
	 *The Alignment enum is used to designate the alignment of text or blocked text for a wireframe element
	 *
	 */
	public enum Alignment{
		LEFT,
		CENTER,
		RIGHT,
		JUSTIFIED;
	}
	
	/**
	 *The ElementType enum designates types of wireframe elements. 
	 *Can potentially be used to assist in drawing elements for a GUI.
	 */
	public enum ElementType{
		ANNOTATION		{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.empty();}},
		IMAGE			{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new Image(location));}},
		ROUNDED_BOX		{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new RoundedBox(location));}},
		TEXT			{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new Text(location));}},
		PARAGRAPH		{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new Paragraph(location));}},
		HEADER			{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new Header(location));}},
		LIST			{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new ListElement(location));}},
		COMBO_BOX		{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new ComboBox(location));}},
		PROGRESS_BAR	{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new ProgressBar(location));}},
		SCROLL_BAR		{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new ScrollBar(location));}},
		SLIDER			{Optional<AbstractWireframeElement> createInstance(Location location) {return Optional.of(new Slider(location));}};
		
		/**
		 * Creates a new AbstractWireframeElement corresponding to the enum type at given location and
		 * returns it in an optional shell.
		 * If the enum is an ElementType.ANNOTATION, then this optional is empty, as an Annotation is not an AbstractWireframeElement. 
		 * 
		 * @param location the location to place the newly created element
		 * @return the new element in an Optional shell
		 */
		abstract Optional<AbstractWireframeElement> createInstance(Location location);
	}
}
