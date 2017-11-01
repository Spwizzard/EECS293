package pic;

import java.util.Arrays;
import java.util.HashSet;

/**
 * AbstractPicture subtype representing the input pictures in a pictures problem.
 *
 * These pictures have the additional restriction that they can only contain one type
 * of capital letter.
 *
 * Created by Brian on 11/10/2016.
 */
class SubPicture extends AbstractPicture {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private final Character overallCharacter;


    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    SubPicture(final CharacterImage characterImage){
        super(characterImage);
        this.overallCharacter = characterImage.findCharacter();
    }


    //////////////////////////////////
    // ACCESSORS
    //////////////////////////////////

    Character getOverallCharacter() {
        return overallCharacter;
    }


    //////////////////////////////////
    // VALIDATION
    //////////////////////////////////

    private void validateNonnullCharacter() throws PictureException {
        if(overallCharacter == null){
            throw new PictureException("Uninitialized SubPicture");
        }
    }
    
    private void validateOnlyLetter() throws PictureException {
        HashSet<Character> validCharacters = new HashSet<Character>(Arrays.asList(overallCharacter));
    	if(!containsExactly(validCharacters)){
            throw new PictureException("Multiple Letters!");
        }
    }


    //////////////////////////////////
    // FACTORY
    //////////////////////////////////

    void validate() throws PictureException{
        validateNonnullCharacter();
        validateOnlyLetter();
    }

	static class PictureFactory extends AbstractPicture.PictureFactory{
        SubPicture make(final CharacterImage image){
            return new SubPicture(image);
        }
    }

}
