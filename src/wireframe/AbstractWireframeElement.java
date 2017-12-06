package wireframe;

import java.util.ArrayList;
import java.util.Optional;

/**
 *A wireframe element this, simply, not an Annotation. The properties differentiating an AbstractWireframeElement 
 *from an AbstractElement are lockability, groupability, and the ability to have annotations. 
 *
 */
public class AbstractWireframeElement extends AbstractElement implements Groupable{
	
	private boolean locked = false;
	private ArrayList<Annotation> annotations = new ArrayList<Annotation>();
	private Optional<Group> parentGroup = Optional.empty();
	
	/**Constructs a AbstractWireframeElement with specified location.
	 * Defaults this element to unlocked with no parent group and no annotations. 
	 * @param location the location to place this element
	 */
	AbstractWireframeElement(Location location){
		super(location);
	}
	
	
	/*
	 * Moves this element to the specified location
	 * Overrided behavior includes notifying a parent group, if it exists, 
	 * that the rest of the group must move as well.
	 * Also moves any annotations that are linked to this element 
	 * 
	 * @param location the location to move to
	 * @throws LockedException if this element is locked
	 */
	void moveTo(Location location) throws LockedException{ //Extended
		verifyNotLocked();
		
		int xOffset = location.getX() - this.getTopLeftLocation().getX();
		int yOffset = location.getY() - this.getTopLeftLocation().getY();
		//Group movement
		if(parentGroup.isPresent()){
			Group parent = parentGroup.get();
			parent.moveGroup(xOffset, yOffset); //delegates rest of movement behavior to parent
		}
		else{
			super.moveTo(location);
			moveAnnotations(xOffset, yOffset);
		}
	}
	
	/*
	 * Moves this element to a new location where
	 * 	x = current X + xOffset
	 *  y = current Y + yOffset
	 *  
	 *  Only used by the parent group of this element
	 * @param xOffset the distance to move in the X direction (pixels)
	 * @param yOffset the distance to move in the Y direction (pixels)
	 * @throws LockedException if this element is locked
	 */
	void moveUsingOffset(int xOffset,int yOffset) throws LockedException{
		verifyNotLocked();
		int newX = getTopLeftLocation().getX() + xOffset;
		int newY = getTopLeftLocation().getY() + yOffset;
		moveAnnotations(xOffset, yOffset);
		super.moveTo(new Location(newX, newY));
	}
	
	/*
	 * Resizes this element to a new width and height, specified by pixelWidth and pixelHeight, respectively.
	 * 
	 * @param pixelWidth the new pixel width to resize this element to
	 * @param pixelHeight the new pixel height to resize this element to
	 * @throws LockedException if this element is locked
	 */
	void resizeTo(int pixelWidth, int pixelHeight) throws LockedException{ //Extended
		verifyNotLocked();
		super.resizeTo(pixelWidth, pixelHeight);
	}
	
	
	/* 
	 * Deletes and dereferences this element from the wireframe.
	 * Overridden behavior allows for locked checks, notifying parent group of deletion, and deleting annotations.
	 * 
	 * @throws LockedException if this element cannot be deleted due to a lock
	 */
	void deleteElement() throws LockedException {
		verifyNotLocked();
		//Parent group behavior
		if(parentGroup.isPresent()){ 
			//Remove this element from its parent
			ArrayList<Groupable> memberList = new ArrayList<Groupable>();
			memberList.add(this);
			parentGroup.get().removeGroupMembers(memberList);
		}
		//always remove all annotations
		while(annotations.size() > 0){
			removeAnnotation(annotations.get(0));
		}
	}
	
	/* Moves this element when its group moves if it isn't locked
	 * @param xOffset the x offset to move this element to (in pixels)
	 * @param yOffset the y offset to move this element to (in pixels)
	 * @throws LockedException if this element is locked
	 */
	@Override
	public void moveGroup(int xOffset, int yOffset) throws LockedException {
		verifyNotLocked();
		moveUsingOffset(xOffset, yOffset);
	}
	
