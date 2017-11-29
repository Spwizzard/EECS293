package wireframe;

public class Enums {
	
	public enum Alignment{
		LEFT,
		CENTER,
		RIGHT,
		JUSTIFIED;
	}
	
	public enum ElementType{
		ANNOTATION 		(Annotation.class), //Shouldn't be used
		IMAGE			(Image.class),
		ROUNDED_BOX		(RoundedBox.class),
		TEXT			(Text.class),
		PARAGRAPH		(Paragraph.class),
		HEADER			(Header.class),
		LIST			(ListElement.class),
		COMBO_BOX		(ComboBox.class),
		PROGRESS_BAR	(ProgressBar.class),
		SCROLL_BAR		(ScrollBar.class),
		SLIDER			(Slider.class);
		
		private final Class<?> elementClass;
		
		ElementType(Class<?> elementClass){
			this.elementClass = elementClass;
		}
		
		final Class<?> getElementClass() {
			return elementClass;
		}
	}
}
