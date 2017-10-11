package lexer;

import java.util.Optional;

public final class ParserException extends Exception {

	private static final long serialVersionUID = 293L;
	
	public enum ErrorCode{
		TOKEN_EXPECTED,
		INVALID_TOKEN,
		TRAILING_INPUT,
		AND_EXPECTED,
		OPEN_EXPECTED,
		CLOSE_EXPECTED,
		ID_EXPECTED
	}
	
	private final ErrorCode errorCode;
	private final int location;
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	public int getLocation() {
		return location;
	}
	
	ParserException(LocationalToken token, ErrorCode errorCode){
		this.location = token.getLocation();
		this.errorCode = errorCode;
	}
	
	ParserException(ErrorCode errorCode){
		this.location = -1;
		this.errorCode = errorCode;
	}
	
	//throw a TOKEN_EXPECTED exception unless the token is present
	public static void verify(Optional<LocationalToken> token) throws ParserException{
		if(!token.isPresent()){
			//token isn't present
			throw new ParserException(ErrorCode.TOKEN_EXPECTED);
		}
	}
	
	//throw a <type>_EXPECTED exception if the expectedType doesn't match the token type
	public static void verify(Token.Type expectedType, LocationalToken token) throws ParserException{
		if(expectedType != token.getTokenType()){
			Optional<ErrorCode> errorCode = expectedType.getErrorCode();
			if(errorCode.isPresent()){
				throw new ParserException(token, errorCode.get());
			}
			else{
				throw new ParserException(token, ErrorCode.TOKEN_EXPECTED);
			}
			
		}
	}
	
	//throw a TRAILING_INPUT exception if the token is present
	public static void verifyEnd(Optional<LocationalToken> token) throws ParserException{
		if(token.isPresent()){
			//token is present
			throw new ParserException(token.get(), ErrorCode.TRAILING_INPUT);
		}
	}
	
	@Override
	public String toString() {
		return "ParserException [errorCode=" + errorCode + ", location=" + location + "]";
	}
}
