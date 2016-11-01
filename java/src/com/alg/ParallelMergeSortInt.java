package com.alg;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class ParallelMergeSortInt
{
    static Random rand = new Random();
    int[] items;
    int itemCount;
    int threshold = 1000000;
    
    public ParallelMergeSortInt(int[] items)
    {
        this.items = items;
        this.itemCount = this.items.length;
    }
    
    private int[] merge(int[] array1, int[] array2)
    {
        int pos1 = 0;
        int pos2 = 0;
        int len1 = array1.length;
        int len2 = array2.length;
        int posOut = 0;
        int[] outArray = new int[len1+len2];
        
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
        return outArray;
    }
    
    public int[] sort(int from, int to)
    {
        return sort(this.items, from, to);
    }

    public int[] sort(int[] items, int from, int to)
    {
        // System.out.println(String.format("Sort : %d to %d", from, to));
        if (to - from < 0)
        {
            return null;
        }
        else if (to - from <= 1)
        {
            int[] out = new int[1];
            out[0] = items[from];
            return out;
        }
        else
        {
            int mid = (from + to) / 2;
            if ( to - from > threshold)
            {
                // System.out.println("Parallel");
                int[] sorted1 = new int[mid-from];
                Thread thread1 = new Thread() {
                    @Override
                    public void run()
                    {
                        int[] sorted = sort(items, from, mid);
                        System.arraycopy(sorted, 0, sorted1, 0, mid-from);
                    }
                };
                int[] sorted2 = new int[to-mid];
                Thread thread2 = new Thread() {
                    @Override
                    public void run()
                    {
                        int[] sorted = sort(items, mid, to);
                        System.arraycopy(sorted, 0, sorted2, 0, to-mid);
                    }
                };
                thread1.start();
                thread2.start();
                try
                {
                    thread1.join();
                    thread2.join();
                }
                catch (InterruptedException ie)
                {
                }
                return merge(sorted1, sorted2);
            }
            else
            {
                int[] sorted1 = sort(from, mid);
                int[] sorted2 = sort(mid, to);
                return merge(sorted1, sorted2);
            }
        }
    }
    
    public static int[] generateRandomInts(int from, int to, int count)
    {
        int[] out = new int[count];
        for (int i=0; i<count; i++)
        {
            out[i] = from + rand.nextInt(to);
        }
        return out;
    }
    
    public void shutdown()
    {
        // exec.shutdown();
    }
    
    public static boolean isSorted(int[] arr)
    {
        int i = 0;
        int prev = -1;
        for (int elem : arr)
        {
            if (i > 0)
            {
                if (elem< prev)
                {
                    System.out.println(i + " : " + prev + " to " + elem);
                    return false;
                }
            }
            i += 1; 
            prev = elem;
        }
        return true;
    }
    
    public static boolean isEqual(int[] arr1, int[] arr2)
    {
        int len1 = arr1.length;
        int len2 = arr2.length;
        if (len1 != len2)
        {
            return false;
        }
        for (int i=0; i<len1; i++)
        {
            if (arr1[i]!= arr2[i])
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args)
    {
        long startTime = 0;
        long endTime = 0;
        int arraySize = 10000000;
        int[] toSort = (int[]) generateRandomInts(0, 10000000, arraySize);
        ParallelMergeSortInt p = new ParallelMergeSortInt(toSort);
        
        startTime = Calendar.getInstance().getTimeInMillis();
        int[] sorted = p.sort(0, toSort.length);
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to sort array of size %d = %d ms", arraySize, (endTime - startTime)));

        startTime = Calendar.getInstance().getTimeInMillis();
        Arrays.sort(toSort);
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to java sort array of size %d = %d ms", arraySize, (endTime - startTime)));
        
        // System.out.println(isSorted(sorted));
        System.out.println(isEqual(sorted, toSort));
        p.shutdown();
    }

}
