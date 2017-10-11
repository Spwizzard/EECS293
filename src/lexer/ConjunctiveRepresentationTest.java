package lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConjunctiveRepresentationTest {

	@Test
	public void testConjunctiveRepresentationCorrect(){
		DisjunctiveLexer lex = new DisjunctiveLexer("( (a and (d and not c)) and not b )");
		try{
			CompoundFactor id = CompoundFactor.Builder.build(LexerHelper.nextVerifiedValid(lex), lex);
			assertEquals("((not a or (not d or c)) or b)", id.conjunctiveRepresentation().getRepresentation());
		}
		catch (ParserException pe){
			fail();
		}
	}
	
	@Test
	public void testConjunctiveRepresentationNegation(){
		DisjunctiveLexer lex = new DisjunctiveLexer("( (a and (d and not c)) and not b )");
		try{
			CompoundFactor id = CompoundFactor.Builder.build(LexerHelper.nextVerifiedValid(lex), lex);
			System.out.println(id.conjunctiveRepresentation().isNegation());
			assertTrue(id.conjunctiveRepresentation().isNegation());
		}
		catch (ParserException pe){
			fail();
		}
	}
	
	
}

