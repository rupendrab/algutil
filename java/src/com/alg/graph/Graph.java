package com.alg.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Random;

public class Graph
{
    HashSet<Integer> nodes = new HashSet<Integer>();
    ArrayList<Edge> edges = new ArrayList<Edge>();
    Hashtable<Integer, ArrayList<Edge>> nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
    Hashtable<Integer, HashSet<Integer>> mergedNodes = new Hashtable<Integer, HashSet<Integer>>();
    
    public Graph copy()
    {
        Graph n = new Graph();
        n.nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
        for (Entry<Integer,ArrayList<Edge>> entry : nodesEdges.entrySet())
        {
            Integer node = entry.getKey();
            n.nodes.add(node);
            ArrayList<Edge> edges = entry.getValue();
            for (Edge e : edges)
            {
                Edge ne = new Edge(e.u, e.v);
                n.addNodeEdge(node, ne);
            }
        }
        for (Edge e : edges)
        {
            n.edges.add(e);
        }
        return n;
    }
    
    public void mergeEdge(Edge e)
    {
        Integer node1 = e.u;
        Integer node2 = e.v;
        ArrayList<Edge> node2Edges = nodesEdges.get(node2);
        ArrayList<Edge> toRemove = new ArrayList<Edge>();
        for (Edge node2Edge : node2Edges)
        {
            int otherNode = node2Edge.u;
            if (otherNode == node2)
            {
                otherNode = node2Edge.v;
            }
            if (otherNode != node1)
            {
                Edge newEdge = new Edge(node1, otherNode);
                addNodeEdge(node1, newEdge);
                addNodeEdge(otherNode, newEdge);
                edges.add(newEdge);
            }
            toRemove.add(node2Edge);
        }
        for (Edge tre : toRemove)
        {
            removeNodeEdge(tre.u, tre);
            removeNodeEdge(tre.v, tre);
            edges.remove(tre);
        }
        HashSet<Integer> merged = mergedNodes.get(node1); 
        if (merged == null)
        {
            merged = new HashSet<Integer>();
            merged.add(node2);
            mergedNodes.put(node1, merged);
        }
        else
        {
            merged.add(node2);
        }
        HashSet<Integer> alreadyMerged = mergedNodes.get(node2);
        if (alreadyMerged != null)
        {
            merged.addAll(alreadyMerged);
        }
        nodes.remove(node2);
    }
    
    public Graph()
    {
        super();
    }
    
    public void addNodeEdge(int node, Edge edge)
    {
        ArrayList<Edge> nodeEdge = nodesEdges.get(node);
        if (nodeEdge == null)
        {
            nodeEdge = new ArrayList<Edge>();
            nodeEdge.add(edge);
            nodesEdges.put(node, nodeEdge);
        }
        else
        {
            nodeEdge.add(edge);
        }
    }
    
    public void removeNodeEdge(int node, Edge edge)
    {
        ArrayList<Edge> nodeEdge = nodesEdges.get(node);
        nodeEdge.remove(edge);
    }

    public void readFromFile(String fileName) throws IOException
    {
        nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
        ArrayList<Edge> myEdges = new ArrayList<Edge>();
        HashSet<Integer> myNodes = new HashSet<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",");
            if (parts.length == 2)
            {
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                Edge e = new Edge(u, v);
                myNodes.add(u);
                myNodes.add(v);
                if (! myEdges.contains(e))
                {
                    myEdges.add(e);
                }
                addNodeEdge(u, e);
                // addNodeEdge(v, e);
            }
        }
        reader.close();
        nodes = myNodes;
        edges = myEdges;
        // System.out.println("Edges = " + edges.size());
    }
    
    public void showEdges(int n)
    {
        ArrayList<Edge> edges = nodesEdges.get(n);
        if (edges != null)
        {
            for (Edge e : edges)
            {
                System.out.println(e);
            }
        }
    }
    
    private Edge getRandomEdge()
    {
        Random rand = new Random();
        int pos = rand.nextInt(edges.size());
        int i=0;
        for (Edge e : edges)
        {
            if (i == pos)
            {
                return e;
            }
            i++;
        }
        return null;
    }
    
    public ArrayList<HashSet<Integer>> minCut()
    {
        ArrayList<HashSet<Integer>> ret = new ArrayList<HashSet<Integer>>();
        Graph n = copy();
        while (n.nodes.size() > 2)
        {
            System.out.println(n.nodes);
            System.out.println(n.edges);
            System.out.println(n.mergedNodes);
            System.out.println("-------------------");
            Edge e = n.getRandomEdge();
            System.out.println("Edge to merge = " + e);
            n.mergeEdge(e);
        }
        System.out.println(n.nodes);
        System.out.println(n.edges);
        System.out.println(n.mergedNodes);
        System.out.println("-------------------");
        return ret;
    }
    
    public static Graph test01(String fileName) throws IOException
    {
        Graph graph = new Graph();
        graph.readFromFile(fileName);
        return graph;
    }
    
    public static void test02() throws IOException
    {
        Graph graph = test01("data/01_edges.txt");
        graph.showEdges(2);
        Graph ngraph = graph.copy();
        System.out.println("----------------");
        ngraph.showEdges(2);
    }

    public static void test03() throws IOException
    {
        Graph graph = test01("data/01_edges.txt");
        Graph n = graph.copy();
        // Edge e = n.getRandomEdge();
        System.out.println(n.nodes.size());
        System.out.println(n.edges.size());
        Edge e = new Edge(3,8);
        System.out.println(e);
        n.mergeEdge(e);
        System.out.println(n.nodes.size());
        System.out.println(n.edges.size());
        System.out.println("Edges of 3");
        n.showEdges(3);
        System.out.println("Edges of 4");
        n.showEdges(4);
        System.out.println(n.mergedNodes.get(3));
        n.mergeEdge(new Edge(3,4));
        System.out.println(n.nodes.size());
        System.out.println(n.edges.size());
        System.out.println(n.mergedNodes.get(3));
        n.showEdges(3);
        n.mergeEdge(new Edge(3,7));
        System.out.println(n.nodes.size());
        System.out.println(n.edges.size());
        System.out.println(n.mergedNodes.get(3));
        n.showEdges(3);
    }
    
    public static void test04() throws IOException
    {
        Graph graph = test01("data/01_edges.txt");
        graph.minCut();
    }

    public static void main(String[] args) throws Exception
    {
        test04();
    }
    
    
}
