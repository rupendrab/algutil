package com.alg.graph.dijkstra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MinHeap
{
    VertexScore[] heap = new VertexScore[2];
    int K = 1;
    private int size;
    HashMap<Integer, Integer> vertexPosition = new HashMap<>();
    
    public MinHeap()
    {
        this.K = 1;
        this.size = 0;
    }
    
    public MinHeap(VertexScore value)
    {
        this.K = 1;
        heap[K] = value;
        this.size = 1;
    }
    
    public MinHeap(MinHeap old, int K)
    {
        this.K = K;
        this.heap = old.heap;
        this.size = old.size;
    }

    private void doubleHeap()
    {
        VertexScore[] oldHeap = heap;
        heap = new VertexScore[heap.length * 2];
        // System.out.println(oldHeap.length + " to " + heap.length);
        System.arraycopy(oldHeap, 1, heap, 1, oldHeap.length-1);
    }
    
    public void exchange(int pos1, int pos2)
    {
        vertexPosition.put(heap[pos1].vertex, pos2);
        vertexPosition.put(heap[pos2].vertex, pos1);
        VertexScore temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }
    
    public int percolateUp(int pos)
    {
        while (pos > 1)
        {
            int parentPos = pos/2;
            if (heap[parentPos].compareTo(heap[pos]) > 0)
            {
                exchange(pos, parentPos);
                pos = parentPos;
            }
            else
            {
                return pos;
            }
        }
        return 1;
    }
    
    public void percolateDown(int pos)
    {
        // System.out.println("Percolatwdown pos = " + pos);
        while (pos <= size/2)
        {
            // System.out.println("percolateDown: " + Arrays.toString(heap));
            int childPos = pos * 2;
            if (childPos > size)
            {
                break;
            }
            if (childPos < size && heap[childPos].compareTo(heap[childPos+1]) > 0)
            {
                childPos++;
            }
            // System.out.println("  PercolateDown childPos = " + childPos);
            // System.out.println(heap[pos] + " *** " + heap[childPos] + " *** " + heap[pos].compareTo(heap[childPos]));
            if (heap[pos].compareTo(heap[childPos]) > 0)
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
    
    public void check()
    {
        for (int i=1; i<=size; i++)
        {
            VertexScore vs = heap[i];
            int pos = vertexPosition.get(vs.vertex);
            if (i != pos)
            {
                System.err.println("Failed at position " + i);
            }
        }
    }
    
    public int insertItem(VertexScore value)
    {
        if (size == heap.length - 1)
        {
            doubleHeap();
        }
        heap[++size] = value;
        vertexPosition.put(value.vertex, size);
        int pos = percolateUp(size);
        return pos;
    }

    public VertexScore deleteMin()
    {
        VertexScore v = heap[1];
        exchange(1, size);
        size--;
        percolateDown(1);
        return v;
    }
    
    public VertexScore getVertex(int vertex)
    {
        Integer pos = vertexPosition.get(vertex);
        if (pos == null)
        {
            return null;
        }
        else
        {
            VertexScore v = heap[pos];
            return v;
        }
    }
    
    public VertexScore deleteVertex(int vertex)
    {
        Integer pos = vertexPosition.get(vertex);
        if (pos == null)
        {
            return null;
        }
        VertexScore v = heap[pos];
        exchange(pos, size);
        size--;
        percolateDown(pos);
        percolateUp(pos);
        return v;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    
    public VertexScore getValue()
    {
        return heap[K];
    }
    
    public boolean isValid()
    {
        for (int i=1; i<=size/2; i++)
        {
            VertexScore p = heap[i];
            if (2 * i <= size)
            {
                VertexScore c = heap[2 * i];
                if (p.compareTo(c) > 0)
                {
                    System.out.println("Error: Parent = " + p + ", Child = " + c);
                    return false;
                }
            }
            if (2 * i + 1 <= size)
            {
                VertexScore c = heap[2 * i + 1];
                if (p.compareTo(c) > 0)
                {
                    System.out.println("Error: Parent = " + p + ", Child = " + c);
                    return false;
                }
            }
        }
        return true;
    }
    
    public VertexScore[] getArray()
    {
        return heap;
    }
    
    public static void test01()
    {
        MinHeap mh = new MinHeap();
        Random rand = new Random();
        for (int i=0; i<20; i++)
        {
            int vertex = 1 + i;
            int score = rand.nextInt(100);
            VertexScore vs = new VertexScore(vertex, score);
            int pos = mh.insertItem(vs);
            /*
            System.out.println(String.format("VertexScore %s inserted at position %d", vs, pos));
            System.out.println(Arrays.toString(mh.heap));
            System.out.println(mh.vertexPosition);
            */
            mh.check();
        }
        System.out.println("Size of Min Heap = " + mh.getSize());
        System.out.println(Arrays.toString(mh.heap));
        System.out.println(mh.vertexPosition);
        int n = mh.getSize();
        for (int i=0; i<n; i++)
        {
            System.out.println(mh.deleteMin());
            mh.check();
        }
        System.out.println(Arrays.toString(mh.heap));
        System.out.println(mh.vertexPosition);
        System.out.println(mh.size);
    }
    
    public static void test02()
    {
        MinHeap mh = new MinHeap();
        Random rand = new Random();
        for (int i=0; i<20; i++)
        {
            int vertex = 1 + i;
            int score = rand.nextInt(100);
            VertexScore vs = new VertexScore(vertex, score);
            int pos = mh.insertItem(vs);
            mh.check();
        }
        int[] toDelete = {1,10,20,13,15,17};
        for (int vertex : toDelete)
        {
            mh.deleteVertex(vertex);
            mh.check();
            System.out.println(mh.size);
        }
        System.out.println(Arrays.toString(mh.heap));
        System.out.println(mh.vertexPosition);
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01();
        test02();
    }


}
