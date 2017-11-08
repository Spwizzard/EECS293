package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Main class, handles IO operation and starts the recognition process

public class NumbersMain {
	
	public static void main(String[] args) {
		
		String result = performNumberAnalysis();
		System.out.print(result);
	}

	
	private static String performNumberAnalysis(){
		try{
			List<String> input = readInputLines();
			NumbersInput.validateInput(input);
			List<Digit> digits = NumbersInput.createDigitsFromInput(input);
			return NumbersRecognizer.findNumber(digits);
		}
		catch(NumbersException e){
			return "failure";
		}
		
	}
	
	private static List<String> readInputLines() throws NumbersException{
		Scanner inputScanner = new Scanner(System.in);
		List<String> input = new ArrayList<String>();
		//We need to read the set amount of input lines
		try{
			for(int i = 0; i < Constants.NUM_INPUT_LINES; i++){
				input.add(inputScanner.nextLine());
			}
		}
		catch(NoSuchElementException e){
			throw new NumbersException("Missing input line!", e);
		}
		finally{
			inputScanner.close();
		}
		return input;
	}
	
}
