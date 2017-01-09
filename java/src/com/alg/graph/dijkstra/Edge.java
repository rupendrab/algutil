package com.alg.graph.dijkstra;

public class Edge
{
    Integer from;
    Integer to;
    int distance;
    
    public Edge(Integer from, Integer to, int distance)
    {
        super();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }
    
    public Edge(Edge o)
    {
        super();
        this.from = o.from;
        this.to = o.to;
        this.distance = o.distance;
    }
    
    public Integer getFrom()
    {
        return from;
    }


    public void setFrom(Integer from)
    {
        this.from = from;
    }


    public Integer getTo()
    {
        return to;
    }


    public void setTo(Integer to)
    {
        this.to = to;
    }


    public int getDistance()
    {
        return distance;
    }


    public void setDistance(int distance)
    {
        this.distance = distance;
    }


    @Override
    public String toString()
    {
        return String.format("%d=>%d(%d)", from, to, distance);
    }
    
    public String toCsvString()
    {
        return String.format("%d,%d,%d", from, to, distance);
    }
    
    public boolean isSame(Edge e2)
    {
        if (e2.from.equals(from) && e2.to.equals(to) && e2.distance == distance)
        {
            return true;
        }
        else if (e2.from.equals(to) && e2.to.equals(from) && e2.distance == distance)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
