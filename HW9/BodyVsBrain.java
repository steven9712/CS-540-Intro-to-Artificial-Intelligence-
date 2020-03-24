import java.util.*;
import java.io.*;

public class BodyVsBrain 
{
	
	static class Person
	{
		double body;
		double brain;
		
		public Person(double body, double brain)
		{
			this.body = body;
			this.brain = brain;
		}
	}
	
	public static ArrayList<Person> parseData(String data) throws FileNotFoundException, IOException{
		ArrayList<Person> records = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(data))){
			String line;
			int linenumb = 0;
			
			while ((line = br.readLine()) != null)
			{
				String[] values = line.split(",");
				
				if (linenumb != 0){
					Double body = Double.parseDouble(values[0]);
					Double brain = Double.parseDouble(values[1]);
					Person person = new Person(body, brain);
					
					records.add(person);
				}
				
				linenumb++;
			}
		}
		
		return records;
	}
	
	public static void computeStatistics(ArrayList<Person> records)
	{
		int length = records.size();
		
		double bodysum = 0, brainsum = 0, bodymean = 0, brainmean = 0, 
				bodySD = 0, brainSD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
			brainsum += records.get(i).brain;
		}
		
		bodymean = bodysum / length;
		brainmean = brainsum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			double brain = records.get(j).brain;
			double body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
			brainSD += Math.pow(brain - brainmean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		brainSD = Math.sqrt(brainSD / (length - 1));
		
		//print out the number of data points
		System.out.println(length);
		
		// print out body values
		System.out.printf("%.4f", bodymean);
		System.out.print(" ");
		System.out.printf("%.4f\n", bodySD);
		
		// print out brain values
		System.out.printf("%.4f", brainmean);
		System.out.print(" ");
		System.out.printf("%.4f\n", brainSD);
		
	}
	
	
	public static double linearRegression(ArrayList<Person> records, double b0, double b1)
	{
		double MSE = 0, body = 0, brain = 0, length = 0;
		
		for (int i = 0; i < records.size(); i++)
		{
			body = records.get(i).body;
			brain = records.get(i).brain;
			MSE += Math.pow(b0 + b1*body - brain, 2);
		}
		
		length = records.size();
		
		MSE = MSE / length;
		
		return MSE;
	}
	
	public static double gradient0(ArrayList<Person> records, double b0, double b1)
	{
		double MSE0 = 0, length = 0;
		length = records.size();
		
		for (int i = 0; i < length; i++)
		{
			double body = records.get(i).body;
			double brain = records.get(i).brain;
			MSE0 += b0 + b1 * body - brain;
		}
		MSE0 = (MSE0 * 2) / length;
		
		return MSE0;
	}
	
	public static double gradient1(ArrayList<Person> records, double b0, double b1)
	{
		double MSE1 = 0, length = 0;
		length = records.size();
		
		for (int i = 0; i < length; i++)
		{
			double body = records.get(i).body;
			double brain = records.get(i).brain;
			MSE1 += (b0 + b1 * body - brain) * body;
		}
		MSE1 = (MSE1 * 2) / length;
		
		return MSE1;
	}
	
	public static void gradientDescent(ArrayList<Person> records, double n, double T)
	{
		double b0 = 0, b1 = 0;
		double tmp0 = 0, tmp1 = 0;
		double MSE = 0;
		int i = 1;
		
		while (i <= T)
		{
			tmp0 = b0 - n * gradient0(records, b0, b1);
			tmp1 = b1 - n * gradient1(records, b0, b1);
			MSE = linearRegression(records, tmp0, tmp1);
			
			System.out.print(i + " ");
			System.out.printf("%.4f ", tmp0);
			System.out.printf("%.4f ", tmp1);
			System.out.printf("%.4f\n", MSE);
			
			b0 = tmp0;
			b1 = tmp1;
			i++;
		}
	}
	
	public static void closedForm(ArrayList<Person> records)
	{
		double b0 = 0, b1 = 0, bodymean = 0, brainmean = 0;
		double body = 0, brain = 0, top = 0, bot = 0, MSE = 0;
		double length = records.size();
		
		// get the mean
		for (int i = 0; i < length; i++)
		{
			body = records.get(i).body;
			brain = records.get(i).brain;
			
			bodymean += body;
			brainmean += brain;
		}
		
		bodymean = bodymean / length;
		brainmean = brainmean / length;
		
		for (int j = 0; j < length; j++)
		{
			body = records.get(j).body;
			brain = records.get(j).brain;
			top += (body - bodymean) * (brain - brainmean);
			bot += Math.pow(body - bodymean, 2);
		}
		
		b1 = top / bot;
		b0 = brainmean - b1 * bodymean;
		MSE = linearRegression(records, b0, b1);

		//printing output
		System.out.printf("%.4f ", b0);
		System.out.printf("%.4f ", b1);
		System.out.printf("%.4f\n", MSE);
	}
	
	public static double makePrediction(ArrayList<Person> records, double bodyWeight)
	{
		double b0 = 0, b1 = 0, bodymean = 0, brainmean = 0;
		double body = 0, brain = 0, top = 0, bot = 0, MSE = 0;
		double length = records.size();
		
		// get the mean
		for (int i = 0; i < length; i++)
		{
			body = records.get(i).body;
			brain = records.get(i).brain;
			
			bodymean += body;
			brainmean += brain;
		}
		
		bodymean = bodymean / length;
		brainmean = brainmean / length;
		
		for (int j = 0; j < length; j++)
		{
			body = records.get(j).body;
			brain = records.get(j).brain;
			top += (body - bodymean) * (brain - brainmean);
			bot += Math.pow(body - bodymean, 2);
		}
		
		b1 = top / bot;
		b0 = brainmean - b1 * bodymean;

		double prediction = 0;
		prediction = b0 + b1 * bodyWeight;
		
		return prediction;
		
	}
	
	public static void gradientDescentv2(ArrayList<Person> records, double n, double T)
	{
		double b0 = 0, b1 = 0;
		double tmp0 = 0, tmp1 = 0;
		double MSE = 0;
		int i = 1;
		
		while (i <= T)
		{
			tmp0 = b0 - n * gradient0v2(records, b0, b1);
			tmp1 = b1 - n * gradient1v2(records, b0, b1);
			MSE = linearRegressionv2(records, tmp0, tmp1);
			
			System.out.print(i + " ");
			System.out.printf("%.4f ", tmp0);
			System.out.printf("%.4f ", tmp1);
			System.out.printf("%.4f\n", MSE);
			
			b0 = tmp0;
			b1 = tmp1;
			i++;
		}
	}
	
	public static double gradient0v2(ArrayList<Person> records, double b0, double b1)
	{
		double MSE0 = 0, length = 0;
		length = records.size();
		
		double bodysum = 0, bodymean = 0, bodySD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
		}
		
		bodymean = bodysum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			double body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		
		
		for (int i = 0; i < length; i++)
		{
			double body = (records.get(i).body - bodymean) / bodySD;
			double brain = records.get(i).brain;
			MSE0 += b0 + b1 * body - brain;
		}
		MSE0 = (MSE0 * 2) / length;
		
		return MSE0;
	}
	
	public static double gradient1v2(ArrayList<Person> records, double b0, double b1)
	{
		double MSE1 = 0, length = 0;
		length = records.size();
		
		double bodysum = 0, bodymean = 0, bodySD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
		}
		
		bodymean = bodysum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			double body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		
		for (int i = 0; i < length; i++)
		{
			double body = (records.get(i).body - bodymean) / bodySD;
			double brain = records.get(i).brain;
			MSE1 += (b0 + b1 * body - brain) * body;
		}
		MSE1 = (MSE1 * 2) / length;
		
		return MSE1;
	}
	
	public static double linearRegressionv2(ArrayList<Person> records, double b0, double b1)
	{
		double MSE = 0, body = 0, brain = 0, length = 0;
		
		length = records.size();
		
		double bodysum = 0, brainsum = 0, bodymean = 0, brainmean = 0, 
				bodySD = 0, brainSD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
		}
		
		bodymean = bodysum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		
		// get MSE
		for (int i = 0; i < records.size(); i++)
		{
			body = (records.get(i).body - bodymean) / bodySD;
			brain = records.get(i).brain;
			MSE += Math.pow(b0 + b1*body - brain, 2);
		}
		
		length = records.size();
		
		MSE = MSE / length;
		
		return MSE;
	}
	
	public static void gradientDescentv3(ArrayList<Person> records, double n, double T)
	{
		double b0 = 0, b1 = 0;
		double tmp0 = 0, tmp1 = 0;
		double MSE = 0;
		int i = 1;
		Random rnd = new Random();
		int l = records.size();
		while (i <= T)
		{
			int random = rnd.nextInt(l);
			tmp0 = b0 - n * gradient0v3(records, b0, b1, random);
			tmp1 = b1 - n * gradient1v3(records, b0, b1, random);
			MSE = linearRegressionv2(records, tmp0, tmp1);
			
			System.out.print(i + " ");
			System.out.printf("%.4f ", tmp0);
			System.out.printf("%.4f ", tmp1);
			System.out.printf("%.4f\n", MSE);
			
			b0 = tmp0;
			b1 = tmp1;
			i++;
		}
	}
	
	public static double gradient0v3(ArrayList<Person> records, double b0, double b1, int random)
	{
		double MSE0 = 0, length = 0;
		length = records.size();
		
		double bodysum = 0, bodymean = 0, bodySD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
		}
		
		bodymean = bodysum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			double body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		
		
		
		double body = (records.get(random).body - bodymean) / bodySD;
		double brain = records.get(random).brain;
		MSE0 = 2 * (b0 + b1 * body - brain);
		
		
		return MSE0;
	}
	
	public static double gradient1v3(ArrayList<Person> records, double b0, double b1, int random)
	{
		double MSE1 = 0, length = 0;
		length = records.size();
		
		double bodysum = 0, bodymean = 0, bodySD = 0;
		
		// getting the mean
		for (int i = 0; i < records.size(); i++)
		{
			bodysum += records.get(i).body;
		}
		
		bodymean = bodysum / length;
		
		// getting the standard deviation
		for (int j = 0; j < records.size(); j++)
		{
			double body = records.get(j).body;
			bodySD += Math.pow(body - bodymean, 2);
		}
		bodySD = Math.sqrt(bodySD / (length - 1));
		
		
		
		double body = (records.get(random).body - bodymean) / bodySD;
		double brain = records.get(random).brain;
		MSE1 = 2 * (b0 + b1 * body - brain) * body;
		
		
		return MSE1;
	}
	

	
	static public void main(String[] args) throws FileNotFoundException, IOException
	{
		int flag = Integer.valueOf(args[0]);
		
		if (args.length > 3)
		{
			System.out.println("incorrect number of arguments");
		}
		
		String data = "./data.csv";
		
		ArrayList<Person> records = parseData(data);
		
//		for (int i = 0; i < records.size(); i++){
//			System.out.println("Body : " + records.get(i).body);
//			System.out.println("Brain : " + records.get(i).brain);
//		}
		
		if (flag == 100)
		{
			computeStatistics(records);
		}
		
		else if (flag == 200)
		{
			double b0 = Double.parseDouble(args[1]);
            double b1 = Double.parseDouble(args[2]);
            double MSE = linearRegression(records, b0, b1);
            System.out.printf("%.4f", MSE);
		}
		
		else if (flag == 300)
		{
			double b0 = Double.parseDouble(args[1]);
            double b1 = Double.parseDouble(args[2]);
            double mse0 = gradient0(records, b0, b1);
            double mse1 = gradient1(records, b0, b1);
            System.out.printf("%.4f\n", mse0);
            System.out.printf("%.4f\n", mse1);
		}
		else if (flag == 400)
		{
			double n = Double.parseDouble(args[1]);
            double T = Double.parseDouble(args[2]);
            
            gradientDescent(records, n, T);
		}
		
		else if (flag == 500)
		{
			closedForm(records);
		}
		
		else if (flag == 600)
		{
			double bodyWeight = Double.parseDouble(args[1]);
			double prediction = makePrediction(records, bodyWeight);
			System.out.printf("%.4f\n", prediction);
		}
		else if (flag == 700)
		{
			double n = Double.parseDouble(args[1]);
            double T = Double.parseDouble(args[2]);
            
            gradientDescentv2(records, n, T);
		}
		
		else if (flag == 800)
		{
			double n = Double.parseDouble(args[1]);
            double T = Double.parseDouble(args[2]);
            
            gradientDescentv3(records, n, T);
		}
		
	}
}
