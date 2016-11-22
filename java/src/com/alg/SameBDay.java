package com.alg;

import java.util.Random;

import com.alg.QuickSortInt.PivotType;

public class SameBDay
{
    int k;
    Random rand = new Random();
    
    public SameBDay(int k)
    {
        super();
        this.k = k;
    } 
    
    public int simulate()
    {
        int[] bDays = new int[k];
        for (int i=0; i<k; i++)
        {
            bDays[i] = rand.nextInt(365);
        }
        QuickSortInt qs = new QuickSortInt(bDays);
        qs.sort(PivotType.MEDIAN);
        int matched = 0;
        for (int i=0; i<bDays.length; i++)
        {
            for (int j=i+1; j<bDays.length; j++)
            {
                if (bDays[i] == bDays[j])
                {
                    matched++;
                }
            }
        }
        return matched;
    }
    
    public double runSimulations(int cnt)
    {
        int totMatch = 0;
        for (int i=0; i<cnt; i++)
        {
            totMatch += simulate();
        }
        return totMatch * 1.0 / cnt;
    }
    
    public static void main(String[] args)
    {
        SameBDay sb = new SameBDay(27);
        System.out.println(sb.runSimulations(1000000));
    }
}
