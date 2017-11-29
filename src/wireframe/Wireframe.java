package wireframe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Wireframe {

	private ArrayList<Groupable> wireframeElements = new ArrayList<Groupable>();
	public Wireframe() {
		
	}
	
	void placeElement(Enums.ElementType type, Location location) throws WireframeException{
		
		if(type.equals(Enums.ElementType.ANNOTATION)){
			return; //We don't make annotations like this
		}
		Constructor<?> elementConstructor;
		try {
			elementConstructor = type.getElementClass().getDeclaredConstructor(Location.class);
			AbstractWireframeElement element = (AbstractWireframeElement) elementConstructor.newInstance(location);
			//Type is AbstractWireframeElement because we create annotations differently
			wireframeElements.add(element);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new WireframeException("Tried to create an Element that doesn't exist!");
		}
	}
	
	void deleteElement(AbstractElement element) throws LockedException{
		assert element != null : "Element is null!";
		
		if(element.getClass() == Annotation.class){
			//if the element is an annotation, to remove it we must go to its linkedElement
			Annotation annotation = (Annotation)element;
			annotation.getLinkedElement().removeAnnotation(annotation);
		}
		else{
			//the element is an AbstractWireframeElement
			AbstractWireframeElement wfElement = (AbstractWireframeElement)element;
			verifyElementNotLocked(wfElement);
			if(wfElement.getParentGroup().isPresent()){ //if it has a parent group
				ArrayList<Groupable> memberList = new ArrayList<Groupable>();
				memberList.add(wfElement);
				wfElement.getParentGroup().get().removeGroupMembers(memberList);
			}
			//always remove annotations
			for(int i = 0; i < wfElement.getAnnotations().size(); i++){
				Annotation annotation = wfElement.getAnnotations().get(i);
				wfElement.removeAnnotation(annotation);
			}
			//finally, remove it from wireframeElements list
			wireframeElements.remove(wfElement);
		}
	}
	
	void bringElementToFront(Groupable element) throws LockedException{
		verifyElementNotLocked(element);	
		wireframeElements.remove(element); //take the member out
		wireframeElements.add(0, element); //add it back in at index 0, shifting all others back
	}
	
	void bringElementForward(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		int index = wireframeElements.indexOf(element);
		if(index > 0){
			Groupable oldElement = wireframeElements.set(index - 1, element); //put member in new position
			wireframeElements.set(index, oldElement);//Put old member in member's position, swapping them
		}
		
	}
	
	void sendElementToBack(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		wireframeElements.remove(element); //take the member out
		wireframeElements.add(element); //add it back in at the end of the list
	}
	
	void sendElementBackward(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		int index = wireframeElements.indexOf(element);
		if(index < wireframeElements.size() - 1){
			Groupable oldElement = wireframeElements.set(index + 1, element); //put member in new position
			wireframeElements.set(index, oldElement);//Put old member in member's position, swapping them
		}
	}
	
	void groupElements(List<Groupable> elements) throws LockedException{
		assert elements != null : "Elements to group are null!";
		
		Group group = new Group(elements); //internal locking checks in constructor
		//take the elements out of the elements list so they dont appear twice
		wireframeElements.removeAll(elements);
		//add them back in as a group
		wireframeElements.add(group);
	}
	
	void ungroupElements(Group group) throws LockedException{
		assert group != null : "Group to ungroup from is null!";
		
		verifyElementNotLocked(group);
		List<Groupable> groupMembers = group.getGroupMembers();
		wireframeElements.addAll(groupMembers);	
		group.deleteGroup();
		wireframeElements.remove(group);
	}
		
	final ArrayList<Groupable> getWireframeElements() {
		return wireframeElements;
	}
	
	private void verifyElementNotLocked(Groupable element) throws LockedException{
		if(element.isLocked()){
			throw new LockedException("Groupable is locked!");
		}
	}

}
