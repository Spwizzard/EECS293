package lexer;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.ParserException.ErrorCode;

public class Lexer {
	
	private static Pattern tokenPatterns = Pattern.compile(	"(" + Token.Type.NOT.getPattern() + ")|(" +
																Token.Type.AND.getPattern() + ")|(" +
																Token.Type.OR.getPattern() + ")|(" +
																Token.Type.OPEN.getPattern() + ")|(" +
																Token.Type.CLOSE.getPattern() + ")|(" +
																Token.Type.ID.getPattern() + ")|(" +
																Token.Type.NUMBER.getPattern() + ")|(" +
																Token.Type.BINARYOP.getPattern() + ")|(" +
																Token.Type.WHITESPACE.getPattern() + ")");
	private final Matcher matcher;
	
	public Lexer(String input){
		matcher = tokenPatterns.matcher(input);
	}
	
	public Boolean hasNext(){
		return matcher.find();
	}
	
	public LocationalToken next() throws ParserException{
		if(!hasNext()){
			//if there is no next token to be found in the input, throw an error
			throw new ParserException(ErrorCode.TOKEN_EXPECTED);
		}
		//there is a next token, get it with group()
		String tokenString = matcher.group();
		Token.Type tokenType = matchTokenToType(tokenString);
		Token token = Token.of(tokenType, tokenString);
		return new LocationalToken(token, matcher.start());
	}
	
	public Optional<LocationalToken> nextValid(	Set<Token.Type> validTypes, 
												Set<Token.Type> invalidTypes) 
														throws ParserException{
		LocationalToken nextToken;
		try{
			 nextToken = next();
		}
		catch (ParserException pe){
			//this will be a TOKEN_EXPECTED error, which is what we want to handle
			return Optional.empty();
		}
		
		if(invalidTypes.contains(nextToken.getTokenType())){
			//this token Type is invalid! Throw an error
			throw new ParserException(nextToken, ErrorCode.INVALID_TOKEN);
		}
		else if(validTypes.contains(nextToken.getTokenType())){
			//the token Type is valid, return it
			return Optional.of(nextToken);
		}
		else{
			//the token is ignored, move on to the next one
			return nextValid(validTypes, invalidTypes);
		}
	}
	
	public Token.Type matchTokenToType(String tokenString){
		Token.Type[] tokenValues = Token.Type.values();
		for (int i = 0; i < tokenValues.length; i++){
			//loop through each group to find the correct token type
			//the groups are offset from the enum types by 1
			String match = matcher.group(i + 1);
			if(match != null && match.equals(tokenString)){
				//we found it!
				return tokenValues[i];
			}
		}
		//we didn't find it somehow
		System.out.println("didn't match token to type!");
		return null;
	}
}
