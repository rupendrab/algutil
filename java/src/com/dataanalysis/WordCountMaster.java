package com.dataanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class WordCountMaster
{
    static final int DEFAULT_BUCKET_SIZE = 100000;
    static final int DEFAULT_THREAD_COUNT = 5;
    
    String fileName;
    BufferedReader reader = null;
    int noThreads = DEFAULT_THREAD_COUNT;
    Hashtable<Integer, Integer> threadStatus = new Hashtable<Integer, Integer>(noThreads);
    Hashtable<Integer, CountRecord[]> threadResults = new Hashtable<Integer, CountRecord[]>(noThreads);

    public WordCountMaster(String fileName) throws IOException
    {
        super();
        this.fileName = fileName;
        openFile();
    }

    public WordCountMaster(String fileName, int noThreads) throws IOException
    {
        this(fileName);
        this.noThreads = noThreads;
        threadStatus = new Hashtable<Integer, Integer>(noThreads);
        threadResults = new Hashtable<Integer, CountRecord[]>(noThreads);
    }

    public void setNoThreads(int noThreads)
    {
        this.noThreads = noThreads;
    }

    public void openFile() throws IOException
    {
        reader = new BufferedReader(new FileReader(new File(fileName)));
    }

    public synchronized ArrayList<String> getNext(int cnt) throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>(cnt);
        if (!reader.ready())
        {
            return lines;
        }
        String line = null;
        int added = 0;
        while ((line = reader.readLine()) != null)
        {
            if (added < cnt)
            {
                lines.add(line);
                added++;
            }
            if (added >= cnt)
            {
                break;
            }
        }
        return lines;
    }

    public void closeFile()
    {
        if (reader != null)
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    public void runCountsInParallel(int bucketSize) throws InterruptedException
    {
        WordCountSlave[] slaves = new WordCountSlave[noThreads];
        Thread[] slaveThreads = new Thread[noThreads];
        for (int i = 0; i < noThreads; i++)
        {
            slaves[i] = new WordCountSlave(this, i, bucketSize);
            slaveThreads[i] = new Thread(slaves[i]);
        }
        for (int i = 0; i < noThreads; i++)
        {
            slaveThreads[i].start();
        }
        for (int i = 0; i < noThreads; i++)
        {
            slaveThreads[i].join();
        }
        closeFile();
    }

    public CountRecord[] mergeResults()
    {
        return ArrayContainer.mergeAddKSorted(threadResults);
    }

    public void printResults()
    {
        for (CountRecord rec : mergeResults())
        {
            if (rec == null)
            {
                break;
            }
            System.out.println(String.format("%s\t%d", rec.word, rec.count));
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length < 1 || args.length > 2)
        {
            System.err.println("Usage java com.dataanalysis.WordCountMaster <File Name> <Thread Count default 5> <Bucket size default 100000>");
            System.exit(1);
        }
        String fileName = args[0];
        WordCountMaster wordCount = new WordCountMaster(fileName);
        if (args.length > 1)
        {
            int noThreads = Integer.parseInt(args[1]);
            wordCount.setNoThreads(noThreads);
        }
        int bucketSize = DEFAULT_BUCKET_SIZE;
        if (args.length > 2)
        {
            bucketSize = Integer.parseInt(args[2]);
        }
        wordCount.runCountsInParallel(bucketSize);
        wordCount.printResults();
    }
}
