package com.alg.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;

import com.alg.datastructures.MinHeap;

public class Knapsack
{
    int[] weights;
    double[] values;
    Double[][] solution;
    MinHeap<KnapsackSolution> toSolve;
    Hashtable<KnapsackSolution, Double> alreadySolved = new Hashtable<>();
    
    public Knapsack(int[] weights, double[] values)
    {
        super();
        this.weights = weights;
        this.values = values;
    }
    
    public void sortByDecreasingWeight()
    {
        ArrayList<SackItem> data = new ArrayList<>(weights.length);
        for (int i=0; i<weights.length; i++)
        {
            SackItem item = new SackItem(weights[i], values[i]);
            data.add(item);
        }
        Collections.sort(data, new Comparator<SackItem>() {

            @Override
            public int compare(SackItem o1, SackItem o2)
            {
                return o2.weight - o1.weight;
            }
        });
        for (int i=0; i<weights.length; i++)
        {
            weights[i] = data.get(i).weight;
            values[i] = data.get(i).value;
        }
    }
    
    public void solve(int W)
    {
        solution = new Double[weights.length + 1][W+1];
        
        // Initialize for i=0
        for (int i=0; i<=W; i++)
        {
            solution[0][i] = 0.0;
        }
        
        for (int i=1; i<=weights.length; i++)
        {
            for (int x=0; x<=W; x++)
            {
                if (x < weights[i-1])
                {
                    solution[i][x] = solution[i-1][x];
                }
                else
                {
                    solution[i][x] = Math.max(solution[i-1][x], solution[i-1][x-weights[i-1]] + values[i-1]);
                }
            }
        }
    }
    
    public void determineNeededSolutions(int W)
    {
        toSolve = new MinHeap<>(new KnapsackSolution[] {});
        populateNeededSolutions(weights.length, W);
    }
    
    public boolean alreadyAdded(int elementNo, int x)
    {
        return toSolve.getActualValue(new KnapsackSolution(elementNo, x)) != null;
    }
    
    public void populateNeededSolutions(int elementNo, int x)
    {
        // System.out.println(elementNo + "," + x + " : " + toSolve.getSize());
        /*
        if (toSolve.getSize() > 100)
        {
            return;
        }
        */
        KnapsackSolution needed = new KnapsackSolution(elementNo, x);
        toSolve.insertItem(needed);
        if (elementNo == 0)
        {
            return;
        }
        int w_i = weights[elementNo-1];
        if (w_i > x)
        {
            return;
        }
        if (x -  w_i >= 0)
        {
            if (! alreadyAdded(elementNo - 1, x - w_i))
            populateNeededSolutions(elementNo - 1, x - w_i);
        }
        if (! alreadyAdded(elementNo- 1, x))
        {
            populateNeededSolutions(elementNo - 1, x);
        }
    }
    
    public Double solveOptimal(int W)
    {
        return solveRecursively(weights.length, W);
    }
    
    public Double solveRecursively(int elementNo, int x)
    {
        if (elementNo == 0)
        {
            return 0.0;
        }
        if (weights[elementNo-1] > x)
        {
            return 0.0;
        }
        KnapsackSolution input = new KnapsackSolution(elementNo, x);
        Double val = alreadySolved.get(input);
        if (val != null)
        {
            return val;
        }
        Double ret = Math.max(solveRecursively(elementNo-1, x), solveRecursively(elementNo-1, x - weights[elementNo-1]) + values[elementNo - 1]);
        alreadySolved.put(input, ret);
        // System.out.println(alreadySolved);
        return ret;
    }
    
    public Double optimalSolution(int W)
    {
        if (solution == null || solution[0].length != (W+1))
        {
            solve(W);
        }
        return solution[solution.length-1][W];
    }
    
    public LinkedList<Integer> selectedPoints(int W)
    {
        if (solution == null || solution[0].length != (W+1))
        {
            solve(W);
        }
        LinkedList<Integer> selected = new LinkedList<>();
        boolean allDone = false;
        int currentX = W;
        int currentI = solution.length - 1;
        while (! allDone)
        {
            Double currentVal = solution[currentI][currentX];
            if (currentVal == 0)
            {
                break;
            }
            Double predecessor1Val = solution[currentI - 1][currentX];
            Double predecessor2Val = solution[currentI - 1][currentX - weights[currentI-1]] + values[currentI-1];
            if (currentVal.equals(predecessor2Val))
            {
                selected.addFirst(currentI-1);
                currentX = currentX - weights[currentI-1];
            }
            currentI = currentI - 1;
        }
        return selected;
    }
    
    public void printSolution()
    {
        if (solution == null)
        {
            System.out.println("No solution!!!");
            return;
        }
        int noRows = solution[0].length;
        int noColumns = solution.length;
        // System.out.println(String.format("Rows = %d, Columns = %d", noRows, noColumns));
        System.out.println();
        System.out.println("Solution Matrix");
        System.out.println("---------------");
        for (int x=noRows-1; x>=0; x--)
        {
            for (int i=0; i<noColumns; i++)
            {
                System.out.print(String.format("%6.2f", solution[i][x]));
            }
            System.out.println();
        }
    }
    
    public void print()
    {
        System.out.println("Number of points = " + weights.length);
        System.out.println();
        System.out.println(String.format("%-6s %-7s" , "values", "weights"));
        for (int i=0; i<weights.length; i++)
        {
            System.out.println(String.format("%6.2f %7d", values[i], weights[i]));
        }
    }
    
    public static void test01()
    {
        int[] weights = {4,3,2,3};
        double[] values = {3,2,4,4};
        Knapsack ks = new Knapsack(weights, values);
        ks.solve(6);
        ks.print();
        ks.printSolution();
        System.out.println();
        System.out.println("Optimal solution is: " + ks.optimalSolution(6));
        System.out.println("Selected items are: " + ks.selectedPoints(6));
        ks.determineNeededSolutions(6);
        ks.toSolve.printData();
        ks.sortByDecreasingWeight();
        ks.print();
        System.out.println(ks.toSolve.getSize());
        System.out.println(ks.solveOptimal(6));
    }
    
    public static void main(String[] args)
    {
        test01();
    }

}
