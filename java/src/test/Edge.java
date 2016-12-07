package test;

public class Edge
{
    int from;
    int to;
    
    public Edge(int from, int to)
    {
        super();
        this.from = from;
        this.to = to;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (! (other instanceof Edge))
        {
            return false;
        }
        Edge e2 = (Edge) other;
        return (from == e2.from) && (to == e2.to);
    }
    
    @Override
    public int hashCode()
    {
        return from * 17 + to;
    }

}
