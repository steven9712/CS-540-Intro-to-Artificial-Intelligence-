
import java.util.*;
import java.io.*;



class Tuple {
	int index;
	double left;
	double right;
	
	public Tuple(int i, double l, double r, int n){
		this.index = i;
		this.left =  l;
		this.right = r;
	}
}

public class Chatbot{
	public static int v = 4700;
    private static String filename = "./corpus.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    
    public static int[] buildUnigram(ArrayList<Integer> corpus) {
		int[] counter = new int[v];
		for(int i = 0; i < corpus.size(); i++) {
			int curr = corpus.get(i);
			counter[curr]++;
		}
		return counter;
	}
    
    public static int[] buildBigram(ArrayList<Integer> corpus, int h){
    	int[] counter = new int[v];
    	for (int i = 0; i < corpus.size() - 1; i ++){
    		int curr = corpus.get(i);
    		int next = corpus.get(i + 1);
    		if (curr == h){
    			counter[next]++;
    		}
    	}
    	return counter;
    }
    
    public static int[] buildTrigramAndArrayList(ArrayList<Integer> corpus, int h1, int h2, ArrayList<Integer> words_after_h1h2){
    	int[] counter = new int[v];
    	
    	for (int i = 0; i < corpus.size() - 2; i++){
    		int curr = corpus.get(i);
    		int next1 = corpus.get(i + 1);
    		int next2 = corpus.get(i + 2);
    		
    		if (curr == h1 && next1 == h2){
    			words_after_h1h2.add(corpus.get(i + 2));
    			counter[next2]++;
    		}
    	}
    	return counter;
    }
    
    public static int[] buildTrigram(ArrayList<Integer> corpus, int h1, int h2){
    	int[] counter = new int[v];
    	
    	for (int i = 0; i < corpus.size() - 2; i++){
    		int curr = corpus.get(i);
    		int next1 = corpus.get(i + 1);
    		int next2 = corpus.get(i + 2);
    		
    		if (curr == h1 && next1 == h2){
    			counter[next2]++;
    		}
    	}
    	return counter;
    }
    
    public static int getCount(int[] counter, int word){
    	return counter[word];
    }
    
    // something wrong here
    public static Tuple getTuple(int[] counter, double r){
    	int total = 0;
    	double left = 0;
    	double right = 0;
    	for (int i = 0; i < counter.length; i++){
    		total += counter[i];
    	}
    	
    	
    	double sum = 0;
    	
    	for (int i = 0; i < counter.length; i++){
    		left = (double) sum;
    		right =  sum + (counter[i] + 1) / (double) (total + v);
    		sum = right;
   
    		if (r == 0){
    			if ((left <= r) && (right >= r)){
    				return new Tuple(i, left, right, total);
    			}
    		}
    		else{
    			if((left < r) && (right >= r)){
    				return new Tuple(i, left, right, total);
    			}
    		}
    	}
    	return null;
    }
    

    
    
    public static double generateProbability(ArrayList<Integer> corpus, int count){
    	
    	double prob = (count + 1) / (double) (corpus.size() + v);
    	
    	return prob;
    }
    
    public static void randomSampling(double r, double v, ArrayList<Integer> corpus){
    	double sum = 0;
    	double prob = 0;
    	int i = 0;
    	for (i = 0; sum < r; i++){
    		int count = 0;
    		
    		count = getCount(corpus, i);
    		
    		prob = generateProbability(corpus, count);
    	
    		sum = sum + prob;
    	}
    	
    	System.out.println(i - 1);
    	System.out.printf("%.7f\n", sum - prob);
    	System.out.printf("%.7f\n", sum);
    	
    	
    }
    
    public static int getCount(ArrayList<Integer> corpus, int word){
    	int count = 0;
    	for (int i : corpus) {
        	if (word == i) {
        		count++;
        	}
        }
    	
    	return count;
    }
    
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		int v = 2700;
        
		if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int[] counter = buildUnigram(corpus);
			int count = getCount(counter, w);
			
			System.out.println(count);
			double prob = generateProbability(corpus, count);
			
			System.out.printf("%.7f\n", prob);

		}
            
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //TODO generate
            
            double r = (double) n1 / n2;
            
            randomSampling(r, v, corpus);

        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //TODO
        	
        	for (int i = 0; i < corpus.size() - 1; i++){
        		if (corpus.get(i) == h){
        			words_after_h.add(h + 1);
        		}
        		if (corpus.get(i) == h && corpus.get(i + 1) == w){
        			count++;
        		}
        	}
        	
        	System.out.println(count);
        	System.out.println(words_after_h.size());
        	double prob = generateProbability(words_after_h, count);
        	System.out.printf("%.7f", prob);
            
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //TODO
            double r = (double) n1 / n2;
            int[] counter = buildBigram(corpus, h);
            Tuple tuple = getTuple(counter, r);
            
            System.out.println(tuple.index);
            System.out.printf("%.7f\n", tuple.left);
            System.out.printf("%.7f\n", tuple.right);
            
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //TODO
            
            int[] counter = buildTrigramAndArrayList(corpus, h1, h2, words_after_h1h2);
            count = getCount(counter, w);
            
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            double prob = generateProbability(words_after_h1h2, count);
            System.out.printf("%.7f\n", prob);
			
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //TODO
            double r = (double) n1 / n2;
            int[] counter = buildTrigram(corpus, h1, h2);
            Tuple tuple = getTuple(counter, r);
            
            if (tuple == null){
            	System.out.println("undefined");
            }
            else{
            	System.out.println(tuple.index);
                System.out.printf("%.7f\n", tuple.left);
                System.out.printf("%.7f\n", tuple.right);
            }
            
            
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                
                int[] counter = buildUnigram(corpus);
                Tuple tuple = getTuple(counter, r);
                h1 = tuple.index;
                
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                // TODO Generate second word using r
                r = rng.nextDouble();
                counter = buildBigram(corpus, h1);
                tuple = getTuple(counter, r);
                h2 = tuple.index;
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                // TODO Generate second word using r
                double r = rng.nextDouble();
                
                int[] counter = buildBigram(corpus, h1);
                Tuple tuple = getTuple(counter, r);
                h2 = tuple.index;
                
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // TODO Generate new word using h1,h2
                int[] counter = buildTrigram(corpus, h1, h2);
                Tuple tuple = getTuple(counter, r);
                if (tuple == null)
                {
                	counter = buildBigram(corpus, h2);
                	tuple = getTuple(counter, r);
                }
                w = tuple.index;
                
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}
