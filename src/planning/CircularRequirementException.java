package planning;

import java.util.HashSet;

public class CircularRequirementException extends Exception{

	private static final long serialVersionUID = 293L;
	
	CircularRequirementException(){
		
	}
	
	public static void verifyIsNotStuck(HashSet<Assignment> blocked, HashSet<Assignment> started) throws CircularRequirementException{
		if (started.isEmpty() && !blocked.isEmpty()){
			throw new CircularRequirementException();
		}
	}
}
