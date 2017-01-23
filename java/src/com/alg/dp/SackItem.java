package com.alg.dp;

public class SackItem
{
    int weight;
    double value;
    
    public SackItem()
    {
        super();
    }
    
    public SackItem(int weight, double value)
    {
        super();
        this.weight = weight;
        this.value = value;
    }

    public int getWeight()
    {
        return weight;
    }
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    public double getValue()
    {
        return value;
    }
    public void setValue(double value)
    {
        this.value = value;
    }
    
}
