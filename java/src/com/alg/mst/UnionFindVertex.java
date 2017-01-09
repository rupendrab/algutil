package com.alg.mst;

import java.util.ArrayList;

public class UnionFindVertex<T>
{
    T vertex;
    int count;
    UnionFindVertex<T> leader;
    ArrayList<UnionFindVertex<T>> children = new ArrayList<>();
    
    public UnionFindVertex(T vertex)
    {
        super();
        this.vertex = vertex;
        this.count = 1;
        this.leader = this;
        children.add(this);
    }

    public T getVertex()
    {
        return vertex;
    }

    public int getCount()
    {
        return count;
    }

    public UnionFindVertex<T> getLeader()
    {
        return leader;
    }

    public void setLeader(UnionFindVertex<T> leader)
    {
        UnionFindVertex<T> oldLeader = this.leader;
        if (oldLeader == leader)
        {
            return;
        }
        oldLeader.leader = leader;
        leader.count += oldLeader.count;
        oldLeader.count = 1;
        for (UnionFindVertex<T> child : oldLeader.children)
        {
            child.leader = leader;
            leader.children.add(child);
        }
        oldLeader.children = new ArrayList<>();
    }
    
    public boolean isConnected(UnionFindVertex<T> other)
    {
        return leader.equals(other.leader);
    }
    
    /*
     * unions the group of the current vertex with another vertex. If they are in the same group,
     * does nothing. If they are in different groups, creates a new group like below:
     *   1. Finds the leader of both groups
     *   2. Selects the leader with lower count
     *   3. Updates the leader of all of it's children including itself to the leader of the other group
     *   4. Computes the count of the new leader as the addition of the two group counts
     *   5. Returns the new leader
     */
    
    public UnionFindVertex<T> union(UnionFindVertex<T> other)
    {
        if (this.leader == other.leader)
        {
            return this.leader;
        }
        if (this.leader.count < other.leader.count)
        {
            setLeader(other.leader);
            return other.leader;
        }
        else
        {
            other.setLeader(leader);
            return leader;
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(vertex);
        sb.append(" -> " + leader.vertex);
        sb.append(" (" + count + ") ");
        sb.append("[");
        int i = 0;
        for (UnionFindVertex<T> child: children)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(child.vertex);
            i++;
        }
        sb.append("]");
        return sb.toString();
    }
    
    public static void printAll(ArrayList<UnionFindVertex<Integer>>vertices)
    {
        for (int i = 0; i<vertices.size(); i++)
        {
            System.out.println(vertices.get(i));
        }
    }
    
    public static void test01()
    {
        ArrayList<UnionFindVertex<Integer>> vertices = new ArrayList<UnionFindVertex<Integer>>(10);
        for (int i = 1; i<=5; i++)
        {
            vertices.add(new UnionFindVertex<Integer>(i));
        }
        System.out.println("Initial State...");
        printAll(vertices);

        vertices.get(0).union(vertices.get(1));
        System.out.println("Added 1 to 2");
        printAll(vertices);
        
        vertices.get(2).union(vertices.get(3));
        System.out.println("Added 3 to 4");
        printAll(vertices);

        vertices.get(3).union(vertices.get(4));
        System.out.println("Added 4 to 5");
        printAll(vertices);

        vertices.get(1).union(vertices.get(2));
        System.out.println("Added 1 to 3");
        printAll(vertices);

    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }
    
}
