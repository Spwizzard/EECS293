package wireframe;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class WireframeTest {
	
	@Test
	public void wireframeStressTest(){
		Wireframe wireframe = new Wireframe();
		//Create lots of objects
		ArrayList<Groupable> elementList = new ArrayList<Groupable>();
		Enums.ElementType[] values = Enums.ElementType.values();
		for(int i = 0; i < 1000; i++){
			Enums.ElementType type = values[(int)(Math.random() * values.length)];
			Location location = new Location((int)(Math.random() * 1000),(int)(Math.random() * 1000));
			wireframe.placeElement(type, location);
			if(type != Enums.ElementType.ANNOTATION){
				elementList.add(wireframe.getWireframeElements().get(0));
			}
		}
		AbstractElement lastElement = (AbstractElement)elementList.get(0);
		try{
			wireframe.groupElements(elementList);//group all elements together
			Groupable group = wireframe.getWireframeElements().get(0); //this should be the group
			assertEquals(1, wireframe.getWireframeElements().size());
			Location newLocation = new Location((int)(Math.random() * 1000),(int)(Math.random() * 1000));
			lastElement.moveTo(newLocation);
			group.lock();
			newLocation = new Location((int)(Math.random() * 1000),(int)(Math.random() * 1000));
			lastElement.moveTo(newLocation);
			fail();//The group is locked, it shouldn't work
		}
		catch(LockedException e){
			//The group is locked
		}	
	}
	
	@Test
	public void createElementTest(){
		Wireframe wireframe = new Wireframe();
		wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
		assertEquals(1 , wireframe.getWireframeElements().size());
	}
	
	@Test
	public void deleteElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement element = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			wireframe.deleteElement(element);
			assertEquals(0 , wireframe.getWireframeElements().size());
		}
		catch(WireframeException e){
			fail();
		}
	}
	
	@Test
	public void deleteLockedElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement element = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			element.lock();
			wireframe.deleteElement(element);
			fail();
		}
		catch(WireframeException e){
			assertEquals("Element is locked!" , e.getMessage());
		}
	}
	
	@Test
	public void createGroupedElementsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			assertEquals(2 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> group = new ArrayList<Groupable>();
			group.add(image);
			group.add(text);
			wireframe.groupElements(group);
			assertEquals(1 , wireframe.getWireframeElements().size());
			
		}
		catch(WireframeException e){
			fail();
		}
	}
	
	@Test
	public void ungroupElementsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			assertEquals(2 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> group = new ArrayList<Groupable>();
			group.add(image);
			group.add(text);
			wireframe.groupElements(group);
			assertEquals(1 , wireframe.getWireframeElements().size());
			Group groupElement = (Group)wireframe.getWireframeElements().get(0);
			wireframe.ungroupElements(groupElement);
			assertEquals(2 , wireframe.getWireframeElements().size());
			
		}
		catch(WireframeException e){
			fail();
		}
	}
	
	@Test
	public void deleteGroupedElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			assertEquals(2 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			wireframe.groupElements(groupMembers);
			wireframe.deleteElement(image);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			assertEquals(1 , group.getGroupMembers().size());
			
		}
		catch(WireframeException e){
			fail();
		}
	}
	
	@Test
	public void deleteLockedGroupedElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			assertEquals(2 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.lock();
			wireframe.deleteElement(image);
			fail();
			
		}
		catch(WireframeException e){
			assertEquals("Element is locked!" , e.getMessage());
		}
	}
	
	@Test
	public void bringElementToFrontTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.bringElementToFront(text);
			AbstractWireframeElement front = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(text , front);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void bringElementForwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.bringElementForward(header);
			AbstractWireframeElement middle = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(header , middle);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void bringFrontElementForwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.bringElementForward(image);
			AbstractWireframeElement first = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement middle = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			assertEquals(image , first);
			assertEquals(text , middle);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendElementToBackTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.sendElementToBack(text);
			AbstractWireframeElement last = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendElementBackwardsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.sendElementBackward(text);
			AbstractWireframeElement middle = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(header , middle);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendLastElementBackwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			wireframe.sendElementBackward(header);
			AbstractWireframeElement middle = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(header , last);
			assertEquals(text , middle);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void moveElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			Location newLoc = new Location(6,7);
			image.moveTo(newLoc);
			assertEquals(newLoc , image.getTopLeftLocation());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void moveGroupTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(2 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			wireframe.groupElements(groupMembers);
			Location newLoc = new Location(6,7);
			image.moveTo(newLoc);
			assertEquals(8 , text.getTopLeftLocation().getX());
			assertEquals(7 , text.getTopLeftLocation().getY());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void moveElementWithOffsetTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			Location newLoc = new Location(6,7);
			image.moveUsingOffset(1,2);
			assertEquals(newLoc.getX() , image.getTopLeftLocation().getX());
			assertEquals(newLoc.getY() , image.getTopLeftLocation().getY());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void resizeElementTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.resizeTo(1, 2);
			assertEquals(1 , image.getPixelWidth());
			assertEquals(2 , image.getPixelHeight());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void createAnnotationTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.createAnnotation();
			assertEquals(1 , image.getAnnotations().size());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void removeAnnotationTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.createAnnotation();
			assertEquals(1 , image.getAnnotations().size());
			Annotation annotation = image.getAnnotations().get(0);
			wireframe.deleteElement(annotation);
			assertEquals(0 , image.getAnnotations().size());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void removeElementWithAnnotationTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.createAnnotation();
			assertEquals(1 , image.getAnnotations().size());
			wireframe.deleteElement(image);
			assertEquals(0 , image.getAnnotations().size());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void alignAnnotationsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.createAnnotation();
			image.createAnnotation();
			assertEquals(2 , image.getAnnotations().size());
			image.alignAnnotations(Enums.Alignment.JUSTIFIED);
			assertEquals(Enums.Alignment.JUSTIFIED, image.getAnnotations().get(1).getAlignment());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void moveAnnotationsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(1 , wireframe.getWireframeElements().size());
			image.createAnnotation();
			Location newLoc = new Location(6,7);
			image.moveTo(newLoc);
			Annotation annotation = image.getAnnotations().get(0);
			assertEquals(newLoc.getX() , annotation.getTopLeftLocation().getX());
			assertEquals(newLoc.getY() , annotation.getTopLeftLocation().getY());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void bringMemberToFrontTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.bringMemberToFront(text);
			AbstractWireframeElement front = (AbstractWireframeElement) group.getGroupMembers().get(0);
			assertEquals(text , front);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void bringMemberForwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.bringMemberForward(header);
			AbstractWireframeElement middle = (AbstractWireframeElement) group.getGroupMembers().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) group.getGroupMembers().get(2);
			assertEquals(header , middle);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void bringFrontMemberForwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.bringMemberForward(image);
			AbstractWireframeElement first = (AbstractWireframeElement) group.getGroupMembers().get(0);
			AbstractWireframeElement middle = (AbstractWireframeElement) group.getGroupMembers().get(1);
			assertEquals(image , first);
			assertEquals(text , middle);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendMemberToBackTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.sendMemberToBack(text);
			AbstractWireframeElement last = (AbstractWireframeElement) group.getGroupMembers().get(2);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendMemberBackwardsTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.sendMemberBackward(text);
			AbstractWireframeElement middle = (AbstractWireframeElement) group.getGroupMembers().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) group.getGroupMembers().get(2);
			assertEquals(header , middle);
			assertEquals(text , last);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void sendLastMemberBackwardTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> groupMembers = new ArrayList<Groupable>();
			groupMembers.add(image);
			groupMembers.add(text);
			groupMembers.add(header);
			wireframe.groupElements(groupMembers);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.sendMemberBackward(header);
			AbstractWireframeElement middle = (AbstractWireframeElement) group.getGroupMembers().get(1);
			AbstractWireframeElement last = (AbstractWireframeElement) group.getGroupMembers().get(2);
			assertEquals(header , last);
			assertEquals(text , middle);
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void moveSubGroupTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> subgroupList = new ArrayList<Groupable>();
			subgroupList.add(image);
			subgroupList.add(text);
			wireframe.groupElements(subgroupList);
			Group subgroup = (Group)wireframe.getWireframeElements().get(1);
			ArrayList<Groupable> groupList = new ArrayList<Groupable>();
			groupList.add(header);
			groupList.add(subgroup);
			wireframe.groupElements(groupList);
			header.moveTo(new Location(3, 8));
			assertEquals(7 , image.getTopLeftLocation().getX());
			assertEquals(8 , image.getTopLeftLocation().getY());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void deleteSubGroupTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			wireframe.placeElement(Enums.ElementType.HEADER, new Location(1,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			AbstractWireframeElement header = (AbstractWireframeElement) wireframe.getWireframeElements().get(2);
			assertEquals(3 , wireframe.getWireframeElements().size());
			ArrayList<Groupable> subgroupList = new ArrayList<Groupable>();
			subgroupList.add(image);
			subgroupList.add(text);
			wireframe.groupElements(subgroupList);
			Group subgroup = (Group)wireframe.getWireframeElements().get(0);
			ArrayList<Groupable> groupList = new ArrayList<Groupable>();
			groupList.add(header);
			groupList.add(subgroup);
			wireframe.groupElements(groupList);
			assertEquals(1 , wireframe.getWireframeElements().size());
			Group group = (Group)wireframe.getWireframeElements().get(0);
			group.deleteGroup();
			assertEquals(0, group.getGroupMembers().size());
			assertEquals(0, subgroup.getGroupMembers().size());
		}
		catch(WireframeException e){
			fail();
		}	
	}
	
	@Test
	public void groupMemberLockedTest(){
		Wireframe wireframe = new Wireframe();
		try{
			wireframe.placeElement(Enums.ElementType.IMAGE, new Location(5,5));
			wireframe.placeElement(Enums.ElementType.TEXT, new Location(7,5));
			AbstractWireframeElement image = (AbstractWireframeElement) wireframe.getWireframeElements().get(0);
			AbstractWireframeElement text = (AbstractWireframeElement) wireframe.getWireframeElements().get(1);
			ArrayList<Groupable> groupList = new ArrayList<Groupable>();
			groupList.add(image);
			groupList.add(text);
			wireframe.groupElements(groupList);
			Group group = (Group)wireframe.getWireframeElements().get(0);
			image.lock();
			text.moveTo(new Location(5,5));
			fail(); //image is locked and they are grouped, so we shouldn't be able to move text
		}
		catch(WireframeException e){
			assertEquals("One of the group members is locked!", e.getMessage());
		}	
	}
}
	