package com.dataanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;

public class WordCount
{
    String fileName;
    Hashtable<String, Integer> counts = new Hashtable<String, Integer>(10000);
    Pattern p = Pattern.compile("\\s+");

    public WordCount(String fileName)
    {
        super();
        this.fileName = fileName;
    }

    public void count() throws IOException
    {
        counts = new Hashtable<String, Integer>(10000);
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(new File(fileName)));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                String[] words = p.split(line);
                for (String word : words)
                {
                    Integer cnt = counts.get(word);
                    if (cnt == null)
                    {
                        counts.put(word, 1);
                    }
                    else
                    {
                        counts.put(word, cnt + 1);
                    }
                }
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }
    }

    public void printCounts()
    {
        Set<String> wordSet = counts.keySet();
        String[] words = new String[wordSet.size()];
        wordSet.toArray(words);
        Arrays.sort(words);
        for (String word : words)
        {
            System.out.println(String.format("%s\t%d", word, counts.get(word)));
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.err.println("Usage java com.dataanalysis.WordCount <File Name>");
            System.exit(1);
        }
        String fileName = args[0];
        WordCount wordCount = new WordCount(fileName);
        wordCount.count();
        wordCount.printCounts();
    }
}