	/* Deletes this element if it is not locked when told to do so by its group
	 * @throws LockedException if this Groupable cannot be deleted due to a lock
	 */
	@Override
	public void groupDelete() throws LockedException {
		verifyNotLocked();
		setParentGroup(Optional.empty());
	}
	
	/* Lock this element. 
	 * 
	 * When an element is locked, it cannot be modified in any way, with few exceptions.
	 */
	public void lock(){
		locked = true;
	}
	
	/* Unlock this element. 
	 * 
	 * When an element is locked, it cannot be modified in any way, with few exceptions.
	 */
	public void unlock(){
		locked = false;
	}
	
	
	/**Adds a new Annotation to the wireframe linked to this element.
	 * @throws LockedException if this element is locked
	 */
	void createAnnotation() throws LockedException{
		verifyNotLocked();
		//Create a new Annotation for this element
		Annotation annotation = new Annotation(this, this.getTopLeftLocation());
		annotations.add(annotation);
	}
	
	/**Removes the specified annotation from this element, effectively deleting it from the wireframe.
	 * @param annotation the annotation of this element to delete
	 * @throws LockedException if this element is locked
	 */
	void removeAnnotation(Annotation annotation) throws LockedException{
		assert annotation != null : "Tried to remove an annotation that doesn't exist";
		assert annotations.contains(annotation) : "Tried to remove an annotation not linked to this element";
		verifyNotLocked();
		annotation.setLinkedElement(null);
		annotations.remove(annotation);
	}
	
	/**Displays all annotations attached to this element.
	 * 
	 */
	void displayAnnotations(){
		for(Annotation annotation : annotations){
			annotation.display();
		}
	}
	
	/**Hides all annotations attached to this element.
	 * 
	 */
	void hideAnnotations(){
		for(Annotation annotation : annotations){
			annotation.hide();
		}
	}
	
	/**Aligns all annotations attached to this element to the specified alignment.
	 * @param alignment the alignment to set all annotations' alignments to
	 * @throws LockedException if this element is locked
	 */
	void alignAnnotations(Enums.Alignment alignment) throws LockedException{
		verifyNotLocked();
		for(Annotation annotation : annotations){
			annotation.setAlignment(alignment);
		}
	}
	
	/* Sets the parent group of this element to the specified parent group.
	 * 
	 * If an element has a parent group, then it is an element of its parent group's member list. 
	 * @param parentGroup the Optional containing the parent group of this element
	 * @throws LockedException if this element is locked and cannot be modified
	 */
	public final void setParentGroup(Optional<Group> parentGroup) throws LockedException{
		verifyNotLocked();
		this.parentGroup = parentGroup;
	}
	
	/* Returns the parent group of this element.
	 * 
	 * If an element has a parent group, then it is an element of its parent group's member list. 
	 * @return the parentGroup of this element, enclosed in an Optional. 
	 */
	public final Optional<Group> getParentGroup() {
		return parentGroup;
	}

	/* Returns whether this element is locked or not.
	 * @return true if this element is locked, false if it is unlocked
	 */
	public final boolean isLocked() {
		return locked;
	}
	
	/**Returns the list of annotations attached to this element
	 * @return the list of annotations attached to this element
	 */
	final ArrayList<Annotation> getAnnotations() {
		return annotations;
	}
		
	//Moves all attached annotations using the specified offsets
	private void moveAnnotations(int xOffset, int yOffset) throws LockedException{
		verifyNotLocked();
		for(Annotation annotation : annotations){
			annotation.moveUsingOffset(xOffset, yOffset);
		}
	}
	
	//Ensures that this element is not locked, throwing an exception if it is.
	private void verifyNotLocked() throws LockedException{
		if(isLocked()){
			throw new LockedException("Element is locked!");
		}
	}

	
}
