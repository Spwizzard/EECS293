package lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Token {

	public enum Type {
		NOT 		("not", false),
		AND 		("and", false),
		OR			("or", false),
		OPEN 		("(", false),
		CLOSE 		(")", false),
		ID 			("[a-z]+", true), //a string of lower case letters
		NUMBER 		("([-])?\\d+", true), //a sequence of digits, with a possible - sign
		BINARYOP 	("[+-*/]", true), //one of +,-,*, or /
		WHITESPACE 	("\\s+", false); //a sequence of whitespace
		
		private final String pattern;
		private final Boolean hasData;
		
		Type(String pattern, Boolean hasData){
			this.pattern = pattern;
			this.hasData = hasData;
		}

		public String getPattern() {
			return pattern;
		}

		public Boolean getHasData() {
			return hasData;
		}
	}
	
	private static class Builder{
		
		private final Type type;
		private final Optional<String> data;
		
		Builder(Type type, Optional<String> data){
			this.type = type;
			this.data = data;
		}
		
		private Token build(){
			return new Token(type, data);
		}
		
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return super.equals(obj);
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}
	}
	
	
	private final Type type;
	private final Optional<String> data;
	private static Map<Builder, Token> tokenMap = new HashMap<Builder, Token>();
	
	public Type getType() {
		return type;
	}
	public Optional<String> getData() {
		return data;
	}

	private Token(Type type, Optional<String> data){
		this.type = type;
		this.data = data;
	}
	
	public static Token of(Type type, String data){
		//we need to create a builder with this type and data to compare to
		//check if this type takes data or not
		Builder builder;
		if(type.hasData){
			 builder = new Builder(type, Optional.ofNullable(data));
		}
		else{
			builder = new Builder(type, Optional.empty());
		}
		
		//now check if that builder already exists in the map		
		if(tokenMap.containsKey(builder)){
			//the token already exists, ignore the new one we made
		}
		else{
			//the token doesn't already exist, add it to the map
			tokenMap.put(builder, builder.build());
		}
		
		//either way we want to return the token that results from the builder
		return tokenMap.get(builder);
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	
}
