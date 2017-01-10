package com.alg.mst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ClusterHamming
{
    Hashtable<Integer, Integer> nodesToDistance = new Hashtable<>();
    Hashtable<Integer, Integer> distanceToNodes = new Hashtable<>();
    int noBits;
    int noNodes;
    
    public ClusterHamming()
    {
        super();
    }
    
    public Integer toInteger(String[] bytes)
    {
        if (bytes.length != noBits)
        {
            return null;
        }
        int ret = 0;
        for (int i=noBits-1; i>=0; i--)
        {
            if (bytes[i].equals("1"))
            {
                ret |= (1 << noBits - 1 - i);
            }
        }
        return ret;
    }
    
    public static ClusterHamming loadFromFile(String fileName) throws IOException
    {
        ClusterHamming ch = new ClusterHamming();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        while ((line = reader.readLine()) != null)
        {
            line = line.trim();
            if (line.equals(""))
            {
                continue;
            }
            lineNo += 1;
            if (lineNo == 1)
            {
                String[] fields = line.split("\\s+");
                ch.noNodes = Integer.parseInt(fields[0]);
                ch.noBits = Integer.parseInt(fields[1]);
            }
            else
            {
                Integer nodeNo = lineNo - 1;
            }
        }
        reader.close();
        return ch;
    }

    public static void test01()
    {
        ClusterHamming ch = new ClusterHamming();
        ch.noBits = 3;
        System.out.println(ch.toInteger("1 0 1".split("\\s")));
    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }
}
