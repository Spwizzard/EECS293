package lexer;

import java.util.Optional;

public final class LocationalToken {

	private final Token token;
	private final int location;
	
	public Token getToken() {
		return token;
	}
	public int getLocation() {
		return location;
	}
	
	public Token.Type getTokenType() {
		return token.getType();
	}
	
	public Optional<String> getTokenData() {
		return token.getData();
	}
	
	public LocationalToken(Token token, int location){
		this.token = token;
		this.location = location;
	}
}
