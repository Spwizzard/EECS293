package numbers;

import java.util.List;
import static numbers.Constants.*;

//Handles the input after NumbersMain has gotten it. 
//This includes validation and turning the input into usable data.

class NumbersInput {
	
	static List<Digit> createDigitsFromInput(List<String> input) throws NumbersException{
		
		assert input.size() == NUM_INPUT_LINES : "Input has incorrect number of lines";
		assert input.get(0).length() == INPUT_LINE_LENGTH : "Input lines have incorrect length";
		
		List<Digit> digits = Digit.createDigitList(input);
		return digits;
	}
	
	
	//Handles doing basic validation of the input.
	//Once done, the input has "passed the barricade"
	static void validateInput(List<String> input) throws NumbersException{
		validateInputLength(input);
		validateInputLines(input);
	}
	
	private static void validateInputLines(List<String> input) throws NumbersException{
		for(String line : input){
			validateInputLineLength(line);
			validateInputLineCharacters(line);
		}
	}

	private static void validateInputLength(List<String> input) throws NumbersException{
		if(input.size() != NUM_INPUT_LINES){
			throw new NumbersException("Incorrect number of input lines");
		}
	}
	
	private static void validateInputLineLength(String line) throws NumbersException{
		if(line.length() != INPUT_LINE_LENGTH){
			throw new NumbersException("Incorrect line length");
		}
	}
	
	private static void validateInputLineCharacters(String line) throws NumbersException{
		String regexPattern = "[ _|]+";
		if(!line.matches(regexPattern)){
			throw new NumbersException("Invalid character in input");
		}
	}
	
	
	
	
	
	
}
