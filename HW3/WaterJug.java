import java.util.*;

class State {
	int cap_jug1;
	int cap_jug2;
	int curr_jug1;
	int curr_jug2;
	int goal;
	int depth;
	State parentPt;

	public State(int cap_jug1, int cap_jug2, int curr_jug1, int curr_jug2, int goal, int depth) {
		this.cap_jug1 = cap_jug1;
		this.cap_jug2 = cap_jug2;
		this.curr_jug1 = curr_jug1;
		this.curr_jug2 = curr_jug2;
		this.goal = goal;
		this.depth = depth;
		this.parentPt = null;
	}

	public ArrayList<State> getSuccessors() {

		// TO DO: get all successors and return them in proper order

		ArrayList<State> successors = new ArrayList<>();

		State temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);

		// empty jug 1
		if (this.curr_jug1 != 0) 
		{
			temp.curr_jug1 = 0;
			temp.parentPt = this;
			temp.depth = this.depth + 1;
			successors.add(temp);
			temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
		}

		// pour jug 1 to jug 2
		if (this.curr_jug2 < this.cap_jug2 && this.curr_jug1 != 0) 
		{
			int cap_left = this.cap_jug2 - this.curr_jug2;
			if (this.curr_jug1 >= cap_left) 
			{
				temp.curr_jug1 -= cap_left;
				temp.curr_jug2 = temp.cap_jug2;
				temp.parentPt = this;
				temp.depth = this.depth + 1;
				successors.add(temp);
				temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
			} 
			else 
			{
				temp.curr_jug2 += temp.curr_jug1;
				temp.curr_jug1 = 0;
				temp.parentPt = this;
				temp.depth = this.depth + 1;
				successors.add(temp);
				temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
			}
		}

		// empty jug 2
		if (this.curr_jug2 != 0) 
		{
			temp.curr_jug2 = 0;
			temp.parentPt = this;
			temp.depth = this.depth + 1;
			successors.add(temp);
			temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
		}

		// fill jug 2
		if (this.curr_jug2 != this.cap_jug2) 
		{
			temp.curr_jug2 = temp.cap_jug2;
			temp.parentPt = this;
			temp.depth = this.depth + 1;
			successors.add(temp);
			temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
		}

		// pour jug 2 to jug 1
		if (this.curr_jug1 < this.cap_jug1 && this.curr_jug2 != 0) 
		{
			int cap_left = this.cap_jug1 - this.curr_jug1;
			if (this.curr_jug2 >= cap_left) 
			{
				temp.curr_jug2 -= cap_left;
				temp.curr_jug1 = temp.cap_jug1;
				temp.parentPt = this;
				temp.depth = this.depth + 1;
				successors.add(temp);
				temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
			} 
			else 
			{
				temp.curr_jug1 += temp.curr_jug2;
				temp.curr_jug2 = 0;
				temp.parentPt = this;
				temp.depth = this.depth + 1;
				successors.add(temp);
				temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
			}
		}

		// fill jug 1
		if (this.curr_jug1 != this.cap_jug1) {
			temp.curr_jug1 = temp.cap_jug1;
			temp.parentPt = this;
			temp.depth = this.depth + 1;
			successors.add(temp);
			temp = new State(this.cap_jug1, this.cap_jug2, this.curr_jug1, this.curr_jug2, this.goal, this.depth);
		}

