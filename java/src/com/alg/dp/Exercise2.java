package com.alg.dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Exercise2
{
    String fileName;
    int[] weights;
    double[] values;
    int W;
    Knapsack ks;

    public Exercise2(String fileName) throws IOException
    {
        super();
        this.fileName = fileName;
        loadFile();
        ks = new Knapsack(weights, values);
        ks.solve(W);
    }
    
    public double optimalSolution()
    {
        return ks.optimalSolution(W);
    }
    
    public void loadFile() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        while ((line = reader.readLine()) != null)
        {
            lineNo += 1;
            String[] fields = line.split("\\s+");
            if (lineNo == 1) // First line
            {
                W = Integer.parseInt(fields[0]);
                int noPoints = Integer.parseInt(fields[1]);
                weights = new int[noPoints];
                values = new double[noPoints];
            }
            else
            {
                values[lineNo-2] = Double.parseDouble(fields[0]);
                weights[lineNo-2] = Integer.parseInt(fields[1]);
            }
        }
        reader.close();
    }
    
    public static void main(String[] args) throws Exception
    {
        Exercise2 ex2 = new Exercise2("/Users/rupen/Documents/Coursera/Algorithms/Course3/knapsack1.txt");
        System.out.println(ex2.optimalSolution());
        /* 2493893.0 */
    }

}
