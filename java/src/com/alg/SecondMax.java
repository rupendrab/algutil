package com.alg;

import java.util.Calendar;
import java.util.Random;

public class SecondMax
{
    /*
     * Find the 2nd maximum number from an array of n unsorted numbers where n is a power of 2
     */
    int[] arr;
    MaxTree tree;
    long buildTreeComparisons = 0;
    long findComparisons = 0;

    public SecondMax(int[] arr)
    {
        super();
        this.arr = arr;
    }
    
    public MaxTree[] constructInitialMaxTree()
    {
        MaxTree[] leafs = new MaxTree[arr.length];
        for (int i=0; i<arr.length; i++)
        {
            leafs[i] = new MaxTree(arr[i]);
        }
        return leafs;
    }
    
    public void constructMaxTree(MaxTree[] startNodes)
    {
        MaxTree[] combinedNodes = new MaxTree[startNodes.length / 2];
        for (int i=0; i<combinedNodes.length; i++)
        {
            MaxTree node1 = startNodes[i*2];
            MaxTree node2 = startNodes[i*2 + 1];
            if (node1.val > node2.val)
            {
                combinedNodes[i] = new MaxTree(node2, node1, node1.val);
            }
            else
            {
                combinedNodes[i] = new MaxTree(node1, node2, node2.val);
            }
            buildTreeComparisons++;
        }
        if (combinedNodes.length == 1)
        {
            tree = combinedNodes[0];
        }
        else
        {
            constructMaxTree(combinedNodes);
        }
    }
    
    public int maxValue()
    {
        constructMaxTree(constructInitialMaxTree());
        return tree.val;
    }
    
    public int secondMaxValue()
    {
        if (tree == null)
        {
            constructMaxTree(constructInitialMaxTree());
        }
        MaxTree next = tree;
        int secondMax = Integer.MIN_VALUE;
        while (! next.isLeaf())
        {
            int val1 = next.left.val;
            secondMax = val1 > secondMax ? val1 : secondMax;
            findComparisons++;
            next = next.right;
        }
        return secondMax;
    }
    
    static class MaxTree
    {
        MaxTree left;
        MaxTree right;
        int val;
        
        public MaxTree(int val)
        {
            super();
            this.val = val;
        }

        public MaxTree(MaxTree left, MaxTree right, int val)
        {
            super();
            this.left = left;
            this.right = right;
            this.val = val;
        }
        
        public boolean isLeaf()
        {
            return left == null && right == null;
        }
        
    }
    
    public static void testMax()
    {
        long start = Calendar.getInstance().getTimeInMillis();
        int[] arr = new int[1024 * 1024 * 16];
        Random rand = new Random();
        int maxVal = 0;
        int secondMaxVal = 0;
        for (int i=0; i<arr.length; i++)
        {
            arr[i] = rand.nextInt(100000000);
            if (i == 0)
            {
                maxVal = arr[i];
            }
            else if (arr[i] > maxVal)
            {
                secondMaxVal = maxVal;
                maxVal = arr[i];
            }
            else if (arr[i] > secondMaxVal)
            {
                secondMaxVal = arr[i];
            }
        }
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to construct array = %d ms", (end-start)));
        SecondMax sm = new SecondMax(arr);
        start = Calendar.getInstance().getTimeInMillis();
        int maxValComputed = sm.maxValue();
        int secondMaxValComputed = sm.secondMaxValue();
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Max: Actual = %d, Computed = %d, matched = %s", maxVal, maxValComputed, maxVal == maxValComputed));
        System.out.println(String.format("2.Max: Actual = %d, Computed = %d, matched = %s", secondMaxVal, secondMaxValComputed, secondMaxVal == secondMaxValComputed));
        System.out.println(String.format("Time to compute = %d ms", (end - start)));
        System.out.println("Build Tree Comparisons = " + sm.buildTreeComparisons);
        System.out.println(String.format("Build Tree Comparisons = %.2f n", (sm.buildTreeComparisons * 1.0 / sm.arr.length)));
        System.out.println("Find Comparisons = " + sm.findComparisons);
    }
    
    public static void main(String[] args)
    {
        testMax();
    }

}
