package lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Token {

	public enum Type {
		NOT 		("not", false, false, Optional.<ParserException.ErrorCode>empty()),
		AND 		("and", false, true, Optional.of(ParserException.ErrorCode.AND_EXPECTED)),
		OR			("or", 	false, true, Optional.<ParserException.ErrorCode>empty()),
		OPEN 		("\\(", false, false, Optional.of(ParserException.ErrorCode.OPEN_EXPECTED)),
		CLOSE 		("\\)", false, false, Optional.of(ParserException.ErrorCode.CLOSE_EXPECTED)),
		ID 			("[a-z]+", true, false, Optional.of(ParserException.ErrorCode.ID_EXPECTED)), //a string of lower case letters
		NUMBER 		("-?\\d+", true, false, Optional.<ParserException.ErrorCode>empty()), //a sequence of digits, with a possible - sign
		BINARYOP 	("[\\+\\-\\*\\/]", true, false, Optional.<ParserException.ErrorCode>empty()), //one of +,-,*, or /
		WHITESPACE 	("\\s+", false, false, Optional.<ParserException.ErrorCode>empty()); //a sequence of whitespace

		private final String pattern;
		private final Boolean hasData;
		private final boolean isComplex;
		private final Optional<ParserException.ErrorCode> errorCode;
		
		Type(String pattern, Boolean hasData, Boolean isComplex, Optional<ParserException.ErrorCode> errorCode){
			this.pattern = pattern;
			this.hasData = hasData;
			this.isComplex = isComplex;
			this.errorCode = errorCode;
		}

		public String getPattern() {
			return pattern;
		}

		public Boolean getHasData() {
			return hasData;
		}
		
		public boolean isComplex() {
			return isComplex;
		}
		
		public Optional<ParserException.ErrorCode> getErrorCode() {
			return errorCode;
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
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Builder other = (Builder) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			if (type != other.type)
				return false;
			return true;
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
			builder = new Builder(type, Optional.<String>empty());
		}
		
		//now check if that builder already exists in the map		
		if(!tokenMap.containsKey(builder)){
			//the token doesn't already exist, add it to the map
			tokenMap.put(builder, builder.build());
		}
		//else case: the token already exists, ignore the new one we made
		
		//either way we want to return the token that results from the builder
		return tokenMap.get(builder);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Token [type=" + type + ", data=" + data + "]";
	}
	
}
