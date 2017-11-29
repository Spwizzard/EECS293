package wireframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Group implements Groupable{
	
	private boolean locked = false;
	private ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
	private Optional<Group> parentGroup = Optional.empty();

	Group(List<Groupable> groupMembers) throws LockedException{
		addGroupMembers(groupMembers);
	}
	
	void addGroupMembers(List<Groupable> groupMembers) throws LockedException{	
		verifyIsNotLocked();
		//Make sure no group members are locked so we aren't left with null pointer
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			if(member == this){
				//we can't this group to its own members list...
				continue;
			}
			member.setParentGroup(this);
			this.groupMembers.add(member);
		}
	}
	
	void removeGroupMembers(List<Groupable> groupMembers) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			member.setParentGroup(null);
			this.groupMembers.remove(member);
		}
	}
	
	void moveGroup(int xOffset, int yOffset) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			if(member.getClass() == Group.class){
				//Have to use a cast to recursively move the group
				Group memberGroup = (Group)member;
				memberGroup.moveGroup(xOffset, yOffset);
			}
			else{
				//The member is an AbstractWireframeElement
				AbstractWireframeElement memberElement = (AbstractWireframeElement)member;
				memberElement.moveUsingOffset(xOffset, yOffset);
			}
		}
	}
	
	void deleteGroup() throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		
		for(Groupable member : groupMembers){
			if(member.getClass() == Group.class){
				Group memberGroup = (Group)member;
				memberGroup.deleteGroup();
			}
			member.setParentGroup(null);
		}
		groupMembers.clear();
		setParentGroup(null);
	}
	
	void bringMemberToFront(Groupable member) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		groupMembers.remove(member); //take the member out
		groupMembers.add(0, member); //add it back in at index 0, shifting all others back
	}
	
	void bringMemberForward(Groupable member) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		int index = groupMembers.indexOf(member);
		if(index > 0){
			Groupable oldMember = groupMembers.set(index - 1, member); //put member in new position
			groupMembers.set(index, oldMember);//Put old member in member's position, swapping them
		}
		
	}
	
	void sendMemberToBack(Groupable member) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		groupMembers.remove(member); //take the member out
		groupMembers.add(member); //add it back in at the end of the list
	}
	
	void sendMemberBackward(Groupable member) throws LockedException{
		verifyIsNotLocked();
		verifyMembersNotLocked();
		int index = groupMembers.indexOf(member);
		if(index < groupMembers.size() - 1){
			Groupable oldMember = groupMembers.set(index + 1, member); //put member in new position
			groupMembers.set(index, oldMember);//Put old member in member's position, swapping them
		}
	}
	
	public void lock(){
		locked = true;
		for(Groupable member : groupMembers){
			member.lock();
		}
	}
	
	public void unlock(){
		locked = false;
		for(Groupable member : groupMembers){
			member.unlock();
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

	final ArrayList<Groupable> getGroupMembers() {
		return groupMembers;
	}
	
	private void verifyIsNotLocked() throws LockedException{
		if(isLocked()){
			throw new LockedException("Group is locked!");
		}
	}
	
	private void verifyMembersNotLocked() throws LockedException{
		for(Groupable member : groupMembers){
			if(member.isLocked()){
				throw new LockedException("One of the group members is locked!");
			}
		}
	}
}