		return successors;
	}

	public boolean isGoalState() {

		// TO DO: determine if the state is a goal node or not and return
		
		boolean isGoal = false;

		if (this.curr_jug1 == this.goal || this.curr_jug2 == this.goal) 
		{
			isGoal = true;
		}
		return isGoal;
	}

	public void printState(int option, int depth) {

		// TO DO: print a State based on option (flag)
		
		// get successors
		if (option == 1 && depth == 0) 
		{

			ArrayList<State> succs = getSuccessors();

			for (int i = 0; i < succs.size(); i++) 
			{
				System.out.println(succs.get(i).getOrderedPair());
			}
		} 
		// get successors + boolean if its goal state
		else if (option == 2 && depth == 0) 
		{
			ArrayList<State> succs = getSuccessors();

			for (int i = 0; i < succs.size(); i++) {
				System.out.println(succs.get(i).getOrderedPair() + " " + succs.get(i).isGoalState());
			}
		} 
		// do bfs
		else if (option == 3 && depth == 0) 
		{
			UninformedSearch temp = new UninformedSearch();
			temp.bfs(this);
		} 
		// do dfs
		else if (option == 4 && depth == 0) 
		{
			UninformedSearch temp = new UninformedSearch();
			temp.dfs(this);
		} 
		// do iddfs
		else if (option == 5) 
		{
			UninformedSearch temp = new UninformedSearch();
			temp.iddfs(this, depth);
		}

	}

	public String getOrderedPair() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.curr_jug1);
		builder.append(this.curr_jug2);
		return builder.toString().trim();
	}

	// TO DO: feel free to add/remove/modify methods/fields

}

class UninformedSearch {

	// helper method to print the contents inside the open and closed queues
	public static String string(Queue<State> a) {
		String s = "[";
		for (State b : a) 
		{
			s += b.getOrderedPair();
			s += ",";
		}
		s = s.substring(0, s.length() - 1);
		s += "]";
		return s;
	}

	// another helper method to print the contents inside the open and closed stacks
	public static String string(Stack<State> a) {
		String s = "[";
		for (State b : a) 
		{
			s += b.getOrderedPair();
			s += ",";
		}
		if (!a.isEmpty()) 
		{
			s = s.substring(0, s.length() - 1);
		}
		s += "]";
		return s;
	}

	public static void bfs(State curr_state) {
		// TO DO: run breadth-first search algorithm
		Queue<State> todo = new LinkedList<>();
		Queue<State> done = new LinkedList<>();

		todo.offer(curr_state);

		System.out.println(curr_state.getOrderedPair());
		State s = null;

		while (!todo.isEmpty()) 
		{

			s = todo.poll();

			if (s.isGoalState())
			{
				System.out.println(s.getOrderedPair() + " Goal");

				String path = "";
				while (s.parentPt != null)
				{
					path = s.getOrderedPair() + " " + path;
					s = s.parentPt;
				}
				path = "Path " + curr_state.getOrderedPair() + " " + path;
				System.out.println(path);
				return;
			} 
			else 
			{
				ArrayList<State> t = s.getSuccessors();

				for (int i = 0; i < t.size(); i++) 
				{
					if (done.isEmpty()) 
					{
						todo.offer(t.get(i));
					} 
					else 
					{
						boolean contains = false;
						for (State b : done) 
						{
							if (t.get(i).getOrderedPair().equals(b.getOrderedPair()))
							{
								contains = true;
							}
						}
						for (State b : todo) 
						{
							if (t.get(i).getOrderedPair().equals(b.getOrderedPair())) 
							{
								contains = true;
							}
						}
						if (!contains) 
						{
							todo.offer(t.get(i));
						}
					}
				}
				done.offer(s);
				System.out.println(s.getOrderedPair() + " " + string(todo) + " " + string(done));
			}
		}

	}

	public static void dfs(State curr_state) 
	{
		// TO DO: run depth-first search algorithm
		Stack<State> todo = new Stack<>();
		Stack<State> done = new Stack<>();

		todo.push(curr_state);

		System.out.println(curr_state.getOrderedPair());
		State s = null;

		while (!todo.isEmpty()) 
		{

			s = todo.pop();

			if (s.isGoalState()) 
			{
				System.out.println(s.getOrderedPair() + " Goal");

				String path = "";
				while (s.parentPt != null) 
				{
					path = s.getOrderedPair() + " " + path;
					s = s.parentPt;
				}
				path = "Path " + curr_state.getOrderedPair() + " " + path;
				System.out.println(path);
				return;
			} 
			else 
			{
				ArrayList<State> t = s.getSuccessors();

				for (int i = 0; i < t.size(); i++) 
				{
					if (done.isEmpty()) 
					{
						todo.push(t.get(i));
					} 
					else 
					{
						boolean contains = false;
						for (State b : done) 
						{
							if (t.get(i).getOrderedPair().equals(b.getOrderedPair())) 
							{
								contains = true;
							}
						}
						for (State b : todo) 
						{
							if (t.get(i).getOrderedPair().equals(b.getOrderedPair())) 
							{
								contains = true;
							}
						}
						if (!contains) 
						{
							todo.push(t.get(i));
						}
					}
				}
				done.push(s);
				System.out.println(s.getOrderedPair() + " " + string(todo) + " " + string(done));
			}
		}

	}

