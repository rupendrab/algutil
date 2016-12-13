package com.alg.counting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BallonDOr
{
    int noVoters;
    int[] denoms;
    int[] playerScores;
    
    Deque<CalculationStep> solution = new ArrayDeque<>();
    
    // Each row is for a player
    // Every column in row is a list of possible combinations
    // That makes this a three level ArrayList object
    ArrayList<ArrayList<ArrayList<Integer>>> allCombinations = new ArrayList<>();
    
    public BallonDOr(int noVoters, int[] denoms, int[] playerScores)
    {
        super();
        this.noVoters = noVoters;
        this.denoms = denoms;
        this.playerScores = playerScores;
        populateCombinations();
    }

    public void populateCombinations()
    {
        for (int score : playerScores)
        {
            CombinationSum cs = new CombinationSum(score, denoms);
            cs.setMaxDenoms(noVoters);
            ArrayList<ArrayList<Integer>> combs = cs.combinations();
            allCombinations.add(combs);
        }
    }
    
    public ArrayList<Integer> sum(ArrayList<Integer> left, ArrayList<Integer> right)
    {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i=0; i<left.size(); i++)
        {
            int summed = left.get(i) + right.get(i);
            ret.add(summed);
        }
        return ret;
    }
    
    public boolean validSum(ArrayList<Integer> sums, boolean finalStep)
    {
        if (finalStep)
        {
            for (int sum : sums)
            {
                if (sum != noVoters)
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            for (int sum : sums)
            {
                if (sum > noVoters)
                {
                    return false;
                }
            }
            return true;
        }
    }
    
    public int[] findNext(int playerNo, int combinationNo)
    {
        int[] nexts = new int[2];
        boolean found = false;
        while (! found)
        {
            ArrayList<ArrayList<Integer>> playerCombinations = allCombinations.get(playerNo);
            if (combinationNo < playerCombinations.size() - 1)
            {
                nexts[0] = playerNo;
                nexts[1] = combinationNo + 1;
                found = true;
            }
            else
            {
                CalculationStep last = solution.pop();
                if (last == null)
                {
                    throw new Error("No solution!!!!");
                }
                playerNo = last.playerNo;
                combinationNo = last.combinationNo;
            }
        }
        return nexts;
    }
    
    public void calculate(int playerNo, int combinationNo)
    {
        CalculationStep last = null;
        if (solution.size() > 0)
        {
            last = solution.getLast();
        }
        ArrayList<Integer> current = allCombinations.get(playerNo).get(combinationNo);
        if (last == null)
        {
            CalculationStep next = new CalculationStep(playerNo, combinationNo, current, current);
            solution.addLast(next);
            calculate(playerNo +1, 0);
        }
        else
        {
            ArrayList<Integer> newSums = sum(last.sums, current);
            CalculationStep next = new CalculationStep(playerNo, combinationNo, current, newSums);
            if (playerNo == playerScores.length - 1)
            {
                if (validSum(newSums, true))
                {
                    solution.addLast(next);
                    System.out.println("Found the solution!!!!");
                    return;
                }
                else
                {
                    int[] nexts = findNext(playerNo, combinationNo);
                    calculate(nexts[0], nexts[1]);
                }
            }
            else
            {
                if (validSum(newSums, false))
                {
                    solution.addLast(next);
                    calculate(playerNo + 1, 0);
                }
                else
                {
                    int[] nexts = findNext(playerNo, combinationNo);
                    calculate(nexts[0], nexts[1]);
                }
            }
        }
    }
    
    public void calculate()
    {
        calculate(0, 0);
    }
    
    public void printSolution()
    {
        for (CalculationStep step : solution)
        {
            System.out.println(step.playerNo + " : " + step.combinations +  " (" + step.sums + ")");
        }
    }
    
    public static void test01()
    {
        int noVoters = 173;
        int[] denoms = {5,3,1};
        int[] playerScores = {745, 316, 198, 91, 68, 60, 20, 11, 8, 8, 7, 6, 5, 4, 4, 3, 1, 1, 1};
        BallonDOr bdr = new BallonDOr(noVoters, denoms, playerScores);
        bdr.calculate();
        bdr.printSolution();
    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }
}
