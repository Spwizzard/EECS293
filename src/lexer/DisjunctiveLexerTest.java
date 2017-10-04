package lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisjunctiveLexerTest {

	@Test
	public void testIdentifierCorrect(){
		DisjunctiveLexer lex = new DisjunctiveLexer("a");
		try{
			Identifier id = Identifier.Builder.build(LexerHelper.nextVerifiedValid(lex));
			assertEquals("a", id.toString());
		}
		catch (ParserException pe){
			fail();
		}
	}
	
	@Test
	public void testIdentifierIncorrect(){
		DisjunctiveLexer lex = new DisjunctiveLexer("and");
		try{
			Identifier id = Identifier.Builder.build(LexerHelper.nextVerifiedValid(lex));
			fail();
		}
		catch (ParserException pe){
			assertEquals(ParserException.ErrorCode.ID_EXPECTED, pe.getErrorCode());
		}
	}
	
	@Test
	public void testCompoundFactorCorrect(){
		DisjunctiveLexer lex = new DisjunctiveLexer("( (a and not c) and not b )");
		try{
			CompoundFactor id = CompoundFactor.Builder.build(LexerHelper.nextVerifiedValid(lex), lex);
			assertEquals("((a and not c) and not b)", id.toString());
		}
		catch (ParserException pe){
			fail();
		}
	}
	
	@Test
	public void testCompoundFactorIncorrect(){
		DisjunctiveLexer lex = new DisjunctiveLexer("( (a and not c) ( not b )");
		try{
			CompoundFactor id = CompoundFactor.Builder.build(LexerHelper.nextVerifiedValid(lex), lex);
			fail();
		}
		catch (ParserException pe){
			assertEquals(ParserException.ErrorCode.AND_EXPECTED, pe.getErrorCode());
		}
	}
	
	
}

