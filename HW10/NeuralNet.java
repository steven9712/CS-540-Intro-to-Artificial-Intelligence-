import java.io.*;
import java.util.*;

class Data
{
	double x1;
	double x2;
	double x3;
	double x4;
	int label;
	
	public Data(double variance, double skewness, double curtosis, double entropy, int label)
	{
		this.x1 = variance;
		this.x2 = skewness;
		this.x3 = curtosis;
		this.x4 = entropy;
		this.label = label;
	}
}

public class NeuralNet {
	
	public static ArrayList<Data> readData(String filename)
	{
		BufferedReader br = null;
		ArrayList<Data> data = new ArrayList<Data>();
		
		try {
			br = new BufferedReader(new FileReader(filename));
			String line = "";
			
			while ((line = br.readLine()) != null)
			{
				String[] info = line.split(",");
				
				if(info.length > 0)
				{
					Data curr = new Data(Double.parseDouble(info[0])
							,Double.parseDouble(info[1])
							,Double.parseDouble(info[2])
							,Double.parseDouble(info[3])
							,Integer.parseInt(info[4]));
					
					data.add(curr);
				}
			}
		}
		catch (IOException ee)
		{
			System.out.println("BufferedREader error");
		}
		return data;
	}
	
	public static void forwardPropagation(String[] args)
	{
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);

		double x1 = Double.valueOf(args[14]);
		double x2 = Double.valueOf(args[15]);
		double x3 = Double.valueOf(args[16]);
		double x4 = Double.valueOf(args[17]);
		

		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
		a21 = 1 / (1 + Math.exp(-a21));
		
		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
		a22 = 1 / (1 + Math.exp(-a22));
		
		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
		a31 = 1 / (1 + Math.exp(-a31));
		
