package com.alg.mst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.alg.graph.dijkstra.Edge;
import com.alg.graph.dijkstra.Graph;
import com.alg.graph.dijkstra.MinHeap;
import com.alg.graph.dijkstra.VertexScore;

public class PrimMSTBasic
{
    Graph g;

    public PrimMSTBasic(Graph g)
    {
        super();
        this.g = g;
    }

    public Graph minimumSpanningTree()
    {
        Graph mst = new Graph();
        HashSet<Integer> nodesExplored = new HashSet<>();
        HashSet<Integer> nodesUnexplored = new HashSet<>();
        HashSet<Edge> edgesSelected = new HashSet<>();
        
        ArrayList<Integer> allNodes = new ArrayList<Integer>(g.getNodes());
        Integer startNode = allNodes.get(0);
        nodesUnexplored.addAll(allNodes);
        nodesExplored.add(startNode);
        nodesUnexplored.remove(startNode);
        
        while (nodesUnexplored.size() > 0)
        {
            Edge bestEdge = null;
            for (Integer node : nodesExplored)
            {
                for (Edge edge : g.getEdges(node))
                {
                    if (nodesUnexplored.contains(edge.getFrom()) ||  nodesUnexplored.contains(edge.getTo()))
                    {
                        if (bestEdge == null)
                        {
                            bestEdge = new Edge(edge);
                        }
                        else
                        {
                            if (bestEdge.getDistance() > edge.getDistance())
                            {
                                bestEdge = new Edge(edge);
                            }
                        }
                    }
                }
            }
            if (bestEdge == null)
            {
                break;
            }
            edgesSelected.add(bestEdge);
            // System.out.println(bestEdge.toCsvString());
            Integer nodeSelected = bestEdge.getTo();
            if (nodesExplored.contains(nodeSelected))
            {
                nodeSelected = bestEdge.getFrom();
            }
            nodesExplored.add(nodeSelected);
            nodesUnexplored.remove(nodeSelected);
        }
        
        mst.setNodes(nodesExplored);
        for (Edge e : edgesSelected)
        {
            mst.addEdge(e);
        }
        return mst;
    }
    
    public Edge getBestEdgeBF(HashSet<Integer> nodesExplored, HashSet<Integer> nodesUnexplored)
    {
        Edge bestEdge = null;
        for (Integer node : nodesExplored)
        {
            for (Edge edge : g.getEdges(node))
            {
                if (nodesUnexplored.contains(edge.getFrom()) ||  nodesUnexplored.contains(edge.getTo()))
                {
                    if (bestEdge == null)
                    {
                        bestEdge = new Edge(edge);
                    }
                    else
                    {
                        if (bestEdge.getDistance() > edge.getDistance())
                        {
                            bestEdge = new Edge(edge);
                        }
                    }
                }
            }
        }
        return bestEdge;
    }
    
