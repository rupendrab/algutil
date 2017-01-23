package com.alg;

import java.util.TreeSet;

public class ZeroPositions implements Comparable<ZeroPositions>
{
    Integer index;
    TreeSet<Integer> subIndices = new TreeSet<>();
    
    public ZeroPositions(Integer index)
    {
        super();
        this.index = index;
    }
    
    public boolean addZeroPosition(Integer subposition)
    {
        return subIndices.add(subposition);
    }
    
    public boolean deleteZeroPosition(Integer subposition)
    {
        return subIndices.remove(subposition);
    }

    public int getSize()
    {
        return subIndices.size();
    }
    
    public Integer getIndex()
    {
        return index;
    }

    public TreeSet<Integer> getSubIndices()
    {
        return subIndices;
    }

    @Override
    public int compareTo(ZeroPositions o)
    {
        return getSize() - o.getSize();
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (! (o instanceof ZeroPositions))
        {
            return false;
        }
        ZeroPositions other = (ZeroPositions) o;
        return getIndex() == other.getIndex(); 
    }
    
    @Override
    public int hashCode()
    {
        return index.hashCode();
    }
    
    public String toString()
    {
        return index + " (" + getSize() + "): " + subIndices;
    }

}
