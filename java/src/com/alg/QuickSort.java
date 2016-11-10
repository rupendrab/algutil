package com.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;

public class QuickSort<T extends Comparable<T>>
{
    T[] items;
    int itemCount;
    boolean inPlace;
    Class<T> clazz;
    int noPartitionCalls = 0;
    int noPrinted = 0;
    Hashtable<Integer, Long> partitionEfficiency = new Hashtable<Integer, Long>();

    public QuickSort(T[] items, boolean inPlace)
    {
        super();
        this.items = items;
        this.itemCount = items.length;
        this.inPlace = inPlace;
    }

    public QuickSort(T[] items, boolean inPlace, Class<T> clazz)
    {
        this(items, inPlace);
        this.clazz = clazz;
    }
    
    public void switchValues(T[] arr, int i, int j)
    {
      T val = arr[i];
      arr[i] = arr[j];
      arr[j] = val;
    }
    
    /*
     * from and to are inclusive
     */
    public int partition(T[] arr, int from, int to)
    {
        noPartitionCalls += 1;
        if (to - from <= 1)
        {
            return from;
        }
        T[] orig = Arrays.copyOfRange(arr, from, to);
        // System.out.println(from + ":" + to + " " + Arrays.toString(orig) );
        int r = to - 1;
        r = (from + to) / 2;
        int q = from;
        int j = from;
        T pivot = arr[r];
        // System.out.println("Pivot = " + pivot);
        while (j < to)
        {
            if (arr[j].compareTo(pivot) > 0)
            {
                j++;
            }
            else
            {
                switchValues(arr, q, j);
                if (j == r)
                {
                    r = q;
                }
                q++;
                j++;
            }
            // System.out.println(String.format("At j = %d, q = %d, Arr = %s", j, q, Arrays.toString(Arrays.copyOfRange(arr, from, to))));
        }
        // if (q == to) q--;
        // System.out.println(String.format("Switching %d with %d", r, q-1));
        switchValues(arr, r, q-1);
        // System.out.println(Arrays.toString(Arrays.copyOfRange(arr, from, to)));
        
        /*
        boolean valid = true;
        for (int i=from; i<to; i++)
        {
            if (i < q && arr[i].compareTo(arr[q-1]) > 0)
            {
                valid = false;
                break;
            }
            if (i > q && arr[i].compareTo(arr[q-1]) < 0)
            {
                valid = false;
                break;
            }
        }
        if (! valid)
        {
            System.out.println("Problem....");
            for (int i=from; i<to; i++)
            {
                System.out.print(arr[i] + ", ");
            }
            System.out.println();
            System.out.println(String.format("Pivot = %s position = %d", arr[q], q-from));
            System.out.println(Arrays.toString(orig));
        }
        */
        int partEff = (int) ((q - from) * 100.0 / (to - from));
        Long eff = partitionEfficiency.get(partEff);
        if (eff == null)
        {
            eff = 1L;
        }
        else
        {
            eff += 1L;
        }
        partitionEfficiency.put(partEff, eff);
        if (eff == 100 && noPrinted < 10)
        {
            System.out.println("length = " + (to-from) + " : " + Arrays.toString(Arrays.copyOfRange(arr, from, to)));
            noPrinted += 1;
        }
        return q;
    }
    
    public void sortPart(T[] arr, int from, int to)
    {
        if (to - from <= 1)
        {
            return;
        }
        else if (to - from == 2)
        {
            if (arr[to-1].compareTo(arr[from]) < 0)
            {
                switchValues(arr, from, to-1);
            }
            return;
        }
        else if (to - from < 100)
        {
            // System.out.println("Invoking insertSort");
            T[] toSort = Arrays.copyOfRange(arr, from, to);
            InsertSort<T> ins = new InsertSort<>(toSort, true);
            ins.sort();
            for (int i=from; i<to; i++)
            {
                arr[i] = toSort[i-from];
            }
        }
        else
        {
            int mid = partition(arr, from, to);
            sortPart(arr, from, mid-1);
            sortPart(arr, mid, to);
        }
    }
    
    public T[] sort()
    {
        if (! inPlace)
        {
            T[] out = Arrays.copyOf(items, itemCount);
            sortPart(out, 0, itemCount);
            return out;
        }
        else
        {
            sortPart(items, 0, itemCount);
            return items;
        }
    }
    
    public static void basicTest()
    {
        Integer[] arr = {2,3,7,5,4,2,6};
        QuickSort<Integer> qs = new QuickSort<>(arr, true);
        int pos = qs.partition(qs.items, 0, qs.itemCount);
        System.out.println(pos);
        System.out.println(Arrays.toString(qs.items));
        qs.sortPart(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr));
    }
    
    public static void basicTest2()
    {
        // Integer[] arr = {8385, 8504, 9380, 8664, 9078, 9088};
        // Integer[] arr = {2152, 2301, 1423, 1692, 2115, 1504, 1338, 1632, 2340, 2435, 2494, 2220, 1905, 2417, 2052, 1314, 1867, 2429, 2147, 1705};
        // Integer[] arr = {2058, 170, 134, 575, 378, 256, 2062, 1863, 164, 54, 910, 356, 2323};
        Integer[] arr = {8580, 8367, 8282, 9108, 8384, 8363};
        QuickSort<Integer> qs = new QuickSort<>(arr, true);
        int q = qs.partition(arr, 0, arr.length);
        System.out.println(q);
        System.out.println(Arrays.toString(arr));
    }
    
    public static void perfTest()
    {
        long startTime = 0;
        long endTime = 0;
        int arraySize = 8000000;
        Integer[] toSort = ParallelMergeSort.generateRandomInts(0, 100000, arraySize);
        QuickSort<Integer> qs = new QuickSort<>(toSort, true);
        startTime = Calendar.getInstance().getTimeInMillis();
        Integer[] arrOut = qs.sort();
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to quick sort array of size %d = %d ms", arraySize, (endTime - startTime)));
        System.out.println(ParallelMergeSort.isSorted(arrOut));
        System.out.println("Number of partition calls = " + qs.noPartitionCalls);
        System.out.println("Partition efficiency table");
        System.out.println("==========================");
        ArrayList<Integer> keys = new ArrayList<Integer>(qs.partitionEfficiency.keySet());
        Collections.sort(keys);
        for (int key : keys)
        {
            System.out.println(String.format("%-4d %d", key, qs.partitionEfficiency.get(key)));
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        // basicTest2();
        Timer t = new Timer(10000);
        Thread t1 = new Thread(t);
        t1.start();
        perfTest();
        t.setStop(true);
        t1.join();
    }
}
