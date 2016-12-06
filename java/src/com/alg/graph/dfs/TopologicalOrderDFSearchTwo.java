package com.alg.graph.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import com.alg.graph.bfs.Graph;

public class TopologicalOrderDFSearchTwo
{
    Graph g;
    TreeMap<Integer, Integer> topoOrder = new TreeMap<>();
    HashSet<Integer> visited = new HashSet<>();
    int current_label;
    
    public TopologicalOrderDFSearchTwo(Graph g)
    {
        super();
        this.g = g;
    }
    
    public void setVisited(Integer node)
    {
        visited.add(node);
    }
    
    public boolean isVisited(Integer node)
    {
        return visited.contains(node);
    }
    
    public void DFS(Integer startNode)
    {
        setVisited(startNode);
        ArrayList<Integer> nextNodes = g.getNodesEdges().get(startNode);
        if (nextNodes != null)
        {
            for (Integer nextNode : g.getNodesEdges().get(startNode))
            {
                if (! isVisited(nextNode))
                {
                    DFS(nextNode);
                }
            }
        }
        topoOrder.put(startNode, current_label--);
    }
    
    public void DFSLoop()
    {
        topoOrder = new TreeMap<>();
        visited = new HashSet<>();
        current_label = g.getNodes().size();
        for (Integer node : g.getNodes())
        {
            if (! isVisited(node))
            {
                DFS(node);
            }
        }
    }

    public TreeMap<Integer, Integer> getTopoOrder()
    {
        return topoOrder;
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        TopologicalOrderDFSearchTwo top = new TopologicalOrderDFSearchTwo(g);
        top.DFSLoop();
        System.out.println(top.getTopoOrder());
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("data/topo_01.txt");
    }
}
