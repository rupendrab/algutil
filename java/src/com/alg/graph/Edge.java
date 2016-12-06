package com.alg.graph;

import java.util.ArrayList;

public class Edge
{
    int u;
    int v;
    boolean valid = true;
    ArrayList<Edge> origEdges = new ArrayList<Edge>();
    
    public Edge(int u, int v)
    {
        super();
        this.u = Math.min(u, v);
        this.v = Math.max(u, v);
    }
    
    public Edge (Edge other)
    {
        super();
        this.u = other.u;
        this.v = other.v;
        this.valid = other.valid;
    }
    
    public boolean isValid()
    {
        return valid;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    public Edge reassignNode(int oldnode, int newnode)
    {
        if (newnode == u || newnode == v)
        {
            return this;
        }
        if (oldnode != u && oldnode != v)
        {
            return this;
        }
        int u1, v1 = -1;
        if (oldnode == u)
        {
            u1 = newnode;
            v1 = v;
        }
        else
        {
            u1 = u;
            v1 = newnode;
        }
        Edge e = new Edge(u1, v1);
        e.origEdges.add(this);
        return e;
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
    
    public static void main(String[] args)
    {
        Edge e = new Edge(4, 5);
        System.out.println(e);
        e.reassignNode(5, 8);
        System.out.println(e);
    }

}
