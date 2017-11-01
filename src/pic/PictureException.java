package pic;

/**
 * General exception class for the Picture module.
 *
 * TODO: create specialized subclasses for types of error
 * (the only argument against this is that for this specific assignemnt,
 * there is no differentiated response for different types of errors. That is,
 * no matter what is wrong with the input, we still have to print 'error' to stdout.
 * That makes the benefits of subclassed errors slightly less pronounced. Hence,
 * why this is a TODO and not a TO-DONE.
 *
 * Created by Brian on 11/10/2016.
 */
class PictureException extends Exception {

    //////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////

    PictureException(String message, Throwable e){
        super(message, e);
    }
    PictureException(String message){
        super(message);
    }
}
