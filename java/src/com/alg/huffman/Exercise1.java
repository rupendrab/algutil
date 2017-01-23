package com.alg.huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Exercise1
{
    public String fileName;
    HuffmanEncode encoder;
    Hashtable<Integer, Integer> characterFrequency = new Hashtable<>();
    
    public Exercise1(String fileName) throws IOException
    {
        super();
        this.fileName = fileName;
        encoder = createEncoder();
    }
    
    public HuffmanEncode createEncoder() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        characterFrequency = new Hashtable<>();
        String line = null;
        int lineNo = 0;
        while ((line = reader.readLine()) != null)
        {
            lineNo++;
            if (lineNo == 1)
            {
                continue;
            }
            characterFrequency.put(lineNo-1, Integer.parseInt(line));
        }
        reader.close();
        // System.out.println(characterFrequency.size());
        return new HuffmanEncode(characterFrequency);
    }
    
    public void computeMinAndMax()
    {
        int minLength = Integer.MAX_VALUE;
        int maxLength = Integer.MIN_VALUE;
        for (int i=1; i<=characterFrequency.size(); i++)
        {
            ArrayList<Boolean> pathTo = encoder.rootNode.getPathTo(i);
            int pathLength = pathTo.size();
            // System.out.println(String.format("Alphabet %4d length %d", i, pathLength));
            if (pathLength < minLength)
            {
                minLength = pathLength;
            }
            if (pathLength > maxLength)
            {
                maxLength = pathLength;
            }
        }
        System.out.println("Min length = " + minLength);
        System.out.println("Max length = " + maxLength);
    }
    
    public static void main(String[] args) throws Exception
    {
        Exercise1 ex1 = new Exercise1("C:\\Users\\rubandyopadhyay\\Downloads\\huffman.txt");
        // System.out.println(ex1.encoder.rootNode);
        ex1.computeMinAndMax();
    }

}
