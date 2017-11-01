package planning;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import planning.Assignment.DependencyType;

public class EstimatedDurationTest {
	private HashSet<Assignment> blocked;
	private HashSet<Assignment> started;
	private HashSet<Assignment> finished;
	private EstimatedDuration.Test test;
	
	
	@Before
	public void setUp() {
		blocked = new HashSet<Assignment>();
		started = new HashSet<Assignment>();
		finished = new HashSet<Assignment>();
		test = new EstimatedDuration.Test();
	}
	
	//CircularRequirementException Tests
	
	//Structured Basis
	//Boundary
	@Test
	public void testVerifyIsNotStuckNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		blocked = new HashSet<Assignment>(Arrays.asList(a));
		started = new HashSet<Assignment>(Arrays.asList(b));
		try{
			CircularRequirementException.verifyIsNotStuck(blocked, started);
		}
		catch(CircularRequirementException e){
			fail();
		}
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testVerifyIsNotStuckBlockedEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		blocked = new HashSet<Assignment>();
		started = new HashSet<Assignment>(Arrays.asList(b));
		try{
			CircularRequirementException.verifyIsNotStuck(blocked, started);
		}
		catch(CircularRequirementException e){
			fail();
		}
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testVerifyIsNotStuckBothEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		blocked = new HashSet<Assignment>();
		started = new HashSet<Assignment>();
		try{
			CircularRequirementException.verifyIsNotStuck(blocked, started);
		}
		catch(CircularRequirementException e){
			fail();
		}
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testVerifyIsNotStuckFails() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		blocked = new HashSet<Assignment>(Arrays.asList(a));
		started = new HashSet<Assignment>();
		try{
			CircularRequirementException.verifyIsNotStuck(blocked, started);
			fail();
		}
		catch(CircularRequirementException e){
			//we want it to throw here
		}
	}
	
	//Assignment Tests
	
	//Structured Basis
	//Boundary
	@Test
	public void testAddDependencyNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		HashSet<Assignment> beginBeginSet = new HashSet<Assignment>(Arrays.asList(b));
		assertEquals(beginBeginSet, a.getDependencySetOfType(DependencyType.BEGIN_BEGIN));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testAddDependencyEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		HashSet<Assignment> emptySet = new HashSet<Assignment>();
		assertEquals(emptySet, a.getDependencySetOfType(DependencyType.BEGIN_END));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testGetDependencySetOfTypeNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.BEGIN_BEGIN, c);
		HashSet<Assignment> beginBeginSet = new HashSet<Assignment>(Arrays.asList(b,c));
		assertEquals(beginBeginSet, a.getDependencySetOfType(DependencyType.BEGIN_BEGIN));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testGetDependencySetOfTypeEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		HashSet<Assignment> beginBeginSet = new HashSet<Assignment>();
		assertEquals(beginBeginSet, a.getDependencySetOfType(DependencyType.BEGIN_BEGIN));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testSetCurrentDurationNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		int newDuration = 7;
		a.setCurrentDuration(newDuration);
		assertEquals(newDuration, a.getCurrentDuration());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testSetCurrentDurationZero() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		int newDuration = 0;
		a.setCurrentDuration(newDuration);
		assertEquals(newDuration, a.getCurrentDuration());
	}

	//EstimatedDuration Tests
	
	//Structured Basis
	//Boundary
	@Test
	public void testDecrementCurrentDurationsSingle(){
		Assignment a = new Assignment(5);
		started.add(a);
		test.decrementCurrentDuration(started);
		assertEquals(4, a.getCurrentDuration());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testDecrementCurrentDurationsSingleZero(){
		Assignment a = new Assignment(0);
		started.add(a);
		test.decrementCurrentDuration(started);
		assertEquals(0, a.getCurrentDuration());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testDecrementCurrentDurationsMultiple(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(6);
		Assignment c = new Assignment(7);
		started.add(a);
		started.add(b);
		started.add(c);
		test.decrementCurrentDuration(started);
		assertEquals(4, a.getCurrentDuration());
		assertEquals(5, b.getCurrentDuration());
		assertEquals(6, c.getCurrentDuration());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testDecrementCurrentDurationsMutlipleZeros(){
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(6);
		Assignment c = new Assignment(0);
		started.add(a);
		started.add(b);
		started.add(c);
		test.decrementCurrentDuration(started);
		assertEquals(0, a.getCurrentDuration());
		assertEquals(5, b.getCurrentDuration());
		assertEquals(0, c.getCurrentDuration());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsFinishedNominal(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		assertTrue(test.isFinished(blocked, started));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsFinishedBlockedNotEmpty(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		blocked.add(a);
		assertFalse(test.isFinished(blocked, started));
	}
	
	//Structured Basis
	//Boundary
	public void testIsFinishedStartedNotEmpty(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		started.add(a);
		assertFalse(test.isFinished(blocked, started));
	}
	
	//Structured Basis
	//Boundary
	public void testIsFinishedBothNotEmpty(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		blocked.add(a);
		started.add(b);
		assertFalse(test.isFinished(blocked, started));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanStartNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		started.add(b);
		finished.add(c);
		assertTrue(test.canStart(blocked, started, finished, a));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanStartFalse() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		blocked.add(b);
		finished.add(c);
		assertFalse(test.canStart(blocked, started, finished, a));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanFinishNominal() throws CircularRequirementException{
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		started.add(b);
		finished.add(c);
		assertTrue(test.canFinish(blocked, started, finished, a));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanFinishNonZeroDuration() throws CircularRequirementException{
		Assignment a = new Assignment(1);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		started.add(b);
		finished.add(c);
		assertFalse(test.canFinish(blocked, started, finished, a));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanFinishFalse() throws CircularRequirementException{
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		blocked.add(b);
		finished.add(c);
		assertFalse(test.canFinish(blocked, started, finished, a));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsTrueEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		started.add(a);
		started.add(b);
		finished.add(c);
		test.handleEligibleAssignments(blocked, started, finished, blocked, started, true);
		assertEquals(0,blocked.size());
		assertEquals(2,started.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsTrueMoveSome() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.END_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		b.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		blocked.add(b);
		finished.add(c);
		test.handleEligibleAssignments(blocked, started, finished, blocked, started, true);
		assertEquals(1,blocked.size());
		assertEquals(1,started.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsTrueMoveAll() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.END_BEGIN, c);
		b.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		blocked.add(b);
		finished.add(c);
		test.handleEligibleAssignments(blocked, started, finished, blocked, started, true);
		assertEquals(0,blocked.size());
		assertEquals(2,started.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsFalseEmpty() throws CircularRequirementException{
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(0);
		Assignment c = new Assignment(0);
		a.addDependency(DependencyType.END_END, c);
		b.addDependency(DependencyType.END_END, c);
		finished.add(a);
		blocked.add(b);
		finished.add(c);
		test.handleEligibleAssignments(blocked, started, finished, started, finished, false);
		assertEquals(0,started.size());
		assertEquals(2,finished.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsFalseMoveSome() throws CircularRequirementException{
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(0);
		Assignment c = new Assignment(0);
		a.addDependency(DependencyType.END_END, c);
		b.addDependency(DependencyType.BEGIN_END, c);
		started.add(a);
		started.add(b);
		started.add(c);
		test.handleEligibleAssignments(blocked, started, finished, started, finished, false);
		assertEquals(1,started.size());
		assertEquals(2,finished.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testHandleEligibleAssignmentsFalseMoveAll() throws CircularRequirementException{
		Assignment a = new Assignment(0);
		Assignment b = new Assignment(0);
		Assignment c = new Assignment(0);
		a.addDependency(DependencyType.END_END, c);
		b.addDependency(DependencyType.END_END, c);
		started.add(a);
		started.add(b);
		finished.add(c);
		test.handleEligibleAssignments(blocked, started, finished, started, finished, false);
		assertEquals(0,started.size());
		assertEquals(3,finished.size());
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsAssignmentEligibleNominalStart() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		started.add(b);
		finished.add(c);
		assertTrue(test.isAssignmentEligible(blocked, started, finished, a, DependencyType.BEGIN_BEGIN, DependencyType.END_BEGIN));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsAssignmentEligibleFalseStart() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		blocked.add(b);
		finished.add(c);
		assertFalse(test.isAssignmentEligible(blocked, started, finished, a, DependencyType.BEGIN_BEGIN, DependencyType.END_BEGIN));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsAssignmentEligibleNominalEnd() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		started.add(b);
		finished.add(c);
		assertTrue(test.isAssignmentEligible(blocked, started, finished, a, DependencyType.BEGIN_END, DependencyType.END_END));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsAssignmentEligibleFalseEnd() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		started.add(b);
		started.add(c);
		assertFalse(test.isAssignmentEligible(blocked, started, finished, a, DependencyType.BEGIN_END, DependencyType.END_END));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testIsAssignmentEligibleBothFalse() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		blocked.add(b);
		started.add(c);
		assertFalse(test.isAssignmentEligible(blocked, started, finished, a, DependencyType.BEGIN_END, DependencyType.END_END));
	}
	
	//Structured Basis
	@Test
	public void testCanDependenciesResolveBeginNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.BEGIN_BEGIN, c);
		blocked.add(a);
		started.add(b);
		finished.add(c);
		assertTrue(test.canDependenciesResolve(blocked, started, finished, a, DependencyType.BEGIN_BEGIN, true));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanDependenciesResolveBeginFail() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.BEGIN_END, b);
		a.addDependency(DependencyType.BEGIN_END, c);
		started.add(a);
		started.add(b);
		blocked.add(c);		
		assertFalse(test.canDependenciesResolve(blocked, started, finished, a, DependencyType.BEGIN_END, true));
	}
	
	//Structured Basis
	@Test
	public void testCanDependenciesResolveEndNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.END_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		blocked.add(a);
		finished.add(b);
		finished.add(c);
		assertTrue(test.canDependenciesResolve(blocked, started, finished, a, DependencyType.END_BEGIN, false));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanDependenciesResolveEndFail() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		a.addDependency(DependencyType.END_END, b);
		a.addDependency(DependencyType.END_END, c);
		started.add(a);
		finished.add(b);
		started.add(c);
		assertFalse(test.canDependenciesResolve(blocked, started, finished, a, DependencyType.END_END, false));
	}
	
	//Structured Basis
	//Boundary
	@Test
	public void testCanDependenciesResolveEmptyNominal() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(5);
		Assignment c = new Assignment(5);
		started.add(a);
		finished.add(b);
		started.add(c);
		assertTrue(test.canDependenciesResolve(blocked, started, finished, a, DependencyType.END_END, false));
	}
	
	//Good Data
	@Test
	public void testEstimatedDurationNoDependencies() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		Assignment c = new Assignment(8);
		Assignment d = new Assignment(6);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		assignments.add(c);
		assignments.add(d);
		int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
		assertEquals(10, estimatedDuration); //algorithm wrongly says 11
	}
	
	//Good Data
	@Test
	public void testEstimatedDurationBEGINDependencies() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		Assignment c = new Assignment(8);
		Assignment d = new Assignment(6);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		a.addDependency(DependencyType.END_BEGIN, c);
		b.addDependency(DependencyType.END_BEGIN, d);
		c.addDependency(DependencyType.BEGIN_BEGIN, d);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		assignments.add(c);
		assignments.add(d);
		int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
		assertEquals(16, estimatedDuration); //algorithm wrongly says 18
	}
	
	//Good Data
	@Test
	public void testEstimatedDurationENDDependencies() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		Assignment c = new Assignment(8);
		Assignment d = new Assignment(6);
		a.addDependency(DependencyType.BEGIN_END, b);
		b.addDependency(DependencyType.BEGIN_END, c);
		c.addDependency(DependencyType.END_END, b);
		d.addDependency(DependencyType.END_END, a);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		assignments.add(c);
		assignments.add(d);
		int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
		assertEquals(10, estimatedDuration); //algorithm wrongly says 12
	}
	
	//Good Data
	@Test
	public void testEstimatedDurationComplexDependencies() throws CircularRequirementException{
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		Assignment c = new Assignment(8);
		Assignment d = new Assignment(6);
		b.addDependency(DependencyType.END_BEGIN, a);
		c.addDependency(DependencyType.BEGIN_BEGIN, b);
		c.addDependency(DependencyType.END_END, b);
		d.addDependency(DependencyType.END_BEGIN, c);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		assignments.add(c);
		assignments.add(d);
		int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
		assertEquals(21, estimatedDuration); //algorithm wrongly says 24
	}
	
	//Boundary
	@Test
	public void testEstimatedDurationCircularDependencies(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		a.addDependency(DependencyType.END_BEGIN, b);
		b.addDependency(DependencyType.END_BEGIN, a);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		try{
			int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
			System.out.println(estimatedDuration);
			fail(); //This should have broken the algorithm
		}
		catch (CircularRequirementException e){
			//we were supposed to fail here
			//pass the test
		}
		
	}
	
	//Stress Test
	//Good Data
	@Test
	public void testEstimatedDurationFalseCircularDependencies(){
		Assignment a = new Assignment(5);
		Assignment b = new Assignment(10);
		a.addDependency(DependencyType.BEGIN_BEGIN, b);
		b.addDependency(DependencyType.BEGIN_BEGIN, a);
		HashSet<Assignment> assignments = new HashSet<Assignment>();
		assignments.add(a);
		assignments.add(b);
		try{
			int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignments);
			System.out.println(estimatedDuration); //This should have been fine
		}
		catch (CircularRequirementException e){
			fail();
		}
	}
	
	//Stress Test (Doesn't finish, dont want to clog up git server)
	//@Test
	public void stressTest() throws CircularRequirementException{
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		for(int i = 0; i < 10000; i++){
			int duration = (int)(Math.random() * 10);
			assignments.add(new Assignment(duration));
		}
		for(int i = 0; i < 10000; i++){
			int numDependencies = (int)(Math.random() * 5);
			for(int j = 0; j < numDependencies; j++){
				DependencyType type = (DependencyType.values()[(int)(Math.random() * 4)]);
				int dependencyIndex = (int)(Math.random() * 10000);
				assignments.get(i).addDependency(type, assignments.get(dependencyIndex));
			}
		}
		HashSet<Assignment> assignmentSet = new HashSet<Assignment>(assignments);
		int estimatedDuration = EstimatedDuration.estimatedDurationOfAssignments(assignmentSet);
		assertEquals(10000, estimatedDuration); 
	}
}
