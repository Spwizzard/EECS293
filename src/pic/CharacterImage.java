package pic;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Class representing a matrix of characters with a defined height and width.
 *
 * The static factory method will produce a CharacterImage from a Chunk only if
 * the Chunk satisfies these conditions:
 *    - the Chunk has the right number of rows
 *    - each row of the Chunk has the right width
 *    - each character in the Chunk is a capital letter, whitespace, or a period
 *
 * CharacterImages are iterable over their characters. Their iterators will return Characters
 * from top to bottom, left to right. Example:
 *
 * CharacterImage                          Iterator return order
 *      A B                 ----->              <A, B, C, D>
 *      C D
 *
 */
class CharacterImage implements Iterable<Character>{

    //////////////////////////////////
    // FIELDS
    //////////////////////////////////

    private final Character[][] characters;
    private final int height;
    private final int width;

    
    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    private CharacterImage(final Character[][] characters, int height, int width) {
        this.characters = characters;
        this.height = height;
        this.width = width;
    }

    static CharacterImage of(final Chunk chunk, final int height, final int width) throws PictureException{
        assert height > 0 : "Must be valid height";
        assert width > 0: "Must be valid width";
        assert chunk != null : "Chunk must exist";

        Character[][] characters = chunk.toCharacterArray(height, width);

        CharacterImage image = new CharacterImage(characters, height, width);

        image.validateCharacters();

        return image;
    }


    //////////////////////////////////
    // INTERFACE
    //////////////////////////////////
    
    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    Character get(final int row, final int col) {

        assert characters != null;

        return characters[row][col];
    }
    /*
    Character findCharacter(){
        Stream<Character> stream = stream().filter(c -> c != null && isValidCharacter(c));
        System.out.println(stream.toArray());
        return 'c';
        //return stream.findAny().orElse(null);
    }*/

    Character findCharacter(){
        return stream()
                .filter(c -> c != null && isValidCharacter(c))
                .findAny()
                .orElse(null);
    }
    // Iterable

    private Stream<Character> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public Iterator<Character> iterator(){
        return new CharacterImageIterator();
    }

    public class CharacterImageIterator implements Iterator<Character> {

        private int idx;

        private CharacterImageIterator() {
            this.idx = 0;
        }

        @Override
        public boolean hasNext() {
            return idx < getHeight() * getWidth();
        }

        @Override
        public Character next() {

            if(!hasNext()){
                throw new NoSuchElementException("No more Characters!");
            }

            int row = idx / getWidth();
            int col = idx % getWidth();

            idx++;

            return get(row, col);
        }
    }


    //////////////////////////////////
    // VALIDATION
    //////////////////////////////////

    private void validateCharacters() throws PictureException{

        assert characters != null : "Previous null checks ensure this list won't be null";
        assert characters.length > 0 : "See previous size checks";

        for(Character c : this){
            validateCharacter(c);
        }
    }

    private static void validateCharacter(final Character c) throws PictureException{
        if(!isValidCharacter(c)){
            throw new PictureException("Must have valid image character");
        }
    }

    private static boolean isValidCharacter(final Character c){

        if(c == null){
            return true;
        }

        boolean isValidLetter = isValidLetter(c);
        boolean isPeriod = (c.equals('.'));
        return isValidLetter || isPeriod;
    }

    private static boolean isValidLetter(final Character c) {
        boolean isLetter = Character.isLetter(c);
        boolean isUpperCase = Character.isUpperCase(c);
        return isLetter && isUpperCase;
    }

}
