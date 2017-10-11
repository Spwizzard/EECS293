package lexer;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.ParserException.ErrorCode;

public class Lexer {
	
	private static Pattern tokenPatterns = Pattern.compile(makeTokenPattern());	
	private final Matcher matcher;
	
	public Lexer(String input){
		matcher = tokenPatterns.matcher(input);
	}
	
	public Boolean hasNext(){
		return matcher.find();
	}
	
	public LocationalToken next() throws ParserException{
		//get the next token with group()
		String tokenString;
		try{
			tokenString = matcher.group();
		}
		catch (IllegalStateException e){
			throw new ParserException(ParserException.ErrorCode.TOKEN_EXPECTED);
		}
		Token.Type tokenType = matchTokenToType(tokenString);
		Token token = Token.of(tokenType, tokenString);
		return new LocationalToken(token, matcher.start());
	}
	
	public Optional<LocationalToken> nextValid(	Set<Token.Type> validTypes, 
												Set<Token.Type> invalidTypes) 
														throws ParserException{
		LocationalToken nextToken;
		while(hasNext()){
			nextToken = next();
			
			if(invalidTypes.contains(nextToken.getTokenType())){
				//this token Type is invalid! Throw an error
				throw new ParserException(nextToken, ErrorCode.INVALID_TOKEN);
			}
			else if(validTypes.contains(nextToken.getTokenType())){
				//the token Type is valid, return it
				return Optional.of(nextToken);
			}
		}
		//we ran out of tokens
		return Optional.<LocationalToken>empty();
	}
	
	private Token.Type matchTokenToType(String tokenString) throws ParserException{
		Token.Type[] tokenValues = Token.Type.values();
		for (Token.Type tokenType : tokenValues){
			//loop through each group to find the correct token type
			String match = matcher.group(tokenType.name());
			if(match != null && match.equals(tokenString)){
				//we found it!
				return tokenType;
			}
		}
		//we didn't find it somehow
		System.out.println("didn't match token to type!");
		throw new ParserException(ParserException.ErrorCode.TOKEN_EXPECTED);
	}
	
	private static String makeTokenPattern(){
		Token.Type[] tokenTypes = Token.Type.values();
		StringBuilder patternBuilder = new StringBuilder();
		for (Token.Type tokenType : tokenTypes){
			patternBuilder.append("(?<" + tokenType.name() + ">" + tokenType.getPattern() + ")|");
		}
		//delete the last '|'
		patternBuilder.deleteCharAt(patternBuilder.length() - 1);
		return patternBuilder.toString();
	}
}
