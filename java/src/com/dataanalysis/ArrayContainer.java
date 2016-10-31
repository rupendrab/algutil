package com.dataanalysis;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class ArrayContainer implements Comparable<ArrayContainer>
{
    CountRecord[] records;
    int index;

    public ArrayContainer(CountRecord[] records, int index)
    {
        super();
        this.records = records;
        this.index = index;
    }

    @Override
    public int compareTo(ArrayContainer o)
    {
        return records[index].compareTo(o.records[o.index]);
    }

    public static CountRecord[] mergeKSorted(Hashtable<Integer, CountRecord[]> kInputs)
    {
        PriorityQueue<ArrayContainer> queue = new PriorityQueue<ArrayContainer>();
        int totalCount = 0;
        for (Entry<Integer, CountRecord[]> item : kInputs.entrySet())
        {
            CountRecord[] recs = item.getValue();
            if (recs != null && recs.length > 0)
            {
                queue.add(new ArrayContainer(recs, 0));
                totalCount += recs.length;
            }
        }
        CountRecord[] results = new CountRecord[totalCount];
        int resultPos = 0;
        while (!queue.isEmpty())
        {
            ArrayContainer nxt = queue.poll();
            results[resultPos++] = nxt.records[nxt.index];
            if (nxt.index < nxt.records.length - 1) // More elements in array
            {
                queue.add(new ArrayContainer(nxt.records, nxt.index + 1));
            }
        }
        return results;
    }

    public static CountRecord[] mergeAddKSorted(Hashtable<Integer, CountRecord[]> kInputs)
    {
        PriorityQueue<ArrayContainer> queue = new PriorityQueue<ArrayContainer>();
        int totalCount = 0;
        for (Entry<Integer, CountRecord[]> item : kInputs.entrySet())
        {
            CountRecord[] recs = item.getValue();
            if (recs != null && recs.length > 0)
            {
                queue.add(new ArrayContainer(recs, 0));
                totalCount += recs.length;
            }
        }
        CountRecord[] results = new CountRecord[totalCount];
        int resultPos = 0;
        while (!queue.isEmpty())
        {
            ArrayContainer nxt = queue.poll();
            CountRecord nxtRec = nxt.records[nxt.index];
            if (resultPos == 0 || !results[resultPos - 1].word.equals(nxtRec.word))
            {
                results[resultPos++] = nxt.records[nxt.index]; // Add New
            }
            else
            {
                results[resultPos - 1].count += nxtRec.count; // Increment old
            }
            if (nxt.index < nxt.records.length - 1) // More elements in array
            {
                queue.add(new ArrayContainer(nxt.records, nxt.index + 1));
            }
        }
        return results;
    }
}
