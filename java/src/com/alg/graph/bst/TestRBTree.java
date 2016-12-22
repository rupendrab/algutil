package com.alg.graph.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class TestRBTree
{
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void printElapsed(String str, long start, long end)
    {
        System.out.println(String.format("Time elapsed for %s : %d ms", str, (end-start)));
    }
    
    public static void test01(int cnt)
    {
        long start, end = 0;
        boolean DEBUG = false;
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        rbt.setDEBUG(DEBUG);
        start = time();
        for (int i=0; i<cnt; i++)
        {
            rbt = rbt.insert(i);
            if (DEBUG)
            {
                System.out.println(rbt.toString());
            }
            /*
            if ( (i+1) % 100000 == 0)
            {
                System.out.println("Processed " + i + " records...");
            }
            */
        }
        end = time();
        printElapsed("Time to insert", start, end);
        System.out.println("Height = " + rbt.computeHeight());
        System.out.println("Size = " + rbt.size);
        System.out.println("OK = " + rbt.checkTree());
        System.out.println("Balanced = " + rbt.isBalanced());
    }
    
    public static void test02()
    {
        long start, end = 0;
        boolean DEBUG = true;
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        rbt.setDEBUG(DEBUG);
        Random rand = new Random();
        start = time();
        Integer[] arr = new Integer[100];
        for (int i=0; i<arr.length; i++)
        {
            arr[i] = rand.nextInt(arr.length);
        }
        if (arr.length <= 1000)
        {
            System.out.println(Arrays.toString(arr));
        }
        for (int i=0; i<arr.length; i++)
        {
            System.out.println("Inserting " + arr[i]);
            rbt = rbt.insert(arr[i]);
            if (DEBUG)
            {
                System.out.println(rbt.toString());
            }
        }
        end = time();
        printElapsed("Time to insert", start, end);
        System.out.println("Height = " + rbt.computeHeight());
        System.out.println("Size = " + rbt.size);
    }

    public static void test02a(int cnt)
    {
        long start, end = 0;
        boolean DEBUG = false;
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        rbt.setDEBUG(DEBUG);
        Random rand = new Random();
        start = time();
        ArrayList<Integer> used = new ArrayList<>();
        for (int i=0; i<cnt; i++)
        {
            Integer n = rand.nextInt(cnt);
            used.add(n);
            rbt = rbt.insert(n);
            rbt.setDEBUG(DEBUG);
            if (DEBUG)
            {
                System.out.println(rbt.toString());
            }
        }
        end = time();
        printElapsed("Time to insert", start, end);
        System.out.println("Height = " + rbt.computeHeight());
        System.out.println("Size = " + rbt.size);
        System.out.println("OK = " + rbt.checkTree());
        System.out.println("Balanced = " + rbt.isBalanced());
        if (rbt.size <= 10)
        {
            System.out.println(rbt);
            System.out.println(used);
        }
    }

    public static void test03()
    {
        long start, end = 0;
        boolean DEBUG = true;
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        rbt.setDEBUG(DEBUG);
        start = time();
        // Integer[] arr = {0, 4, 9, 9, 7, 9, 4, 5, 5, 8};
        // Integer[] arr = {0, 4, 9, 9, 7};
        // Integer[] arr = {5, 0, 6, 1, 4, 3, 5, 6, 1, 0};
        // Integer[] arr = {8, 3, 8, 5, 6, 0, 6, 3, 6, 6};
        Integer[] arr = {77, 6, 39, 68, 9, 15, 47, 68, 64, 2, 38, 99, 52, 69, 68, 15, 21, 17, 10, 27};
        System.out.println(Arrays.toString(arr));
        for (int i=0; i<arr.length; i++)
        {
            System.out.println("Inserting " + arr[i]);
            rbt = rbt.insert(arr[i]);
            rbt.setDEBUG(DEBUG);
            if (DEBUG)
            {
                System.out.println(rbt.toString());
                System.out.println("Balanced = " + rbt.isBalanced());
            }
        }
        end = time();
        printElapsed("Time to insert", start, end);
        System.out.println("Height = " + rbt.computeHeight());
        System.out.println("Size = " + rbt.size);
        System.out.println("Balanced = " + rbt.isBalanced());
        System.out.println("Median = " + rbt.median());
    }
    
    public static void testMedian()
    {
        // Integer[] arr = TestBST.createArray(1000000, 10000000);
        Integer[] arr = TestBST.createArray(20, 100);
        if (arr.length <= 20)
        {
            System.out.println(Arrays.toString(arr));
        }
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        for (Integer val :arr)
        {
            rbt = rbt.insert(val);
        }
        Integer rbtMedian = rbt.median();
        System.out.println("RBT Median = " + rbtMedian);
        if (arr.length <= 20)
        {
            System.out.println(rbt);
            System.out.println(rbt.size);
        }
        Arrays.sort(arr);
        int pos = arr.length / 2;
        if (arr.length % 2 == 0)
        {
            pos -= 1;
        }
        Integer actualMedian = arr[pos];
        System.out.println("Actual Median = " + actualMedian);
        System.out.println("Matched = " + (actualMedian.equals(rbtMedian)));
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01(10000000);
        // test02();
        // test02a(10000000);
        // test03();
        testMedian();
    }

}
