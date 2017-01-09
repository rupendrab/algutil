package com.alg.mst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.alg.graph.dijkstra.Edge;
import com.alg.graph.dijkstra.Graph;

public class KruskalMSTBasic
{
    Graph g;

    public KruskalMSTBasic(Graph g)
    {
        super();
        this.g = g;
    }
    
    private Hashtable<Integer, UnionFindVertex<Integer>> getInitialUF()
    {
        Hashtable<Integer, UnionFindVertex<Integer>> vertices = new Hashtable<>();
        for (Integer node : g.getNodes())
        {
            vertices.put(node, new UnionFindVertex<Integer>(node));
        }
        return vertices;
    }
    
    public ArrayList<Edge> edgesSortedByLength()
    {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Entry<Integer, ArrayList<Edge>> entry : g.getNodesEdges().entrySet())
        {
            for (Edge edge : entry.getValue())
            {
                edges.add(edge);
            }
        }
        
        Collections.sort(edges, new Comparator<Edge>() {

            @Override
            public int compare(Edge o1, Edge o2)
            {
                if (o1.getDistance() != o2.getDistance())
                {
                    return o1.getDistance() - o2.getDistance();
                }
                else
                {
                    int fromCompare = o1.getFrom().compareTo(o2.getFrom());
                    if (fromCompare != 0)
                    {
                        return fromCompare;
                    }
                    else
                    {
                        return o1.getTo().compareTo(o2.getTo());
                    }
                }
            }
        });
        
        // Now eliminate the duplicates
        ArrayList<Edge> edgesFinal = new ArrayList<Edge>();
        int i=0;
        Edge prevEdge = null;
        for (Edge e : edges)
        {
            if (i> 0 && i % 2 == 0 && e.isSame(prevEdge))
            {
                continue;
            }
            edgesFinal.add(e);
            i++;
            prevEdge = e;
        }
        return edgesFinal;
    }
    
    public Graph minimumSpanningTree()
    {
        Graph mst = new Graph();
        ArrayList<Edge> edges = edgesSortedByLength();
        Hashtable<Integer, UnionFindVertex<Integer>> uf = getInitialUF();
        
        HashSet<Integer> nodesExplored = new HashSet<>();
        HashSet<Edge> edgesSelected = new HashSet<>();
        
        int edgesAdded = 0;
        for (Edge edge : edges)
        {
            Integer from = edge.getFrom();
            Integer to = edge.getTo();
            UnionFindVertex<Integer> fromVertex = uf.get(from);
            UnionFindVertex<Integer> toVertex = uf.get(to);
            if (fromVertex.isConnected(toVertex))
            {
                continue;
            }
            nodesExplored.add(from);
            nodesExplored.add(to);
            edgesSelected.add(edge);
            fromVertex.union(toVertex);
            edgesAdded += 1;
            if (edgesAdded == g.nodeCount() - 1)
            {
                break;
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
        KruskalMSTBasic primMST = new KruskalMSTBasic(g);
        Graph mst = primMST.minimumSpanningTree();
        System.out.println(String.format("Original Graph: nodes = %d, edges = %d, sum edge lengths = %d", g.nodeCount(), g.edgeCount(), g.sumEdgeLengths()));
        System.out.println(String.format("MST Graph: nodes = %d, edges = %d, sum edge lengths = %d", mst.nodeCount(), mst.edgeCount(), mst.sumEdgeLengths()));
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("C:\\Users\\rubandyopadhyay\\Downloads\\edges.txt");
        test01("C:\\Users\\rubandyopadhyay\\Downloads\\edges-02.txt");
    }

}
