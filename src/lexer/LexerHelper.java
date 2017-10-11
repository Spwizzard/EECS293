package lexer;

import java.util.Optional;

public class LexerHelper {

	//gets the next valid token in the lexer, then verifies it before returning
	public static LocationalToken nextVerifiedValid(DisjunctiveLexer lexer) throws ParserException{
		Optional<LocationalToken>nextToken = lexer.nextValid();
		ParserException.verify(nextToken);
		return nextToken.get();
	}
}
