package com.alg;

import java.util.Calendar;
import java.util.Random;

public class ArrayInversion
{
    int[] arr;

    public ArrayInversion(int[] arr)
    {
        super();
        this.arr = arr;
    }
    
    public long countBruteForce()
    {
        long inversions = 0;
        for (int i=0; i<arr.length; i++)
        {
            for (int j=i+1; j<arr.length; j++)
            {
                if (arr[i] > arr[j])
                {
                    // System.out.println(arr[i] + ", " + arr[j]);
                    inversions++;
                }
            }
        }
        return inversions;
    }
    
    public long countSplit(int start, int mid, int end)
    {
        return 0L;
    }
    
    private ArrayAndCounter mergeAndCount(int[] array1, int[] array2)
    {
        int pos1 = 0;
        int pos2 = 0;
        int len1 = array1.length;
        int len2 = array2.length;
        int posOut = 0;
        int[] outArray = new int[len1+len2];
        long inversions = 0;
        
        while (pos1 < len1)
        {
            if (pos2 < len2)
            {
                if (array1[pos1]<= array2[pos2])
                {
                    outArray[posOut++] = array1[pos1++];
                }
                else
                {
                    outArray[posOut++] = array2[pos2++];
                    inversions += len1 - pos1;
                }
            }
            else
            {
                outArray[posOut++] = array1[pos1++];
            }
        }
        while (pos2 < len2)
        {
            outArray[posOut++] = array2[pos2++];
        }
        return new ArrayAndCounter(outArray, inversions);
    }

    
    public ArrayAndCounter countDivideConquer(int[] items, int from, int to)
    {
        if ((to - from) < 1)
        {
            return new ArrayAndCounter(null, 0);
        }
        else if ((to - from) == 1)
        {
            int[] out = new int[1];
            out[0] = items[from];
            return new ArrayAndCounter(out, 0);
        }
        else
        {
            int mid = (from + to) / 2;
            ArrayAndCounter leftInversions = countDivideConquer(items, from, mid);
            ArrayAndCounter rightInversions = countDivideConquer(items, mid, to);
            ArrayAndCounter splitInversions = mergeAndCount(leftInversions.arr, rightInversions.arr);
            long counter = leftInversions.counter + rightInversions.counter + splitInversions.counter;
            return new ArrayAndCounter(splitInversions.arr, counter);
        }
    }
    
    public long countDivideConquer()
    {
        return countDivideConquer(this.arr, 0, arr.length).counter;
    }

    public static void test_01()
    {
        int[] arr = {1,3,5,2,4,6};
        ArrayInversion ai = new ArrayInversion(arr);
        System.out.println(ai.countBruteForce());
    }
    
    public static void test_02()
    {
        int[] arr = {1,3,5,2,4,6};
        ArrayInversion ai = new ArrayInversion(arr);
        System.out.println(ai.countDivideConquer());
    }
    
    public static void test_03()
    {
        int[] arr = { 4, 80, 70, 23, 9, 60, 68, 27, 66, 78, 12, 40, 52, 53, 44, 8, 49, 28, 18, 46, 21, 39, 51, 7, 87, 99, 69, 62, 84, 6, 79, 67, 14, 98, 83, 0, 96, 5, 82, 10, 26, 48, 3, 2, 15, 92, 11, 55, 63, 97, 43, 45, 81, 42, 95, 20, 25, 74, 24, 72, 91, 35, 86, 19, 75, 58, 71, 47, 76, 59, 64, 93, 17, 50, 56, 94, 90, 89, 32, 37, 34, 65, 1, 73, 41, 36, 57, 77, 30, 22, 13, 29, 38, 16, 88, 61, 31, 85, 33, 54 };
        ArrayInversion ai = new ArrayInversion(arr);
        System.out.println(ai.countDivideConquer());
    }

    public static void perf_test_01()
    {
        int[] arr = new int[100000];
        Random rand = new Random();
        for (int i=0; i<arr.length; i++)
        {
            arr[i] = rand.nextInt(10000);
        }
        ArrayInversion ai = new ArrayInversion(arr);
        long start = Calendar.getInstance().getTimeInMillis();
        long inv1 = ai.countBruteForce();
        System.out.println(inv1);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to count inversions by brute force for array of size %d = %d ms", arr.length, (end-start)));
        
        start = Calendar.getInstance().getTimeInMillis();
        long inv2 = ai.countDivideConquer();
        System.out.println(inv2);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to count inversions for divide and conquer array of size %d = %d ms", arr.length, (end-start)));
        System.out.println("Matched = " + (inv1 == inv2));
    }
    
    public static void main(String[] args)
    {
        test_01();
        test_02();
        test_03();
    }
    
    static class ArrayAndCounter
    {
        int[] arr;
        long counter;
        
        public ArrayAndCounter()
        {
            super();
        }

        public ArrayAndCounter(int[] arr, long counter)
        {
            super();
            this.arr = arr;
            this.counter = counter;
        }
        
    }

}
