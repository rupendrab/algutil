package com.alg.graph.bfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BFSearch
{
    Graph G;
    Integer startNode;
    HashMap<Integer, Integer> visited = new HashMap<>();
    
    public BFSearch(Graph g, Integer startNode)
    {
        super();
        G = g;
        this.startNode = startNode;
    }
    
    public void compute()
    {
        visited = computeFromNode(startNode);
    }
    
    public HashMap<Integer, Integer> computeFromNode(int startNode)
    {
        HashMap<Integer, Integer> visited = new HashMap<>();
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        
        queue.add(startNode);
        visited.put(startNode, 0);
        
        while (! queue.isEmpty())
        {
            Integer nodeToExamine = queue.poll();
            // System.out.println("Examining Node " + nodeToExamine);
            Integer currentLayer = visited.get(nodeToExamine);
            ArrayList<Integer> neighbors = G.nodesEdges.get(nodeToExamine);
            for (Integer neighbor : neighbors)
            {
                // System.out.println("  Examining neighbor " + neighbor);
                if (!visited.containsKey(neighbor)) // If neighbor is unexplored
                {
                    visited.put(neighbor, currentLayer+1);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }
    
    public void addToVisited(HashMap<Integer, Integer> newVisits)
    {
        for (Entry<Integer, Integer> entry : newVisits.entrySet())
        {
            visited.put(entry.getKey(), entry.getValue());
        }
    }
    
    public ArrayList<HashMap<Integer, Integer>> connectedComponents()
    {
        ArrayList<HashMap<Integer, Integer>> ret = new ArrayList<>();
        visited = new HashMap<>();
        for (Integer node : G.nodes)
        {
            if (! visited.containsKey(node))
            {
                HashMap<Integer, Integer> newVisits = computeFromNode(node);
                ret.add(newVisits);
                addToVisited(newVisits);
            }
        }
        return ret;
    }
    
    public int shortestPathLength(Integer from, Integer to)
    {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        HashMap<Integer, Integer> visited = new HashMap<>();
        
        queue.add(from);
        visited.put(from, 0);
        
        while (! queue.isEmpty())
        {
            Integer nodeToExamine = queue.poll();
            // System.out.println("Examining Node " + nodeToExamine);
            Integer currentLayer = visited.get(nodeToExamine);
            ArrayList<Integer> neighbors = G.nodesEdges.get(nodeToExamine);
            for (Integer neighbor : neighbors)
            {
                // System.out.println("  Examining neighbor " + neighbor);
                if (!visited.containsKey(neighbor)) // If neighbor is unexplored
                {
                    visited.put(neighbor, currentLayer+1);
                    queue.add(neighbor);
                    if (neighbor == to)
                    {
                        return currentLayer + 1;
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }
    
    public void print()
    {
        LinkedList<Map.Entry<Integer, Integer>> data = new LinkedList<>(visited.entrySet());
        Collections.sort(data, new Comparator<Map.Entry<Integer, Integer>>() {

            @Override
            public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Integer prevLayer = null;
        for (Entry<Integer, Integer> v : data)
        {
            Integer node = v.getKey();
            Integer layer = v.getValue();
            if (layer != prevLayer)
            {
                System.out.println();
                System.out.print("Layer : " + layer + ", Nodes : " + node);
            }
            else
            {
                System.out.print(", " + node);
            }
            prevLayer = layer;
        }
        System.out.println();
    }
    
    public static void test01(String fileName, Integer startNode) throws IOException
    {
        Graph G = Graph.readFromFile(fileName);
        BFSearch bfs = new BFSearch(G, startNode);
        bfs.compute();
        bfs.print();
    }
    
    public static void test02(String fileName) throws IOException
    {
        Graph G = Graph.readFromFile(fileName);
        System.out.println(G.nodesEdges);
        BFSearch bfs = new BFSearch(G, 0);
        System.out.println(bfs.shortestPathLength(0, 3));
        System.out.println(bfs.shortestPathLength(1, 2));
        System.out.println(bfs.shortestPathLength(1, 4));
        System.out.println(bfs.shortestPathLength(1, 5));
        System.out.println(bfs.shortestPathLength(5, 1));
        System.out.println(bfs.shortestPathLength(3, 0));
    }

    public static void test03(String fileName) throws IOException
    {
        Graph G = Graph.readFromFile(fileName);
        BFSearch bfs = new BFSearch(G, null);
        ArrayList<HashMap<Integer, Integer>> cComps = bfs.connectedComponents();
        System.out.println("Number of segregated parts: " + cComps.size());
        int i = 0;
        for (HashMap<Integer, Integer> part : cComps)
        {
            System.out.println(String.format("  Part %d : %s", (i+1), part.keySet()));
            i++;
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("data/bfs_01.txt", 3);
        test02("data/bfs_01.txt");
        test03("data/bfs_01.txt");
        test03("data/bfs_02.txt");
    }
}
