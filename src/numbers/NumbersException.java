package numbers;

public class NumbersException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	
	NumbersException(String message, Throwable e){
        super(message, e);
    }
	
    NumbersException(String message){
        super(message);
    }
}
