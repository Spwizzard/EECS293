package wireframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *A Group links together a list of elements so that modifications to one element of the group also apply to
 *other elements of the group. A group may be locked and grouped. A group also maintains an order for its 
 *member elements. Member elements can be rearranged within that order. 
 *
 */
public class Group implements Groupable{
	
	private boolean locked = false;
	private ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
	private Optional<Group> parentGroup = Optional.empty();

	private Group(ArrayList<Groupable> groupMembers){
		this.groupMembers = groupMembers;
	}
	
	/**Builds a new Group using the specified group members. 
	 * @param groupMembers the Groupables that the new group will contain
	 * @return the new group containing the specified group members
	 * @throws LockedException if one or more of the group members is locked
	 */
	static Group of(ArrayList<Groupable> groupMembers) throws LockedException{
		//Make sure no group members are locked so we aren't left with null pointer if one in the middle is locked
		for(Groupable member : groupMembers){
			if(member.isLocked()){
				throw new LockedException("Tried to make a group with a locked member!");
			}
		}
		//create the group
		Group group = new Group(groupMembers);
		//notify group members that this is their new parent group
		for(Groupable member : group.groupMembers){
			member.setParentGroup(Optional.of(group));
		}
		return group;
	}
	
	/**Inserts the specified group members to the end of the list of this Group's member list.
	 * @param groupMembers the list of group members to add
	 * @throws LockedException if this group is locked or one of the new members is locked
	 */
	void addGroupMembers(List<Groupable> groupMembers) throws LockedException{	
		verifyNotLocked();
		//Make sure no group members are locked so we aren't left with null pointer
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			//we can't have this group to its own members list...
			if(member == this){
				continue;
			}
			//notify group member that this is their new parent group
			member.setParentGroup(Optional.of(this));
			this.groupMembers.add(member);
		}
	}
	
	/**Removes the specified group members from this group's member list.
	 * @param groupMembers the group members to remove
	 * @throws LockedException if this group is locked or one of the group members is locked
	 */
	void removeGroupMembers(List<Groupable> groupMembers) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			member.setParentGroup(Optional.empty());
			this.groupMembers.remove(member);
		}
	}
	
	/* Moves all group members according to the specified offsets
	 * @param xOffset the offset in the x axis (pixels)
	 * @param yOffset the offset in the y axis (pixels)
	 * @throws LockedException if this group is locked or one of the group members is locked
	 */
	public void moveGroup(int xOffset, int yOffset) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			//moveGroup delegates behvaior depending on whether member is an element or group
			member.moveGroup(xOffset, yOffset);
		}
	}
	
	/**Recursively deletes this group and any subgroups under it.
	 * @throws LockedException if this group or any Groupables contained in it are locked. 
	 */
	void deleteGroup() throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			//Use groupDelete because behvaior is delegated based on whether member is a group or element
			member.groupDelete();
		}
		groupMembers.clear();
		setParentGroup(Optional.empty());
	}
	
	/* Deletes this group when told to do so by parent group. 
	 * @throws LockedException if this group cannot be deleted due to a lock
	 */
	@Override
	public void groupDelete() throws LockedException {
		deleteGroup();
		setParentGroup(Optional.empty());
	}
	
	/**Brings the given group member in front of all other members.
	 * If the element is a group, all group members are brought to the front in their original arrangement.
	 * @param member the group member to move to the front
	 * @throws LockedException if the group or member is locked
	 */
	void bringMemberToFront(Groupable member) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		//take member out if it exists and place it back in the front
		if(groupMembers.remove(member)){
			groupMembers.add(0, member); //shifts all others back
		}
	}
	
	/** Brings the specified member forward in the arrangement of group members.
	 * If the element is a group, all members are brought forward in their original arrangement.
	 * 
	 * @param member the member to be to be brought forward
	 * @throws LockedException if the group or member is locked
	 */
	void bringMemberForward(Groupable member) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		int index = groupMembers.indexOf(member);
		//checks both if member exists (index > -1) and if we can move it forward (index > 0)
		if(index > 0){
			Groupable oldMember = groupMembers.set(index - 1, member); //put member in new position
			groupMembers.set(index, oldMember);//Put old member in member's position, swapping them
		}
		
	}
	
	/** Sends the given member behind all other members.
	 * If the element is a group, all members are sent to the back in their original arrangement.
	 * 
	 * @param member the member to be sent to the back
	 * @throws LockedException if the group or member is locked
	 */
	void sendMemberToBack(Groupable member) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		//take member out if it exists and place it at the end
		if(groupMembers.remove(member)){//take the member out
			groupMembers.add(member); //add it back in
		}; 
		
	}
	
	/**Sends the given member backward in the arrangement of members.
	 * If the element is a group, all members are sent backward in their original arrangement.
	 * 
	 * @param member the member to be sent backwards
	 * @throws LockedException if the group or member is locked
	 */
	void sendMemberBackward(Groupable member) throws LockedException{
		verifyNotLocked();
		verifyMembersNotLocked();
		int index = groupMembers.indexOf(member);
		//checks both if element exists (index >= 0) and if we can move it backward (index < size -1)
		if(index < groupMembers.size() - 1 && index >= 0){
			Groupable oldMember = groupMembers.set(index + 1, member); //put member in new position
			groupMembers.set(index, oldMember);//Put old member in member's position, swapping them
		}
	}
	
	//Locks this group and all group members
	public void lock(){
		locked = true;
		for(Groupable member : groupMembers){
			member.lock();
		}
	}
	
	//Unlocks this group and all group members
	public void unlock(){
		locked = false;
		for(Groupable member : groupMembers){
			member.unlock();
		}
	}
	
	/* Sets the parent group of this group to the specified parent group.
	 * 
	 * If a group has a parent group, then it is an element of its parent group's member list. 
	 * @param parentGroup the Optional containing the parent group of this group
	 * @throws LockedException if this group is locked and cannot be modified
	 */
	public final void setParentGroup(Optional<Group> parentGroup) throws LockedException{
		verifyNotLocked();
		this.parentGroup = parentGroup;
	}
	
	/* Returns the parent group of this group.
	 * 
	 * If a group has a parent group, then it is an element of its parent group's member list. 
	 * @return the parentGroup of this group, enclosed in an Optional. 
	 */
	public final Optional<Group> getParentGroup() {
		return parentGroup;
	}

	/* Returns whether this group is locked or not.
	 * @return true if this group is locked, false if it is unlocked
	 */
	public final boolean isLocked() {
		return locked;
	}

	/**Returns the ordered list of all group members of this Group
	 * @return the ordered list of all group members of this Group
	 */
	final ArrayList<Groupable> getGroupMembers() {
		return groupMembers;
	}
	
	//Ensures that this group isn't locked by throwing an exception if it is
	private void verifyNotLocked() throws LockedException{
		if(isLocked()){
			throw new LockedException("Group is locked!");
		}
	}

	//Ensures that all group members aren't locked by throwing an exception if one is
	private void verifyMembersNotLocked() throws LockedException{
		for(Groupable member : groupMembers){
			if(member.isLocked()){
				throw new LockedException("One of the group members is locked!");
			}
		}
	}
}
