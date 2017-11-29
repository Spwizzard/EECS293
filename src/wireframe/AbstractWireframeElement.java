package wireframe;

import java.util.ArrayList;
import java.util.Optional;

public class AbstractWireframeElement extends AbstractElement implements Groupable{
	
	private boolean locked = false;
	private ArrayList<Annotation> annotations = new ArrayList<Annotation>();
	private Optional<Group> parentGroup = Optional.empty();
	
	AbstractWireframeElement(Location location){
		super(location);
	}
	
	void moveTo(Location location) throws LockedException{ //Extended
		verifyIsNotLocked();
		
		int xOffset = location.getX() - this.getTopLeftLocation().getX();
		int yOffset = location.getY() - this.getTopLeftLocation().getY();
		if(parentGroup.isPresent()){
			Group parent = parentGroup.get();
			parent.moveGroup(xOffset, yOffset);
		}
		else{
			super.moveTo(location);
			moveAnnotations(xOffset, yOffset);
		}
	}
	
	//Only used by the parent group of this element
	void moveUsingOffset(int xOffset,int yOffset) throws LockedException{
		verifyIsNotLocked();
		int newX = getTopLeftLocation().getX() + xOffset;
		int newY = getTopLeftLocation().getY() + yOffset;
		moveAnnotations(xOffset, yOffset);
		super.moveTo(new Location(newX, newY));
	}
	
	void resizeTo(int pixelWidth, int pixelHeight) throws LockedException{ //Extended
		verifyIsNotLocked();
		super.resizeTo(pixelWidth, pixelHeight);
	}
	
	public void lock(){
		locked = true;
	}
	
	public void unlock(){
		locked = false;
	}
	
	void createAnnotation() throws LockedException{
		verifyIsNotLocked();
		//Create a new Annotation for this element
		Annotation annotation = new Annotation(this, this.getTopLeftLocation());
		annotations.add(annotation);
	}
	
	void removeAnnotation(Annotation annotation) throws LockedException{
		verifyIsNotLocked();
		if(annotation != null){
			annotation.setLinkedElement(null);
			annotations.remove(annotation);
		}
	}
	
	void displayAnnotations(){
		for(Annotation annotation : annotations){
			annotation.display();
		}
	}
	
	void hideAnnotations(){
		for(Annotation annotation : annotations){
			annotation.hide();
		}
	}
	
	void alignAnnotations(Enums.Alignment alignment) throws LockedException{
		verifyIsNotLocked();
		for(Annotation annotation : annotations){
			annotation.setAlignment(alignment);
		}
	}
	
	public final void setParentGroup(Group parentGroup) throws LockedException{
		verifyIsNotLocked();
		if(parentGroup != null){
			this.parentGroup = Optional.of(parentGroup);
		}
		else{
			this.parentGroup = Optional.empty();
		}
	}
	
	public final Optional<Group> getParentGroup() {
		return parentGroup;
	}

	public final boolean isLocked() {
		return locked;
	}
	
	final ArrayList<Annotation> getAnnotations() {
		return annotations;
	}
	
	private void moveAnnotations(int xOffset, int yOffset) throws LockedException{
		verifyIsNotLocked();
		for(Annotation annotation : annotations){
			annotation.moveUsingOffset(xOffset, yOffset);
		}
	}
	
	private void verifyIsNotLocked() throws LockedException{
		if(isLocked()){
			throw new LockedException("Element is locked!");
		}
	}
}
