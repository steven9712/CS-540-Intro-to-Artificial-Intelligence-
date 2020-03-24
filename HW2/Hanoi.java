import java.util.*;

public class Hanoi {
	
	// parses the command line arguments and constructs the original state of the hanoi tower in an array of stacks
	public static List<List<String>> acquireState(String[] hanoi){
		
		 List<List<String>> state = new ArrayList<>();
	        for (int i = 0; i < hanoi.length; i++)
	        {
	        	ArrayList<String> rod = new ArrayList<>();
	        	
	        	String[] disk = hanoi[i].split("");
	        	for (int j = 0; j < hanoi[i].length(); j++){
	        		rod.add(disk[j]);
	        	}
	        	state.add(rod);   	
	        }
        return state;
	}
	
	//toString function to change the elements of the list of lists to the required format
	public static String toString(List<List<String>> tower)
	{
		String next = "";
		for (int i = 0; i < tower.size(); i++)
		{
			for (int j = 0; j < tower.get(i).size(); j++)
			{
				next += tower.get(i).get(j);
			}
			next += " ";
		}
		return next;
	}
	
    
    public static List<String> getSuccessor(String[] hanoi) {
    	
        // TO DO
        // Implement the getSuccessor method
        List<String> result = new ArrayList<>();
        
        List<List<String>> original = acquireState(hanoi);
        
        //check each rod
        for (int i = 0; i < original.size(); i++){
        	if (original.get(i).get(0) == "0")
        	{
        		continue;
        	}
        	
        	// as long as its not the last rod we would just check the next index
        	if (i != original.size() - 1)
        	{
        		// check if the current rod is not 0, the next rod is not 0, then compare and add to result
        		if (!original.get(i).get(0).equals("0") && original.get(i + 1).get(0).equals("0") && original.get(i).get(0).compareTo(original.get(i+1).get(0)) > 0)
        		{
        			String currDisk = original.get(i).get(0);
        			original.get(i).remove(0);
        			if (original.get(i + 1).get(0).equals("0"))
        			{
        				original.get(i + 1).remove(0);
        			}
        			if (original.get(i).size() == 0)
        			{
        				original.get(i).add("0");
        			}
        			original.get(i + 1).add(0, currDisk);
        			
        			// compiling the string to add to the results
            		String successor = toString(original);
            		
            		result.add(successor);
            		// reacquire the original state
            		original = acquireState(hanoi);
        		}
        		// check if the current rod is not 0, the next rod is 0, then compare and add to result
        		else if (!original.get(i).get(0).equals("0") && !original.get(i + 1).get(0).equals("0") && original.get(i).get(0).compareTo(original.get(i+1).get(0)) < 0 )
        		{
        			String currDisk = original.get(i).get(0);
        			original.get(i).remove(0);
        			if (original.get(i + 1).get(0).equals("0"))
        			{
        				original.get(i + 1).remove(0);
        			}
        			if (original.get(i).size() == 0)
        			{
        				original.get(i).add("0");
        			}
        			original.get(i + 1).add(0, currDisk);
        			
        			// compiling the string to add to the results
            		String successor = toString(original);
            		
            		result.add(successor);
            		//reacquire the original state
            		original = acquireState(hanoi);
        		}
        		
        	}
        	
        	// as long as the rod isn't the first rod, we would have to check the rod before the current, since we check bot adjacent rods
        	if (i != 0)
        	{
        		// if the current rod is not 0, the next rod is 0, then compare and add to result 
        		if (!original.get(i).get(0).equals("0") && original.get(i-1).get(0).equals("0") && original.get(i).get(0).compareTo(original.get(i-1).get(0)) > 0)
        		{
        			String currDisk = original.get(i).get(0);
        			original.get(i).remove(0);
        			if (original.get(i - 1).get(0).equals("0"))
        			{
        				original.get(i - 1).remove(0);
        			}
        			if (original.get(i).size() == 0)
        			{
        				original.get(i).add("0");
        			}
        			original.get(i - 1).add(0, currDisk);
        			
        			// compiling the string to add to the results
        			String successor = toString(original);
        			
            		result.add(successor);
            		//reacquire the original state
            		original = acquireState(hanoi);
        		}
        		
        		// if the current rod is not 0, the next rod is not 0, then compare and add to result
        		else if (!original.get(i).get(0).equals("0") && !original.get(i-1).get(0).equals("0") && original.get(i).get(0).compareTo(original.get(i-1).get(0)) < 0)
        		{
        			String currDisk = original.get(i).get(0);
        			original.get(i).remove(0);
        			if (original.get(i - 1).get(0).equals("0"))
        			{
        				original.get(i - 1).remove(0);
        			}
        			if (original.get(i).size() == 0)
        			{
        				original.get(i).add("0");
        			}
        			original.get(i - 1).add(0, currDisk);
        			
        			// compiling the string to add to the results
        			String successor = toString(original);
        			
            		result.add(successor);
            		original = acquireState(hanoi);
        		}
        		
        	}
        	
        }   
        return result;    
    }

    
    
    
    
    
    
    public static void main(String[] args) {
        if (args.length < 3) {
	        return;
	    }
        
        List<String> sucessors = getSuccessor(args);
        for (int i = 0; i < sucessors.size(); i++) {
            System.out.println(sucessors.get(i));
        }    
    }
 
}