		System.out.printf("%.5f ", a21);
		System.out.printf("%.5f\n", a22);
		System.out.printf("%.5f\n", a31);
	}
	
	public static void backPropagation(String[] args)
	{
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);

		double x1 = Double.valueOf(args[14]);
		double x2 = Double.valueOf(args[15]);
		double x3 = Double.valueOf(args[16]);
		double x4 = Double.valueOf(args[17]);
		double y = Double.valueOf(args[18]);
		
		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
		a21 = 1 / (1 + Math.exp(-a21));
		
		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
		a22 = 1 / (1 + Math.exp(-a22));
		
		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
		a31 = 1 / (1 + Math.exp(-a31));
		
		double d31 = (a31 - y) * a31 * (1 - a31);
		
		System.out.printf("%.5f\n", d31);
	}
	
	public static void partialDerivative(String[] args)
	{
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);

		double x1 = Double.valueOf(args[14]);
		double x2 = Double.valueOf(args[15]);
		double x3 = Double.valueOf(args[16]);
		double x4 = Double.valueOf(args[17]);
		double y = Double.valueOf(args[18]);
		
		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
		a21 = 1 / (1 + Math.exp(-a21));
		
		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
		a22 = 1 / (1 + Math.exp(-a22));
		
		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
		a31 = 1 / (1 + Math.exp(-a31));
		double d31 = (a31 - y) * a31 * (1 - a31);
		
		double d21 = d31 * w311 * a21 * (1 - a21);
		double d22 = d31 * w312 * a22 * (1 - a22);
		
		System.out.printf("%.5f ", d21);
		System.out.printf("%.5f", d22);
	}
	
	public static void gradientOfError(String[] args)
	{
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);

		double x1 = Double.valueOf(args[14]);
		double x2 = Double.valueOf(args[15]);
		double x3 = Double.valueOf(args[16]);
		double x4 = Double.valueOf(args[17]);
		double y = Double.valueOf(args[18]);
		
		double a20 = 1;
		
		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
		a21 = 1 / (1 + Math.exp(-a21));
		
		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
		a22 = 1 / (1 + Math.exp(-a22));
		
		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
		a31 = 1 / (1 + Math.exp(-a31));
		double d31 = (a31 - y) * a31 * (1 - a31);
		
		double d21 = d31 * w311 * a21 * (1 - a21);
		double d22 = d31 * w312 * a22 * (1 - a22);
		
		double DERw310 = d31 * a20;
		double DERw311 = d31 * a21;
		double DERw312 = d31 * a22;
		
		double DERw210 = d21 * 1;
		double DERw211 = d21 * x1;
		double DERw212 = d21 * x2;
		double DERw213 = d21 * x3;
		double DERw214 = d21 * x4;
		
		double DERw220 = d22 * 1;
		double DERw221 = d22 * x1;
		double DERw222 = d22 * x2;
		double DERw223 = d22 * x3;
		double DERw224 = d22 * x4;
		
		System.out.printf("%.5f ", DERw310);
		System.out.printf("%.5f ", DERw311);
		System.out.printf("%.5f\n", DERw312);
		
		System.out.printf("%.5f ", DERw210);
		System.out.printf("%.5f ", DERw211);
		System.out.printf("%.5f ", DERw212);
		System.out.printf("%.5f ", DERw213);
		System.out.printf("%.5f\n", DERw214);
		
		System.out.printf("%.5f ", DERw220);
		System.out.printf("%.5f ", DERw221);
		System.out.printf("%.5f ", DERw222);
		System.out.printf("%.5f ", DERw223);
		System.out.printf("%.5f\n", DERw224);
		
	}
	
	public static void stochastic(String[] args, ArrayList<Data> train, ArrayList<Data> eval)
	{
		
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);
		double n = Double.valueOf(args[14]);
		
		for (int i = 0; i < train.size(); i++)
		{
			double x1 = train.get(i).x1;
    		double x2 = train.get(i).x2;
    		double x3 = train.get(i).x3;
    		double x4 = train.get(i).x4;
    		double y = train.get(i).label;
    		
    		double a20 = 1;
    		
    		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
    		a21 = 1 / (1 + Math.exp(-a21));
    		
    		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
    		a22 = 1 / (1 + Math.exp(-a22));
    		
    		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
    		a31 = 1 / (1 + Math.exp(-a31));
    		double d31 = (a31 - y) * a31 * (1 - a31);
    		
    		double d21 = d31 * w311 * a21 * (1 - a21);
    		double d22 = d31 * w312 * a22 * (1 - a22);
    		
    		double DERw310 = d31 * a20;
    		double DERw311 = d31 * a21;
    		double DERw312 = d31 * a22;
    		
    		double DERw210 = d21 * 1;
    		double DERw211 = d21 * x1;
    		double DERw212 = d21 * x2;
    		double DERw213 = d21 * x3;
    		double DERw214 = d21 * x4;
    		
    		double DERw220 = d22 * 1;
    		double DERw221 = d22 * x1;
    		double DERw222 = d22 * x2;
    		double DERw223 = d22 * x3;
    		double DERw224 = d22 * x4;
    		
    		w210 = w210 - n * DERw210;
    		w211 = w211 - n * DERw211;
    		w212 = w212 - n * DERw212;
    		w213 = w213 - n * DERw213;
    		w214 = w214 - n * DERw214;
    		
    		w220 = w220 - n * DERw220;
    		w221 = w221 - n * DERw221;
    		w222 = w222 - n * DERw222;
    		w223 = w223 - n * DERw223;
    		w224 = w224 - n * DERw224;
    		
    		w310 = w310 - n * DERw310;
    		w311 = w311 - n * DERw311;
    		w312 = w312 - n * DERw312;
    		
    		double Eeval = 0;
    		
    		for (int j = 0; j < eval.size(); j++)
    		{
    			double evalx1 = eval.get(j).x1;
        		double evalx2 = eval.get(j).x2;
        		double evalx3 = eval.get(j).x3;
        		double evalx4 = eval.get(j).x4;
        		double evalY = eval.get(j).label;
        		
        		double NEWa21 = 1 * w210 + evalx1 * w211 + evalx2 * w212 
        				+ evalx3 * w213 + evalx4 * w214;
        		
        		NEWa21 = 1 / (1 + Math.exp(-NEWa21));
        		
        		double NEWa22 = 1 * w220 + evalx1 * w221 + evalx2 * w222 
        				+ evalx3 * w223 + evalx4 * w224;
        		
        		NEWa22 = 1 / (1 + Math.exp(-NEWa22));
        		
        		double NEWa23 = 1 * w310 + NEWa21 * w311 + NEWa22 * w312;
        		NEWa23 = 1 / (1 + Math.exp(-NEWa23));
        		
        		Eeval += 0.5 * Math.pow((NEWa23 - evalY), 2);
    		}
    		
    		
    		
    		System.out.printf("%.5f ", w210);
    		System.out.printf("%.5f ", w211);
    		System.out.printf("%.5f ", w212);
    		System.out.printf("%.5f ", w213);
    		System.out.printf("%.5f ", w214);
    		
    		System.out.printf("%.5f ", w220);
    		System.out.printf("%.5f ", w221);
    		System.out.printf("%.5f ", w222);
    		System.out.printf("%.5f ", w223);
    		System.out.printf("%.5f ", w224);
    		
    		System.out.printf("%.5f ", w310);
    		System.out.printf("%.5f ", w311);
    		System.out.printf("%.5f\n", w312);
    		
    		System.out.printf("%.5f\n", Eeval);
		}
	}
	
	public static void makePrediction(String[] args, ArrayList<Data> train, ArrayList<Data> test)
	{
		double w210 = Double.valueOf(args[1]);
		double w211 = Double.valueOf(args[2]);
		double w212 = Double.valueOf(args[3]);
		double w213 = Double.valueOf(args[4]);
		double w214 = Double.valueOf(args[5]);
		
		double w220 = Double.valueOf(args[6]);
		double w221 = Double.valueOf(args[7]);
		double w222 = Double.valueOf(args[8]);
		double w223 = Double.valueOf(args[9]);
		double w224 = Double.valueOf(args[10]);
		
		double w310 = Double.valueOf(args[11]);
		double w311 = Double.valueOf(args[12]);
		double w312 = Double.valueOf(args[13]);
		double n = Double.valueOf(args[14]);
		
		for (int i = 0; i < train.size(); i++) {
			
    		
    		double x1 = train.get(i).x1;
    		double x2 = train.get(i).x2;
    		double x3 = train.get(i).x3;
    		double x4 = train.get(i).x4;
    		double y = train.get(i).label;
    		
    		double a20 = 1;
    		
    		double a21 = 1 * w210 + x1 * w211 + x2 * w212 + x3 * w213 + x4 * w214;
    		a21 = 1 / (1 + Math.exp(-a21));
    		
    		double a22 = 1 * w220 + x1 * w221 + x2 * w222 + x3 * w223 + x4 * w224;
    		a22 = 1 / (1 + Math.exp(-a22));
    		
    		double a31 = 1 * w310 + a21 * w311 + a22 * w312;
    		a31 = 1 / (1 + Math.exp(-a31));
    		double d31 = (a31 - y) * a31 * (1 - a31);
    		
    		double d21 = d31 * w311 * a21 * (1 - a21);
    		double d22 = d31 * w312 * a22 * (1 - a22);
    		
    		double DERw310 = d31 * a20;
    		double DERw311 = d31 * a21;
    		double DERw312 = d31 * a22;
    		
    		double DERw210 = d21 * 1;
    		double DERw211 = d21 * x1;
    		double DERw212 = d21 * x2;
    		double DERw213 = d21 * x3;
    		double DERw214 = d21 * x4;
    		
    		double DERw220 = d22 * 1;
    		double DERw221 = d22 * x1;
    		double DERw222 = d22 * x2;
    		double DERw223 = d22 * x3;
    		double DERw224 = d22 * x4;
    		
    		w210 = w210 - n * DERw210;
    		w211 = w211 - n * DERw211;
    		w212 = w212 - n * DERw212;
    		w213 = w213 - n * DERw213;
    		w214 = w214 - n * DERw214;
    		
    		w220 = w220 - n * DERw220;
    		w221 = w221 - n * DERw221;
    		w222 = w222 - n * DERw222;
    		w223 = w223 - n * DERw223;
    		w224 = w224 - n * DERw224;
    		
    		w310 = w310 - n * DERw310;
    		w311 = w311 - n * DERw311;
    		w312 = w312 - n * DERw312;
    		
		}
		
		int count = 0;
		
		for (int i = 0; i < test.size(); i++) {
			
			double testx1 = test.get(i).x1;
    		double testx2 = test.get(i).x2;
    		double testx3 = test.get(i).x3;
    		double testx4 = test.get(i).x4;
    		int testY = test.get(i).label;
    		
    		double NEWa21 = 1 * w210 + testx1 * w211 + testx2 * w212 
    				+ testx3 * w213 + testx4 * w214;
    		NEWa21 = 1 / (1 + Math.exp(-NEWa21));
    		
    		double NEWa22 = 1 * w220 + testx1 * w221 + testx2 * w222 
    				+ testx3 * w223 + testx4 * w224;
    		NEWa22 = 1 / (1 + Math.exp(-NEWa22));
    		
    		double NEWa31 = 1 * w310 + NEWa21 * w311 + NEWa22 * w312;
    		NEWa31 = 1 / (1 + Math.exp(-NEWa31));
    		
    		int prediction = 0;
    		if(NEWa31 > 0.5) {
    			prediction = 1;
    		}
    		
    		if (prediction == testY) {
    			count++;
    		}
    		
    		System.out.println(testY + " " + prediction + " "
    				+ String.format("%.5f", NEWa31));
		}
		
		double accuracy = count / (double) test.size();
		System.out.printf("%.2f", accuracy);
	}

	
	public static void main(String[] args)
	{
		String trainfile = "./train.csv";
		String evalfile = "./eval.csv";
		String testfile = "./test.csv";
		int flag = Integer.valueOf(args[0]);
		
		ArrayList<Data> trainData = readData(trainfile);
		ArrayList<Data> evalData = readData(evalfile);
		ArrayList<Data> testData = readData(testfile);
		
		if (flag == 100) {
			forwardPropagation(args);
		}
		
		else if (flag == 200) {
			backPropagation(args);
		}
		
		else if (flag == 300) {
			partialDerivative(args);
		}
		
		else if (flag == 400) {
			gradientOfError(args);
		}
		
		else if (flag == 500) {
			stochastic(args, trainData, evalData);
		}
		
		else if (flag == 600) {
			makePrediction(args, trainData, testData);
		}
	}

}
