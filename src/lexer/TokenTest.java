package lexer;

import static org.junit.Assert.*;
import org.junit.Test;

public class TokenTest {

	@Test
	public void testTokenCreationWithOptionals(){
		Token tokenWithData = Token.of(Token.Type.NUMBER, "1234");
		Token tokenWithEmptyData = Token.of(Token.Type.NUMBER, null);
		Token tokenWithoutData = Token.of(Token.Type.AND, null);
		Token tokenWithoutDataGivenData = Token.of(Token.Type.AND, "1234");

		assertTrue(tokenWithData.getData().isPresent());
		assertFalse(tokenWithEmptyData.getData().isPresent());
		assertFalse(tokenWithoutData.getData().isPresent());
		assertFalse(tokenWithoutDataGivenData.getData().isPresent());
	}
}
