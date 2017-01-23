package com.alg.dp;

public class KnapsackSolution implements Comparable<KnapsackSolution>
{
    int elementNo;
    int weight;
    double solution;
    
    public KnapsackSolution()
    {
        super();
    }

    public KnapsackSolution(int elementNo, int weight)
    {
        super();
        this.elementNo = elementNo;
        this.weight = weight;
    }

    public int getElementNo()
    {
        return elementNo;
    }

    public void setElementNo(int elementNo)
    {
        this.elementNo = elementNo;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public double getSolution()
    {
        return solution;
    }

    public void setSolution(double solution)
    {
        this.solution = solution;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (! (o instanceof KnapsackSolution))
        {
            return false;
        }
        KnapsackSolution other = (KnapsackSolution) o;
        return other.elementNo == elementNo && other.weight == weight;
    }
    
    @Override
    public int hashCode()
    {
        return elementNo * 1000 + weight % 10000;
    }

    @Override
    public int compareTo(KnapsackSolution o)
    {
        if (elementNo != o.elementNo)
        {
            return elementNo - o.elementNo;
        }
        else
        {
            return weight - o.weight;
        }
    }
    
    @Override
    public String toString()
    {
        return "[" + elementNo + "," + weight + "] : " + solution; 
    }

}
