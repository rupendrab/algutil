package com.alg.datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

public class Prob2Sum2
{
    String fileName;
    Long[] data;
    HashedData<Long, Integer> hashed;
    boolean hasDuplicates = false;
    long getOps = 0L;
    
    public Prob2Sum2(Long[] data)
    {
        super();
        this.data = data;
        toHash();
    }

    public Prob2Sum2(String fileName) throws IOException
    {
        super();
        this.fileName = fileName;
        data = readLongsFromFile(fileName);
        toHash();
    }

    public static Long[] readLongsFromFile(String fileName) throws IOException
    {
        Long[] data = new Long[1000];
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        int pos = 0;
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            try
            {
                Long val = Long.parseLong(line);
                if (pos == data.length)
                {
                    data = doubleArray(data);
                }
                data[pos++] = val;
            }
            catch (NumberFormatException ne)
            {
            }
        }
        reader.close();
        data = resizeArray(data, pos);
        System.out.println("Read lines = " + data.length);
        return data;
    }
    
    public static Long[] doubleArray(Long[] data)
    {
        Long[] ret = new Long[data.length * 2];
        System.arraycopy(data, 0, ret, 0, data.length);
        return ret;
    }
    
    public static Long[] resizeArray(Long[] data, int len)
    {
        Long[] ret = new Long[len];
        System.arraycopy(data, 0, ret, 0, len);
        return ret;
    }
    
    private void toHash()
    {
        Arrays.sort(data);
        hashed = new HashedData<>(new HashFuncLong(data.length));
        for (long val : data)
        {
            // System.out.println("Adding " + val);
            Integer stored = hashed.get(val);
            if ( stored != null)
            {
                hashed.put(val, stored + 1);
                hasDuplicates = true;
            }
            else
            {
                hashed.put(val, 1);
            }
        }
    }
    
    public boolean exists2Sum(long sum)
    {
        boolean exists = false;
        for (long val : data)
        {
            long toAdd = sum - val;
            if (val == toAdd)
            {
                continue;
            }
            if (val > sum)
            {
                return false;
            }
            getOps++;
            if (hashed.get(toAdd) != null)
            {
                System.out.println(String.format("%d + %d = %d", val, toAdd, sum));
                return true;
            }
        }
        return exists;
    }
    
    public boolean exists2Sum(long sum, int from, int to)
    {
        boolean exists = false;
        for (int i=to; i>=from; i--)
        {
            long val = data[i];
            long toAdd = sum - val;
            if (val == toAdd)
            {
                continue;
            }
            /*
            if (val > sum)
            {
                return false;
            }
            */
            getOps++;
            if (hashed.get(toAdd) != null)
            {
                System.out.println(String.format("%d + %d = %d", val, toAdd, sum));
                return true;
            }
        }
        return exists;
    }
    
    public int countExists2Sum(long start, long end)
    {
        int count = 0;
        for (long sum = start; sum <= end; sum++)
        {
            if (exists2Sum(sum))
            {
                count += 1;
            }
        }
        return count;
    }
    
    public int countExists2Sum(long start, long end, int from, int to)
    {
        int count = 0;
        for (long sum = start; sum <= end; sum++)
        {
            if (exists2Sum(sum, from, to))
            {
                count += 1;
            }
        }
        return count;
    }
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void test01(String fileName) throws IOException
    {
        Prob2Sum2 ps = new Prob2Sum2(fileName);
        long rangeStart = -10000;
        long rangeEnd = 10000;
        System.out.println(String.format("Min + Max = %s", ps.data[0], ps.data[ps.data.length -1]));
        int arrlen = ps.data.length;
        int minPos = 0;
        int maxPos = arrlen - 1;
        for (int i=1; i<arrlen; i++)
        {
            if (ps.data[i-1] + ps.data[i] < rangeStart)
            {
                minPos = i;
            }
            else if (ps.data[0] + ps.data[i] > rangeEnd)
            {
                maxPos = i;
            }
            else
            {
                break;
            }
        }
        System.out.println(String.format("%d - %d", minPos, maxPos));
    }
    
    public static void test02(String fileName) throws IOException
    {
        int rangeStart = -100;
        int rangeEnd = 100;
        Prob2Sum2 ps = new Prob2Sum2(fileName);
        System.out.println("Has duplicates = " + ps.hasDuplicates);
        System.out.println("Number of unique values = " + ps.hashed.size());
        System.out.println("Max bucket length = " + ps.hashed.maxBucketLength());
        System.out.println("Average bucket length = " + ps.hashed.avgBucketLength());
        int arrlen = ps.data.length;
        int minPos = 0;
        int maxPos = arrlen - 1;
        for (int i=1; i<arrlen; i++)
        {
            if (ps.data[i-1] + ps.data[i] < rangeStart)
            {
                minPos = i;
            }
            else if (ps.data[0] + ps.data[i] > rangeEnd)
            {
                maxPos = i;
            }
            else
            {
                break;
            }
        }
        System.out.println(String.format("%d - %d", minPos, maxPos));
        long start, end = 0;
        start = time();
        System.out.println(ps.countExists2Sum(rangeStart, rangeEnd, minPos, maxPos));
        end = time();
        System.out.println("Get Ops = " + ps.getOps);
        System.out.println(String.format("Time elapsed = %d ms", (end - start)));
        System.out.println(ps.exists2Sum(-94));
        System.out.println(ps.exists2Sum(-94, minPos, maxPos));
        System.out.println(String.format("%d %d %d", ps.data[minPos-1], ps.data[minPos], ps.data[minPos + 1]));
    }
    
    public static void test03(String fileName) throws IOException
    {
        Prob2Sum2 ps = new Prob2Sum2(fileName);
        System.out.println("Has duplicates = " + ps.hasDuplicates);
        System.out.println("Number of unique values = " + ps.hashed.size());
        System.out.println("Max bucket length = " + ps.hashed.maxBucketLength());
        System.out.println("Average bucket length = " + ps.hashed.avgBucketLength());
        long start, end = 0;
        start = time();
        System.out.println(ps.countExists2Sum(-100, 100));
        end = time();
        System.out.println("Get Ops = " + ps.getOps);
        System.out.println(String.format("Time elapsed = %d ms", (end - start)));
    }
    
    public static void main(String[] args) throws Exception
    {
        String fileName = "/Users/rupen/Documents/Coursera/Algorithms/Course2/2sum.txt";
        // test01(fileName);
        test02(fileName);
        // test03(fileName);
    }

}
