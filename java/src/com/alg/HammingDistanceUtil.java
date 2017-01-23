package com.alg;

import java.util.ArrayList;
import java.util.HashSet;

public class HammingDistanceUtil
{
    public static int numberOfPoints(int bytes, int minDistanceFromAny)
    {
        int minPoint = 0;
        int maxPoint = (int) Math.pow(2, bytes) - 1;
        int count = 0;
        for (int p1 = minPoint; p1 <= maxPoint; p1++)
        {
            int min_d = Integer.MAX_VALUE;
            for (int p2 = p1+1; p2 <= maxPoint; p2++)
            {
                int d = distance(p1,p2);
                if (d < min_d)
                {
                    min_d = d;
                }
            }
            if ( min_d >= minDistanceFromAny && min_d <= bytes)
            {
                System.out.println(String.format("%d", p1));
                count++;
            }
        }
        System.out.println(String.format("Min = %d. Max = %d", minPoint, maxPoint));
        return count;
    }
    
    public static int distance(int first, int second)
    {
        return NumberOfSetBits(first ^ second);
    }
    
    public static int NumberOfSetBits(int i)
    {
         i = i - ((i >>> 1) & 0x55555555);
         i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
         return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
    }
    
    public static int toggleBytes(int noBits, int val, Integer[] positions)
    {
        if (positions != null && positions.length > 0)
        {
            for (int position : positions)
            {
                int bytePos = noBits - 1 - position;
                val ^= (1 << bytePos);
            }
        }
        return val;
    }
    
    public static ArrayList<Integer> insertOne(ArrayList<Integer> orig, Integer value, int pos)
    {
        ArrayList<Integer> ret = new ArrayList<>(orig.size() + 1);
        int i = 0;
        for (Integer val : orig)
        {
            if (i == pos)
            {
                ret.add(value);
            }
            ret.add(val);
            i++;
        }
        if (pos == orig.size())
        {
            ret.add(value);
        }
        return ret;
    }
    
    public static ArrayList<ArrayList<Integer>> permutations(int startIndex, int endIndex, int groupSize)
    {
        System.out.println(String.format("%d to %d: %d", startIndex, endIndex, groupSize));
        if (groupSize == 0)
        {
            return new ArrayList<ArrayList<Integer>>();
        }
        else if (groupSize == 1)
        {
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            for (int i = startIndex; i<endIndex; i++)
            {
                ArrayList<Integer> data = new ArrayList<>();
                data.add(i);
                ret.add(data);
            }
            System.out.println("Returning: " + ret);
            return ret;
        }
        else
        {
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            for (int i=startIndex; i<endIndex; i++)
            {
                for (ArrayList<Integer> sub : permutations(startIndex+1, endIndex, groupSize-1))
                {
                    ret.add(insertOne(sub, i, 0));
                }
            }
            System.out.println("Returning: " + ret);
            return ret;
        }
    }
    
    public static ArrayList<ArrayList<Integer>> combinations(int startIndex, int endIndex, int groupSize)
    {
        if (groupSize == 0)
        {
            return new ArrayList<ArrayList<Integer>>();
        }
        else if (groupSize == 1)
        {
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            for (int i = startIndex; i<endIndex; i++)
            {
                ArrayList<Integer> data = new ArrayList<>();
                data.add(i);
                ret.add(data);
            }
            return ret;
        }
        else if (endIndex - startIndex < groupSize)
        {
            return new ArrayList<ArrayList<Integer>>();
        }
        else if (endIndex - startIndex == groupSize)
        {
            ArrayList<Integer> data = new ArrayList<>();
            for (int i= startIndex; i<endIndex; i++)
            {
                data.add(i);
            }
            ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
            ret.add(data);
            return ret;
        }
        else
        {
            ArrayList<ArrayList<Integer>> first = combinations(startIndex+1, endIndex, groupSize);
            ArrayList<ArrayList<Integer>> second = combinations(startIndex+1, endIndex, groupSize-1);
            // System.out.println("first = " + first);
            // System.out.println("second = " + first);
            for (ArrayList<Integer> secondPart : second)
            {
                secondPart.add(0, startIndex);
                first.add(secondPart);
            }
            return first;
        }
    }
    
    public static int noCombinations(int available, int choose)
    {
        if (choose == 0)
        {
            return 1;
        }
        long numerator = 1;
        long denominator = 1;
        for (int i = choose; i>0; i--)
        {
            numerator *= (available - (choose-i));
            denominator *= i;
        }
        return (int) (numerator / denominator);
    }
    
    public static HashSet<Integer> pointsAtDistance(int noBits, int startPoint, int minDistance)
    {
        HashSet<Integer> points = new HashSet<>();
        int st = startPoint;
        points.add(st);
        HashSet<Integer> newPoints = new HashSet<>();
        newPoints.add(st);
        while (newPoints.size() > 0)
        {
            HashSet<Integer> newPointsBackup = new HashSet<>();
            newPointsBackup.addAll(newPoints);
            newPoints = new HashSet<>();
            System.out.println(String.format("  New points = %d", newPointsBackup.size()));
            int i=0;
            for (Integer stpt : newPointsBackup)
            {
                // System.out.println("Starting point : " + stpt);
                for (ArrayList<Integer> comb : combinations(0, noBits, minDistance))
                {
                    Integer[] positions = new Integer[minDistance];
                    comb.toArray(positions);
                    int np = toggleBytes(noBits, stpt, positions);
                    // System.out.println("      Trying new point :" + np);
                    boolean add = true;
                    for (Integer pt : points)
                    {
                        if (distance(pt, np) < minDistance)
                        {
                            add = false;
                            break;
                        }
                    }
                    if (add)
                    {
                        points.add(np);
                        newPoints.add(np);
                    }
                }
                i += 1;
                int pct_done = i * 100 / newPointsBackup.size();
                if (pct_done % 10 == 0)
                {
                    System.out.println(String.format("    Pct done = %d (%d) = %d", pct_done, i, points.size()));
                }
            }
            System.out.println(String.format("  Size = %d, Added = %d", points.size(), newPoints.size()));
        }
        return points;
    }


    public static void test01(int p1, int p2)
    {
        // numberOfPoints(10, 3);
        // System.out.println(NumberOfSetBits(45));
        System.out.println(distance(p1, p2));
        System.out.println(Integer.toBinaryString(p1));
        System.out.println(Integer.toBinaryString(p2));
        System.out.println(Integer.toBinaryString(p1 ^ p2));
    }
    
    public static void test02()
    {
        ArrayList<Integer> data = new ArrayList();
        data.add(1);
        data.add(2);
        data.add(3);
        for (int i=0; i<=data.size(); i++)
        {
            System.out.println(insertOne(data, 100, i));
        }
    }
    
    public static void test03()
    {
        System.out.println(permutations(0, 3, 3));
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01(511, 512);
        // System.out.println(numberOfPoints(24, 3));
        // System.out.println(combinations(0, 24, 5).size());
        // System.out.println(noCombinations(24, 5));
        // HashSet<Integer> pts = pointsAtDistance(24, 0, 5);
        // System.out.println(pts.size());
        // System.out.println(pointsAtDistance(24, 0, 4).size());
        // System.out.println(pointsAtDistance(24, 0, 8).size());
        // test02();
        test03();
    }

}
