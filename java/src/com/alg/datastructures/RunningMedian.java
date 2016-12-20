package com.alg.datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.alg.graph.bst.BinarySearchTree;

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
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static int test02(String fileName) throws IOException
    {
        long start, end = 0;
        start = time();
        RunningMedian<Integer> rm = new RunningMedian<>(new Integer[] {});
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        long sumOfMedians = 0;
        while((line = reader.readLine()) != null)
        {
            lineNo++;
            try
            {
                Integer val = Integer.parseInt(line);
                rm.addItem(val);
                ArrayList<Integer> medians = rm.median();
                Integer median = medians.get(0);
                sumOfMedians += median;
            }
            catch (NumberFormatException ne)
            {
                System.err.println(String.format("Invalid Integer at line %d = %s", lineNo, line));
            }
        }
        reader.close();
        end = time();
        System.out.println(String.format("Time elapsed in test02 = %d ms", (end - start)));
        return (int) (sumOfMedians % 10000);
    }
    
    public static int test03(String fileName) throws IOException
    {
        long start, end = 0;
        start = time();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        long sumOfMedians = 0;
        while((line = reader.readLine()) != null)
        {
            lineNo++;
            try
            {
                Integer val = Integer.parseInt(line);
                bst.insert(val);
                Integer median = bst.median();
                sumOfMedians += median;
            }
            catch (NumberFormatException ne)
            {
                System.err.println(String.format("Invalid Integer at line %d = %s", lineNo, line));
            }
        }
        reader.close();
        end = time();
        System.out.println(String.format("Time elapsed in test03 = %d ms", (end - start)));
        System.out.println(bst.computeHeight());
        return (int) (sumOfMedians % 10000);
    }

    public static void main(String[] args) throws Exception
    {
        // test01();
        System.out.println(test02("C:\\Users\\rubandyopadhyay\\Downloads\\Median.txt"));
        System.out.println(test03("C:\\Users\\rubandyopadhyay\\Downloads\\Median.txt"));
    }
    
}
