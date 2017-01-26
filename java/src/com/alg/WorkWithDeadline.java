package com.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class WorkWithDeadline
{
    int[] p;
    int[] d;
    Work[] works;
    
    public WorkWithDeadline(int[] p, int[] d)
    {
        super();
        this.p = p;
        this.d = d;
        populateWorks();
    }

    public void populateWorks()
    {
        works = new Work[p.length];
        for (int i=0; i<p.length; i++)
        {
            works[i] = new Work(p[i], d[i]);
        }
    }
    
    public int computeLateness()
    {
        int lateness = 0;
        int timeElapsed = 0;
        for (int i=0; i<works.length; i++)
        {
            timeElapsed += works[i].p;
            if (timeElapsed > works[i].d)
            {
                lateness += (timeElapsed - works[i].d);
            }
        }
        return lateness;
    }
    
    public ArrayList<LinkedList<Integer>> allPermutations(ArrayList<Integer> input)
    {
        ArrayList<LinkedList<Integer>> ret = new ArrayList<>();
        if (input.size() == 0)
        {
            return ret;
        }
        else if (input.size() == 1)
        {
            LinkedList<Integer> onlyVal = new LinkedList<>(input);
            ret.add(onlyVal);
        }
        else
        {
            for (int i=0; i<input.size(); i++)
            {
                Integer selected = input.get(i);
                ArrayList<Integer> others = new ArrayList<>(input);
                others.remove(i);
                for (LinkedList<Integer> nxt : allPermutations(others))
                {
                    LinkedList<Integer> nxtNew = new LinkedList<>(nxt);
                    nxtNew.addFirst(selected);
                    ret.add(nxtNew);
                }
            }
        }
        return ret;
    }
    
    public void calculateBestLateness()
    {
        ArrayList<Integer> positions = new ArrayList<>();
        Work[] worksOld = new Work[works.length];
        for (int i=0; i<worksOld.length; i++)
        {
            worksOld[i] = works[i];
        }
        for (int i=0; i<works.length; i++)
        {
            positions.add(i);
        }
        LinkedList<Integer> best = new LinkedList<>();
        int minLateness = Integer.MAX_VALUE;
        for (LinkedList<Integer> pos : allPermutations(positions))
        {
            works = new Work[worksOld.length];
            for (int i=0; i<worksOld.length; i++)
            {
                works[i] = worksOld[pos.get(i)];
            }
            int lateness = computeLateness();
            // System.out.println(Arrays.toString(works) + " , lateness = " + lateness);
            if (lateness <= minLateness)
            {
                minLateness = lateness;
                best = pos;
            }
        }
        System.out.println("Best = " + minLateness);
        System.out.println("Pos = " + best);
    }
    
    static class Work
    {
        int p;
        int d;
        
        public Work(int p, int d)
        {
            super();
            this.p = p;
            this.d = d;
        }
        
        @Override
        public String toString()
        {
            return p + "," + d;
        }
        
    }
    
    public static void test01(int[] p, int[] d)
    {
        WorkWithDeadline wwd = new WorkWithDeadline(p, d);
        Work[] worksOld = new Work[wwd.works.length];
        for (int i=0; i<worksOld.length; i++)
        {
            worksOld[i] = wwd.works[i];
        }
        System.out.println("Original: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return o1.p - o2.p;
            }
        });
        System.out.println("Sort by p: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return o1.p * o1.d - o2.p * o2.d;
            }
        });
        System.out.println("Sort by p * d: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return (int) (o1.p * 1.0 / o1.d - o2.p * 1.0 / o2.d);
            }
        });
        System.out.println("Sort by p / d: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return (int) (o1.d * 1.0 / o1.p - o2.d * 1.0 / o2.p);
            }
        });
        System.out.println("Sort by d / p: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return (int) (o1.p - o1.d) - (o2.p - o2.d);
            }
        });
        System.out.println("Sort by p - d: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return (int) (o1.d - o1.p) - (o2.d - o2.p);
            }
        });
        System.out.println("Sort by d - p: " + wwd.computeLateness());
        Arrays.sort(wwd.works, new Comparator<Work>() {

            @Override
            public int compare(Work o1, Work o2)
            {
                return (int) (o1.d + o1.p) - (o2.d + o2.p);
            }
        });
        System.out.println("Sort by d + p: " + wwd.computeLateness());
        wwd.works = worksOld;
        wwd.calculateBestLateness();
    }
    
    public static void test02(int[] p, int[] d)
    {
        WorkWithDeadline wwd = new WorkWithDeadline(p, d);
        ArrayList<Integer> data = new ArrayList<>();
        for (int val : new int[] {1,2,3,4,5})
        {
            data.add(val);
        }
        System.out.println(wwd.allPermutations(data).size());
        System.out.println(wwd.allPermutations(data));
    }
    
    public static void test03(int n)
    {
        int[] p = new int[n];
        int[] d = new int[n];
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        for (int i=0; i<n; i++)
        {
            p[i] = rand.nextInt(100) + 1;
            d[i] = rand.nextInt(100) + 1;
        }
        test01(p, d);
    }
    public static void main(String[] args)
    {
        System.out.println("Test 1");
        test01(new int[] {3,4,5,1}, new int[] {10,7,8,9});
        System.out.println("Test 2");
        test01(new int[] {3,4,5,1}, new int[] {2,3,4,5});
        // test02(new int[] {3,4,5,1}, new int[] {2,3,4,5});
        test03(7);
    }
    
}

