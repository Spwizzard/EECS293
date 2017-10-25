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
		return (started.isEmpty() && blocked.isEmpty());
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
		handleEligibleAssignments(blocked, started, true);
	}
	
	private static void endEligibleAssignments(){
		handleEligibleAssignments(started, finished, false);
	}
	
	private static void handleEligibleAssignments(	HashSet<Assignment> initialSet,
													HashSet<Assignment> moveToSet,
													Boolean start){
		Iterator<Assignment> initialIterator = initialSet.iterator();
		while(initialIterator.hasNext()){
			Assignment assignment = initialIterator.next();
			if(start && canStart(assignment)){
				initialIterator.remove();
				moveToSet.add(assignment);
			}
			else if(canFinish(assignment)){
				initialIterator.remove();
				moveToSet.add(assignment);
			}
		}
	}
	
	private static boolean canStart(Assignment assignment){
		return isAssignmentEligible(assignment, 
				Assignment.DependencyType.BEGIN_BEGIN, 
				Assignment.DependencyType.END_BEGIN);
	}
	
	private static boolean canFinish(Assignment assignment){
		if(assignment.getCurrentDuration() > 0){
			return false;
		}
		return isAssignmentEligible(assignment, 
									Assignment.DependencyType.BEGIN_END, 
									Assignment.DependencyType.END_END);
	}
	
	private static boolean isAssignmentEligible(Assignment assignment, 
												Assignment.DependencyType beginType, 
												Assignment.DependencyType endType){
		return (canDependenciesResolve(assignment, beginType, true) &&
				(canDependenciesResolve(assignment, endType, false)));
	}
	
	private static boolean canDependenciesResolve(	Assignment assignment, 
													Assignment.DependencyType type,
													Boolean beginType){
		for(Assignment dependency : assignment.getDependencySetOfType(type)){
			if(beginType){
				if(blocked.contains(dependency)){ 
					return false;//&& !canStart(dependency)
				}
				continue;
			}
			else if(!finished.contains(dependency)){ // && !canFinish(dependency)
				return false;
			}
		}
		return true;
	}
}
