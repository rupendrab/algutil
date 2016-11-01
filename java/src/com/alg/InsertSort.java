package com.alg;

import java.lang.reflect.Array;
import java.util.Arrays;

public class InsertSort<T extends Comparable<T>>
{
    T[] items;
    int itemCount;
    boolean inPlace;
    Class<T> clazz;
    
    public InsertSort(T[] items, boolean inPlace, Class<T> clazz)
    {
        super();
        this.items = items;
        this.itemCount = items.length;
        this.inPlace = inPlace;
        this.clazz = clazz;
    }
    
    public InsertSort(T[] items, boolean inPlace)
    {
        super();
        this.items = items;
        this.itemCount = items.length;
        this.inPlace = inPlace;
    }

    public void sort(T[] src, T[] dest)
    {
        if (src == null || dest == null)
        {
            return;
        }
        if (src.length != dest.length)
        {
            return;
        }
        if (src.length == 0)
        {
            return;
        }
        
        for (int i=0; i<src.length; i++)
        {
            dest[i] = src[i];
        }
        
        // System.out.println(String.format("i = %d, Array = %s", 0, Arrays.toString(dest)));
        for (int i=1; i<dest.length; i++)
        {
            int j = i-1;
            T val = dest[i];
            int nPos = j;
            while (dest[j].compareTo(val) > 0)
            {
                dest[j+1] = dest[j];
                nPos = j;
                j -= 1;
                if (j < 0)
                {
                    break;
                }
            }
            if (j < i-1)
            {
                dest[nPos] = val;
            }
            // System.out.println(String.format("i = %d, Array = %s", i, Arrays.toString(dest)));
        }
    }
    
    public T[] sort()
    {
        if (inPlace)
        {
            sortInPlace();
            return items;
        }
        else
        {
            @SuppressWarnings("unchecked")
            T[] dest = (T[]) Array.newInstance(clazz, items.length);
            sort(items, dest);
            return dest;
        }
    }
    
    public void sortInPlace()
    {
        sort(items, items);
    }
    
    public static void main(String[] args)
    {
        Integer[] vals = {3,2,4,5,6,1,2,3};
        InsertSort<Integer> is = new InsertSort<>(vals, false, Integer.class);
        Integer[] sorted = is.sort();
        System.out.println(Arrays.toString(sorted));
        Arrays.sort(vals);
        System.out.println(Arrays.toString(vals));
    }
}
