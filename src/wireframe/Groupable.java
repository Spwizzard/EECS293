package wireframe;

import java.util.Optional;

/**
 * A Groupable consists of something on a Wireframe that can be grouped together. In addition to
 * grouping and ungrouping operations, the related operations of locking / unlocking are also tied to
 * the Groupable interface.
 */
public interface Groupable {
	
	/**Lock this element. 
	 * 
	 * When a Groupable is locked, it cannot be modified in any way, with few exceptions.
	 */
	public void lock();
	
	/**Unlock this element. 
	 * 
	 * When a Groupable is locked, it cannot be modified in any way, with few exceptions.
	 */
	public void unlock();

	/**Returns whether this Groupable is locked or not.
	 * @return true if this Groupable is locked, false if it is unlocked
	 */
	public boolean isLocked();
	
	/**Sets the parent group of this Groupable to the specified parent group.
	 * 
	 * If a Groupable has a parent group, then it is an element of its parent group's member list. 
	 * @param parentGroup the Optional containing the parent group of this element
	 * @throws LockedException if this Groupable is locked and cannot be modified
	 */
	public void setParentGroup(Optional<Group> parentGroup) throws LockedException;
	
	/**Returns the parent group of this Groupable.
	 * 
	 * If a Groupable has a parent group, then it is an element of its parent group's member list. 
	 * @return the parentGroup of this Groupable, enclosed in an Optional. 
	 */
	public Optional<Group> getParentGroup();
	
	/**Specifies the movement behavior to do when this Groupable's group moves to a new location using given offset.
	 * @param xOffset the x offset to move this Groupable to (in pixels)
	 * @param yOffset the y offset to move this Groupable to (in pixels)
	 * @throws LockedException if this Groupable is locked or its parent Group is locked
	 */
	public void moveGroup(int xOffset, int yOffset) throws LockedException;
	
	/**Specifies the deletion behavior to do when this Groupable's group is deleted. 
	 * @throws LockedException if this Groupable cannot be deleted due to a lock
	 */
	public void groupDelete() throws LockedException;
}
