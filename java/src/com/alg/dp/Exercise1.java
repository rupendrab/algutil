package com.alg.dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Exercise1
{
    String fileName;
    ArrayList<Integer> points;
    WIS wis;
    
    public Exercise1(String fileName) throws IOException
    {
        super();
        this.fileName = fileName;
        loadFile();
        wis.calculate();
    }
    
    private void loadFile() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        int lineNo = 0;
        String line = null;
        points = new ArrayList<>();
        while ((line = reader.readLine()) != null)
        {
            lineNo++;
            if (lineNo == 1)
            {
                continue;
            }
            points.add(Integer.parseInt(line));
        }
        wis = new WIS(points);
        reader.close();
    }
    
    public void show()
    {
        System.out.println(points);
        System.out.println("Number of points = " + points.size());
        wis.showSums();
        System.out.println(wis.chosenPositions(points.size()));
        wis.validate();
    }
    
    public String matchedBitString(int[] givenPoints)
    {
        StringBuilder sb = new StringBuilder();
        LinkedList<Integer> chosenPoints = wis.chosenPositions(points.size());
        for (int givenPoint : givenPoints)
        {
            if (chosenPoints.contains(givenPoint - 1))
            {
                sb.append("1");
            }
            else
            {
                sb.append("0");
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception
    {
        Exercise1 ex1 = new Exercise1("C:\\Users\\rubandyopadhyay\\Downloads\\mwis.txt");
        ex1.show();
        System.out.println(ex1.matchedBitString(new int[] {1, 2, 3, 4, 17, 117, 517, 997}));
        /* 00100110 */
        /* 10100110 */
    }

}
