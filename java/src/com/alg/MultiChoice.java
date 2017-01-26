package com.alg;

import java.util.ArrayList;
import java.util.LinkedList;

public class MultiChoice
{
    int options;

    public MultiChoice(int options)
    {
        super();
        this.options = options;
    }
    
    ArrayList<LinkedList<Integer>> allOptions()
    {
        return allOptions(options);
    }
    
    ArrayList<LinkedList<Integer>> allOptions(int n)
    {
        ArrayList<LinkedList<Integer>> ret = new ArrayList<>();
        if (n == 1)
        {
            for (int i=0; i<2; i++)
            {
                LinkedList<Integer> a1 = new LinkedList<>();
                a1.add(i);
                ret.add(a1);
            }
        }
        else
        {
            ArrayList<LinkedList<Integer>> prev = allOptions(n-1);
            for (LinkedList<Integer> opt : prev)
            {
                for (int i=0; i<2; i++)
                {
                    LinkedList<Integer> optNew = new LinkedList<>(opt);
                    optNew.addFirst(i);
                    ret.add(optNew);
                }
            }
        }
        return ret;
    }
    
    public ArrayList<Integer> toArrayList(int[] data)
    {
        ArrayList<Integer> ret = new ArrayList<>(data.length);
        for (int val : data)
        {
            ret.add(val);
        }
        return ret;
    }
    
    public double score(LinkedList<Integer> act, int[] answer)
    {
        int correct = 0;
        for (int i=0; i<act.size(); i++)
        {
            if (answer[i] == act.get(i))
            {
                correct++;
            }
        }
        return correct * 1.0 / act.size();
    }
    
    public ArrayList<LinkedList<Integer>> possibleAnswers(double score, int[] answer)
    {
        ArrayList<LinkedList<Integer>> allPossible = allOptions();
        ArrayList<LinkedList<Integer>> ret = new ArrayList<>();
        for (LinkedList<Integer> opt : allPossible)
        {
            double optionScore = score(opt, answer);
            if (optionScore == score)
            {
                ret.add(opt);   
            }
        }
        return ret;
    }
    
    public static void showPossibleAnswers(MultiChoice mc, int[] answer, double score)
    {
        System.out.println(mc.possibleAnswers(score, answer));
    }
    
    public static void main(String[] args)
    {
        MultiChoice mc = new MultiChoice(4);
        showPossibleAnswers(mc, new int[] {1,1,1,1}, 0.75);
        showPossibleAnswers(mc, new int[] {0,0,1,1}, 0.75);
        showPossibleAnswers(mc, new int[] {1,1,1,1}, 0.5);
        System.out.print("Problem 8: ");
        showPossibleAnswers(mc, new int[] {0,0,1,1}, 0.75);
    }

}
