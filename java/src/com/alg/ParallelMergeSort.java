package com.alg;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelMergeSort<T extends Comparable<T>>
{
    static Random rand = new Random();
    T[] items;
    int itemCount;
    Class<T> clazz;
    ExecutorService exec;
    ArrayList<Callable<T[]>> tasks;
    int parallelThreshold = 1000000;
    int quicksortThreshold = 10000;
    
    public ParallelMergeSort(Class<T> clazz, T[] items)
    {
        this.items = items;
        this.itemCount = this.items.length;
        this.clazz = clazz;
        // exec = Executors.newFixedThreadPool(4);
        // tasks = new ArrayList<Callable<T[]>>();
    }
    
    private T[] merge(T[] array1, T[] array2)
    {
        int pos1 = 0;
        int pos2 = 0;
        int len1 = array1.length;
        int len2 = array2.length;
        int posOut = 0;
        @SuppressWarnings("unchecked")
        T[] outArray = (T[]) Array.newInstance(clazz, len1+len2);
        
        while (pos1 < len1)
        {
            if (pos2 < len2)
            {
                if (array1[pos1].compareTo(array2[pos2]) <= 0)
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
    
    public T[] sort(int from, int to)
    {
        return sort(this.items, from, to);
    }

    public T[] sort(T[] items, int from, int to)
    {
        // System.out.println(String.format("Sort : %d to %d", from, to));
        if (to - from < 0)
        {
            return null;
        }
        else if (to - from <= 1)
        {
            @SuppressWarnings("unchecked")
            T[] out = (T[]) Array.newInstance(clazz, 1);
            out[0] = items[from];
            return out;
        }
        else
        {
            int mid = (from + to) / 2;
            if ( to - from > parallelThreshold)
            {
                @SuppressWarnings("unchecked")
                T[] sorted1 = (T[]) Array.newInstance(clazz, mid-from);
                Thread thread1 = new Thread() {
                    @Override
                    public void run()
                    {
                        T[] sorted = sort(items, from, mid);
                        /*
                        if (sorted == null)
                        {
                            System.out.println(String.format("Sorted is null: from + %d, mid = %d", from, mid));
                            return;
                        }
                        System.out.println(String.format("from = %d, mid = %d", from, mid));
                        System.out.println(String.format("sorted = %d, copyto = %d", sorted.length, sorted1.length));
                        */
                        System.arraycopy(sorted, 0, sorted1, 0, mid-from);
                        // System.out.println("copied");
                    }
                };
                @SuppressWarnings("unchecked")
                T[] sorted2 = (T[]) Array.newInstance(clazz, to-mid);
                Thread thread2 = new Thread() {
                    @Override
                    public void run()
                    {
                        T[] sorted = sort(items, mid, to);
                        /*
                        if (sorted == null)
                        {
                            System.out.println(String.format("Sorted is null: from + %d, mid = %d", from, mid));
                            return;
                        }
                        System.out.println(String.format("mid = %d, to = %d", mid, to));
                        System.out.println(String.format("sorted = %d, copyto = %d", sorted.length, sorted2.length));
                        */
                        System.arraycopy(sorted, 0, sorted2, 0, to-mid);
                        // System.out.println("copied");
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
            else if (to - from < quicksortThreshold)
            {
                T[] sorted = Arrays.copyOfRange(items, from, to);
                QuickSort<T> qs = new QuickSort<T>(sorted, true);
                return qs.sort();
            }
            else
            {
                T[] sorted1 = sort(from, mid);
                T[] sorted2 = sort(mid, to);
                return merge(sorted1, sorted2);
            }
        }
    }
    
    public static Integer[] generateRandomInts(int from, int to, int count)
    {
        Integer[] out = new Integer[count];
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
    
    public static <T extends Comparable<T>> boolean isSorted(T[] arr)
    {
        int i = 0;
        T prev = null;
        for (T elem : arr)
        {
            if (i > 0)
            {
                if (elem.compareTo(prev) < 0)
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
    
    public static <T extends Comparable<T>> boolean isEqual(T[] arr1, T[] arr2)
    {
        int len1 = arr1.length;
        int len2 = arr2.length;
        if (len1 != len2)
        {
            return false;
        }
        for (int i=0; i<len1; i++)
        {
            if (arr1[i].compareTo(arr2[i]) != 0)
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
        Integer[] toSort = generateRandomInts(0, 10000, arraySize);
        ParallelMergeSort<Integer> p = new ParallelMergeSort<Integer>(Integer.class, toSort);
        
        startTime = Calendar.getInstance().getTimeInMillis();
        Integer[] sorted = p.sort(0, toSort.length);
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
