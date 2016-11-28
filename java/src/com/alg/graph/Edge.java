package com.alg.graph;

public class Edge
{
    int u;
    int v;
    
    public Edge(int u, int v)
    {
        super();
        this.u = Math.min(u, v);
        this.v = Math.max(u, v);
    }
    
    @Override
    public boolean equals(Object other)   
    {
        if (other == null)
        {
            return false;
        }
        if (! (other instanceof Edge))
        {
            return false;
        }
        Edge e2 = (Edge) other;
        return e2.u == u && e2.v == v;
    }
    
    @Override
    public int hashCode()
    {
        return u + 17 * v;
    }
    
    @Override
    public String toString()
    {
        return u + "," + v;
    }

}
