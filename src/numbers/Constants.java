package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Global constants. Some only used by one file, but kept here for consistency

class Constants {

	static final int NUM_INPUT_LINES = 3;
	static final int NUM_DIGITS = 9;
	static final int DIGIT_HEIGHT = 3;
	static final int DIGIT_WIDTH = 3;
	static final int INPUT_LINE_LENGTH = 27;
	
	static final char[][][] KNOWN_DIGITS = makeKnownDigits();
	static final List<ArrayList<Character>> ALLOWED_SEGMENTS = makeAllowedSegmentsList();
	
	private static char[][][] makeKnownDigits(){
		char[][][] knownDigits = {{{' ','_',' '}, //0
								   {'|',' ','|'},
								   {'|','_','|'}},
								  {{' ',' ',' '}, //1
								   {' ',' ','|'},
								   {' ',' ','|'}},
								  {{' ','_',' '}, //2
								   {' ','_','|'},
								   {'|','_',' '}},
								  {{' ','_',' '}, //3
								   {' ','_','|'},
								   {' ','_','|'}},
								  {{' ',' ',' '}, //4
								   {'|','_','|'},
								   {' ',' ','|'}},
								  {{' ','_',' '}, //5
								   {'|','_',' '},
								   {' ','_','|'}},
								  {{' ','_',' '}, //6
								   {'|','_',' '},
								   {'|','_','|'}},
								  {{' ','_',' '}, //7
								   {' ',' ','|'},
								   {' ',' ','|'}},
								  {{' ','_',' '}, //8
								   {'|','_','|'},
								   {'|','_','|'}},
								  {{' ','_',' '}, //9
								   {'|','_','|'},
								   {' ','_','|'}}};
		return knownDigits;
	}
	
	private static List<ArrayList<Character>> makeAllowedSegmentsList(){
		ArrayList<ArrayList<Character>> allowed = new ArrayList<ArrayList<Character>>();
		char space = ' ';
		char underscore = '_';
		char bar = '|';
		allowed.add(new ArrayList<Character>(Arrays.asList(space)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, underscore)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, bar)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, underscore)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, bar)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, bar)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, underscore)));
		allowed.add(new ArrayList<Character>(Arrays.asList(space, bar)));
		return allowed;
	}
	
}
