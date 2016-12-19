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
    
    @Override
    public String toString()
    {
        return String.format("%d=>%d(%d)", from, to, distance);
    }
}
