package com.alg.graph.dijkstra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DijkstraHeap
{
    Graph g;
    Integer startNode;
    HashMap<Integer, ArrayList<Integer>> shortestPaths = new HashMap<>();
    HashMap<Integer, Integer> shortestPathLengths = new HashMap<>();
    
    public DijkstraHeap(Graph g, Integer startNode)
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
        MinHeap mh = new MinHeap();
        HashSet<Integer> processed = new HashSet<>();
        processed.add(startNode);
        for (Edge e : g.nodesEdges.get(startNode))
        {
            VertexScore v = new VertexScore(startNode, e.to, e.distance);
            mh.insertItem(v);
        }
        while (mh.getSize() > 0)
        {
            VertexScore newV = mh.deleteMin();
            processed.add(newV.vertex);
            shortestPathLengths.put(newV.vertex, newV.score);
            @SuppressWarnings("unchecked")
            ArrayList<Integer> startingPath = (ArrayList<Integer>) shortestPaths.get(newV.sourceVertex).clone();
            startingPath.add(newV.vertex);
            shortestPaths.put(newV.vertex, startingPath);
            
            // Now add the new paths from this node to the Min Heap
            for (Edge e : g.nodesEdges.get(newV.vertex))
            {
                Integer toNode = e.to;
                if (! processed.contains(e.to))
                {
                    // System.out.println("Edge: " + e);
                    VertexScore oldScore = mh.deleteVertex(toNode);
                    VertexScore newScore = new VertexScore(newV.vertex, toNode, newV.score + e.distance);
                    if (oldScore == null || newScore.score < oldScore.score)
                    {
                        mh.insertItem(newScore);
                    }
                    else
                    {
                        mh.insertItem(oldScore);
                    }
                }
            }
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
        DijkstraHeap dh = new DijkstraHeap(g, startNode);
        /*
        System.out.println(dh.shortestPathLengths);
        System.out.println(dh.shortestPaths);
        */
        System.out.println(dh.showDetailedResults());
    }

    public static void test02(String fileName, int startNode, int... nodes) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        DijkstraHeap dh = new DijkstraHeap(g, startNode);
        System.out.println(dh.getShortestPathLengths(nodes));
    }

    public static void main(String[] args) throws Exception
    {
        // test01("data/shortest_path_01.txt", 1);
        // test01("C:/Users/rubandyopadhyay/Downloads/dijkstraData.txt", 1);
        long start, end;
        start = Calendar.getInstance().getTimeInMillis();
        test02("C:/Users/rubandyopadhyay/Downloads/dijkstraData.txt", 1, 7,37,59,82,99,115,133,165,188,197);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time to calculate = %d ms", (end-start)));
        // 2599,2610,2947,2052,2367,2399,2029,2442,2505,3068
        // 2599,2610,2947,2052,2367,2399,2029,2442,2505,3068
    }
}
