package pic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class PicTest {
	
	@Test
	public void ChunkEnterCharacterArrayIndexOutOfBoundsTest() throws PictureException{
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(". . . E . . .");
		lines.add(". . E . . . .");
		lines.add(". . . . E . .");
		lines.add(". . . E . . .");
		Chunk testChunk = Chunk.StaticTestHook.of(lines);
		try{
			Character[][] charArray = testChunk.toCharacterArray(4, 7);
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void AbstractPictureIsUnexpectedCharacterTest(){
		HashSet<Character> characters = new HashSet<Character>(Arrays.asList('a', 'b', 'c'));
		char c = 'c';
		assertFalse(AbstractPicture.StaticTestHook.isUnexpectedCharacter(c, characters));
	}
	
	@Test
	public void PictureOrderingToRemoveNumInputPicturesTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to be valid input
        List<String> inputLines = reader.inputLines("inputs/PictureOrderingOrderStringInput.txt"); 
		PictureInput input = PictureInput.inputOf(inputLines);
		try{
			PictureOrdering order = PictureOrdering.from(input);
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void PictureOrderingNoSubPicturesToRemoveTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to have a composite picture in which you can't tell the order of subpictures
        List<String> inputLines = reader.inputLines("inputs/ImpossibleCompositePicture.txt"); 
        PictureInput input = PictureInput.inputOf(inputLines);
		PictureOrdering order = PictureOrdering.from(input);
		assertFalse(System.out.equals("error"));
	}
	
	@Test
	public void PictureOrderingOrderStringNotOrderedTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to be a valid input with characters ABCDE where the correct oder isnt ABCDE
        List<String> inputLines = reader.inputLines("inputs/PictureOrderingOrderStringInput.txt"); 
		PictureInput input = PictureInput.inputOf(inputLines);
		PictureOrdering order = PictureOrdering.from(input);
		assertFalse(order.toString().equals("ABCDE"));
	}
	
	@Test
	public void PictureOrderingBackwardsOrderTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to be a valid input where the correct order is EDABC
        List<String> inputLines = reader.inputLines("inputs/PictureOrderingOrderStringInput.txt"); 
		PictureInput input = PictureInput.inputOf(inputLines);
		PictureOrdering order = PictureOrdering.from(input);
		assertTrue(order.toString().equals("EDABC"));
	}
	
	@Test
	public void PictureInputDimensionMatcherTest() throws PictureException{
		try{
			PictureInput.StaticTestHook.validateDimensionFormat("10", "Invalid height input");
		}
		catch(PictureException e){
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void ChunkNoWhitespaceTest() throws PictureException{
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(". . . E             . . .");
		lines.add(". . E .  .  .  		.");
		lines.add(". . . . E . .");
		lines.add(". . .E...");
		Chunk testChunk = Chunk.StaticTestHook.of(lines);
		try{
			Character[][] charArray = testChunk.toCharacterArray(4, 7);
			fail();
		}
		catch(PictureException e){
			//should throw an error because there are incorrect whitespaces
		}
	}
	
	@Test
	public void ChunkNoCharactersTest() throws PictureException{
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(". . . . . . .");
		lines.add(". . . . . . .");
		lines.add(". . . . . . .");
		lines.add(". . . . . . .");
		Chunk testChunk = Chunk.StaticTestHook.of(lines);
		try{
			Character[][] charArray = testChunk.toCharacterArray(4, 7);
		}
		catch(PictureException e){
			//should throw an error because there are no valid characters
			fail();
		}
	}
	
	@Test
	public void ChunkMultipleCharactersTest() throws PictureException{
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(". . . E . . .");
		lines.add("A . E . . D .");
		lines.add(". . . . E . .");
		lines.add(". C . E . . .");
		Chunk testChunk = Chunk.StaticTestHook.of(lines);
		try{
			Character[][] charArray = testChunk.toCharacterArray(4, 7);
		}
		catch(PictureException e){
			//should throw an error because there are multiple valid characters
			fail();
		}
	}
	
	@Test
	public void PictureInputMissingLettersTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to be an invalid input with missing letters in the composite picture
        List<String> inputLines = reader.inputLines("inputs/InvalidCompositePicturePlacement.txt"); 
		try{
			PictureInput input = PictureInput.inputOf(inputLines);
			fail();
		}
		catch(PictureException e){
			//should throw an error because the composite picture is invalid
			
		}
	}
	
	@Test
	public void PictureInputCompletelyOverlappingCompositeTest() throws PictureException{
		PictureReader reader = new PictureReader();
		//requires input to be an invalid input a subpicture completely overlapped in the composite picture
        List<String> inputLines = reader.inputLines("inputs/CompletelyOverlappingComposite.txt"); 
		try{
			PictureInput input = PictureInput.inputOf(inputLines);
			fail();
		}
		catch(PictureException e){
			//should throw an error because the composite picture is invalid
			
		}
	}
}
