package com.alg.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class WIS
{
    ArrayList<Integer> points = new ArrayList<>();
    ArrayList<Long> sumAt = new ArrayList<>();

    public WIS()
    {
        super();
    }

    public WIS(ArrayList<Integer> points)
    {
        super();
        this.points = points;
    }
    
    public WIS(Integer[] points)
    {
        this.points.addAll(Arrays.asList(points));
    }
    
    public void calculate()
    {
        sumAt = new ArrayList<>();
        for (int i=0; i<=points.size(); i++)
        {
            sumAt.add(0L);
        }
        sumAt.set(0,  0L);
        sumAt.set(1, (long) points.get(0));
        for (int i=1; i<points.size(); i++)
        {
            sumAt.set(i+1, Math.max(sumAt.get(i-1) + points.get(i), sumAt.get(i)));
        }
    }
    
    public LinkedList<Integer> chosenPositions(int upto)
    {
        LinkedList<Integer> ret = new LinkedList<>();
        // System.out.println(String.format("upto = %d, sumAt size = %d", upto, sumAt.size()));
        if (upto > sumAt.size() - 1)
        {
            System.out.println("Maximum value of upto can be" + (sumAt.size() - 1));
            return ret;
        }
        else if (upto == 0)
        {
            return ret;
        }
        else
        {
            while (upto >= 1)
            {
                if (upto == 1)
                {
                    ret.addFirst(0);
                    break;
                }
                else if (sumAt.get(upto - 1) >= sumAt.get(upto - 2) + (long) points.get(upto-1))
                {
                    upto--;
                }
                else
                {
                    ret.addFirst(upto - 1);
                    upto -= 2;
                }
            }
        }
        return ret;
    }
    
    public void validate()
    {
        long totalSum = sumAt.get(sumAt.size()-1);
        long calculatedSum = 0;
        LinkedList<Integer> chosens = chosenPositions(sumAt.size()-1);
        for (int chosen : chosens)
        {
            calculatedSum += points.get(chosen);
        }
        System.out.println(String.format("Sum = %d, calculated = %d, matched = %s", totalSum, calculatedSum, totalSum == calculatedSum));
    }
    
    public void showSums()
    {
        System.out.println(sumAt);
    }
    
    public static void test01(Integer[] data)
    {
        WIS wis = new WIS(data);
        System.out.println(wis.points);
        wis.calculate();
        wis.showSums();
        System.out.println(wis.chosenPositions(data.length));
        wis.validate();
    }
    
    public static void main(String[] args)
    {
        test01(new Integer[] {1,4,5,4});
        test01(new Integer[] {1,2,3,5,4,7,2});
    }

}
