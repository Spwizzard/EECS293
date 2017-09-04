package hw1;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class CommonPrefix {
	
	public static void main(String[] args) {
		String test1 = args[0];
		String test2 = args[1];
		CharComparator charCmp = new CharComparator();
		
		//support for char[] to List<Character> doesn't exist in default JDK, use loop to convert
		List<Character> list1 = new ArrayList<Character>();
		for(char c : test1.toCharArray()) {
		    list1.add(c);
		}
		List<Character> list2 = new ArrayList<Character>();
		for(char c : test2.toCharArray()) {
		    list2.add(c);
		}
		
		//now use longestPrefix to get the longest prefix
		List<Character> commonPrefix = CommonPrefix.<Character>longestPrefix(list1, list2, charCmp);
		
		System.out.println(commonPrefix);
		
	}
	
	/*A recursive method that takes two comparable lists and returns the longest common
	  prefix between them.*/ 
	static <T> List<T> longestPrefix(List<T> a, List<T> b, Comparator<? super T> cmp){
		
		//base case: one of the lists is empty
		if(a.isEmpty() || b.isEmpty()){
			//we can return an empty list
			return new ArrayList<T>();
		}
		
		//check if the first element of a is equal to the first element of b
		if(cmp.compare(a.get(0), b.get(0)) == 0){
			//the first elements are equal
			//remove the first element from a and b
			T commonPrefixElem = a.remove(0);
			b.remove(0);
			
			//now get the common prefix from the rest of the list
			List<T> restOfPrefix = longestPrefix(a, b, cmp);
			
			//add the commonPrefixElem we removed back onto the rest of the prefix and return
			restOfPrefix.add(0, commonPrefixElem);
			
			//finally, return restOfPrefix and continue up the recursive tree
			return restOfPrefix;
		}
		else{
			//the first elements were not equal
			//thus, the common prefix ends here. We can return an empty list
			return new ArrayList<T>();
		}
	}
	
	static class CharComparator implements Comparator<Character> {
		  public int compare(Character obj1, Character obj2) {
		    if (obj1 == null) {
		        return -1;
		    }
		    if (obj2 == null) {
		        return 1;
		    }
		    if (obj1.equals( obj2 )) {
		        return 0;
		    }
		    return obj1.compareTo(obj2);
		  }
		}
}

