package pic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import static pic.Constants.*;

/**
 * Class representing a well formed pictures problem input.
 *
 * A well formed pictures problem input contains:
 *  - a well formed picture height
 *  - a well formed picture width
 *  - a list of well formed input pictures
 *  - a well formed composite picture
 *
 * The inputOf static factory method will only produce a PictureInput
 * from a list of Strings if it can deduce a well formed pictures problem
 * from those lines. If it can't, it will throw a PicturesException.
 *
 * The PictureInput class performs validations that are beyond the scope of individual
 * AbstractPictures. A set of perfectly valid AbstractPictures could still very much a
 * malformed PictureInput. For example, two input pictures could both use the Character
 * 'B'. On their own, they are well formed, but a pictures problem can't have two input
 * pictures with the same letter.
 *
 * Created by Brian on 11/10/2016.
 */
class PictureInput {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////
    
    private final List<SubPicture> inputPictures;
    private final CompositePicture compositePicture;
    private final int height;
    private final int width;


    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    // private. Use inputOf to get PictureInput object
    private PictureInput(final List<SubPicture> inputPictures,
                         final CompositePicture compositePicture,
                         final int height,
                         final int width) {
        this.inputPictures = inputPictures;
        this.compositePicture = compositePicture;
        this.height = height;
        this.width = width;
    }

    static PictureInput inputOf(final List<String> inputStrings) throws PictureException{

        validateLength(inputStrings);

        assert inputStrings.size() >= REQUIRED_NUMBER_OF_INPUT_LINES : "Has minimum number of entry lines";

        String heightString = inputStrings.get(0);
        String widthString = inputStrings.get(1);

        validateDimensionFormat(heightString, INVALID_HEIGHT_MSG);
        validateDimensionFormat(widthString, INVALID_WIDTH_MSG);

        int height = Integer.parseInt(heightString);
        int width = Integer.parseInt(widthString);

        assert height > 0 : "Valid height entry";
        assert width > 0: "Valid width entry";
        List<String> remainder = inputStrings.subList(2, inputStrings.size());

        List<Chunk> chunkList = Chunk.byEmptyLine(remainder);

        validateChunks(chunkList);

        List<SubPicture> inputPictures = extractInputPictures(chunkList, height, width);
        CompositePicture compositePicture = extractCompositePicture(chunkList, height, width);

        PictureInput input = new PictureInput(inputPictures, compositePicture, height, width);

        input.validate();

        return input;
    }

    //////////////////////////////////
    // ACCESSORS
    //////////////////////////////////

    List<SubPicture> getInputPictures() { return inputPictures; }
    CompositePicture getCompositePicture() { return compositePicture; }

    int numberOfInputPictures(){
        return inputPictures.size();
    }

    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    private static CompositePicture extractCompositePicture(final List<Chunk> chunkList,
                                                            final int height,
                                                            final int width) throws PictureException{

        assert 2 <= chunkList.size() : "Must have at least two pictures";

        CompositePicture.PictureFactory factory = new CompositePicture.PictureFactory();

        Chunk finalChunk = chunkList.get(chunkList.size() - 1);

        return (CompositePicture) factory.of(finalChunk, height, width);
    }

    private static List<SubPicture> extractInputPictures(final List<Chunk> chunkList,
                                                         final int height,
                                                         final int width) throws PictureException{

        assert 2 <= chunkList.size() : "Must have at least two pictures";

        List<SubPicture> inputPictures = new ArrayList<>(chunkList.size());
        SubPicture.PictureFactory factory = new SubPicture.PictureFactory();

        List<Chunk> inputChunks = chunkList.subList(0, chunkList.size() - 1);

        for(Chunk chunk : inputChunks){
            SubPicture subPicture = (SubPicture) factory.of(chunk, height, width);
            inputPictures.add(subPicture);
        }

        return inputPictures;
    }

    private Set<Character> letters() {
        HashSet<Character> letters = new HashSet<>();
        inputPictures.forEach(e -> letters.add(e.getOverallCharacter()));
        return letters;
    }

    
    //////////////////////////////////
    // VALIDATION
    //////////////////////////////////


    private void validate() throws PictureException{

        // Null checks
        assert inputPictures != null : "Enforced by factory";
        assert compositePicture != null : "Enforced by factory";

        validateInputPictures();
        validateCompositePicture();
    }

    private void validateCompositePicture() throws PictureException{

        validateCharacterPresence();
        validateCharacterPlacement();
    }

    private void validateCharacterPresence() throws PictureException{
        if(!compositePicture.containsExactly(letters())){
            throw new PictureException("Composite contains characters not found in input pictures");
        }
    }

    private void validateCharacterPlacement() throws PictureException {
        for(int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                validateCompositePixelComesFromInputPictures(row, col);
            }
        }
    }

    private void validateCompositePixelComesFromInputPictures(final int row, final int col) throws PictureException{

        Set<Character> currentCharacters = new HashSet<>();

        for(SubPicture subPicture : inputPictures) {
            currentCharacters.add(subPicture.get(row, col));
        }
        
        if(currentCharacters.size() > 1){
        	//If there is any potential for a pixel to not be null, it cant be null
        	currentCharacters.remove(null);
        }

        Character compositeCharacter = compositePicture.get(row, col);
        
        if(!currentCharacters.contains(compositeCharacter)){
            throw new PictureException("Out of place character");
        }
    }

    private void validateInputPictures() throws PictureException{

        assert inputPictures != null : "can't be null";

        Set<Character> letters = new HashSet<>();

        for(SubPicture subPicture : inputPictures){
            validateInputPicture(subPicture, letters);
        }
    }

    private void validateInputPicture(final SubPicture subPicture,
                                      final Set<Character> letters) throws PictureException{
        if(!letters.add(subPicture.getOverallCharacter())){
            throw new PictureException("Duplicate letters in input picture list");
        }

        if(subPicture.getHeight() != height){
            throw new PictureException("Input picture of incorrect height");
        }

        if(subPicture.getWidth() != width){
            throw new PictureException("Input picture of incorrect width");
        }
    }
    
    private static void validateChunks(final List<Chunk> chunkList) throws PictureException{

        if(chunkList.size() < REQUIRED_NUMBER_OF_CHUNKS){
            throw new PictureException("Must contain at least two pictures");
        }
    }
    
    private static void validateLength(final List<String> inputStrings) throws PictureException{
        if(inputStrings.size() < REQUIRED_NUMBER_OF_INPUT_LINES){
            throw new PictureException("Not enough input lines");
        }
    }

    private static void validateDimensionFormat(final String dimensionString,
                                                final String errorMessage) throws PictureException {

        Matcher dimensionMatcher = DIMENSION_PATTERN.matcher(dimensionString);
        if(!dimensionMatcher.matches()){
            throw new PictureException(errorMessage);
        }
    }


    //////////////////////////////////
    // TEST HOOKS
    //////////////////////////////////

    static class StaticTestHook{
        static void validateDimensionFormat(String dimensionString, String errorMessage) throws PictureException {
            PictureInput.validateDimensionFormat(dimensionString, errorMessage);
        }
    }
}