    public Graph minimumSpanningTreeHeap()
    {
        Graph mst = new Graph();
        HashSet<Integer> nodesExplored = new HashSet<>();
        HashSet<Integer> nodesUnexplored = new HashSet<>();
        HashSet<Edge> edgesSelected = new HashSet<>();
        
        ArrayList<Integer> allNodes = new ArrayList<Integer>(g.getNodes());
        Integer startNode = allNodes.get(0);
        nodesUnexplored.addAll(allNodes);
        nodesExplored.add(startNode);
        nodesUnexplored.remove(startNode);
        MinHeap mh = new MinHeap();
        for (Edge e : g.getEdges(startNode))
        {
            Integer newVertex = e.getFrom().equals(startNode) ? e.getTo() : e.getFrom();
            mh.insertItem(new VertexScore(startNode, newVertex, e.getDistance()));
            /*
            if (!mh.isValid())
            {
                System.out.println("Error while inserting...");
                System.out.println(Arrays.toString(mh.getArray()));
                return mst;
            }
            */
        }
        // System.out.println("Initial inserts OK");
        
        while (nodesUnexplored.size() > 0)
        {
            /*
            if (!mh.isValid())
            {
                System.out.println("Here..");
                System.out.println(Arrays.toString(mh.getArray()));
                return mst;
            }
            */
            VertexScore minPath = mh.deleteMin();
            /*
            System.out.println("Before Delete Min..");
            System.out.println(Arrays.toString(mh.getArray()));
            if (!mh.isValid())
            {
                System.out.println("After Delete Min..");
                System.out.println(Arrays.toString(mh.getArray()));
                return mst;
            }
            */
            Integer nodeSelected = minPath.getVertex();
            Edge bestEdge = new Edge(minPath.getSourceVertex(), nodeSelected, minPath.getScore());
            /*
            Edge bestEdgeBF = getBestEdgeBF(nodesExplored, nodesUnexplored);
            if (bestEdge.getDistance() != bestEdgeBF.getDistance())
            {
                System.out.println("Mismatch: " + bestEdge.toCsvString() + " : " + bestEdgeBF.toCsvString());
                System.out.println("Node selected Heap = " + nodeSelected);
                Integer newNodeBF = nodesExplored.contains(bestEdgeBF.getFrom()) ? bestEdgeBF.getTo() : bestEdgeBF.getFrom();
                System.out.println("New node BF = " + newNodeBF);
                System.out.println("Is this in Heap = " + (mh.getVertex(newNodeBF) != null));
                VertexScore vs = mh.getVertex(newNodeBF);
                System.out.println("Heap Score = " + vs.getScore());
                return mst;
            }
            */
            edgesSelected.add(bestEdge);
            // System.out.println(bestEdge.toCsvString());
            nodesExplored.add(nodeSelected);
            nodesUnexplored.remove(nodeSelected);
            if (nodesUnexplored.size() == 0)
            {
                break;
            }
            for (Edge edge : g.getEdges(nodeSelected))
            {
                if (nodesUnexplored.contains(edge.getFrom()) ||  nodesUnexplored.contains(edge.getTo()))
                {
                    Integer vertex = edge.getFrom().equals(nodeSelected) ? edge.getTo() : edge.getFrom();
                    VertexScore currentScore = mh.getVertex(vertex);
                    VertexScore newScore = new VertexScore(nodeSelected, vertex, edge.getDistance());
                    if (currentScore == null)
                    {
                        mh.insertItem(newScore);
                        /*
                        if (! mh.isValid())
                        {
                            System.out.println("  inserting " + newScore);
                        }
                        */
                    }
                    else if (edge.getDistance() < currentScore.getScore())
                    {
                        /*
                        System.out.println("Before delete...");
                        System.out.println(Arrays.toString(mh.getArray()));
                        System.out.println("Size = " + mh.getSize());
                        */
                        mh.deleteVertex(vertex);
                        /*
                        if (! mh.isValid())
                        {
                            System.out.println("  deleting " + vertex);
                            System.out.println(Arrays.toString(mh.getArray()));
                            System.out.println("Size = " + mh.getSize());
                        }
                        */
                        mh.insertItem(newScore);
                        /*
                        if (! mh.isValid())
                        {
                            System.out.println("  inserting " + newScore);
                            System.out.println(Arrays.toString(mh.getArray()));
                        }
                        */
                    }
                }
            }
        }
        
        mst.setNodes(nodesExplored);
        for (Edge e : edgesSelected)
        {
            mst.addEdge(e);
        }
        return mst;        
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFileFormatTwo(fileName, 1);
        PrimMSTBasic primMST = new PrimMSTBasic(g);
        Graph mst = primMST.minimumSpanningTree();
        System.out.println(String.format("Original Graph: nodes = %d, edges = %d, sum edge lengths = %d", g.nodeCount(), g.edgeCount(), g.sumEdgeLengths()));
        System.out.println(String.format("MST Graph: nodes = %d, edges = %d, sum edge lengths = %d", mst.nodeCount(), mst.edgeCount(), mst.sumEdgeLengths()));
        Graph mstHeap = primMST.minimumSpanningTreeHeap();
        System.out.println(String.format("MST Heap Graph: nodes = %d, edges = %d, sum edge lengths = %d", mstHeap.nodeCount(), mstHeap.edgeCount(), mstHeap.sumEdgeLengths()));
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("C:\\Users\\rubandyopadhyay\\Downloads\\edges.txt");
        test01("C:\\Users\\rubandyopadhyay\\Downloads\\edges-02.txt");
    }
}
