import java.util.*;

class Math
{
	int val;
	int steps;
	
	public Math(int val, int steps)
	{
		this.val = val;
		this.steps = steps;
	}
}

public class Number{
	
           
	
    public static int getStep(int x, int y) {
        // TO DO
        // Implement the getStep method
        
        Set<Math> visited = new HashSet<>(100);
        LinkedList<Math> queue = new LinkedList<Math>();
        Math num = new Math(x, 0);
        queue.offer(num);
        visited.add(num);
        
        while (!queue.isEmpty())
        {
        	Math tmp = queue.poll();
        	visited.add(tmp);
        	
        	if (tmp.val == y){
        		return tmp.steps;
        	}
        	// +1
        	int add = tmp.val + 1;
        	
        	if (add >= 0 && add < 100){
        		Math addNum = new Math(add, tmp.steps + 1);
        		queue.offer(addNum);
        	}
        	// -1
        	int sub = tmp.val - 1;
        	
        	if (sub >= 0 && sub < 100){
        		Math subNum = new Math(sub, tmp.steps + 1);
        		queue.offer(subNum);
        	}
        	// *3
        	int mul = tmp.val * 3;
        	
        	if (mul >= 0 && mul < 100){
        		Math mulNum = new Math(mul, tmp.steps + 1);
        		queue.offer(mulNum);
        	}
        	
        	// ^2
        	int square = tmp.val * tmp.val;
        	
        	if (square >= 0 && square < 100){
        		Math squareNum = new Math(square, tmp.steps + 1);
        		queue.offer(squareNum);
        	}
        	
        	
        }      
        return -1;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        int x, y;
        x = Integer.parseInt(args[0]);
        y = Integer.parseInt(args[1]);
        
        System.out.println(getStep(x,y));

    }
}