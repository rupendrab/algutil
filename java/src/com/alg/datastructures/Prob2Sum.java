package com.alg.datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;

public class Prob2Sum
{
    String fileName;
    Long[] data;
    Hashtable<Long, Integer> hashed;
    boolean hasDuplicates = false;
    
    public Prob2Sum(Long[] data)
    {
        super();
        this.data = data;
        toHash();
    }

    public Prob2Sum(String fileName) throws IOException
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
        hashed = new Hashtable<>(data.length);
        for (long val : data)
        {
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
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void main(String[] args) throws Exception
    {
        String fileName = "/Users/rupen/Documents/Coursera/Algorithms/Course2/2sum.txt";
        Prob2Sum ps = new Prob2Sum(fileName);
        System.out.println("Has duplicates = " + ps.hasDuplicates);
        System.out.println("Number of unique values = " + ps.hashed.size());
        long start, end = 0;
        start = time();
        System.out.println(ps.countExists2Sum(-10000, 10000));
        end = time();
        System.out.println(String.format("Time elapsed = %d ms", (end - start)));
    }

}
