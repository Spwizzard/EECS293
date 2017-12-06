package wireframe;

/**
 * A LockedException is an exception meant to be thrown whenever a method attempted to 
 * illegally modify an object that was locked.
 * 
 * Recommended handling by the GUI that uses the Wireframe package.
 */
public class LockedException extends WireframeException {

	private static final long serialVersionUID = 1L;

	/**Constructs a LockedException out of the specified message
     * @param message the message to be passed with this exception
     */
    LockedException(String message){
        super(message);
    }
}
