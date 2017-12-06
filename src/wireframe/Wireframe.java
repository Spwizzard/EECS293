package wireframe;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * The Wireframe class is the outermost interface of the wireframe package. A GUI that uses the wireframe
 * package can create a Wireframe object and perform the operations in this class on it to display a working wireframe.
 */
public class Wireframe {

	private ArrayList<Groupable> wireframeElements = new ArrayList<Groupable>();

	public Wireframe() {
	}
	
	/**
	 * Creates a new element using given type and places it at given location on this wireframe at the front.
	 * 
	 * @param type the type of element to be placed
	 * @param location the location at which this element should be placed
	 */
	void placeElement(Enums.ElementType type, Location location){
		assert type != null : "Tried to place an element of unknown type";
		assert location != null : "Tried to place element at null location";
		
		Optional<AbstractWireframeElement> newElement = type.createInstance(location);
		//Prevents the creation of Annotation type elements here
		if(newElement.isPresent()){ 
			wireframeElements.add(0,newElement.get());
		}
	}
	
	/**
	 * Deletes the given element from this wireframe. If the element has annotations, those are deleted as well.
	 * If the element is grouped, the element is also removed from the group.
	 * 
	 * @param element the element to be deleted
	 * @throws LockedException if the element is locked or is in a locked group.
	 */
	void deleteElement(AbstractElement element) throws LockedException{
		assert element != null : "Element to delete is null!";
		
		//behavior delegated to element
		element.deleteElement();
		
		if(element.getType() != Enums.ElementType.ANNOTATION){
			//If not an annotation, then we have to remove from wireframeElements too
			wireframeElements.remove(element);
		}
	}
	
	/**
	 * Brings the given element in front of all other elements.
	 * If the element is a group, all members are brought to the front in their original arrangement.
	 * 
	 * @param element the element(or group) to be brought to the front
	 * @throws LockedException if element is locked or part of a locked group.
	 */
	void bringElementToFront(Groupable element) throws LockedException{
		verifyElementNotLocked(element);	
		//take element out if it exists and place it back in the front
		if(wireframeElements.remove(element)){
			wireframeElements.add(0, element); //shifts all others back
		}
	}
	
	/**
	 * Brings the given element forward in the arrangement of elements.
	 * If the element is a group, all members are brought forward in their original arrangement.
	 * 
	 * @param element the element(or group) to be brought forward
	 * @throws LockedException if element is locked or part of a locked group.
	 */
	void bringElementForward(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		int index = wireframeElements.indexOf(element);
		//checks both if element exists (index > -1) and if we can move it forward (index > 0)
		if(index > 0){
			Groupable oldElement = wireframeElements.set(index - 1, element); //put member in new position
			wireframeElements.set(index, oldElement);//Put old member in member's position, swapping them
		}
		
	}
	
	/**
	 * Sends the given element behind all other elements.
	 * If the element is a group, all members are sent to the back in their original arrangement.
	 * 
	 * @param element the element(or group) to be sent to the back
	 * @throws LockedException if element is locked or part of a locked group.
	 */
	void sendElementToBack(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		//take element out if it exists and place it in the back
		if(wireframeElements.remove(element)){
			wireframeElements.add(element); //add it back in at the end
		}
		
	}
	
	/**
	 * Sends the given element backward in the arrangement of elements.
	 * If the element is a group, all members are sent backward in their original arrangement.
	 * 
	 * @param element the element(or group) to be sent backward
	 * @throws LockedException if element is locked or part of a locked group.
	 */
	void sendElementBackward(Groupable element) throws LockedException{
		verifyElementNotLocked(element);
		int index = wireframeElements.indexOf(element);
		//checks both if element exists (index >= 0) and if we can move it backward (index < size -1)
		if(index < wireframeElements.size() - 1 && index >= 0){
			Groupable oldElement = wireframeElements.set(index + 1, element); //put member in new position
			wireframeElements.set(index, oldElement);//Put old member in member's position, swapping them
		}
	}
	

	/**Groups the given list of elements together. The new group is placed at the index of the farthest-forward
	 * element in the new group. If a group is given as an element, then it is added as a subgroup to the new group. 
	 * 
	 * @param elements the elements or groups to be placed in the new group. 
	 * @throws LockedException if one or more of the elements are locked.
	 */
	void groupElements(ArrayList<Groupable> elements) throws LockedException{
		assert elements != null : "Elements to group are null!";
		//get the wireframeElements index of the closest element
		int index = wireframeElements.size() - 1;
		ListIterator<Groupable> iter = wireframeElements.listIterator();
		while(iter.hasNext()){
			int nextIndex = iter.nextIndex();
			if(elements.contains(iter.next())){
				index = nextIndex;
				break;
			}
		}
		Group group = Group.of(elements); //internal locking checks in static "of" method
		//Remove elements and "add them back in" as a group
		wireframeElements.removeAll(elements);
		wireframeElements.add(index, group);
	}
	
	/**Separates the given group back into separate elements in this wireframe. The elements of the
	 * group are placed back in in the same order as they were in the group, at the index of the group.
	 * If a subgroup is one of the elements, then it is not split. 
	 * The group is deleted after its elements have been ungrouped.
	 * 
	 * @param group the group to be split up
	 * @throws LockedException if the group is locked
	 */
	void ungroupElements(Group group) throws LockedException{
		assert group != null : "Group to ungroup from is null!";
		int index = wireframeElements.indexOf(group);	//the index to place the elements back into
		verifyElementNotLocked(group);
		
		//Add then delete because otherwise we would be adding back in an empty list
		List<Groupable> groupMembers = group.getGroupMembers();
		wireframeElements.addAll(index, groupMembers);	
		group.deleteGroup();
		wireframeElements.remove(group);
	}
		
	/**Returns the wireframe elements list.
	 * @return the wireframe elements list.
	 */
	final ArrayList<Groupable> getWireframeElements() {
		return wireframeElements;
	}
	
	/**Ensures that the given element is unlocked
	 * 
	 * @param element the element to check
	 * @throws LockedException if the element is locked
	 */
	private void verifyElementNotLocked(Groupable element) throws LockedException{
		if(element.isLocked()){
			throw new LockedException("Groupable is locked!");
		}
	}

}
