package com.alg;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.swing.JFrame;

import com.alg.display.BinaryTreeDisplay;

public class MinHeapInt extends BinaryTree
{
    Integer[] heap = new Integer[2];
    int K = 1;
    private int size;
    
    public MinHeapInt()
    {
        this.K = 1;
        this.size = 0;
    }
    
    public MinHeapInt(int value)
    {
        this.K = 1;
        heap[K] = value;
        this.size = 1;
    }
    
    public MinHeapInt(MinHeapInt old, int K)
    {
        this.K = K;
        this.heap = old.heap;
        this.size = old.size;
    }
    
    public MinHeapInt clone()
    {
        Integer[] newHeap = new Integer[heap.length];
        System.arraycopy(heap, 1, newHeap, 1, heap.length-1);
        MinHeapInt n = new MinHeapInt();
        n.heap = newHeap;
        n.K = K;
        n.size = size;
        return n;
    }
    
    private void doubleHeap()
    {
        Integer[] oldHeap = heap;
        heap = new Integer[heap.length * 2];
        // System.out.println(oldHeap.length + " to " + heap.length);
        System.arraycopy(oldHeap, 1, heap, 1, oldHeap.length-1);
    }
    
    public void insertItem(Integer value)
    {
        if (size == heap.length - 1)
        {
            doubleHeap();
        }
        heap[++size] = value;
        percolateUp(size);
    }
    
    public void exchange(int pos1, int pos2)
    {
        Integer temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }
    
    public void percolateUp(int pos)
    {
        while (pos > 1)
        {
            int parentPos = pos/2;
            if (heap[parentPos] > heap[pos])
            {
                exchange(pos, parentPos);
                pos = parentPos;
            }
            else
            {
                break;
            }
        }
    }

    public void percolateDown(int pos)
    {
        while (pos <= size/2)
        {
            // System.out.println("percolateDown: " + Arrays.toString(heap));
            int childPos = pos * 2;
            if (childPos > size)
            {
                break;
            }
            if (childPos < size && heap[childPos] > heap[childPos+1])
            {
                childPos++;
            }
            if (heap[pos] > heap[childPos])
            {
                exchange(pos, childPos);
                pos = childPos;
            }
            else
            {
                break;
            }
        }
    }
    
    public int deleteMin()
    {
        int v = heap[1];
        exchange(1, size);
        size--;
        percolateDown(1);
        return v;
    }
    
    public Integer getVal(int pos)
    {
        if (pos < heap.length)
        {
            return heap[pos];
        }
        else
        {
            return null;
        }
    }
    
    public MinHeapInt getLeft()
    {
        if (getVal(2 * K) == null || 2 * K > size)
        {
            return null;
        }
        return new MinHeapInt(this, 2 * K);
    }

    public MinHeapInt getRight()
    {
        if (getVal(2 * K + 1) == null || 2 * K + 1 > size)
        {
            return null;
        }
        return new MinHeapInt(this, 2 * K + 1);
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    
    public int getValue()
    {
        return heap[K];
    }
    
    public BinaryTreeDisplay displayHeap()
    {
        JFrame jFrame = new JFrame();
        BinaryTreeDisplay btd = new BinaryTreeDisplay(this);
        jFrame.setLayout(new BorderLayout());
        jFrame.add(btd, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(btd.getScreenWidth(), btd.getScreenHeight());
        jFrame.setVisible(true);
        return btd;
    }
    
    public static void main(String[] args)
    {
        Integer[] arr = {2, 1, 3, 5, 9, 0, 4};
        MinHeapInt mhi = new MinHeapInt();
        for (Integer v : arr)
        {
            mhi.insertItem(v);
        }
        /*
        System.out.println(Arrays.toString(mhi.heap));
        System.out.println(mhi.getValue());
        System.out.println(mhi.getLeft().getValue());
        System.out.println(mhi.getRight().getValue());
        System.out.println(mhi.getLeft().getLeft().getValue());
        System.out.println(mhi.getLeft().getRight().getValue());
        System.out.println("Level = " + mhi.level());
        */
        BinaryTreeDisplay orig = mhi.displayHeap();
        mhi.insertItem(6);
        mhi.insertItem(10);
        mhi.insertItem(10);
        mhi.insertItem(10);
        orig.revalidate();
        MinHeapInt mhi2 = mhi.clone();
        mhi2.insertItem(20);
        BinaryTreeDisplay orig2 = mhi2.displayHeap();
        // System.out.println(Arrays.toString(mhi2.heap));
        // System.out.println(mhi2.size);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press enter to continue at each step...");
        while (mhi2.size > 0)
        {
            try
            {
                reader.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.exit(0);
            }
            System.out.println("Min = " + mhi2.deleteMin());
            orig2.revalidate();
        }
        // System.out.println(orig.getParent().getParent().getParent().getParent());
        // orig.getParent().getParent().getParent().getParent().setSize(orig.getScreenWidth(), orig.getScreenHeight());
        /*
        System.out.println(mhi.getLeft().level());
        System.out.println(mhi.getRight().level());
        System.out.println(mhi.getLeft().getLeft().level());
        */
        // System.out.println(Arrays.toString(mhi2.heap));
        // System.out.println(mhi2.size);
    }

}
