package com.alg;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class SelectionDeterministic
{
    int comparisons = 0;
    int noSwaps = 0;
    
    public SelectionDeterministic()
    {
        super();
    }
    
    public int getPositionInArray(int[] arr, int val)
    {
        // System.out.println(String.format("Trying to find %d in %s", val, Arrays.toString(arr)));
        int i=0;
        for (int arrval : arr)
        {
            if (arrval == val)
            {
                return i;
            }
            i+=1;
        }
        return -1;
    }
    // Returns the position in arr of the ith smallest element in the array (count from 0)
    public int DSelect(int[] arr, int len, int i)
    {
        /*
        System.out.println(String.format("DSelect len = %d, i = %d, arr = %s", 
                len, i, Arrays.toString(arr)));
        pause();
        */
        if (len <= 1)
        {
            return arr[0];
        }
        int[] medians = getMediansByFive(arr, arr.length);
        int n = medians.length;
        int pivot = DSelect(medians, n, n/2);
        /*
        System.out.println("Pivot is " + pivot);
        System.out.println("arr is " + Arrays.toString(arr));
        pause();
        */
        int pivotPosition = getPositionInArray(arr, pivot);
        // System.out.println("Pivot position is " + pivotPosition);
        pivotPosition = partition(arr, 0, len, pivotPosition);
        // System.out.println("New Pivot position is " + pivotPosition);
        // System.out.println("Array is " + Arrays.toString(arr));
        if (pivotPosition - 1 == i)
        {
            return arr[pivotPosition-1];
        }
        else if (pivotPosition - 1 > i)
        {
            int[] arrLeft = new int[pivotPosition-1];
            System.arraycopy(arr, 0, arrLeft, 0, pivotPosition-1);
            return DSelect(arrLeft, pivotPosition-1, i);
        }
        else
        {
            int[] arrRight = new int[len-pivotPosition];
            System.arraycopy(arr, pivotPosition, arrRight, 0, len-pivotPosition);
            return DSelect(arrRight, arrRight.length, i-pivotPosition);
        }
    }
    
    public int getQuickMedian(int[] data)
    {
        Arrays.sort(data);
        int dataLen = data.length;
        int medianPos = dataLen / 2; 
        if (data.length % 2 == 0)
        {
            medianPos--;
        }
        return data[medianPos];
    }
    
    public int[] getMediansByFive(int[] arr, int len)
    {
        int lenOfMedians = (len) / 5;
        if (lenOfMedians * 5 < (len))
        {
            lenOfMedians++;
        }
        int[] out = new int[lenOfMedians];
        int outPos = 0;
        for (int i=0; i<len; i+=5)
        {
            int nxt = Math.min(i+4, len-1);
            int[] newdata = new int[nxt+1-i];
            /*
            System.out.println(String.format("getMediansBy5 i = %d, nxt = %d, len = %d", 
                    i, nxt, len));
            System.out.println(Arrays.toString(arr));
            */
            System.arraycopy(arr, i, newdata, 0, nxt+1-i);
            int median = getQuickMedian(newdata);
            out[outPos++] = median;
        }
        /*
        System.out.println("getMediansByFive");
        System.out.println("Input Array = " + Arrays.toString(arr));
        System.out.println("Output Array = " + Arrays.toString(out));
        */
        return out;
    }
    
    private void swap(int[] arr, int posOne, int posTwo)
    {
        if (posOne != posTwo)
        {
            int val = arr[posOne];
            arr[posOne] = arr[posTwo];
            arr[posTwo] = val;
            noSwaps++;
        }
    }

    public int partition(int[] arr, int from, int to, int pivotPosition)
    {
        // System.out.println("PivotPosition = " + (pivotPosition - from));
        // System.out.println("Input Array = " + arrayView(from, to));
        // System.out.println(String.format("partiton %d to %d (%d) : %s", from, to, pivotPosition, Arrays.toString(arr)));
        int pivotValue = arr[pivotPosition];
        swap(arr, pivotPosition, from); // Switch Pivot to the first position of the
                                   // array
        // Implement partition subroutine

        /*
         * variable lookedAt: Defines the position up to which the array has
         * been examined Everything to the left should be partitioned according
         * to the Pivot element variable splitPosition: Defined the position in
         * the looked at array where the partition split occurs
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
                    swap(arr, lookedAt, splitPosition);
                }
                splitPosition++;
            }
            lookedAt++;
            // System.out.println(Arrays.toString(arr) + " : " + splitPosition);
        }
        swap(arr, from, splitPosition - 1);
        // System.out.println(arrayView(from, to) + " : " + splitPosition);
        return splitPosition;
    }
    
    public static void testQuickMedian()
    {
        int[] data = {3,4,5,6,7,1};
        int median = new SelectionDeterministic().getQuickMedian(data);
        System.out.println(median);
        int[] data2 = {5,4,3,6,7};
        median = new SelectionDeterministic().getQuickMedian(data2);
        System.out.println(median);
    }
    
    public static int[] createRandomIntegerArrayNoDuplicates(int size, int maxVal)
    {
        int[] data = new int[size];
        Random rand = new Random();
        HashSet<Integer> added = new HashSet<Integer>();
        for (int i=0; i<size; i++)
        {
            int newVal = rand.nextInt(maxVal);
            while (added.contains(newVal))
            {
                newVal = rand.nextInt(maxVal);
            }
            added.add(newVal);
            data[i] = newVal;
        }
        return data;
    }
    
    public static void pause()
    {
        pause(false);
        
    }
    
    public static void pause(boolean pause)
    {
        if (!pause)
        {
            return;
        }
        try
        {
            System.in.read();
        }
        catch (IOException ioe)
        {
        }
    }
    
    public static void test01(int size, int rank) 
    {
        int[] data = createRandomIntegerArrayNoDuplicates(size, size * 10);
        if (size <= 1000)
        {
            System.out.println(Arrays.toString(data));
        }
        pause();
        SelectionDeterministic sel = new SelectionDeterministic();
        long start, end = 0;
        int act = -1;
        try
        {
            start = Util.getTime();
            act = sel.DSelect(data, data.length, rank);
            System.out.println("Calculated = " + act);
            end = Util.getTime();
            Util.showElapsed(start, end);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            System.out.println("data = " + Arrays.toString(data));
        }
        if (size <= 1000)
        {
            System.out.println("data = " + Arrays.toString(data));
        }
        start = Util.getTime();
        Arrays.sort(data);
        System.out.println("Actual = " + data[rank]);
        end = Util.getTime();
        Util.showElapsed(start, end);
        System.out.println("Matched = " + (act == data[rank]));
    }
    
    public static void test02()
    {
        int[] data = {6, 19, 11, 111, 10, 5, 16, 13, 17, 15, 13, 61, 7, 14, 51, 3, 191, 4, 15, 141};
        SelectionDeterministic sel = new SelectionDeterministic();
        try
        {
            int pos5th = sel.DSelect(data, data.length, 5);
            System.out.println("Calculated = " + pos5th);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            System.out.println("data = " + Arrays.toString(data));
        }
        System.out.println("data = " + Arrays.toString(data));
        Arrays.sort(data);
        System.out.println("Actual = " + data[5]);
    }
    
    public static void test03()
    {
        int[] data = {102, 25, 185, 199, 154, 45, 9, 127, 130, 37, 89, 38, 35, 13, 17, 45, 55, 36, 101, 182};
        SelectionDeterministic sel = new SelectionDeterministic();
        long start, end = 0;
        int act = -1;
        try
        {
            start = Util.getTime();
            act = sel.DSelect(data, data.length, 5);
            end = Util.getTime();
            Util.showElapsed(start, end);
            System.out.println("Calculated = " + act);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            System.out.println("data = " + Arrays.toString(data));
        }
        System.out.println("data = " + Arrays.toString(data));
        start = Util.getTime();
        Arrays.sort(data);
        System.out.println("Actual = " + data[5]);
        end = Util.getTime();
        Util.showElapsed(start, end);
        System.out.println("Matched = " + (data[5] == act));
    }
    
    public static void main(String[] args) throws Exception
    {
        // testQuickMedian();
        // test02();
        // test03();
        test01(15000000, 100);
    }

}
