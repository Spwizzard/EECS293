package planning;

import static org.junit.Assert.*;

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
	}
	
	@Test
	public void testNoDependencies() throws CircularRequirementException{
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
	
	@Test
	public void testBEGINDependencies() throws CircularRequirementException{
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
	
	@Test
	public void testENDDependencies() throws CircularRequirementException{
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
		assertEquals(10, estimatedDuration); //algorithm wrongly says 11
	}
	
	@Test
	public void testComplexDependencies() throws CircularRequirementException{
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
	
	@Test
	public void testCircularDependencies(){
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
	
	@Test
	public void testAlgorithmNotWorking(){
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
}
