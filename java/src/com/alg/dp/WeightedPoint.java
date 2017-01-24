package com.alg.dp;

public class WeightedPoint implements Comparable<WeightedPoint>
{
    Integer point;
    Double weight;
    
    public WeightedPoint()
    {
        super();
    }

    public WeightedPoint(Integer point, Double weight)
    {
        super();
        this.point = point;
        this.weight = weight;
    }

    public Integer getPoint()
    {
        return point;
    }

    public void setPoint(Integer point)
    {
        this.point = point;
    }

    public Double getWeight()
    {
        return weight;
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedPoint o)
    {
        return point - o.point;
    }
    
    

}
