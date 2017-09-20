package lexer;

import static org.junit.Assert.*;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

public class LexerTest {

	@Test
	public void testNext(){
		Lexer lex = new Lexer("and b");
		try{
			LocationalToken token = lex.next();	
			assertEquals(Token.of(Token.Type.AND, "") , token.getToken());
			token = lex.next();	
			assertEquals(Token.of(Token.Type.WHITESPACE, "") , token.getToken());
			token = lex.next();	
			assertEquals(Token.of(Token.Type.ID, "b") , token.getToken());
			token = lex.next();	
		}
		catch (ParserException pe){
			assertEquals(ParserException.ErrorCode.TOKEN_EXPECTED, pe.getErrorCode());
		}
	}
	
	@Test
	public void testNextValid(){
		Lexer lex = new Lexer("and + ");
		Set<Token.Type> valid = EnumSet.of(Token.Type.AND, Token.Type.BINARYOP);
		Set<Token.Type> invalid = EnumSet.of(Token.Type.ID);
		try{
			Optional<LocationalToken> token = lex.nextValid(valid, invalid);	
			assertEquals(Token.of(Token.Type.AND, "") , token.get().getToken());
			token = lex.nextValid(valid, invalid);	
			assertEquals(Token.of(Token.Type.BINARYOP, "+") , token.get().getToken());
			token = lex.nextValid(valid, invalid);	
			assertFalse(token.isPresent());
			
			//this part should throw an error
			lex = new Lexer("b and +");
			token = lex.nextValid(valid, invalid);	
		}
		catch (ParserException pe){
			assertEquals(ParserException.ErrorCode.INVALID_TOKEN, pe.getErrorCode());
		}
	}
	
	@Test
	public void testMatchTokenTypeToType(){
		Lexer lex = new Lexer("and or +");
		lex.hasNext();	
		assertEquals(Token.Type.AND, lex.matchTokenToType("and"));
		
		lex = new Lexer(" and or +");
		lex.hasNext();	
		assertEquals(Token.Type.WHITESPACE, lex.matchTokenToType(" "));
		
		lex = new Lexer("a and or +");
		lex.hasNext();	
		assertEquals(Token.Type.ID, lex.matchTokenToType("a"));
	}
	
	@Test
	public void testComplexityEstimate(){
		Lexer lex = new Lexer("andnotor () or a + / 5"); //16 tokens
		LocationalToken token;
		int estimate = 0;
		try{
			for (int i = 0; i < 16; i++){
				token = lex.next();
				if(token.getTokenType().isComplex()){
					estimate++;
				}
			}
			assertEquals(3, estimate);
			
		}
		catch (ParserException pe){
			//the error tells us we have found all of the tokens
		}
	}
}
