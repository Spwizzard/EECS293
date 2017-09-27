package lexer;

public final class DisjunctiveExpression {
	
	private final Factor factor;
	private final boolean positive;
	
	public static class Builder{
		public static final DisjunctiveExpression build(LocationalToken token, 
														DisjunctiveLexer lexer) throws ParserException{
			//The first type can be an optional NOT token
			boolean pos;
			if(token.getTokenType() == Token.Type.NOT){
				pos = false;
				//move on to the next token
				token = LexerHelper.nextVerifiedValid(lexer);
			}
			else{
				pos = true;
			}
			
			//this next token should either be an ID or an OPEN
			if(token.getTokenType() == Token.Type.ID){
				Factor factor = Identifier.Builder.build(token);
				return new DisjunctiveExpression(factor, pos);
			}
			else if(token.getTokenType() == Token.Type.OPEN){
				Factor factor = CompoundFactor.Builder.build(token, lexer);
				return new DisjunctiveExpression(factor, pos);
			}
			else {
				//the next token wasn't an ID or an OPEN
				throw new ParserException(token, ParserException.ErrorCode.ID_EXPECTED);
			}
			
			
		}
	}
	
	private DisjunctiveExpression(Factor factor, boolean positive){
		this.factor = factor;
		this.positive = positive;
	}
	
	public final DisjunctiveExpression negate(){
		return new DisjunctiveExpression(factor, !positive);
		
	}
	
	@Override
	public String toString() {
		if(positive){
			return factor.toString();
		}
		return "not " + factor.toString();
	}
	
}
