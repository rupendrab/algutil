package com.alg.graph.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.alg.graph.bfs.Graph;

public class TopologicalOrderDFSearch
{
    Graph g;
    TreeMap<Integer, Integer> topoOrder = new TreeMap<>();
    
    public TopologicalOrderDFSearch(Graph g)
    {
        super();
        this.g = g;
    }
    
    public Integer findSink(Graph g)
    {
        // This is O(n)
        for (Integer node : g.getNodes())
        {
            ArrayList<Integer> outgoing = g.getNodesEdges().get(node);
            if (outgoing == null || outgoing.size() == 0)
            {
                return node;
            }
        }
        return null;
    }
    
    public void deleteNode(Graph g, Integer node)
    {
        // Remove outgoing edges
        g.getNodesEdges().remove(node);
        
        // Remove incoming edges (This is O(n+m))
        for ( Entry<Integer, ArrayList<Integer>> entry : g.getNodesEdges().entrySet())
        {
            ArrayList<Integer> incoming = entry.getValue();
            incoming.remove(node); // This is really bad for performance
        }
        
        // Remove the node
        g.getNodes().remove(node);
    }
    
    public void compute()
    {
        Graph ng = g.clone();
        int n = ng.getNodes().size(); 
        int loopCount = 0;
        while ( n > 0)
        {
            loopCount++;
            // System.out.println(ng.getNodes());
            Integer sinkNode = findSink(ng);
            topoOrder.put(n, sinkNode);
            deleteNode(ng, sinkNode);
            n = ng.getNodes().size(); 
            if (loopCount >= 10)
            {
                break;
            }
        }
        System.out.println(topoOrder);
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        TopologicalOrderDFSearch top = new TopologicalOrderDFSearch(g);
        top.compute();
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("data/topo_01.txt");
    }
}
