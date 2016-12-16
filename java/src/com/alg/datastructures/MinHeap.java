package com.alg.datastructures;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;

public class MinHeap<T extends Comparable<? super T>>
{
    T[] data;
    int size;
    Comparator<? super T> comparator;

    @SuppressWarnings("unchecked")
    public MinHeap(T[] a)
    {
        size = 0;
        data = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 2);
    }

    @SuppressWarnings("unchecked")
    public MinHeap(T[] a, Comparator<? super T> comparator)
    {
        size = 0;
        data = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 2);
        this.comparator = comparator;
    }
    
    public MinHeap(T[] data, boolean heapify)
    {
        super();
        this.data = data;
        size = data.length;
        if (heapify)
        {
            heapify();
        }
    }
    
    public MinHeap(T[] data, boolean heapify, Comparator<? super T> comparator)
    {
        super();
        this.data = data;
        size = data.length;
        if (heapify)
        {
            heapify();
        }
        this.comparator = comparator;
    }
    
    public int compareTo(T a, T b)
    {
        if (comparator == null)
        {
            return a.compareTo(b);
        }
        else
        {
            return comparator.compare(a, b);
        }
    }

    public int getSize()
    {
        return size;
    }

    private void swap(int i, int j)
    {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private int leftChildPos(int pos)
    {
        return 2 * (pos + 1) - 1;
    }

    private int rightChildPos(int pos)
    {
        return 2 * (pos + 1);
    }

    private int parentPos(int pos)
    {
        if (pos == 0)
        {
            return pos;
        }
        else
        {
            return ((pos + 1) / 2) - 1;
        }
    }

    private int smaller(int i, int j)
    {
        if (compareTo(data[i], data[j]) <= 0)
        {
            return i;
        }
        else
        {
            return j;
        }
    }

    private void bubbleDown(int pos)
    {
        int leftPos = leftChildPos(pos);
        int rightPos = rightChildPos(pos);
        boolean hasLeft = leftPos < size;
        boolean hasRight = rightPos < size;
        // System.out.println(String.format("left = %d, rigth = %d", leftPos,
        // rightPos));
        if (hasLeft && hasRight)
        {
            if (compareTo(data[pos], data[leftPos]) > 0 || compareTo(data[pos], data[rightPos]) > 0)
            {
                int smallerPos = smaller(leftPos, rightPos);
                swap(pos, smallerPos);
                bubbleDown(smallerPos);
            }
        }
        else if (hasLeft)
        {
            if (compareTo(data[pos], data[leftPos]) > 0)
            {
                swap(pos, leftPos);
            }
        }
        else if (hasRight)
        {
            if (compareTo(data[pos], data[rightPos]) > 0)
            {
                swap(pos, rightPos);
            }
        }
    }

    private void bubbleUp(int pos)
    {
        if (pos == 0)
        {
            return;
        }
        int parentPos = parentPos(pos);
        if (compareTo(data[pos], data[parentPos]) < 0)
        {
            swap(pos, parentPos);
            bubbleUp(parentPos);
        }
    }

    public void insertItem(T item)
    {
        if (size == data.length - 1)
        {
            doubleSize();
        }
        data[size++] = item;
        bubbleUp(size - 1);
    }
    
    public T takeFirst()
    {
        T ret = topValue();
        swap(0, size-1);
        size--;
        bubbleDown(0);
        return ret;
    }

    private void heapify()
    {
        int startPos = parentPos(size - 1);
        for (int i = startPos; i >= 0; i--)
        {
            // System.out.println(i);
            bubbleDown(i);
        }
    }

    public boolean isValid()
    {
        for (int pos = 0; pos < size / 2 + 10; pos++)
        {
            int leftPos = leftChildPos(pos);
            int rightPos = rightChildPos(pos);
            if (leftPos < size && compareTo(data[pos], data[leftPos]) > 0)
            {
                return false;
            }
            if (rightPos < size && compareTo(data[pos], data[rightPos]) > 0)
            {
                return false;
            }
        }
        return true;
    }

    public T topValue()
    {
        return data[0];
    }

    private void doubleSize()
    {
        T[] oldData = data;
        data = multiplyArray(2);
        System.arraycopy(oldData, 0, data, 0, size);
    }

    @SuppressWarnings("unchecked")
    private T[] multiplyArray(int times)
    {
        return (T[]) java.lang.reflect.Array.newInstance(data.getClass().getComponentType(), (size+1) * times);
    }

    public static Integer[] createArray(int arrayLength)
    {
        Integer[] ret = new Integer[arrayLength];
        Random rand = new Random();
        for (int i = 0; i < arrayLength; i++)
        {
            ret[i] = rand.nextInt(1000000);
        }
        return ret;
    }
    
    public static <T extends Comparable<T>> boolean isSorted(T[] arr)
    {
        boolean first = true;
        T prevElem = null;
        for(T elem : arr)
        {
            if (! first && elem.compareTo(prevElem) < 0)
            {
                return false;
            }
            first = false;
            prevElem = elem;
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] arr, Comparator<? super T> comparator)
    {
        boolean first = true;
        T prevElem = null;
        for(T elem : arr)
        {
            if (! first && comparator.compare(elem, prevElem) < 0)
            {
                return false;
            }
            first = false;
            prevElem = elem;
        }
        return true;
    }

    public static void test01()
    {
        Integer[] data = { 3, 4, 5, 6, 9, 10, 5, 5, 4, 11, 2, 3, 4, 5, 6, 7 };
        MinHeap<Integer> mh = new MinHeap<>(data, true);
        System.out.println(mh.topValue());
        System.out.println(Arrays.toString(mh.data));
        System.out.println(mh.isValid());
    }

    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static void test02()
    {
        Random rand = new Random();
        boolean ok = true;
        long start, end = 0;
        long time = 0;
        int arrayLen = 2000000;
        for (int i = 0; i < 100; i++)
        {
            Integer[] arr = createArray(arrayLen + rand.nextInt(1000));
            start = time();
            MinHeap<Integer> mh = new MinHeap<>(arr, true);
            end = time();
            time += (end - start);
            ok = mh.isValid();
            if (!ok)
            {
                System.out.println("Test failed in iteration " + (i + 1));
            }
        }
        System.out.println("test02: All tests passed");
        System.out.println(String.format("Time to heapify %d records = %f ms", arrayLen, time * 1.0 / 100));
    }

    public static void test03()
    {
        MinHeap<Integer> mh = new MinHeap<Integer>(new Integer[] {});
        mh.insertItem(10);
        mh.insertItem(5);
        mh.insertItem(3);
        mh.insertItem(11);
        mh.insertItem(2);
        mh.insertItem(1);
        mh.insertItem(20);
        System.out.println(Arrays.toString(mh.data));
        System.out.println(mh.topValue());
    }
    
    public static void test04(int numElements)
    {
        Integer[] arr = createArray(numElements);
        long start, end = 0;
        start = time();
        MinHeap<Integer> mh = new MinHeap<Integer>(new Integer[] {});
        for (Integer v : arr)
        {
            mh.insertItem(v);
        }
        Integer[] sorted = new Integer[arr.length];
        int i = 0;
        while(mh.size > 0)
        {
            sorted[i++] = mh.takeFirst();
        }
        end = time();
        System.out.println(String.format("Time to sort = %d ms", (end-start)));
        if (numElements <= 100)
        {
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.toString(sorted));
        }
        System.out.println(String.format("Original array sorted = %s, New Array Sorted = %s", isSorted(arr), isSorted(sorted)));
    }

    public static void test05(int numElements)
    {
        Integer[] arr = createArray(numElements);
        long start, end = 0;
        start = time();
        MinHeap<Integer> mh = new MinHeap<Integer>(new Integer[] {}, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2 - o1;
            }
        });
        for (Integer v : arr)
        {
            mh.insertItem(v);
        }
        Integer[] sorted = new Integer[arr.length];
        int i = 0;
        while(mh.size > 0)
        {
            sorted[i++] = mh.takeFirst();
        }
        end = time();
        System.out.println(String.format("Time to sort = %d ms", (end-start)));
        if (numElements <= 100)
        {
            System.out.println(Arrays.toString(arr));
            System.out.println(Arrays.toString(sorted));
        }
        System.out.println(String.format("Original array sorted = %s, New Array Sorted = %s", 
                isSorted(arr), 
                isSorted(sorted, new Comparator<Integer>() {

                    @Override
                    public int compare(Integer o1, Integer o2)
                    {
                        return o2 - o1;
                    }
                })));
    }


    public static void main(String[] args) throws Exception
    {
        // test01();
        // test02();
        // test03();
        test04(1000);
        test05(10);
    }

}
