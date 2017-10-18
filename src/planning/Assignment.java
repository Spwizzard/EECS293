package planning;

import java.util.EnumMap;
import java.util.HashSet;

public class Assignment {
	
	public enum DependencyType{
		BEGIN_BEGIN,
		END_BEGIN,
		BEGIN_END,
		END_END;
	}

	private final int estimatedDuration;
	private int currentDuration = 0;
	private EnumMap<DependencyType, HashSet<Assignment>> dependencyMap = new EnumMap<DependencyType, HashSet<Assignment>>(DependencyType.class);

	public Assignment(int estimatedDuration){
		this.estimatedDuration = estimatedDuration;
		dependencyMap.put(DependencyType.BEGIN_BEGIN, new HashSet<Assignment>());
		dependencyMap.put(DependencyType.END_BEGIN, new HashSet<Assignment>());
		dependencyMap.put(DependencyType.BEGIN_END, new HashSet<Assignment>());
		dependencyMap.put(DependencyType.END_END, new HashSet<Assignment>());
	}

	public int getEstimatedDuration(){
		return estimatedDuration;
	} 

	public int getCurrentDuration(){
		return currentDuration;
	}
	
	public void setCurrentDuration(int currentDuration){
		this.currentDuration = currentDuration;
	}

	public HashSet<Assignment> getDependencySetOfType(DependencyType type){
		return dependencyMap.get(type);
	}
	
	public void addDependency(DependencyType type, Assignment dependency){
		if(!dependencyMap.containsKey(type)){
			HashSet<Assignment> list = new HashSet<Assignment>();
			list.add(dependency);
			dependencyMap.put(type, list);
		}
		else{
			dependencyMap.get(type).add(dependency);
		}
	}
}	
	
