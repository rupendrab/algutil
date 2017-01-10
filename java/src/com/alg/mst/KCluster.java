package com.alg.mst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.alg.graph.dijkstra.Edge;
import com.alg.graph.dijkstra.Graph;

public class KCluster
{
    Graph g;
    Hashtable<Integer, UnionFindVertex<Integer>> uf;

    public KCluster(Graph g)
    {
        super();
        this.g = g;
    }

    
    public int computeClustersMaxDistance(int K)
    {
        KruskalMSTBasic krsMST = new KruskalMSTBasic(g);
        uf = krsMST.getInitialUF();
        ArrayList<Edge> edges = krsMST.edgesSortedByLength();
        int clusters = uf.size();
        int lastDistance = 0;
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
            else
            {
                lastDistance = edge.getDistance();
                if (clusters == K)
                {
                    return lastDistance;
                }
                fromVertex.union(toVertex);
                clusters -= 1;
            }
        }
        return 0;
    }
    
    public static void test01(String fileName, int K) throws IOException
    {
        Graph g = Graph.readFromFileFormatTwo(fileName, 1);
        System.out.println(String.format("Graph nodes = %d, edges = %d", g.nodeCount(), g.edgeCount()));
        KCluster cluster = new KCluster(g);
        int dist = cluster.computeClustersMaxDistance(K);
        System.out.println(String.format("Max distance of %d clusters = %d", K, dist));
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("/Users/rupen/Documents/Coursera/Algorithms/Course3/clustering1.txt", 4);
        test01("/Users/rupen/Documents/Coursera/Algorithms/Course3/clustering_test_01.txt", 2);
        test01("/Users/rupen/Documents/Coursera/Algorithms/Course3/clustering_test_01.txt", 3);
        test01("/Users/rupen/Documents/Coursera/Algorithms/Course3/clustering_test_01.txt", 4);
    }

}
