package com.alg.graph.bfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class Graph
{
    HashSet<Integer> nodes = new HashSet<>();
    HashMap<Integer, ArrayList<Integer>> nodesEdges = new HashMap<>();

    public Graph()
    {
        super();
    }
    
    public HashSet<Integer> getNodes()
    {
        return nodes;
    }

    public HashMap<Integer, ArrayList<Integer>> getNodesEdges()
    {
        return nodesEdges;
    }

    public static Graph readFromFile(String fileName) throws IOException
    {
        Graph g = new Graph();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            if (line.trim().equals(""))
            {
                continue;
            }
            String[] fields = line.split("\\s+");
            if (fields.length > 0)
            {
                int i = 0;
                Integer startNode = null;
                ArrayList<Integer> nodeEdges = null;
                for (String field : fields)
                {
                    Integer node = Integer.parseInt(field);
                    g.nodes.add(node);
                    if (i == 0)
                    {
                        startNode = node;
                        nodeEdges = g.nodesEdges.get(startNode);
                        if (nodeEdges == null)
                        {
                            nodeEdges = new ArrayList<>();
                            g.nodesEdges.put(startNode, nodeEdges);
                        }
                    }
                    else
                    {
                        nodeEdges.add(node);
                    }
                    i++;
                }
            }
        }
        reader.close();
        return g;
    }
    
    @Override
    public Graph clone()
    {
        Graph g = new Graph();
        for (Entry<Integer, ArrayList<Integer>> entry : nodesEdges.entrySet())
        {
            Integer node = entry.getKey();
            g.nodes.add(node);
            ArrayList<Integer> connectedNodes = entry.getValue();
            ArrayList<Integer> nodeEdges = g.nodesEdges.get(node);
            if (nodeEdges == null)
            {
                nodeEdges = new ArrayList<>();
                g.nodesEdges.put(node, nodeEdges);
            }
            nodeEdges.addAll(connectedNodes);
            g.nodes.addAll(connectedNodes);
        }
        return g;
    }
    
    public int vertexCount()
    {
        return nodes.size();
    }
    
    public int edgesCount()
    {
        int edges = 0;
        for ( ArrayList<Integer> nodes : nodesEdges.values())
        {
            edges += nodes.size();
        }
        return edges;
    }
    
    public void addEdge(Integer fromNode, Integer toNode)
    {
        ArrayList<Integer> nodeEdges = nodesEdges.get(fromNode);
        if (nodeEdges == null)
        {
            nodeEdges = new ArrayList<>();
            nodesEdges.put(fromNode, nodeEdges);
        }
        nodeEdges.add(toNode);
    }
    
    public Graph reversedEdges()
    {
        Graph g = new Graph();
        for (Integer node : nodes)
        {
            g.nodes.add(node);
        }
        for (Entry<Integer, ArrayList<Integer>> entry : nodesEdges.entrySet())
        {
            Integer node = entry.getKey();
            g.nodes.add(node);
            ArrayList<Integer> connectedNodes = entry.getValue();
            if (connectedNodes != null)
            {
                for (Integer neighborNode : connectedNodes)
                {
                    g.addEdge(neighborNode, node);
                }
            }
        }
        return g;
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        System.out.println(g.nodes);
        System.out.println(g.nodesEdges);
        Graph gRev = g.reversedEdges();
        System.out.println(gRev.nodes);
        System.out.println(gRev.nodesEdges);
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("data/SCC_01.txt");
    }
}
