package com.alg.dp;

import java.util.Arrays;

public class BSTWeighted
{
    Integer[] points;
    Double[] weights;
    WeightedPoint[] wps;
    int numPoints;
    Double[][] scores;
    
    public BSTWeighted(Integer[] points, Double[] weights)
    {
        super();
        this.points = points;
        this.weights = weights;
        numPoints = weights.length;
        wps = new WeightedPoint[numPoints];
        for (int i=0; i<numPoints; i++)
        {
            WeightedPoint wp = new WeightedPoint(points[i], weights[i]);
            wps[i] = wp;
        }
        sortPoints();
        scores = new Double[numPoints][numPoints];
        // initScores();
    }
    
    public boolean isSorted()
    {
        for (int i=1; i<numPoints; i++)
        {
            if (wps[i].compareTo(wps[i-1]) < 0)
            {
                return false;
            }
        }
        return true;
    }
    public void sortPoints()
    {
        if (! isSorted())
        {
            Arrays.sort(wps);
        }
    }
    
    public void initScores()
    {
        for (int i=0; i<numPoints-1; i++)
        {
            scores[i][i+1] = wps[i].weight;
        }
    }
    
    public Double sum(int from, int to)
    {
        Double ret = 0.0;
        for (int i=from; i<=to && i<numPoints; i++)
        {
            ret += wps[i].weight;
        }
        return ret;
    }
    
    public void solve()
    {
        for (int s=0; s<numPoints; s++) // s represents the distance j - i
        {
            for (int i=0; i<numPoints; i++)
            {
                if (i+s >= numPoints)
                {
                    continue;
                }
                System.out.println(String.format("Calculating: %d, %d", i, i+s));
                Double minValue = Double.MAX_VALUE;
                Double sumPk = sum(i, i+s);
                int minR = -1;
                for (int r=i; (r <= (i+s)) && (r < numPoints); r++)
                {
                    Double calculated = sumPk + (i==r ? 0 : scores[i][r-1]) + (r==i+s ? 0 : scores[r+1][i+s]);
                    if (calculated < minValue)
                    {
                        minValue = calculated;
                        minR = r;
                    }
                }
                scores[i][i+s] = minValue;
                System.out.println("  Score = " + minValue);
            }
        }
        
    }
    
    public void printScores()
    {
        System.out.println();
        for (int i=0; i<scores.length; i++)
        {
            for (int j=0; j<scores[i].length; j++)
            {
                System.out.print(String.format("%6.2f ", (scores[i][j] == null ? 0 : scores[i][j])));
            }
            System.out.println();
        }
    }
    public static void test01()
    {
        Integer[] points = {1, 2, 3, 4};
        Double[] weights = {2.0, 23.0, 73.0, 1.0};
        BSTWeighted bst = new BSTWeighted(points, weights);
        bst.solve();
        bst.printScores();
    }
    public static void main(String[] args) throws Exception
    {
        test01();
    }

}
