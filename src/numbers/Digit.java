package numbers;

import static numbers.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//A Digit represents the 3x3 character array of a digit gotten from the input.
//It also stores the digit that it matches, if any
//As well as any potential other digits it could match if it is the garbled digit

class Digit {
	
	@SuppressWarnings("unused")
	private char[][] characters; //Technically never used but it feels wrong to not store it
	private Optional<Integer> digit;
	private List<Integer> potentialDigits;
	
	public final Optional<Integer> getDigit() {
		return digit;
	}

	public final List<Integer> getPotentialDigits() {
		return potentialDigits;
	}
	
	private Digit(char[][] characters, Optional<Integer> digit, List<Integer> potentialDigits){
		this.characters = characters;
		this.digit = digit;
		this.potentialDigits = potentialDigits;
	}
	
	private static Digit from(List<String> lines) throws NumbersException{
		char[][] characters = new char[DIGIT_WIDTH][DIGIT_HEIGHT];
		for(int i = 0; i < DIGIT_HEIGHT; i++){
			String line = lines.get(i);
			for(int j = 0; j < DIGIT_WIDTH; j++){
				characters[i][j] = line.charAt(j);
			}
		}
		
		validateAllowableDigit(characters);
		Optional<Integer> matchedDigit = matchToKnownDigits(characters);
		List<Integer> potentialDigits = potentialOtherDigitMatches(characters, matchedDigit);
		return new Digit(characters, matchedDigit, potentialDigits);
		
	}
	
	static List<Digit> createDigitList(List<String> input) throws NumbersException{
		
		assert input.size() == DIGIT_HEIGHT : "Digits are incorrect height";
		assert input.get(0).length() == DIGIT_WIDTH * NUM_DIGITS : "Incorrect Number of Digits or Incorrect Width";
		
		List<Digit> digits = new ArrayList<Digit>();
		for(int i = 0; i < NUM_DIGITS; i++){
			List<String> digitStrings = new ArrayList<String>();
			for(int j = 0; j < DIGIT_HEIGHT; j++){
				digitStrings.add(input.get(j).substring(DIGIT_WIDTH * i, DIGIT_WIDTH * i + DIGIT_WIDTH));
			}
			digits.add(Digit.from(digitStrings));
		}
		
		assert digits != null : "Digit list is somehow null";
		
		return digits;
	}
	
	private static Optional<Integer> matchToKnownDigits(char[][] inputDigit) throws NumbersException{
		assert inputDigit != null : "Input Digit is somehow null";
		assert KNOWN_DIGITS != null : "KNOWN_DIGITS is somehow null";
		
		List<Integer> possibleMatches = new ArrayList<Integer>(); //This should only really ever be max length 1
		for(int i = 0; i < KNOWN_DIGITS.length; i++){
			char[][] knownDigit = KNOWN_DIGITS[i];
			if(inputDigitMatchesKnownDigit(inputDigit, knownDigit)){
				possibleMatches.add(new Integer(i));
			}
		}
		
		if(possibleMatches.size() == 0){
			return Optional.empty();
		}
		else if(possibleMatches.size() != 1){ //More than 1 (or negative????)
			throw new NumbersException("Input digit matches more than 1 known digit");
		}
		else{
			return Optional.of(new Integer(possibleMatches.get(0)));
		}
	}
	
	private static List<Integer> potentialOtherDigitMatches(char[][] inputDigit, Optional<Integer> match){
		assert inputDigit != null : "Input Digit is somehow null";
		assert KNOWN_DIGITS != null : "KNOWN_DIGITS is somehow null";
		
		List<Integer> potentialDigits = new ArrayList<Integer>();
		for(int i = 0; i < KNOWN_DIGITS.length; i++){
			char[][] knownDigit = KNOWN_DIGITS[i];
			if(isSubsetOfKnownDigit(inputDigit, knownDigit)){
				potentialDigits.add(new Integer(i));
			}
		}
		if(match.isPresent()){
			potentialDigits.remove(match.get());
		}
		return potentialDigits;
	}
	
	private static boolean inputDigitMatchesKnownDigit(char[][] inputDigit, char[][] knownDigit){
		assert knownDigit != null : "Known digit is somehow null";
		
		for(int i = 0; i < DIGIT_HEIGHT; i++){
			for(int j = 0; j < DIGIT_WIDTH; j++){
				if(inputDigit[i][j] != knownDigit[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean isSubsetOfKnownDigit(char[][] inputDigit, char[][] knownDigit){
		assert knownDigit != null : "Known digit is somehow null";
		for(int i = 0; i < DIGIT_HEIGHT; i++){
			for(int j = 0; j < DIGIT_WIDTH; j++){
				if(!isSubsetOfKnownCharacter(inputDigit[i][j], knownDigit[i][j])){
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean isSubsetOfKnownCharacter(char inputChar, char knownChar){
		return (inputChar == ' ' || inputChar == knownChar);
	}
	
	private static void validateAllowableDigit(char[][] characters) throws NumbersException{
		for(int i = 0; i < DIGIT_HEIGHT; i++){
			for(int j = 0; j < DIGIT_WIDTH; j++){
				if(!ALLOWED_SEGMENTS.get(DIGIT_HEIGHT * i + j).contains(characters[i][j])){
					throw new NumbersException("A segment is in the wrong spot");
				}
			}
		}
	}
	
	
}
