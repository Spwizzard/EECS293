package planning;

import java.util.HashSet;
import java.util.Iterator;


public class EstimatedDuration {
	
	public class Test{
		
		public boolean isFinished(HashSet<Assignment> blocked, HashSet<Assignment> started){
			EstimatedDuration.blocked = blocked;
			EstimatedDuration.started = started;
			return EstimatedDuration.isFinished();
		}
		
		public void decrementCurrentDuration(HashSet<Assignment> assignments){
			EstimatedDuration.decrementCurrentDuration(assignments);
		}

		public void handleEligibleAssignments(	HashSet<Assignment> blocked,
												HashSet<Assignment> started,
												HashSet<Assignment> finished,
												HashSet<Assignment> initialSet,
												HashSet<Assignment> moveToSet,
												Boolean start){
			setupEstimatedDurationStateHelper(blocked, started, finished);
			EstimatedDuration.handleEligibleAssignments(initialSet, moveToSet, start);
		}
		
		public boolean canStart(HashSet<Assignment> blocked,
								HashSet<Assignment> started,
								HashSet<Assignment> finished,
								Assignment assignment){
			setupEstimatedDurationStateHelper(blocked, started, finished);
			return EstimatedDuration.canStart(assignment);
		}
		
		public boolean canFinish(	HashSet<Assignment> blocked,
									HashSet<Assignment> started,
									HashSet<Assignment> finished,
									Assignment assignment){
			setupEstimatedDurationStateHelper(blocked, started, finished);
			return EstimatedDuration.canFinish(assignment);
		}
		
		public boolean isAssignmentEligible(HashSet<Assignment> blocked,
											HashSet<Assignment> started,
											HashSet<Assignment> finished,
											Assignment assignment, 
											Assignment.DependencyType beginType, 
											Assignment.DependencyType endType){
			setupEstimatedDurationStateHelper(blocked, started, finished);
			return EstimatedDuration.isAssignmentEligible(assignment, beginType, endType);
		}
		
		public boolean canDependenciesResolve(	HashSet<Assignment> blocked,
												HashSet<Assignment> started,
												HashSet<Assignment> finished,
												Assignment assignment, 
												Assignment.DependencyType type,
												Boolean begin){
			setupEstimatedDurationStateHelper(blocked, started, finished);
			return EstimatedDuration.canDependenciesResolve(assignment, type, begin);
		}
		
		private void setupEstimatedDurationStateHelper(	HashSet<Assignment> blocked,
														HashSet<Assignment> started,
														HashSet<Assignment> finished){
			EstimatedDuration.blocked = blocked;
			EstimatedDuration.started = started;
			EstimatedDuration.finished = finished;
		}
	}
	
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
			handleEligibleAssignments(blocked, started, true);
			CircularRequirementException.verifyIsNotStuck(blocked, started);
			handleEligibleAssignments(started, finished, false);
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
	
	private static void handleEligibleAssignments(	HashSet<Assignment> initialSet,
													HashSet<Assignment> moveToSet,
													Boolean start){
		Iterator<Assignment> initialIterator = initialSet.iterator();
		while(initialIterator.hasNext()){
			Assignment assignment = initialIterator.next();
			if(start){
				if(canStart(assignment)){
					initialIterator.remove();
					moveToSet.add(assignment);
				}
			}
			else{
				if(canFinish(assignment)){
					initialIterator.remove();
					moveToSet.add(assignment);
				}
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
													Boolean begin){
		for(Assignment dependency : assignment.getDependencySetOfType(type)){
			if(begin){
				if(blocked.contains(dependency)){ //&& !canStart(dependency)
					return false;
				}
			}
			else{
				if(!finished.contains(dependency)){ //&& !canStart(dependency)
					return false;
				}
			}
		}
		return true;
	}
}
