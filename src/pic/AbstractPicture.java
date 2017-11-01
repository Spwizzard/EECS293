package pic;

import java.util.*;

/**
 * Class representing a rectangular image of characters.
 *
 * The only restrictions on the character matrix associated with an AbstractPicture
 * is that it be a well formed CharacterImage. See that class for what that means.
 *
 * AbstractPictures have static factory classes that can be used to form an AbstractPicture
 * (of the appropriate subtype) from a Chunk. Example usage:
 *
 * SubPicture.PictureFactory factory = new SubPicture.PictureFactory();
 * SubPicture subPicture = factory.of(chunk, 3, 4);
 *
 * A PictureFactory will only produce a valid AbstractPicture from a Chunk. If it can't build
 * a well formed AbstractPicture, it will throw a PictureException.
 *
 * AbstractPicture also provides some helpful methods to give information on the AbstractPicture's
 * relationship with other AbstractPictures. See: isSubPictureObstructed, containsExactly
 *
 * AbstractPictures are iterable over their characters by delegating the iteration to their
 * underlying CharacterImages.
 *
 * Created by Brian on 11/10/2016.
 */
abstract class AbstractPicture implements Iterable<Character> {

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private final CharacterImage characterImage;


    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    // package-private. Use factory to get AbstractPicture object. Can't be private, subclasses
    // need to call this as super constructor.
    AbstractPicture(final CharacterImage characterImage) {
        this.characterImage = characterImage;
    }


    //////////////////////////////////
    // ACCESSORS
    //////////////////////////////////

    int getHeight() {
        return characterImage.getHeight();
    }

    int getWidth() {
        return characterImage.getWidth();
    }

    // DELEGATE
    Character get(final int row, final int col) {
        return characterImage.get(row, col);
    }

    // DELEGATE
    public Iterator<Character> iterator() {
        return characterImage.iterator();
    }


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////

    /**
     * Method to determine if a sub picture is obstructed overall in a picture, given a list of already
     * removed characters.
     *
     * @param subPicture a subPicture
     * @param removedCharacters the List of characters already removed
     * @return true if subPicture is still obstructed in this composite picture
     */
    boolean isSubPictureObstructed(final SubPicture subPicture, List<Character> removedCharacters) {

        assert subPicture != null : "Enforced by factory";

        // If list of removed characters is null, make it a blank list
        removedCharacters = Common.nonNullList(removedCharacters);

        Iterator<Character> myChars = iterator();
        Iterator<Character> subPictureChars = subPicture.iterator();

        while(myChars.hasNext() && subPictureChars.hasNext()){
            if (characterObstructs(subPictureChars.next(), myChars.next(), removedCharacters)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff (c \in characters <-> c \in Picture )
     *
     * @param characters the character set to check for membership in the picture
     * @return true if condition above is met, false otherwise
     */
    boolean containsExactly(final Set<Character> characters) {

        Set<Character> checkOff = new HashSet<>(characters);

        for (Character c : this) {

            // If character not in the set shows up, report false
            if (isUnexpectedCharacter(c, characters))
                return false;

                // If it's approved, check it off on the set
            else {
                checkOff.remove(c);
            }
        }
        return checkOff.isEmpty();
    }


    //////////////////////////////////
    // HELPERS
    //////////////////////////////////

    /**
     * Determines if a character is not expected to be in the given set. If the character
     * is null, this is treated as not being a problem (i.e. a "null" is arbitrarily in
     * every set).
     *
     * @param character   the character being checked for membership
     * @param containingSet the set of characters being searched through
     * @return true if the character is unexpected, false otherwise
     */
    private static boolean isUnexpectedCharacter(final Character character, Set<Character> containingSet) {

        containingSet = Common.nonNullSet(containingSet);

        boolean isNull = (character == null);
        boolean isContained = (containingSet.contains(character));
        return !isNull && !isContained;
    }


    /**
     * Helper method to determine if a hiddenCharacterCandidate is actually hidden
     * by the surface character.
     *
     * This is a bit of complicated logic, sorry...
     *
     * @param hiddenCharacterCandidate the Character that might be hidden in this picture
     * @param surfaceCharacter the Character at the surface of this Picture at the same place as hiddenCharacterCandidate
     * @param removedCharacters the list of Characters already removed
     * @return true if hiddenCharacterCandidate is obstructed by the surfaceCharacter
     */
    private static boolean characterObstructs(final Character hiddenCharacterCandidate,
                                              final Character surfaceCharacter,
                                              List<Character> removedCharacters) {

        removedCharacters = Common.nonNullList(removedCharacters);

        // null characters can't be obstructed
        if(hiddenCharacterCandidate == null || surfaceCharacter == null){
            return false;
        }

        // Guard clause -- clearly not obstructed if it's on the surface...
        if(hiddenCharacterCandidate.equals(surfaceCharacter)){
            return false;
        }

        boolean surfaceCharacterAlreadyRemoved = removedCharacters.contains(surfaceCharacter);

        //noinspection UnnecessaryLocalVariable for clarity
        boolean hiddenCharacterCandidateIsActuallyHidden = !surfaceCharacterAlreadyRemoved;

        return hiddenCharacterCandidateIsActuallyHidden;
    }

    //////////////////////////////////
    // FACTORY
    //////////////////////////////////

    /**
     * Validate an AbstractPicture object. Does nothing if well formed, throws
     * PictureException if malformed.
     * @throws PictureException if malformed AbstractPicture
     */
    abstract void validate() throws PictureException;

    /**
     * Static factory class to produce a fully formed AbstractPicture of appropriate subtype.
     * @param <T> subclass of AbstractPicture that the factory will produce
     */
    abstract static class PictureFactory<T extends AbstractPicture> {

        T of(final Chunk chunk, final int height, final int width) throws PictureException {
            CharacterImage image = CharacterImage.of(chunk, height, width);
            T picture = make(image);
            picture.validate();
            return picture;
        }

        abstract T make(final CharacterImage image);

    }


    //////////////////////////////////
    // TEST HOOKS
    //////////////////////////////////

    static class StaticTestHook {
        static boolean isUnexpectedCharacter(Character c, Set<Character> characters) {
            return AbstractPicture.isUnexpectedCharacter(c, characters);
        }

        static boolean characterObstructs(Character targetCharacter,
                                          Character obstructingCharacter,
                                          List<Character> nonObstructingCharacters) {
            return AbstractPicture.characterObstructs(targetCharacter, obstructingCharacter, nonObstructingCharacters);
        }
    }
}
