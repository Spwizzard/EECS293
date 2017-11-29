package wireframe;

public class WireframeException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	
	WireframeException(String message, Throwable e){
        super(message, e);
    }
	
    WireframeException(String message){
        super(message);
    }
}
