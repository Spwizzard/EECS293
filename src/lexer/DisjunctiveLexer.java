package lexer;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public final class DisjunctiveLexer {
	
	public static final Set<Token.Type> valid = EnumSet.of(	Token.Type.AND, 
															Token.Type.ID,
															Token.Type.NOT,
															Token.Type.OPEN,
															Token.Type.CLOSE);
	public static final Set<Token.Type> invalid = EnumSet.of(	Token.Type.OR,
																Token.Type.NUMBER,
																Token.Type.BINARYOP);
	private Lexer lexer;
	
	DisjunctiveLexer(String input){
		lexer = new Lexer(input);
	}
	
	public Optional<LocationalToken> nextValid() throws ParserException{
		return lexer.nextValid(valid, invalid);
	}
}
