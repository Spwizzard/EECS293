package planning;

import java.util.HashSet;
import java.util.Iterator;

public class EstimatedDuration {
	
	private static HashSet<Assignment> blocked;
	private static HashSet<Assignment> started;
	private static HashSet<Assignment> finished; 
	
	public static int estimatedDurationOfAssignments(HashSet<Assignment> assignments) throws CircularRequirementException{
		blocked = new HashSet<Assignment>();
		started = new HashSet<Assignment>();
		finished = new HashSet<Assignment>();
		blocked.addAll(assignments);		
		int totalDuration = 0;
		while(!isFinished()){
			startEligibleAssignments();
			CircularRequirementException.verifyIsNotStuck(blocked, started);
			endEligibleAssignments();
			decrementCurrentDuration(started);
			totalDuration++;
		}
		return totalDuration;	
	}
	
	private static boolean isFinished(){
		if (started.isEmpty() && blocked.isEmpty()){
			return true; //all tasks are in finished
		}
		else{
			return false;
		}	
	}
	
	private static void decrementCurrentDuration(HashSet<Assignment> assignments){
		for(Assignment assignment : assignments){
			int current = assignment.getCurrentDuration();
			if(current > 0){
				assignment.setCurrentDuration(assignment.getCurrentDuration() - 1);
			}
		}
	}
	
	private static void startEligibleAssignments(){
		Iterator<Assignment> blockedIterator = blocked.iterator();
		while(blockedIterator.hasNext()){
			Assignment assignment = blockedIterator.next();
			if(canStart(assignment)){
				blockedIterator.remove();
				started.add(assignment);
				assignment.setCurrentDuration(assignment.getEstimatedDuration());
			}
		}
	}
	
	private static void endEligibleAssignments(){
		Iterator<Assignment> startedIterator = started.iterator();
		while(startedIterator.hasNext()){
			Assignment assignment = startedIterator.next();
			if(canFinish(assignment)){
				startedIterator.remove();
				finished.add(assignment);
			}
		}
	}
	
	private static boolean canStart(Assignment assignment){
		if(!canDependenciesEnd(assignment, Assignment.DependencyType.END_BEGIN)){
			return false;
		}
		else if(!canDependenciesBegin(assignment, Assignment.DependencyType.BEGIN_BEGIN)){
			return false;
		}
		return true;
	}
	
	private static boolean canFinish(Assignment assignment){
		if(assignment.getCurrentDuration() > 0){
			return false;
		}
		else if(!canDependenciesBegin(assignment, Assignment.DependencyType.BEGIN_END)){
			return false;
		}
		else if(!canDependenciesEnd(assignment, Assignment.DependencyType.END_END)){
			return false;
		}
		return true;
	}
	
	private static boolean canDependenciesBegin(Assignment assignment, Assignment.DependencyType type){
		for(Assignment dependency : assignment.getDependencySetOfType(type)){
			if(blocked.contains(dependency)){ //&& !canStart(dependency)
				return false;
			}
		}
		return true;
	}
	
	private static boolean canDependenciesEnd(Assignment assignment, Assignment.DependencyType type){
		for(Assignment dependency : assignment.getDependencySetOfType(type)){
			if(!finished.contains(dependency)){ // && !canFinish(dependency)
				return false;
			}
		}
		return true;
	}
}
