package wireframe;

import java.util.Optional;

public interface Groupable {
	public void lock();
	public void unlock();
	public boolean isLocked();
	public void setParentGroup(Group parentGroup) throws LockedException;
	public Optional<Group> getParentGroup();
}
