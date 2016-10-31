package com.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntUnaryOperator;

public class ParallelArrayManipulation
{
    static int cores = Runtime.getRuntime().availableProcessors();
    
    public static void addOne(int[] array, int start, int end, boolean parallel)
    {
        // System.out.println("addOne " + start + " to "  + end);
        if (end - start < 1)
        {
            return;
        }
        int threshold = Math.max(1000, array.length/(cores));
        if (! parallel || (end - start < threshold))
        {
            for (int i=start; i<Math.min(end, array.length); i++)
            {
                array[i] += 1;
                if (i % 10000 == 0)
                {
                    ArrayAdd.sleep(1);
                }
            }
        }
        else
        {
            if (parallel)
            {
                int mid = (start + end) / 2;
                Thread thread1 = new Thread() {
                    @Override
                    public void run()
                    {
                        addOne(array, start, mid, parallel);
                    }
                };

                Thread thread2 = new Thread() {
                    @Override
                    public void run()
                    {
                        addOne(array, mid, end, parallel);
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
                
                /*
                addOne(array, start, mid);
                addOne(array, mid, end);
                */
            }
        }
    }

    public static void addOne(int[] array, boolean parallel)
    {
        if (array == null || array.length == 0)
        {
            return;
        }
        addOne(array, 0, array.length, parallel);
    }
    
    public static int[] getArray(int from, int to, int by)
    {
        int size = (to - from) / by;
        if ((to - from) % by != 0)
        {
            size += 1;
        }
        int[] ret = new int[size];
        ret[0] = from;
        for (int i=1; i<size; i++)
        {
            ret[i] =ret[i-1] + by; 
        }
        return ret;
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
            if (arr1[i] != arr2[i])
            {
                System.out.println("Mismatch at " + i + " " + arr1[i] + " : " + arr2[i] );
                return false;
            }
        }
        return true;
    }
    
    public static void addUsingThreads(int[] arr, int add, int threads)
    {
        int arrlen = arr.length;
        int len = arrlen / threads;
        ArrayAdd[] adders = new ArrayAdd[threads];
        Thread[] adderThreads = new Thread[threads];
        
        for (int i=0; i<threads; i++)
        {
            int from = i * len;
            int to = from + len;
            if (i == threads - 1)
            {
                to = arrlen;
            }
            // System.out.println(from + ", " + to);
            adders[i] = new ArrayAdd(arr, from, to, add);
            adderThreads[i] = new Thread(adders[i]);
            adderThreads[i].start();
        }
        for (int i=0; i<threads; i++)
        {
            try
            {
                adderThreads[i].join();
            }
            catch (InterruptedException ie)
            {
            }
        }
    }
    
    public static void addUsingExecutorThreads(int[] arr, int add, int threads)
    {
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        ArrayList<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();

        int arrlen = arr.length;
        int len = arrlen / threads;
        for (int i=0; i<threads; i++)
        {
            int from = i * len;
            int to = from + len;
            if (i == threads - 1)
            {
                to = arrlen;
            }
            tasks.add(new ArrayAdd(arr, from, to, add));
        }
        try
        {
            List<Future<Integer>> results = exec.invokeAll(tasks);
            exec.shutdown();
        }
        catch (InterruptedException e)
        {
        }
    }

    public static void addJavaParallel(int[] arr)
    {
        // IntUnaryOperator i = (x) -> x + 1;
        Arrays.parallelSetAll(arr, x -> arr[x] + 1);
    }
    
    public static void testBasic()
    {
        int[] myArray = getArray(0, 500, 3);
        System.out.println(Arrays.toString(myArray));
        addOne(myArray, true);
        System.out.println(Arrays.toString(myArray));
    }
    
    public static void testPerformance()
    {
        int arraySize = 50000000;
        int[] myArray = getArray(0, arraySize, 1);
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i=0; i<10; i++)
        {
            addOne(myArray, false);
            // Arrays.setAll(myArray, x -> myArray[x] + 1);
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        long seqTime = endTime - startTime;
        System.out.println("Sequential time = " + seqTime);
        
        int[] myArray2 = getArray(0, arraySize, 1);
        startTime = Calendar.getInstance().getTimeInMillis();
        // addOne(myArray2, true);
        // addUsingThreads(myArray2, 1, 4);
        // addJavaParallel(myArray2);
        for (int i=0; i<10; i++)
        {
            addOne(myArray2, true);
            // Arrays.parallelSetAll(myArray2, x -> myArray2[x] + 1);
            // addUsingThreads(myArray2, 1, 4);
            // addUsingExecutorThreads(myArray2, 1, 4);
        }
        endTime = Calendar.getInstance().getTimeInMillis();
        long parTime = endTime - startTime;
        System.out.println("Parallel time = " + (endTime - startTime));
        System.out.println(String.format("Gain = %10.3f", seqTime * 1.0 / parTime));
        
        
        System.out.println("Matching result = " + isEqual(myArray, myArray2));
    }
    
    public static void main(String[] args)
    {
        testPerformance();
    }

}
