package com.alg.graph.dijkstra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

public class Graph
{
    HashSet<Integer> nodes = new HashSet<>();
    HashMap<Integer, ArrayList<Edge>> nodesEdges = new HashMap<>();

    public Graph()
    {
        super();
    }
    
    public ArrayList<Edge> getEdges(Integer node)
    {
        return nodesEdges.get(node);
    }
    
    public static Graph readFromFile(String fileName) throws IOException
    {
        Graph g = new Graph();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            line = line.trim();
            if (line != null && line.length() > 0)
            {
                String[] parts = line.split("\\s+");
                if (parts.length > 0)
                {
                    int startNode = Integer.parseInt(parts[0]);
                    g.nodes.add(startNode);
                    ArrayList<Edge> nodeEdges = g.nodesEdges.get(startNode);
                    if (nodeEdges == null)
                    {
                        nodeEdges = new ArrayList<>();
                        g.nodesEdges.put(startNode, nodeEdges);
                    }
                    for (int i=1; i<parts.length; i++)
                    {
                        String[] nextPart = parts[i].split(",");
                        if (nextPart.length == 2)
                        {
                            int nextNode = Integer.parseInt(nextPart[0]);
                            int distance = Integer.parseInt(nextPart[1]);
                            Edge e = new Edge(startNode, nextNode, distance);
                            nodeEdges.add(e);
                        }
                    }
                }
            }
        }
        reader.close();
        return g;
    }
    
    public int nodeCount()
    {
        return nodes.size();
    }
    
    public int edgeCount()
    {
        int edges = 0;
        for (Entry<Integer, ArrayList<Edge>> entry : nodesEdges.entrySet())
        {
            edges += entry.getValue().size();
        }
        return edges;
    }
    
    public static Graph generateRandomGraph(int nodes)
    {
        Graph g = new Graph();
        for (int i=1; i<=nodes; i++)
        {
            g.nodes.add(i);
            g.nodesEdges.put(i, new ArrayList<>());
        }
        Random rand = new Random();
        ArrayList<Integer> nodesArray = new ArrayList<Integer>(g.nodes);
        HashSet<String> edges = new HashSet<>();
        for (int i=0; i<nodes*2; i++)
        {
            int pos1 = rand.nextInt(nodes);
            int pos2 = rand.nextInt(nodes-1);
            int node1 = nodesArray.get(pos1);
            int node2 = nodesArray.get(pos2);
            if (pos2 >= pos1)
            {
                node2 = nodesArray.get(pos2+1);
            }
            int distance = 1 + rand.nextInt(100);
            Edge e1 = new Edge(node1, node2, distance);
            if (! edges.contains(node1 + ":"  + node2))
            {
                ArrayList<Edge> nodeEdges1 = g.nodesEdges.get(node1);
                nodeEdges1.add(e1);
                Edge e2 = new Edge(node2, node1, distance);
                ArrayList<Edge> nodeEdges2 = g.nodesEdges.get(node2);
                nodeEdges2.add(e2);
                edges.add(node1 + ":"  + node2);
                edges.add(node2 + ":"  + node1);
            }
        }
        return g;
    }
    
    public void print()
    {
        try
        {
            print(null);
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void print(String fileName) throws IOException
    {
        BufferedWriter out = null;
        if (fileName != null)
        {
            out = new BufferedWriter(new FileWriter(new File(fileName)));
        }
        ArrayList<Integer> nodesArray = new ArrayList<Integer>(nodes);
        Collections.sort(nodesArray);
        for (Integer node : nodesArray)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(node);
            ArrayList<Edge> nodeEdges = nodesEdges.get(node);
            if (nodesEdges != null)
            {
                for (Edge e : nodeEdges)
                {
                    sb.append("\t");
                    sb.append(e.to + "," + e.distance);
                }
            }
            if (out != null)
            {
                out.write(sb.toString());
                out.write("\r\n");
            }
            else
            {
                System.out.println(sb.toString());
            }
        }
        if (out != null)
        {
            out.close();
        }
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        System.out.println("Number of Nodes = " + g.nodeCount());
        System.out.println("Number of Edges = " + g.edgeCount());
    }
    
    public static void genRandomGraph(int nodes)
    {
        Graph g = generateRandomGraph(nodes);
        g.print();
        System.out.println("Number of nodes = " + g.nodeCount());
        System.out.println("Number of edges = " + g.edgeCount());
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01("C:/Users/rubandyopadhyay/Downloads/dijkstraData.txt");
        // test01("data/shortest_path_01.txt");
        genRandomGraph(30);
    }

}
