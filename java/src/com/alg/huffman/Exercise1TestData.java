package com.alg.huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Exercise1TestData
{
    static String NL = "\r\n";
    
    public static ArrayList<Integer> createTestData(int maxVal, int noVals)
    {
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        ArrayList<Integer> data = new ArrayList<>(noVals);
        for (int i=0; i<noVals; i++)
        {
            data.add(rand.nextInt(maxVal) + 1);
        }
        return data;
    }
    
    public static void printList(ArrayList<Integer> data)
    {
        System.out.println(data.size());
        for (int val : data)
        {
            System.out.println(val);
        }
    }
    
    public static String saveListToFile(ArrayList<Integer> data) throws IOException
    {
        File tempFile = File.createTempFile("huff", "txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("" + data.size());
        writer.write(NL);
        for (int val : data)
        {
            writer.write("" + val);
            writer.write(NL);
        }
        writer.close();
        return tempFile.getAbsolutePath();
    }
    
    public static void test01(int maxVal, int noVals) throws IOException
    {
        ArrayList<Integer> data = createTestData(maxVal, noVals);
        printList(data);
        System.out.println();
        String fileName = saveListToFile(data);
        Exercise1 ex1 = new Exercise1(fileName);
        ex1.computeMinAndMax();
        new File(fileName).delete();
    }
    
    public static void main(String[] args) throws Exception
    {
        test01(1000, 15);
    }
    
}
