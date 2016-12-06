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

import com.alg.Util;

public class Graph
{
    HashSet<Integer> nodes = new HashSet<Integer>();
    Hashtable<Edge, Integer> edges = new Hashtable<Edge, Integer>();
    Hashtable<Integer, ArrayList<Edge>> nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
    Hashtable<Integer, HashSet<Integer>> mergedNodes = new Hashtable<Integer, HashSet<Integer>>();
    int noEdges;
    
    Random rand = new Random();

    public Graph()
    {
        super();
    }
    
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
                ne.setValid(e.isValid());
                n.addNodeEdge(node, ne);
                if (node == e.u)
                {
                    Integer cnt = n.edges.get(ne);
                    if (cnt == null)
                    {
                        n.edges.put(ne,  1);
                    }
                    else
                    {
                        n.edges.put(ne,  cnt + 1);
                    }
                }
            }
        }
        n.noEdges = noEdges;
        return n;
    }
    
    public void validate()
    {
        for (int n : nodes)
        {
            ArrayList<Edge> nodeEdges = nodesEdges.get(n);
            System.out.println("Evaluating node " + n);
            for (Edge e : nodeEdges)
            {
            }
        }
    }
    
    public void removeEdge(Edge e)
    {
        edges.remove(e);
        noEdges--;
    }
    
    public void mergeEdge(Edge e)
    {
        Integer node1 = e.u;
        Integer node2 = e.v;
        // System.out.println(String.format("Node1 = %d, Node2 = %d", node1, node2));
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
                /*
                Edge newEdge = new Edge(node1, otherNode);
                addNodeEdge(node1, newEdge);
                addNodeEdge(otherNode, newEdge);
                edges.add(newEdge);
                */
                Integer cnt = edges.remove(node2Edge);
                toRemove.add(node2Edge);
                Edge newEdge = node2Edge.reassignNode(node2, node1);
                addNodeEdge(node1, newEdge);
                addNodeEdge(otherNode, newEdge);
                Integer cnt2 = edges.get(newEdge);
                if (cnt2 == null)
                {
                    edges.put(newEdge, 1);
                }
                else
                {
                    edges.put(newEdge, cnt2 + 1);
                }
                // System.out.println("Reassigned node = " + node2Edge + " : " + node2Edge.origEdges);
            }
            else
            {
                toRemove.add(node2Edge);
            }
        }
        for (Edge tre : toRemove)
        {
            // System.out.println("Removing edge " + tre);
            removeNodeEdge(tre.u, tre);
            removeNodeEdge(tre.v, tre);
            removeEdge(tre);
        }
        // showEdges(node1);
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
        // System.out.println("Remove edge " + edge + " from node " + node);
        ArrayList<Edge> nodeEdge = nodesEdges.get(node);
        // System.out.println("Before removal " + nodeEdge);
        nodeEdge.remove(edge);
        // System.out.println("After removal " + nodeEdge);
    }

    public void readFromNodeFile(String fileName) throws IOException
    {
        nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
        HashSet<Integer> myNodes = new HashSet<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split("\\s+");
            // System.out.println(Arrays.toString(parts));
            if (parts.length > 0)
            {
                Integer startNode = Integer.parseInt(parts[0]);
                myNodes.add(startNode);
                for (int i=1; i<parts.length; i++)
                {
                    Integer adjacentNode = Integer.parseInt(parts[i]);
                    myNodes.add(adjacentNode);
                    Edge e = new Edge(startNode, adjacentNode);
                    if (startNode > adjacentNode)
                    {
                        Integer cnt = edges.get(e);
                        if (cnt == null)
                        {
                            edges.put(e, 1);
                        }
                        else
                        {
                            edges.put(e, cnt + 1);
                        }
                    }
                    addNodeEdge(startNode, e);
                }
            }
        }
        reader.close();
        nodes = myNodes;
        noEdges = edges.size();
    }
    
    public void readFromFile(String fileName) throws IOException
    {
        nodesEdges = new Hashtable<Integer, ArrayList<Edge>>();
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
                if (u > v)
                {
                    Integer cnt = edges.get(e);
                    if (cnt == null)
                    {
                        edges.put(e,  1);
                    }
                    else
                    {
                        edges.put(e,  cnt + 1);
                    }
                }
                addNodeEdge(u, e);
                // addNodeEdge(v, e);
            }
        }
        reader.close();
        nodes = myNodes;
        noEdges = edges.size();
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
    
    private int getRandomNode()
    {
        int nodePos = rand.nextInt(nodes.size());
        int i = 0;
        for (int n : nodes)
        {
            if (i == nodePos)
            {
                return n;
            }
            i++;
        }
        return -1;
    }
    
    private Edge getRandomEdge()
    {
        int n = getRandomNode();
        // System.out.println("Random node chosen is "  + n);
        ArrayList<Edge> edges = nodesEdges.get(n);
        int pos = rand.nextInt(edges.size());
        return edges.get(pos);
    }
    
    public String showEdges()
    {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
    
    public Edge[] cutEdges()
    {
        return null;
    }
    
    public Edge firstEdge(Edge e)
    {
        while (e.origEdges != null && e.origEdges.size() != 0)
        {
            e = e.origEdges.get(0);
        }
        return e;
    }
    
    public Graph minCut(boolean print)
    {
        // validate();
        Graph n = copy();
        // n.validate();
        while (n.nodes.size() > 2)
        {
            if (print)
            {
                System.out.println(n.nodes);
                System.out.println(n.edges);
                System.out.println(n.nodesEdges);
                System.out.println(n.mergedNodes);
                // n.validate();
                System.out.println("-------------------");
            }
            Edge edge = n.getRandomEdge();
            if (print)
            {
                System.out.println("Edges merged = " + edge);
            }
            n.mergeEdge(edge);
        }
        if (print)
        {
            System.out.println(n.nodes);
            System.out.println(n.edges);
            System.out.println(n.mergedNodes);
            Integer[] nodes = new Integer[2];
            n.nodes.toArray(nodes);
            for (Edge e : n.nodesEdges.get(nodes[0]))
            {
                System.out.println(firstEdge(e));
            }
            System.out.println("-------------------");
        }
        return n;
    }
    
    int noCuts()
    {
        if (nodes.size() == 2)
        {
            for (Entry<Edge, Integer> entry : edges.entrySet())
            {
                return entry.getValue();
            }
        }
        return Integer.MAX_VALUE;
    }
    
    public Graph findMinCutByIteration()
    {
        long start, end = 0;
        start = Util.getTime();
        int n = nodes.size();
        int noIter = (int) (n * (n-1) * 0.5 * Math.log(n * 1.0) / Math.log(2.0));
        Graph minCutGraph = null;
        for (int i=0; i<noIter; i++)
        {
            Graph graph = minCut(false);
            if (i == 0)
            {
                minCutGraph = graph;
            }
            else if (graph.noCuts() < minCutGraph.noCuts())
            {
                minCutGraph = graph;
            }
            int pctComplete = (int) ((i+1) * 100.0 / noIter);
            if ((pctComplete >= 10 && pctComplete % 10 == 0))
            {
                end = Util.getTime();
                System.out.print(String.format("Cut %d of %d: minCut = %d : ", (i+1), noIter, minCutGraph.noCuts()));
                Util.showElapsed(start, end);
            }
        }
        System.out.print(String.format("Cut %d of %d: minCut = %d : ", noIter, noIter, minCutGraph.noCuts()));
        Util.showElapsed(start, end);
        return minCutGraph;
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
        graph.minCut(true);
    }
    
    public static void test05(String fileName) throws IOException
    {
        Graph graph = new Graph();
        graph.readFromNodeFile(fileName);
        // graph.showEdges(3);
        graph.minCut(true);
    }

    public static void test06(String fileName) throws IOException
    {
        Graph graph = new Graph();
        graph.readFromNodeFile(fileName);
        Graph mn = graph.findMinCutByIteration();
        System.out.println(mn.edges);
        Integer[] nodes = new Integer[2];
        mn.nodes.toArray(nodes);
        for (Edge e : mn.nodesEdges.get(nodes[0]))
        {
            System.out.println(mn.firstEdge(e));
        }
    }

    public static void examineGraph(String fileName) throws IOException
    {
        Graph graph = new Graph();
        graph.readFromNodeFile(fileName);
        System.out.println("Number of nodes = " + graph.nodes.size());
        System.out.println("Number of edges = " + graph.noEdges);
    }
    
    public static void main(String[] args) throws Exception
    {
        // test03();
        // test04();
        // test05("data/02_nodes.txt");
        // test06("data/kargerMinCut.txt");
        examineGraph("data/kargerMinCut.txt");
    }
    
    
}
