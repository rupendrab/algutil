package com.alg.graph.dijkstra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DijkstraSimple
{
    Graph g;
    Integer startNode;
    HashMap<Integer, ArrayList<Integer>> shortestPaths = new HashMap<>();
    HashMap<Integer, Integer> shortestPathLengths = new HashMap<>();
    
    public DijkstraSimple(Graph g, Integer startNode)
    {
        super();
        this.g = g;
        this.startNode = startNode;
        process();
    }

    public void process()
    {
        shortestPaths = new HashMap<>();
        shortestPathLengths = new HashMap<>();
        shortestPathLengths.put(startNode, 0);
        shortestPaths.put(startNode, new ArrayList<>());
        HashSet<Integer> processed = new HashSet<>();
        processed.add(startNode);
        HashSet<Integer> remaining = new HashSet<>();
        for (Integer node : g.nodes)
        {
            if (node != startNode)
            {
                remaining.add(node);
            }
        }
        while (remaining.size() > 0)
        {
            int minPath = -1;
            int chosenNode = -1;
            Edge chosenEdge = null;
            int counted = 0;
            for (Integer s : processed)
            {
                int sourcePathLength = shortestPathLengths.get(s);
                for (Edge e : g.nodesEdges.get(s))
                {
                    if (remaining.contains(e.to))
                    {
                        int pathLength = sourcePathLength + e.distance;
                        if (counted == 0)
                        {
                            minPath = pathLength;
                            chosenNode = e.to;
                            chosenEdge = e;
                        }
                        else
                        {
                            if (pathLength < minPath)
                            {
                                minPath = pathLength;
                                chosenNode = e.to;
                                chosenEdge = e;
                            }
                        }
                        counted++;
                    }
                }
            }
            shortestPathLengths.put(chosenNode, minPath);
            @SuppressWarnings("unchecked")
            ArrayList<Integer> startingPath = (ArrayList<Integer>) shortestPaths.get(chosenEdge.from).clone();
            startingPath.add(chosenNode);
            shortestPaths.put(chosenNode, startingPath);
            processed.add(chosenNode);
            remaining.remove(chosenNode);
            // System.out.println(shortestPaths);
        }
    }
    
    public String getShortestPathLengths(int... nodes)
    {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int node : nodes)
        {
            if (i > 0)
            {
                sb.append(",");
            }
            Integer shortestPath = shortestPathLengths.get(node);
            if (shortestPath == null)
            {
                shortestPath = 1000000;
            }
            sb.append(shortestPath);
            i++;
        }
        return sb.toString();
    }
    
    public String showDetailedResults()
    {
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> nodes = new ArrayList<Integer>(shortestPaths.keySet());
        Collections.sort(nodes);
        for (Integer node : nodes)
        {
            sb.append(String.format("%d %d %s", node, shortestPathLengths.get(node), shortestPaths.get(node)));
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public static void test01(String fileName, int startNode) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        DijkstraSimple ds = new DijkstraSimple(g, startNode);
        // System.out.println(ds.shortestPathLengths);
        // System.out.println(ds.shortestPaths);
        System.out.println(ds.showDetailedResults());
    }
    
    public static void test02(String fileName, int startNode, int... nodes) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        DijkstraSimple ds = new DijkstraSimple(g, startNode);
        System.out.println(ds.getShortestPathLengths(nodes));
    }

    public static void main(String[] args) throws Exception
    {
        //test01("data/shortest_path_01.txt", 1);
        // test01("C:/Users/rubandyopadhyay/Downloads/dijkstraData.txt", 1);
        long start, end;
        start = Calendar.getInstance().getTimeInMillis();
        test02("C:/Users/rubandyopadhyay/Downloads/dijkstraData.txt", 1, 7,37,59,82,99,115,133,165,188,197);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to calculate = %d ms", (end-start)));
        // 2599,2610,2947,2052,2367,2399,2029,2442,2505,3068
        test01("data/shortest_path_04.txt", 1);
    }

}
