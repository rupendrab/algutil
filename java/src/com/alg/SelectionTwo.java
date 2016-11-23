package com.alg;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import com.alg.QuickSortInt.PivotType;

public class SelectionTwo
{
    int[] arr;
    int len;
    Random rand = new Random();
    int comparisons = 0;
    int noSwaps = 0;

    public SelectionTwo(int[] arr)
    {
        super();
        this.arr = arr;
        this.len = arr.length;
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
            int last = arr[to - 1];
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
            int[] vals = { first, middle, last };
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
        else if (pivotType == PivotType.RANDOM)
        {
            return rand.nextInt(to - from) + from;
        }
        else
        {
            return rand.nextInt(to - from) + from;
        }
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

    public String arrayView(int from, int to)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            if (i > 0)
            {
                sb.append(",");
            }
            if (i == from)
            {
                sb.append("[");
            }
            sb.append(arr[i]);
            if (i == to-1)
            {
                sb.append("]");
            }
        }
        return sb.toString();

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

    public int findMedian() throws Exception
    {
        if (len % 2 == 0)
        {
            int n1 = len / 2;
            int n2 = len / 2 - 1;
            return (findIth(n1) + findIth(n2)) / 2;
        }
        else
        {
            return findIth(len/2);
        }
    }
    
    public int findIth(int i)  throws Exception
    {
        return findIth(i, PivotType.RANDOM);
    }

    public int findIth(int i, PivotType pivotType) throws Exception
    {
        return findIth(i, 0, len, pivotType);
    }

    public static String ith(int i)
    {
        if (i == 1)
        {
            return "1st";
        }
        else if (i == 2)
        {
            return "2nd";
        }
        else if (i == 3)
        {
            return "3rd";
        }
        else
        {
            return i + "th";
        }
    }

    public int findIth(int i, int from, int to, PivotType pivotType) throws Exception
    {
        // System.out.println(String.format("Find %s in %s", ith(i), arrayView(from, to)));
        if (i > to - from - 1)
        {
            throw new Exception(String.format("Cannot find value at position %d for array of length %d", i, to - from -1));
        }
        if (to - from <= 1)
        {
            return arr[from];
        }
        int[] splitPositions = partition(from, to, pivotType);
        if (splitPositions[0] - from > i)
        {
            return findIth(i, from, splitPositions[0], pivotType);
        }
        else if (splitPositions[0] - from <= i && splitPositions[1] - from > i)
        {
            return arr[splitPositions[0]];
        }
        else
        {
            return findIth(i + from - splitPositions[1], splitPositions[1], to, pivotType);
        }
    }

    public static void test01() throws Exception
    {
        int[] arr = { 5, 3, 2, 4, 1, 9, 7, 8 };
        SelectionTwo sel = new SelectionTwo(arr);
        System.out.println(sel.findIth(3));
    }

    public static void repeatedTest(int[] arr, int i , int expected) throws Exception
    {
        for (int trial=0; trial<10000; trial++)
        {
            SelectionTwo sel = new SelectionTwo(arr);
            int calc = sel.findIth(i);
            if (calc != expected)
            {
                System.out.println(String.format("Failed on trial %d for %d; actual = %d, expected = %d", 
                        (trial + 1), i, calc, expected));
            }
        }
    }
    
    public static void test02() throws Exception
    {
        int[] arr = { 5, 3, 2, 4, 1, 9, 7, 8 };
        repeatedTest(arr, 0, 1);
        repeatedTest(arr, 1, 2);
        repeatedTest(arr, 2, 3);
        repeatedTest(arr, 3, 4);
        repeatedTest(arr, 4, 5);
        repeatedTest(arr, 5, 7);
        repeatedTest(arr, 6, 8);
        repeatedTest(arr, 7, 9);
        // repeatedTest(arr, 8, 9);
    }

    public static void test03() throws Exception
    {
        int[] arr = { 5, 3, 2, 4, 1, 9, 7, 8 };
        SelectionTwo sel = new SelectionTwo(arr);
        System.out.println("Median = " + sel.findMedian());
    }
    
    public static int[] createRandomIntgerArray(int size, int maxVal)
    {
        int[] ret = new int[size];
        Random rand = new Random();
        for (int i = 0; i<size; i++)
        {
            ret[i] = rand.nextInt(maxVal+1);
        }
        return ret;
    }
    
    public static int ithValueBySort(int[] arr, int i)
    {
        Arrays.sort(arr);
        return arr[i];
    }
    
    public static long getTime()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void perftest01(int n) throws Exception
    {
        long start, end = 0;
        int[] arr = createRandomIntgerArray(15000000, 10000000);
        SelectionTwo sel = new SelectionTwo(arr);
        start = getTime();
        int itemn = sel.findIth(n);
        end = getTime();
        System.out.println(String.format("Time to find ith = %d ms" , end - start));
        start = getTime();
        int itemnBySort = ithValueBySort(arr, n);
        end = getTime();
        System.out.println(String.format("Time to find ith by sort = %d ms" , end - start));
        System.out.println(String.format("%d, %d, equal = %s", itemn, itemnBySort, (itemn == itemnBySort)));
    }
    

    public static void main(String[] args) throws Exception
    {
        test01();
        test02();
        test03();
        perftest01(12345);
    }

}