	// helper method for iddfs
	public static boolean dls(State curr_state, int limit) {

		Stack<State> todo = new Stack<>();
		Stack<State> done = new Stack<>();

		State s = curr_state;

		todo.push(s);

		System.out.println(limit + ":" + curr_state.getOrderedPair());

		while (!todo.isEmpty()) 
		{

			s = todo.pop();
			// System.out.println("s.depth:"+s.depth);
			if (s.depth <= limit) 
			{

				if (s.isGoalState()) 
				{
					System.out.println(s.getOrderedPair() + " Goal");

					String path = "";
					while (s.parentPt != null) 
					{
						path = s.getOrderedPair() + " " + path;
						s = s.parentPt;
					}
					path = "Path " + curr_state.getOrderedPair() + " " + path;
					System.out.println(path);
					return true;
				} 
				else 
				{
					done.push(s);

					ArrayList<State> t = s.getSuccessors();

					for (int i = 0; i < t.size(); i++) {
						if (done.isEmpty()) 
						{
							if (t.get(i).depth <= limit)
							{
								todo.push(t.get(i));
							}
								
						} 
						else 
						{
							boolean contains = false;
							for (State b : done) 
							{
								if (t.get(i).getOrderedPair().equals(b.getOrderedPair())) 
								{
									contains = true;
								}
							}
							for (State b : todo) 
							{
								if (t.get(i).getOrderedPair().equals(b.getOrderedPair())) 
								{
									contains = true;
								}
							}
							if (!contains) 
							{
								if (t.get(i).depth <= limit)
								{
									todo.push(t.get(i));
								}
									
							}
						}
					}
				}
				System.out.println(limit + ":" + s.getOrderedPair() + " " + string(todo) + " " + string(done));

			}

		}
		return false;

	}

	public static void iddfs(State curr_state, int depth) {
		// TO DO : run IDDFS search algorithm
		for (int i = 0; i <= depth; i++) 
		{
			if (dls(curr_state, i)) 
			{
				break;
			}
		}
	}
}

public class WaterJug {
	public static void main(String args[]) {
		if (args.length != 6) {
			System.out.println("Invalid Number of Input Arguments");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		int cap_jug1 = Integer.valueOf(args[1]);
		int cap_jug2 = Integer.valueOf(args[2]);
		int curr_jug1 = Integer.valueOf(args[3]);
		int curr_jug2 = Integer.valueOf(args[4]);
		int goal = Integer.valueOf(args[5]);

		int option = flag / 100;
		int depth = flag % 100;

		if (option < 1 || option > 5) {
			System.out.println("Invalid flag input");
			return;
		}
		if (cap_jug1 > 9 || cap_jug2 > 9 || curr_jug1 > 9 || curr_jug2 > 9) {
			System.out.println("Invalid input: 2-digit jug volumes");
			return;
		}
		if (cap_jug1 < 0 || cap_jug2 < 0 || curr_jug1 < 0 || curr_jug2 < 0) {
			System.out.println("Invalid input: negative jug volumes");
			return;
		}
		if (cap_jug1 < curr_jug1 || cap_jug2 < curr_jug2) {
			System.out.println("Invalid input: jug volume exceeds its capacity");
			return;
		}
		State init = new State(cap_jug1, cap_jug2, curr_jug1, curr_jug2, goal, 0);
		init.printState(option, depth);
	}
}
