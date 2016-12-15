package com.alg.counting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CombinationSum
{
    Integer[] denom;
    int finalSum;
    int maxDenoms = -1;
    
    public CombinationSum(int finalSum, int... denom)
    {
        super();
        this.denom = new Integer[denom.length];
        for (int i=0; i<denom.length; i++)
        {
            this.denom[i] = denom[i];
        }
        this.finalSum = finalSum;
        Arrays.sort(this.denom, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2 - o1;
            }
        });
    }
    
    public int getMaxDenoms()
    {
        return maxDenoms;
    }

    public void setMaxDenoms(int maxDenoms)
    {
        this.maxDenoms = maxDenoms;
    }

    public int[] nextDenoms()
    {
        int[] next = new int[denom.length - 1];
        for (int i=1; i<denom.length; i++)
        {
            next[i-1] = denom[i];
        }
        return next;
    }
    
    public int[] denoms()
    {
        int[] denoms = new int[denom.length];
        for (int i=0; i<denom.length; i++)
        {
            denoms[i] = denom[i];
        }
        return denoms;
    }
    
    public int denomsUsed(ArrayList<Integer> comb)
    {
        int tot = 0;
        for (int val : comb)
        {
            tot += val;
        }
        return tot;
    }
    
    public ArrayList<ArrayList<Integer>> combinations()
    {
        if (finalSum == 0) // No more money to add
        {
            ArrayList<Integer> x = new ArrayList<Integer>();
            for (int i=0; i<denom.length; i++)
            {
                x.add(0);
            }
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            ret.add(x);
            return ret;
        }
        else if (denom.length == 0) // No more coins left
        {
            return null;
        }
        else
        {
            ArrayList<ArrayList<Integer>> ret1 = null;
            if (finalSum >= denom[0])
            {
                ret1 = (new CombinationSum(finalSum - denom[0], denoms())).combinations();
            }
            ArrayList<ArrayList<Integer>> ret2 = (new CombinationSum(finalSum, nextDenoms())).combinations();
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            if (ret1 != null)
            {
                for (ArrayList<Integer> comb : ret1)
                {
                    comb.set(0, comb.get(0) + 1);
                    if (maxDenoms == -1 || denomsUsed(comb) <= maxDenoms)
                    {
                        ret.add(comb);
                    }
                }
            }
            if (ret2 != null)
            {
                for (ArrayList<Integer> comb : ret2)
                {
                    comb.add(0, 0);
                    if (maxDenoms == -1 || denomsUsed(comb) <= maxDenoms)
                    {
                        ret.add(comb);
                    }
                }
            }
            return ret;
        }
    }
    
    public static void test01(int val)
    {
        CombinationSum cs = new CombinationSum(val, 5, 3, 1);
        cs.setMaxDenoms(173);
        ArrayList<ArrayList<Integer>> combs = cs.combinations();
        System.out.println(combs);
        System.out.println("Number of possible combinations = " + combs.size());
    }
    
    public static void main(String[] args) throws Exception
    {
        test01(745);
        test01(316);
        test01(198);
        test01(91);
        test01(68);
        test01(60);
        test01(20);
        test01(11);
        test01(8);
        test01(7);
        test01(6);
        test01(5);
        test01(4);
        test01(3);
        test01(1);
    }
}
