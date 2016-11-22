package com.alg;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import com.alg.QuickSortInt.PivotType;

public class QuickSortIntTwo
{
    int[] arr;
    int len;
    int noSwaps = 0;
    int[] pcts = new int[101];
    int cntr = 0;
    Random rand = new Random();
    
    public QuickSortIntTwo(int[] arr)
    {
        super();
        this.arr = arr;
        this.len = arr.length;
    }
    
    public void insertSort(int from, int to)
    {
        for (int i=from+1; i<to; i++)
        {
            int j = i;
            while (j > 0 && arr[j-1] > arr[j])
            {
                swap(j, j-1);
                j--;
            }
        }
    }
    
    public void sort(PivotType pivotType)
    {
        noSwaps = 0;
        sort(0, len, pivotType);
    }
    
    public void sort(int from, int to, PivotType pivotType)
    {
        /*
        System.out.println(String.format("from = %d, to = %d", from, to));
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
        else if (to - from <= 8)
        {
            insertSort(from, to);
            return;
        }
        int[] splitPosition = partition(from, to, pivotType);
        sort(from, splitPosition[0], pivotType);
        sort(splitPosition[1], to, pivotType);
        /*
        if (to - from > 7)
        {
            int pctLeft = (int) ((splitPosition[0]-1 - from) * 100.0 / (to-from));
            pcts[pctLeft] += 1;
            if (pctLeft > 99)
            {
                cntr++;
                if (cntr >= 9000000)
                {
                    for (int i=from; i<to; i++)
                    {
                        System.out.print(arr[i] + ", ");
                    }
                    System.out.println();
                    System.out.println(from + " : " + (splitPosition[0]-1) + ", " + splitPosition[1] + " : " + to);
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
            // noSwaps++;
        }
    }
    
    public int[] partition(int from, int to, PivotType pivotType)
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
        int[] splitPositions = {lookedAt, lookedAt};
        while (lookedAt < to)
        {
            int diff = arr[lookedAt] - pivotValue;
            if (diff > 0)
            {
            }
            else
            {
                if (lookedAt > from + 1)
                {
                    swap(lookedAt, splitPositions[1]);
                    if (diff < 0)
                    {
                        swap(splitPositions[1], splitPositions[0]);
                        splitPositions[0]++;
                        splitPositions[1]++;
                    }
                    else
                    {
                        splitPositions[1]++;
                    }
                }
                else
                {
                    splitPositions[0]++;
                    splitPositions[1]++;
                }
            }
            lookedAt++;
            // System.out.println(Arrays.toString(arr) + " : " + Arrays.toString(splitPositions) + " : " + lookedAt);
        }
        swap(from, splitPositions[0]-1);
        splitPositions[0]--;
        // System.out.println(splitPosition);
        return splitPositions;
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
        QuickSortIntTwo qSort = new QuickSortIntTwo(arr);
        int[] splitPositions = qSort.partition(0, arr.length, PivotType.FIRST);
        System.out.println(Arrays.toString(arr));
        System.out.println("Split Positions = " + Arrays.toString(splitPositions));
    }
    
    public static void test02()
    {
        int[] arr = {3, 8, 2, 5, 1, 4, 7, 6};
        QuickSortIntTwo qSort = new QuickSortIntTwo(arr);
        qSort.sort(PivotType.FIRST);        
        System.out.println(Arrays.toString(arr));
    }
    
    public static void test03()
    {
        int[] arr = {3, 8, 3, 5, 1, 3, 7, 6};
        QuickSortIntTwo qSort = new QuickSortIntTwo(arr);
        int[] splitPositions = qSort.partition(0, arr.length, PivotType.FIRST);
        System.out.println(Arrays.toString(arr));
        System.out.println("Split Positions = " + Arrays.toString(splitPositions));
    }
    
    public static void perftest01(int size)
    {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i=0; i<size; i++)
        {
            arr[i] = rand.nextInt(1000000);
        }
        QuickSortIntTwo qSort = new QuickSortIntTwo(arr);
        long start = Calendar.getInstance().getTimeInMillis();
        qSort.sort(PivotType.MEDIAN);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort = %d ms", (end - start)));
        System.out.println("Sorted = " + qSort.isSorted());
        System.out.println("Swaps = " + qSort.noSwaps);
        /*
        for (int i=0; i<qSort.pcts.length; i++)
        {
            System.out.println(String.format("%3d = %3d" , i, qSort.pcts[i]));
        }
        */
    }
    
    public static void perftest02(int size)
    {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i=0; i<size; i++)
        {
            arr[i] = rand.nextInt(1000000);
        }
        QuickSortInt qSort = new QuickSortInt(arr);
        long start = Calendar.getInstance().getTimeInMillis();
        Arrays.sort(arr);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort = %d ms", (end - start)));
        System.out.println("Sorted = " + qSort.isSorted());
        System.out.println("Swaps = " + qSort.noSwaps);
    }

    public static boolean arrayCompare(int[] arr1, int[] arr2)
    {
        int len1 = arr1.length;
        int len2 = arr2.length;
        if (len1 != len2)
        {
            return false;
        }
        for (int i=0; i<len1; i++)
        {
            if (arr1[i] != arr2[i])
            {
                return false;
            }
        }
        return true;
    }
    
    public static void perftest03(int size, int max)
    {
        Random rand = new Random();
        int[] arr1 = new int[size];
        int[] arr2 = new int[size];
        for (int i=0; i<size; i++)
        {
            int val = rand.nextInt(max);
            arr1[i] = val;
            arr2[i] = val;
        }
        QuickSortIntTwo qSort = new QuickSortIntTwo(arr1);
        long start = Calendar.getInstance().getTimeInMillis();
        qSort.sort(PivotType.MEDIAN);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort (QuickSort) = %d ms", (end - start)));
        System.out.println("Sorted = " + qSort.isSorted());

        start = Calendar.getInstance().getTimeInMillis();
        Arrays.sort(arr2);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort (Java TimSort) = %d ms", (end - start)));
        
        System.out.println("Sorted arrays are equal = " + arrayCompare(arr1, arr2));
    }

    public static void main(String[] args)
    {
        // test01();
        // test02();
        // test03();
        // perftest01(15000000);
        // perftest02(15000000);
        perftest03(50000000, 1000000);
    }
    
}
