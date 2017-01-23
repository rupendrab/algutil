package com.alg.dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

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
    }
    
    public void solve()
    {
        ks.solve(W);
    }
    
    public double optimalSolution()
    {
        return ks.optimalSolution(W);
    }
    
    public int callDetermine()
    {
        ks.determineNeededSolutions(W);
        return ks.toSolve.getSize();
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
    
    public void sortByDecreasingWeight()
    {
        ks.sortByDecreasingWeight();
    }
    
    public Double solveOptimal()
    {
        return ks.solveOptimal(W);
    }
    
    public static void test01(String fileName) throws IOException
    {
        // Exercise2 ex2 = new Exercise2("/Users/rupen/Documents/Coursera/Algorithms/Course3/knapsack1.txt");
        Exercise2 ex2 = new Exercise2(fileName);
        ex2.solve();
        ex2.ks.print();
        System.out.println(ex2.ks.selectedPoints(ex2.W));
        System.out.println(ex2.optimalSolution());
        /* 2493893.0 */
    }
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void test02(String fileName) throws IOException
    {
        long start, end = 0;
        start = time();
        Exercise2 ex2 = new Exercise2(fileName);
        ex2.sortByDecreasingWeight();
        System.out.println(ex2.solveOptimal());
        end = time();
        System.out.println(String.format("Time to solve = %d ms", (end - start)));
        System.out.println("Size of hash table = " + ex2.ks.alreadySolved.size());
        /* 4243395.0 */
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack1.txt");
        test02("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack_big.txt");
        // test01("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack_test_03.txt");
        // test02("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack_test_03.txt");
        // test01("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack_test_02.txt");
        // test02("C:\\Users\\rubandyopadhyay\\Downloads\\knapsack1.txt");
    }

}
