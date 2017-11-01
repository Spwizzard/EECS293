package pic;

/**
 * AbstractPicture subtype representing final stacking of all the input pictures from a
 * pictures problem.
 *
 * Created by Brian on 11/11/2016.
 */
class CompositePicture extends AbstractPicture {

    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    CompositePicture(final CharacterImage characterImage) {
        super(characterImage);
    }


    //////////////////////////////////
    // FACTORY
    //////////////////////////////////

    void validate() throws PictureException{}

    static class PictureFactory extends AbstractPicture.PictureFactory{
        CompositePicture make(final CharacterImage image){
            return new CompositePicture(image);
        }
    }
    
}
