package com.alg.counting;

import java.util.ArrayList;

public class CalculationStep
{
    int playerNo;
    int combinationNo;
    ArrayList<Integer> combinations;
    ArrayList<Integer> sums;
    
    public CalculationStep(int playerNo, int combinationNo, ArrayList<Integer> combinations, ArrayList<Integer> sums)
    {
        super();
        this.playerNo = playerNo;
        this.combinationNo = combinationNo;
        this.combinations = combinations;
        this.sums = sums;
    }

    
}
