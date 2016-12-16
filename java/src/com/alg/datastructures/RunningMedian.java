package com.alg.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class RunningMedian<T extends Comparable<? super T>>
{
    MinHeap<T> left;
    MinHeap<T> right;
    
    public RunningMedian(T[] arr)
    {
        super();
        // Left Heap is configured as a Max Heap
        left = new MinHeap<T>(arr, new Comparator<T>() {

            @Override
            public int compare(T o1, T o2)
            {
                return o2.compareTo(o1);
            }
        });
        right = new MinHeap<T>(arr);
    }
    
    public void addItem(T val)
    {
        int ls = left.getSize();
        int rs = right.getSize();
        if (ls == 0 && rs == 0) // Very First Item
        {
            left.insertItem(val);
        }
        else
        {
            T leftTop = left.topValue();
            T rightTop = right.topValue();
            if (left.compareTo(val, leftTop) >= 0) // New value
            {
                left.insertItem(val);
                if (ls > rs)
                {
                    leftTop = left.takeFirst();
                    right.insertItem(leftTop);
                }
            }
            else
            {
                right.insertItem(val);
                if (rs > ls)
                {
                    rightTop = right.takeFirst();
                    left.insertItem(rightTop);
                }
            }
        }
    }
    
    public ArrayList<T> median()
    {
        ArrayList<T> medians = new ArrayList<>();
        int ls = left.getSize();
        int rs = right.getSize();
        if (ls == rs)
        {
            medians.add(left.topValue());
            medians.add(right.topValue());
        }
        else if (ls > rs)
        {
            medians.add(left.topValue());
        }
        else
        {
            medians.add(right.topValue());
        }
        return medians;
    }
    
    public static void test01()
    {
        RunningMedian<Integer> rm = new RunningMedian<>(new Integer[] {});
        Random rand = new Random();
        ArrayList<Integer> data = new ArrayList<>();
        for (int i=0; i<10; i++)
        {
            Integer val = 1 + rand.nextInt(100);
            data.add(val);
            rm.addItem(val);
            Collections.sort(data);
            System.out.println(String.format("%d\t%-20s\t%s", val, rm.median(), data));
        }
    }
    
    public static void main(String[] args)
    {
        test01();
    }
    
}
