package lexer;

public final class Identifier implements Factor{
	
	public static class Builder{
		
		public static final Identifier build(LocationalToken token) throws ParserException{
			ParserException.verify(Token.Type.ID, token);
			if(token.getTokenData().isPresent()){
				return new Identifier(token.getTokenData().get());
			}
			else{
				throw new ParserException(token, ParserException.ErrorCode.ID_EXPECTED);
			}
			
		}
		
	}
	
	private final String id;
	
	private Identifier(String id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return id;
	}
}
