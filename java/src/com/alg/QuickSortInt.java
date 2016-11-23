package com.alg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class QuickSortInt
{
    int[] arr;
    int len;
    int noSwaps = 0;
    int noSorts = 0;
    Random rand = new Random();
    int[] pcts = new int[101];
    int cntr = 0;
    int comparisons = 0;
    public enum PivotType {FIRST, LAST, MEDIAN, RANDOM};
    
    public QuickSortInt(int[] arr)
    {
        super();
        this.arr = arr;
        this.len = arr.length;
    }
    
    public void sort(PivotType pivotType)
    {
        noSwaps = 0;
        comparisons = 0;
        sort(0, len, pivotType);
    }
    
    public void sort(int from, int to, PivotType pivotType)
    {
        // System.out.println(String.format("from = %d, to = %d", from, to));
        /*
        for (int i=from; i<to; i++)
        {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
        */
        
        // Nothing to sort if there is just one element
        if (to - from <= 1)
        {
            return;
        }
        noSorts++;
        int splitPosition = partition(from, to, pivotType);
        sort(from, splitPosition-1, pivotType);
        sort(splitPosition, to, pivotType);
        /*
        if (to - from > 7)
        {
            int pctLeft = (int) ((splitPosition-1 - from) * 100.0 / (to-from));
            pcts[pctLeft] += 1;
            if (pctLeft == 99)
            {
                cntr++;
                if (cntr >= 9000000)
                {
                    for (int i=from; i<to; i++)
                    {
                        System.out.print(arr[i] + ", ");
                    }
                    System.out.println();
                    System.out.println(splitPosition);
                }
            }
        }
        */
    }
    
    public int choosePivot(int from, int to, PivotType pivotType)
    {
        if (pivotType == PivotType.FIRST)
        {
            return from;
        }
        else if (pivotType == PivotType.LAST)
        {
            return to - 1;
        }
        else if (pivotType == PivotType.MEDIAN)
        {
            int first = arr[from];
            int last = arr[to-1];
            int middlePos = 0;
            if ((to - from) % 2 == 0)
            {
                middlePos = (from + to) / 2 - 1;
            }
            else
            {
                middlePos = (from + to) / 2;
            }
            int middle = arr[middlePos];
            int[] vals = {first, middle, last};
            Arrays.sort(vals);
            if (first == vals[1])
            {
                return from;
            }
            else if (last == vals[1])
            {
                return to - 1;
            }
            else
            {
                return middlePos;
            }
        }
        // return from;
        // return to-1;
        // return (from + to) / 2;
        return rand.nextInt(to-from) + from;
    }
    
    private void swap(int posOne, int posTwo)
    {
        if (posOne != posTwo)
        {
            int val = arr[posOne];
            arr[posOne] = arr[posTwo];
            arr[posTwo] = val;
            noSwaps++;
        }
    }
    
    public int partition(int from, int to, PivotType pivotType)
    {
        int pivotPosition = choosePivot(from, to, pivotType);
        int pivotValue = arr[pivotPosition];
        swap(pivotPosition, from); // Switch Pivot to the first position of the array
        // Implement partition subroutine
     
        /* variable lookedAt: 
         *   Defines the position up to which the array has been examined
         *   Everything to the left should be partitioned according to the Pivot element
         * variable splitPosition:
         *   Defined the position in the looked at array where the partition split occurs
         */
        int lookedAt = from + 1; 
        int splitPosition = lookedAt;
        while (lookedAt < to)
        {
            comparisons++;
            if (arr[lookedAt] > pivotValue)
            {
            }
            else
            {
                if (lookedAt >= from + 1)
                {
                    swap(lookedAt, splitPosition);
                }
                splitPosition++;
            }
            lookedAt++;
            // System.out.println(Arrays.toString(arr) + " : " + splitPosition);
        }
        swap(from, splitPosition-1);
        // System.out.println(splitPosition);
        return splitPosition;
    }
    
    public boolean isSorted()
    {
        int prevVal = 0;
        int i = 0;
        for (int val : arr)
        {
            if (i > 0 && prevVal > val)
            {
                return false;
            }
            i++;
            prevVal = val;
        }
        return true;
    }
    
    public static void test01()
    {
        int[] arr = {3, 8, 2, 5, 1, 4, 7, 6};
        QuickSortInt qSort = new QuickSortInt(arr);
        qSort.partition(0, arr.length, PivotType.FIRST);
        System.out.println(Arrays.toString(arr));
    }
    
    public static void test02()
    {
        int[] arr = {3, 8, 2, 5, 1, 4, 7, 6};
        QuickSortInt qSort = new QuickSortInt(arr);
        qSort.sort(PivotType.FIRST);        
        System.out.println(Arrays.toString(arr));
        System.out.println("Number of calls to sort = " + qSort.noSorts);
    }
    
    public static void test03()
    {
        int[] arr = {3, 8, 3, 5, 1, 3, 7, 6};
        QuickSortInt qSort = new QuickSortInt(arr);
        int splitPosition = qSort.partition(0, arr.length, PivotType.FIRST);
        System.out.println(Arrays.toString(arr));
        System.out.println("Split Position = " + splitPosition);
    }
    
    public static void perftest01(int size)
    {
        Random rand = new Random();
        int[] arr = new int[size];
        int noZeros = 0;
        for (int i=0; i<size; i++)
        {
            arr[i] = rand.nextInt(10000);
            if (arr[i] == 0)
            {
                noZeros++;
            }
        }
        System.out.println("Number of zeros = " + noZeros);
        QuickSortInt qSort = new QuickSortInt(arr);
        long start = Calendar.getInstance().getTimeInMillis();
        qSort.sort(PivotType.FIRST);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort = %d ms", (end - start)));
        System.out.println("Sorted = " + qSort.isSorted());
        System.out.println("Swaps = " + qSort.noSwaps);
        System.out.println("Number of calls to sort = " + qSort.noSorts);
        for (int i=0; i<qSort.pcts.length; i++)
        {
            System.out.println(String.format("%3d = %3d" , i, qSort.pcts[i]));
        }
    }
    
    public static int[] readFileIntoArray(String fileName) throws IOException
    {
        ArrayList<Integer> data = new ArrayList<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            data.add(Integer.parseInt(line));
        }
        reader.close();
        int[] ret = new int[data.size()];
        int i=0;
        for (Integer val : data)
        {
            ret[i++] = val;
        }
        return ret;
    }
    
    public static void testFile(String fileName, PivotType pivotType) throws IOException
    {
        int[] arr = readFileIntoArray(fileName);
        QuickSortInt qSort = new QuickSortInt(arr);
        qSort.sort(pivotType);        
        System.out.println("Comparisons for PivotType " + pivotType + " = " + qSort.comparisons);
        if (! qSort.isSorted())
        {
            System.out.println("Sorting failed");
        }
        // System.out.println(Arrays.toString(arr));
    }
    
    public static void test04()
    {
        int[] arr = {3, 9, 8, 4, 6, 10, 2, 5, 7, 1};
        int[] newArr = new int[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        QuickSortInt qSort = new QuickSortInt(newArr);
        qSort.sort(PivotType.FIRST);        
        System.out.println("Comparisons = " + qSort.comparisons);
        
        newArr = new int[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        qSort = new QuickSortInt(newArr);
        qSort.sort(PivotType.LAST);        
        System.out.println("Comparisons = " + qSort.comparisons);

        newArr = new int[arr.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        qSort = new QuickSortInt(newArr);
        qSort.sort(PivotType.MEDIAN);        
        System.out.println("Comparisons = " + qSort.comparisons);
    }
    
    public static void testFile(String fileName) throws IOException
    {
        testFile(fileName, PivotType.FIRST);
        testFile(fileName, PivotType.LAST);
        testFile(fileName, PivotType.MEDIAN);
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01();
        // test02();
        // test03();
        // perftest01(10000000);
        test04();
        testFile("C:\\Users\\rubandyopadhyay\\Downloads\\QuickSort.txt");
    }
    
}
