package com.alg.dp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Exercise1TestData
{
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
    
    public static ArrayList<Integer> addOne(List<Integer> data)
    {
        ArrayList<Integer> ret = new ArrayList<>(data);
        for (int i=0; i<ret.size(); i++)
        {
            ret.set(i, ret.get(i) + 1);
        }
        return ret;
    }
    
    public static void main(String[] args) throws Exception
    {
        ArrayList<Integer> points = createTestData(1000, 10);
        printList(points);
        WIS wis = new WIS(points);
        wis.calculate();
        System.out.println();
        System.out.println("Max sum: " + wis.sumAt.get(wis.sumAt.size() - 1));
        System.out.println("Chosen points (position): " + addOne(wis.chosenPositions(wis.sumAt.size()-1)));
        System.out.println();
        wis.validate();
    }

}
