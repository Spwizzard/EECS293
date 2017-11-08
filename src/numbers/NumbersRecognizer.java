package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static numbers.Constants.*;

//This class is given the list of digits and attempts to find the number from it

class NumbersRecognizer {

	static String findNumber(List<Digit> digits) throws NumbersException{
		assert digits.size() == NUM_DIGITS : "Incorrect number of digits!";
		
		//First, check if the input already represents a valid 9-digit number
		if(isValidNumber(digits)){
			return constructValidNumber(digits);
		}
		
		//One or more digits were garbled
		//If more than one, we need to throw an exception
		validateNumberOfGarbledDigits(digits);
		
		//There must be a single garbled digit
		return constructGarbledNumber(digits);
	}
	
	private static String constructValidNumber(List<Digit> digits){
		StringBuilder str = new StringBuilder();
		for(Digit digit : digits){
			str.append(digit.getDigit().get().toString());
		}
		return str.toString();
	}
	
	private static String constructGarbledNumber(List<Digit> digits){
		StringBuilder str = new StringBuilder();
		for(Digit digit : digits){
			Optional<Integer> optDigit = digit.getDigit();
			if(optDigit.isPresent()){
				str.append(optDigit.get().toString());
			}
			else{
				//This is the only garbled digit! If there is only 1 potential we can choose it
				List<Integer> potentials = digit.getPotentialDigits();
				assert potentials.size() > 0 : "Garbled digit doesn't have any potentials!";
				if(potentials.size() == 1){
					str.append(potentials.get(0));
				}
				else{
					//There was more than 1 potential, we say the solution is ambiguous
					return "ambiguous";
				}
			}
		}
		return str.toString();
	}
	
	private static boolean isValidNumber(List<Digit> digits){
		//digits represents a valid number when each digit's "Digit" optional is non-empty
		for(Digit digit : digits){
			if(!digit.getDigit().isPresent()){
				return false;
			}
		}
		return true;
	}
	
	private static void validateNumberOfGarbledDigits(List<Digit> digits) throws NumbersException{
		//A digit is garbled if it has an empty optional 
		//If we have more than 1 of these, our assumptions are violated
		List<Digit> garbled = new ArrayList<Digit>();
		for(Digit digit : digits){
			if(!digit.getDigit().isPresent()){
				garbled.add(digit);
			}
		}
		if(garbled.size() > 1){
			throw new NumbersException("More than 1 garbled digit");
		}
		
	}
}
