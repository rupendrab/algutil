package com.alg;

import java.util.concurrent.Callable;

public class ArrayAdd implements Runnable, Callable<Integer>
{
    int[] arr;
    int from;
    int to;
    int add;
    
    public ArrayAdd(int[] arr, int from, int to, int add)
    {
        super();
        this.arr = arr;
        this.from = from;
        this.to = to;
        this.add = add;
    }

    @Override
    public void run()
    {
        for (int i=from; i<to; i++)
        {
            arr[i] += add;
            if (i % 10000 == 0)
            {
                sleep(1);
            }
        }
    }

    @Override
    public Integer call() throws Exception
    {
        for (int i=from; i<to; i++)
        {
            arr[i] += add;
            if (i % 10000 == 0)
            {
                sleep(1);
            }
        }
        return to - from;
    }
    
    public static void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ie)
        {
        }
    }

}
