package pic;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Intermediate data class. This class is a halfway point between the raw List<String>
 * data that comes from the input file and the fully parsed CharacterImage.
 *
 * A Chunk is essentially a group of input lines bookended by blank lines in the input
 * file.
 *
 * The static factory method will only produce a Chunk object if it can create one
 * that is well formed. Otherwise, it will throw a PictureException.
 *
 * Essentially, this class is just here to "chunk" up the input.
 */
class Chunk {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private final List<String> lines;


    //////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////

    // use of method to get Chunk object
    private Chunk(final List<String> lines) {
        this.lines = lines;
    }

    private static Chunk of(final List<String> lines) throws PictureException {
        Chunk chunk = new Chunk(lines);
        chunk.validateNoNullStrings();
        return chunk;
    }

    static List<Chunk> byEmptyLine(final List<String> inputLines) throws PictureException{

        assert inputLines.size() >= 3 : "Will have at least three lines";

        List<Integer> emptyLineIndices = chunkEndIndices(inputLines);
        List<Chunk> chunkList = new ArrayList<>();

        int chunkStartIndex = 0;
        for(Integer chunkEndIndex : emptyLineIndices){
            List<String> chunkLines = inputLines.subList(chunkStartIndex, chunkEndIndex);
            Chunk currentChunk = Chunk.of(chunkLines);
            chunkList.add(currentChunk);
            chunkStartIndex = chunkEndIndex + 1;
        }

        return chunkList;
    }


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    Character[][] toCharacterArray(final int height, final int width) throws PictureException {

        Character[][] characters = new Character[height][width];

        List<String> linesWithoutWhitespace = noWhitespace(width);

        validateList(linesWithoutWhitespace, height, width);

        enterLines(characters, linesWithoutWhitespace);

        return characters;
    }


    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    private static void enterLines(final Character[][] characters, final List<String> lines){
        for (ListIterator<String> it = lines.listIterator(); it.hasNext(); ) {
            enterLine(characters, it.next(), it.previousIndex());
        }
    }

    private static void enterLine(final Character[][] characters,
                                  final String line,
                                  final int lineIdx){
        char[] lineChars = line.toCharArray();
        for (int charIdx = 0; charIdx < lineChars.length; charIdx++){
            enterCharacter(characters, lineChars[charIdx], lineIdx, charIdx);
        }
    }

    private static void enterCharacter(final Character[][] characters,
                                       final Character character,
                                       final int lineIdx,
                                       final int charIdx) {

        assert characters != null : "Previous null checks";
        assert character != null : "Instantiated above";

        if (character.equals('.')) {
            characters[lineIdx][charIdx] = null;
        } else {
            characters[lineIdx][charIdx] = character;
        }
    }

    private List<String> noWhitespace(final int width) throws PictureException{

        List<String> withoutWhitespace = new ArrayList<>();

        for (String str : lines){
        	/*We want to enforce that there is ONLY 1 space between each character
        	For a line of length n, there should be floor(n / 2) spaces
        	Ex. line has width 7, so length is 13, so 6 spaces
        	We will replace exactly width - 1 spaces, then make sure they are all gone
        	If not, there was the incorrect number of spaces*/
        	validateLineLength(str, width *2 - 1);
        	String newString = str;
        	for(int i = 0; i < width - 1 ; i++){
        		newString = newString.replaceFirst(" ", "");
        	}
        	validateLegalCharacters(newString);
        	withoutWhitespace.add(newString);
        	
        }
        return withoutWhitespace;
    }

    private static List<Integer> chunkEndIndices(final List<String> inputLines) {
        final List<Integer> indices = new ArrayList<>();
        for(int lineIdx = 0; lineIdx <= inputLines.size(); lineIdx++){
            if(isChunkEnd(inputLines, lineIdx)){
                indices.add(lineIdx);
            }
        }
        return indices;
    }

    private static boolean isChunkEnd(final List<String> inputLines, final int lineIdx) {
        return lineIdx == inputLines.size() || inputLines.get(lineIdx).equals("");
    }

    //////////////////////////////////
    // VALIDATION
    //////////////////////////////////

    private void validateNoNullStrings() throws PictureException {
        for (String str : lines) {
            if (str == null) {
                throw new PictureException("Can't have null lines of a picture");
            }
        }
    }

    private static void validateList(final List<String> lines,
                                     final int height,
                                     final int width) throws PictureException{

        assert lines != null : "Previous null checks ensure this list won't be null";

        validateListLength(lines, height);
        validateLineLengths(lines, width);
    }

    private static void validateListLength(final List<String> lines, final int height) throws PictureException{

        assert lines != null : "Previous null checks";

        if(lines.size() != height){
            throw new PictureException("Each picture must have the correct number of lines");
        }
    }

    private static void validateLineLengths(final List<String> lines, final int width) throws PictureException {
        for(String line : lines){
            validateLineLength(line, width);
        }
    }

    private static void validateLineLength(final String str, final int width) throws PictureException {
        if(str.length() != width){
            throw new PictureException("Picture width mismatched");
        }
    }
    
    private static void validateLegalCharacters(final String str) throws PictureException {
    	if(!str.matches("[.A-Z]+")){
    		throw new PictureException("Line contains invalid characters after removing spaces");
    	}
    }


    //////////////////////////////////
    // TEST HOOKS
    //////////////////////////////////

    static class StaticTestHook{
        static Chunk of(final List<String> lines) throws PictureException {
            return Chunk.of(lines);
        }
    }
}

