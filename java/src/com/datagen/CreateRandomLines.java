package com.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class CreateRandomLines
{
    Pattern p = Pattern.compile("\\s+");
    Random rand = new Random(100);
    String dictionaryFile;
    String[] dictWords;
    int minWords = 10;
    int maxWords = 20;
    long maxSize = 1 * (long) Math.pow(1024, 1);

    public CreateRandomLines(String dictionaryFile, long maxSize) throws IOException
    {
        super();
        this.maxSize = maxSize;
        this.dictionaryFile = dictionaryFile;
        populateDictWords();
    }

    public CreateRandomLines(String dictionaryFile, long maxSize, int minWords, int maxWords) throws IOException
    {
        this(dictionaryFile, maxSize);
        this.minWords = minWords;
        this.maxWords = maxWords;
    }

    public void populateDictWords() throws IOException
    {
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(new File(dictionaryFile)));
            String line = null;
            ArrayList<String> allWords = new ArrayList<String>(10000);
            while ((line = reader.readLine()) != null)
            {
                allWords.add(line);
            }
            dictWords = new String[allWords.size()];
            allWords.toArray(dictWords);
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }
    }

    public String getRandomWord()
    {
        return dictWords[rand.nextInt(dictWords.length)];
    }

    public String getRandomLine()
    {
        int noWords = minWords + rand.nextInt(maxWords + 1 - minWords);
        StringBuilder sb = new StringBuilder(10 * noWords);
        for (int i = 0; i < noWords; i++)
        {
            if (i > 0)
            {
                sb.append(" ");
            }
            sb.append(getRandomWord());
        }
        return sb.toString();
    }

    public void generate()
    {
        long sizeSoFar = 0;
        long reportedSize = 0;
        long reportThreshold = 10 * 1024 * 1024;
        while (true)
        {
            String nextLine = getRandomLine();
            sizeSoFar += nextLine.length();
            if (sizeSoFar < maxSize)
            {
                System.out.println(nextLine);
            }
            else
            {
                sizeSoFar -= nextLine.length();
                break;
            }
            if (sizeSoFar - reportedSize > reportThreshold)
            {
                System.err.println("Written so far = " + sizeSoFar);
                reportedSize = sizeSoFar;
            }
        }
        System.err.println("Written total = " + sizeSoFar);
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length < 2 || args.length > 4)
        {
            System.err.println(
                    "Usage: Java com.datagen.CreateRandomLines <Dict File> <Max Size Bytes> <Min words in line, default 10> <Max words in line, default 20>");
            System.exit(1);
        }
        String dictionaryFile = args[0];
        long maxSize = Long.parseLong(args[1]);
        int minWords = 10;
        int maxWords = 20;
        if (args.length > 2)
        {
            minWords = Integer.parseInt(args[2]);
        }
        if (args.length > 3)
        {
            maxWords = Integer.parseInt(args[3]);
        }
        CreateRandomLines cr = new CreateRandomLines(dictionaryFile, maxSize, minWords, maxWords);
        cr.generate();
    }
}
