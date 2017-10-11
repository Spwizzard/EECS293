package lexer;

public class CompoundFactor implements Factor{

	private final DisjunctiveExpression leftExpression;
	private final DisjunctiveExpression rightExpression;
	
	public static class Builder{
		public static final CompoundFactor build(LocationalToken token, DisjunctiveLexer lexer) throws ParserException{
			
			//first check if the first token is an OPEN
			ParserException.verify(Token.Type.OPEN, token);
			
			//get next token, DisjunctiveExpression verifies it is correct type
			DisjunctiveExpression leftExp = DisjunctiveExpression.Builder.build(LexerHelper.nextVerifiedValid(lexer), lexer);

			//Check if the next token is an AND
			ParserException.verify(Token.Type.AND, LexerHelper.nextVerifiedValid(lexer));
			
			//get next token, DisjunctiveExpression verifies it
			DisjunctiveExpression rightExp = DisjunctiveExpression.Builder.build(LexerHelper.nextVerifiedValid(lexer), lexer);
			
			//Check if the next token is a CLOSE
			ParserException.verify(Token.Type.CLOSE, LexerHelper.nextVerifiedValid(lexer));
			
			return new CompoundFactor(leftExp, rightExp);
			
		}
	}
	
	private CompoundFactor(DisjunctiveExpression leftExpression, DisjunctiveExpression rightExpression){
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public String toString() {
		return "(" + leftExpression + " and " + rightExpression + ")";
	}

	@Override
	public ConjunctiveRepresentation conjunctiveRepresentation() {
		String negation = "(" + leftExpression.negate().conjunctiveRepresentation() + " or " + rightExpression.negate().conjunctiveRepresentation() + ")";
		return new ConjunctiveRepresentation(negation, true);
	}
}
