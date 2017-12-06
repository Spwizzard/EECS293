package wireframe;

/**
 * A WireframeException is a general exception type that can be thrown by classes in the Wireframe package.
 * 
 */
public class WireframeException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	
	/**Constructs a WireframeException out of the specified message and throwable
	 * @param message the message to be passed with this exception
	 * @param e the throwable that this WireframeException encapsulates
	 */
	WireframeException(String message, Throwable e){
        super(message, e);
    }
	
    /**Constructs a WireframeException out of the specified message
     * @param message the message to be passed with this exception
     */
    WireframeException(String message){
        super(message);
    }
}
