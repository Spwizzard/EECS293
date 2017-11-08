package numbers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class NumbersTest {
	
	@After
	public void reset(){
		System.setIn(System.in);
		System.out.flush();
	}
	
	@Test
	public void GivenExampleATest(){
		String input = "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "123456789");
	}
	
	@Test
	public void GivenExampleBTest(){
		String input = "    _  _  _  _  _  _     _ \n|_||_|| || ||_   |  |  ||  \n  | _||_||_||_|  |  |  | _|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "ambiguous");
	}
	
	@Test
	public void GivenExampleCTest(){
		String input = "    _  _  _     _  _  _  _ \n|_||_||_||_||_||_||_||_||_|\n|_||_||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void GivenExampleDTest(){
		String input = " _     _  _  _  _  _  _  _ \n|_|  ||_||_||_||_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "818888888");
	}
	
	@Test
	public void SegmentInWrongSpotTest(){
		String input = " _     _  _  _  |  _  _  _ \n|_|  ||_||_||__|_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void GarbledNumberNominalTest(){
		String input = " _  _  _  _     _  _  _  _ \n|_||_||_||_||_||_||_||_||_|\n|_||_||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "888888888");
	}
	
	@Test
	public void InvalidInputNumLinesShortTest(){
		String input = " |_|  ||_||_||_||_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void InvalidInputNumLinesLongTest(){
		String input = " |_|  ||_||_||_||_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|\n|_|  ||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void InvalidInputLineLengthTest(){
		String input = " _     _  _  _  _  _  _  _ \n |_|  ||_||_||_||_||_||_||_|             \n|_|  ||_||_||_||_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void InvalidInputCharactersTest(){
		String input = " _     _  _  _  _  8  _  _ \n|_|  ||_||_||_||_||_||_|l_|\n|_|  ||_||_||_|;|_||_||_||_|";
		ByteArrayInputStream inStream = new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream printStream = new ByteArrayOutputStream();
		System.setIn(inStream);
		System.setOut(new PrintStream(printStream));
		NumbersMain.main(null);
		assertEquals(printStream.toString(), "failure");
	}
	
	@Test
	public void InvalidInputLinesTest(){
		List<String> input = new ArrayList<String>(Arrays.asList(" _     _  _  _  _  "));
		try{
			NumbersInput.validateInput(input);
			fail();
		}
		catch(NumbersException e){
			//correct path
		}
	}
	
	@Test
	public void InvalidFindNumberTest(){
		List<String> input = new ArrayList<String>(Arrays.asList("    _  _     _  _  _  _  _ ","  | _| _||_||_ |_   ||_||_|","  ||_  _|  | _||_|  ||_| _|"));
		try{
			List<Digit> digits = NumbersInput.createDigitsFromInput(input);
			digits.remove(2);
			NumbersRecognizer.findNumber(digits);
		}
		catch(NumbersException e){
			//correct path
		}
	}
}

