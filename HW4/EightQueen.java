import java.util.*;
import java.lang.Math;

class State {
    char[] board;

    public State(char[] arr) {
        this.board = Arrays.copyOf(arr, arr.length);
    }
    
    // find the heuristic cost of the current state
    public int findCost(int option, int iteration, int seed) {
    	int cost = 0;
    	char curr, temp;
    	
    	for (int i = 0; i < this.board.length; i++){
    		for (int j = i + 1; j < this.board.length; j++){
    			curr = this.board[i];
    			temp = this.board[j];
    			
    			if (j == i + 1)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 1)
    				{
    					cost++;
    				}
    				else if (temp == curr - 1)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 2)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 2)
    				{
    					cost++;
    				}
    				else if (temp == curr - 2)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 3)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 3)
    				{
    					cost++;
    				}
    				else if (temp == curr - 3)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 4)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 4)
    				{
    					cost++;
    				}
    				else if (temp == curr - 4)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 5)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 5)
    				{
    					cost++;
    				}
    				else if (temp == curr - 5)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 6)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 6)
    				{
    					cost++;
    				}
    				else if (temp == curr - 6)
    				{
    					cost++;
    				}
    			}
    			else if (j == i + 7)
    			{
    				if (temp == curr){
    					cost++;
    				}
    				else if (temp == curr + 7)
    				{
    					cost++;
    				}
    				else if (temp == curr - 7)
    				{
    					cost++;
    				}
    			}
    		}
    	}
    	
    	return cost;
    }

    // get all the successors that has the least heuristic cost
    public ArrayList<State> getSuccessor(int option, int iteration, int seed) {
    	ArrayList<State> succs = new ArrayList<>();
    	ArrayList<State> allSuccs = new ArrayList<>();
    	char[] possible = new char[] {'0','1','2','3','4','5','6','7'};
    	int leastCost = this.findCost(option, iteration, seed);
    	int tempCost = 100;
    	
    	for (int i = 0; i < this.board.length; i++)
    	{
    		for (int j = 0; j < possible.length; j++)
    		{
    			
    			State temp = new State(this.board);
    			
    			if (temp.board[i] != possible[j])
    			{
    				temp.board[i] = possible[j];
    				allSuccs.add(temp);
    			}
    		}
    	}
    	
    	for (int k = 0; k < allSuccs.size(); k++){
    		tempCost = allSuccs.get(k).findCost(option, iteration, seed);
    		if (tempCost < leastCost){
    			leastCost = tempCost;
    		}
    	}
    	
    	for (int m = 0; m < allSuccs.size(); m++){
    		if (allSuccs.get(m).findCost(option, iteration, seed) == leastCost)
    		{
    			succs.add(allSuccs.get(m));
    		}
    	}
    	
    	
    	return succs;
    }
    
    // use the hill climbing algorithm to try to solve the problem
    public ArrayList<State> hillClimb(int option, int seed, int iteration) {
    	ArrayList<State> moves = new ArrayList<>();
    	ArrayList<State> succs = this.getSuccessor(option, iteration, seed);
    	State curr = this;
    	
    	Random rng = new Random();
    	if (seed != -1)
    	{
    		rng.setSeed(seed);
    	}
    	moves.add(this);
    	while (moves.size() <= iteration && !this.getSuccessor(option, iteration, seed).isEmpty())
    	{
    		int r = rng.nextInt(succs.size());
    		curr = succs.get(r);
    		moves.add(curr);
    		if (curr.findCost(option, iteration, seed) != 0)
    		{
    			succs = curr.getSuccessor(option, iteration, seed);
    		}
    		else
    		{
    			break;
    		}
    		
    	}
    	
    	return moves;
    }
    
    // get all the successors that has a lower heuristic cost than the current state
    public ArrayList<State> getAllLowerSuccessor(int option, int iteration, int seed)
    {
    	ArrayList<State> allSuccs = new ArrayList<>();
    	ArrayList<State> lowerSuccs = new ArrayList<>();
    	char[] possible = new char[] {'0','1','2','3','4','5','6','7'};
    	int leastCost = this.findCost(option, iteration, seed);
    	
    	for (int i = 0; i < this.board.length; i++)
    	{
    		for (int j = 0; j < possible.length; j++)
    		{
    			
    			State temp = new State(this.board);
    			
    			if (temp.board[i] != possible[j])
    			{
    				temp.board[i] = possible[j];
    				allSuccs.add(temp);
    			}
    		}
    	}
    	
    	for (int j = 0; j < allSuccs.size(); j++)
    	{
    		if (allSuccs.get(j).findCost(option, iteration, seed) < leastCost)
    		{
    			lowerSuccs.add(allSuccs.get(j));
    		}
    	}
    	
    	
    	return lowerSuccs;
    	
    }
    
    // perform the first choice hill climb algorithm to solve the problem
    public ArrayList<State> fcHillClimb(int option, int iteration, int seed) {
    	
    	ArrayList<State> moves = new ArrayList<>();
    	ArrayList<State> succs = this.getAllLowerSuccessor(option, iteration, seed);
    	State curr = this;
    	int cost = this.findCost(option, iteration, seed);

    	moves.add(curr);
    	
    	while (moves.size() <= iteration && moves.get(moves.size()-1).findCost(option, iteration, seed) != 0 && !succs.isEmpty())
    	{
    		curr = succs.get(0);
			cost = succs.get(0).findCost(option, iteration, seed);
			moves.add(curr);
    		succs = curr.getAllLowerSuccessor(option, iteration, seed);
    	}
    	
    	return moves;
    }
    
    public ArrayList<State> SAalgo(int option, int iteration, int seed) {
    	
    	ArrayList<State> moves = new ArrayList<>();
    	State curr = this;
    	
    	Random rng = new Random();
    	if (seed != -1)
    	{
    		rng.setSeed(seed);
    	}
    	
    	moves.add(curr);
    	double T = 100.0;
    	double alpha = 0.95;
    	
    	while (moves.size() <= iteration && moves.get(moves.size()-1).findCost(option, iteration, seed) != 0)
    	{
    		int index = rng.nextInt(7);
    		int value = rng.nextInt(7);
    		double prob = rng.nextDouble();
    		
    		State temp = new State(curr.board);
    		temp.board[index] = (char) (value + 48);
    		
//    		moves.add(temp);
//    		curr = temp;
    		
    		if (temp.findCost(option, iteration, seed) < curr.findCost(option, iteration, seed))
    		{
    			moves.add(temp);
    			curr = temp;
    		}
    		else if (prob < T)
    		{
    			moves.add(temp);
    			curr = temp;
    		}
    		else
    		{
    			temp = curr;
    		}
    		T *= alpha;
    	}
    	
    	
    	return moves;
    }
    
    // helper method to print out what's inside the board of the current state
    public void printArray(State successor) {
    	
    	String output = "";
    	for (int i = 0; i < successor.board.length; i++){
    		output += successor.board[i];
    	}
    	System.out.print(output);
    }
    
    
    public void printState(int option, int iteration, int seed) {

        // find the heuristic cost
    	if (option == 1 && iteration == 0 && seed == -1)
    	{
    		int heuCost = this.findCost(option, iteration, seed);
    		System.out.println(heuCost);
    	}
    	// find the optimal successors
    	if (option == 2 && iteration == 0 && seed == -1)
    	{
    		ArrayList<State> succs = this.getSuccessor(option, iteration, seed);
    		
    		for (int i = 0; i < succs.size(); i++)
    		{
    			this.printArray(succs.get(i));
    			System.out.println("");
    		}
    		
    		if (!succs.isEmpty())
    		{
    			System.out.println(succs.get(0).findCost(option, iteration, seed));
    		}
    	}
    	// use hill climbing algorithm
    	if (option == 3)
    	{
    		ArrayList<State> moves = this.hillClimb(option, seed, iteration);
    		
    		for (int i = 0; i < moves.size(); i++)
    		{
    			System.out.print(i + ":");
    			printArray(moves.get(i));
    			System.out.println(" " + moves.get(i).findCost(option, iteration, seed));
    			
    		}
    		
    		if (moves.get(moves.size() - 1).findCost(option, iteration, seed) == 0)
    		{
    			System.out.println("Solved");
    		}
    		
    	}
    	// use first choice hill climbinb algorithm
    	if (option == 4)
    	{
    		ArrayList<State> fcmoves = this.fcHillClimb(option, iteration, seed);
    		
    		for (int i = 0; i < fcmoves.size(); i++)
    		{
    			System.out.print(i + ":");
    			printArray(fcmoves.get(i));
    			System.out.println(" " + fcmoves.get(i).findCost(option, iteration, seed));
    		}
    		if (fcmoves.get(fcmoves.size() - 1).findCost(option, iteration, seed) == 0)
    		{
    			System.out.println("Solved");
    		}
    		else if (fcmoves.get(fcmoves.size() - 1).getAllLowerSuccessor(option, iteration, seed).isEmpty())
    		{
    			System.out.println("Local optimum");
    		}
    	}
    	
    	if (option == 5)
    	{
    		ArrayList<State> SAmoves = this.SAalgo(option, iteration, seed);
    		
    		for (int i = 0; i < SAmoves.size(); i++)
    		{
    			System.out.print(i + ":");
    			printArray(SAmoves.get(i));
    			System.out.println(" " + SAmoves.get(i).findCost(option, iteration, seed));
    		}
    		if (SAmoves.get(SAmoves.size() - 1).findCost(option, iteration, seed) == 0)
    		{
    			System.out.println("Solved");
    		}
    	}

    }

    // TO DO: feel free to add/remove/modify methods/fields
    
}

public class EightQueen {
    public static void main(String args[]) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        int flag = Integer.valueOf(args[0]);
        int option = flag / 100;
        int iteration = flag % 100;
        char[] board = new char[8];
        int seed = -1;
        int board_index = -1;

        if (args.length == 2 && (option == 1 || option == 2 || option == 4)) {
            board_index = 1;
        } else if (args.length == 3 && (option == 3 || option == 5)) {
            seed = Integer.valueOf(args[1]);
            board_index = 2;
        } else {
            System.out.println("Invalid Number of Input Arguments");
            return;
        }

        if (board_index == -1) return;
        for (int i = 0; i < 8; i++) {
            board[i] = args[board_index].charAt(i);
            int pos = board[i] - '0';
            if (pos < 0 || pos > 7) {
                System.out.println("Invalid input: queen position(s)");
                return;
            }
        }

        State init = new State(board);
        init.printState(option, iteration, seed);
    }
}
