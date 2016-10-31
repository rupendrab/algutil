package com.dataanalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class WordCountSlave implements Runnable
{
    private WordCountMaster master;
    int threadID;
    int bucketSize = WordCountMaster.DEFAULT_BUCKET_SIZE;

    Hashtable<String, CountRecord> counts = new Hashtable<String, CountRecord>(10000);
    Pattern p = Pattern.compile("\\s+");

    public WordCountSlave(WordCountMaster master, int threadID)
    {
        super();
        this.master = master;
        this.threadID = threadID;
    }

    public WordCountSlave(WordCountMaster master, int threadID, int bucketSize)
    {
        this(master, threadID);
        this.bucketSize = bucketSize;
    }

    public void processLines(ArrayList<String> lines)
    {
        for (String line : lines)
        {
            String[] words = p.split(line);
            for (String word : words)
            {
                CountRecord count = counts.get(word);
                if (count == null)
                {
                    counts.put(word, new CountRecord(word, 1));
                }
                else
                {
                    count.count += 1;
                }
            }
        }
    }

    public void processFinal()
    {
        Collection<CountRecord> wordSet = counts.values();
        CountRecord[] results = new CountRecord[wordSet.size()];
        wordSet.toArray(results);
        Arrays.sort(results);
        master.threadResults.put(threadID, results);
        master.threadStatus.put(threadID, 0);
        System.err.println("Records processed by thread " + threadID + " = " + results.length);
    }

    @Override
    public void run()
    {
        System.err.println("Processing thread: " + threadID);
        while (true)
        {
            try
            {
                ArrayList<String> lines = master.getNext(bucketSize);
                if (lines.size() > 0)
                {
                    processLines(lines);
                }
                if (lines.size() < bucketSize)
                {
                    processFinal();
                    break;
                }
            }
            catch (IOException e)
            {
                System.err.println("IO Exception encountered in thread: " + threadID);
                e.printStackTrace();
                master.threadStatus.put(threadID, 1);
            }
        }
    }

}
