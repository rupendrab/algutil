package com.alg.mst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;

import com.alg.graph.dijkstra.Edge;
import com.alg.graph.dijkstra.Graph;

public class ClusterHamming
{
    Hashtable<Integer, Integer> nodesToDistance = new Hashtable<>();
    Hashtable<Integer, HashSet<Integer>> distanceToNodes = new Hashtable<>();
    int noBits;
    int noNodes;
    
    public ClusterHamming()
    {
        super();
    }
    
    public Integer toInteger(String[] bytes)
    {
        if (bytes.length != noBits)
        {
            return null;
        }
        int ret = 0;
        for (int i=noBits-1; i>=0; i--)
        {
            if (bytes[i].equals("1"))
            {
                ret |= (1 << noBits - 1 - i);
            }
        }
        return ret;
    }
    
    public Integer toggleBytes(int val, int[] positions)
    {
        if (positions != null && positions.length > 0)
        {
            for (int position : positions)
            {
                int bytePos = noBits - 1 - position;
                val ^= (1 << bytePos);
            }
        }
        return val;
    }
    
    
    public int noCombinations(int available, int choose)
    {
        if (choose == 0)
        {
            return 1;
        }
        long numerator = 1;
        long denominator = 1;
        for (int i = choose; i>0; i--)
        {
            numerator *= (available - (choose-i));
            denominator *= i;
        }
        return (int) (numerator / denominator);
    }
    
    public Integer[] allCombinations(int val, int toggledBytes)
    {
        Integer[] ret = new Integer[noCombinations(noBits, toggledBytes)];
        if (toggledBytes == 0)
        {
            ret[0] = val;
            return ret;
        }
        else if (toggledBytes == 1)
        {
            for (int i=0; i<noBits; i++)
            {
                ret[i] = toggleBytes(val, new int[] {i});
            }
        }
        else if (toggledBytes == 2)
        {
            int pos = 0;
            for (int i=0; i<noBits; i++)
            {
                for (int j=i+1; j<noBits; j++)
                {
                    ret[pos++] = toggleBytes(val, new int[] {i,j});
                }
            }
        }
        else if (toggledBytes == 3)
        {
            int pos = 0;
            for (int i=0; i<noBits; i++)
            {
                for (int j=i+1; j<noBits; j++)
                {
                    for (int k=j+1; k<noBits; k++)
                    {
                        ret[pos++] = toggleBytes(val, new int[] {i,j, k});
                    }
                }
            }
        }
        return ret;
    }
    
    public static ClusterHamming loadFromFile(String fileName) throws IOException
    {
        ClusterHamming ch = new ClusterHamming();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        while ((line = reader.readLine()) != null)
        {
            line = line.trim();
            if (line.equals(""))
            {
                continue;
            }
            lineNo += 1;
            String[] fields = line.split("\\s+");
            if (lineNo == 1)
            {
                ch.noNodes = Integer.parseInt(fields[0]);
                ch.noBits = Integer.parseInt(fields[1]);
            }
            else
            {
                Integer nodeNo = lineNo - 1;
                Integer hammingDistance = ch.toInteger(fields); 
                if (hammingDistance != null)
                {
                    ch.nodesToDistance.put(nodeNo, hammingDistance);
                    HashSet<Integer> distanceData = ch.distanceToNodes.get(hammingDistance);
                    if (distanceData == null)
                    {
                        distanceData = new HashSet<>();
                        ch.distanceToNodes.put(hammingDistance, distanceData);
                    }
                    distanceData.add(nodeNo);
                }
            }
        }
        reader.close();
        return ch;
    }
    
    public Graph makeGraph(int maxDistance)
    {
        int noEdges = 0;
        Graph g = new Graph();
        for (Integer node : nodesToDistance.keySet())
        {
           g.addNode(node);
        }
        for (int distance=0; distance<=maxDistance; distance++)
        {
            int edgesForDistance = 0;
            for (Entry<Integer, Integer> entry : nodesToDistance.entrySet())
            {
                Integer node = entry.getKey();
                Integer hammingDistance = entry.getValue();
                for (int newHammingDistance : allCombinations(hammingDistance, distance))
                {
                    HashSet<Integer> allMatchedNodes = distanceToNodes.get(newHammingDistance);
                    if (allMatchedNodes != null)
                    {
                        for (Integer matchedNode : allMatchedNodes)
                        {
                            if (matchedNode> node)
                            {
                                Edge edge = new Edge(node, matchedNode, distance);
                                g.addEdge(edge);
                                edgesForDistance++;
                                noEdges++;
                                if (noEdges % 200000 == 0)
                                {
                                    // System.out.println("Edges added = " + noEdges);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(String.format("Edges added with distance %d = %d", distance, edgesForDistance));
        }
        System.out.println("Edges added = " + noEdges);
        return g;
    }
    
    public void showDuplicates(int max)
    {
        int i=0;
        for (Entry<Integer, HashSet<Integer>> entry : distanceToNodes.entrySet())
        {
            if (entry.getValue().size() > 1)
            {
                i += 1;
                System.out.println("Distance = " + entry.getKey());
                System.out.println("  Nodes = " + entry.getValue());
            }
            if (i == max)
            {
                break;
            }
        }
    }

    public static void test01()
    {
        ClusterHamming ch = new ClusterHamming();
        ch.noBits = 5;
        String[] data = "1 0 1 0 1".split("\\s");
        int val = ch.toInteger(data);
        System.out.println(val);
        System.out.println(ch.toggleBytes(val, new int[] {0,1,2}));
    }
    
    public static void test02() throws IOException
    {
        ClusterHamming ch = ClusterHamming.loadFromFile("C:\\Users\\rubandyopadhyay\\Downloads\\clustering_big.txt");
        System.out.println(String.format("Nodes = %d, %d", ch.nodesToDistance.size(), ch.distanceToNodes.size()));
        ch.showDuplicates(1);
        System.out.println(ch.noCombinations(ch.noBits, 0));
        System.out.println(ch.noCombinations(ch.noBits, 1));
        System.out.println(ch.noCombinations(ch.noBits, 2));
        System.out.println(ch.allCombinations(11796300, 0).length);
        System.out.println(ch.allCombinations(11796300, 1).length);
        System.out.println(ch.allCombinations(11796300, 2).length);
    }
    
    public static void test03(String fileName, int maxEdgeLength) throws IOException
    {
        ClusterHamming ch = ClusterHamming.loadFromFile(fileName);
        System.out.println(String.format("Nodes = %d, %d", ch.nodesToDistance.size(), ch.distanceToNodes.size()));
        Graph g = ch.makeGraph(maxEdgeLength);
        KCluster cluster = new KCluster(g);
        System.out.println(cluster.computeClusters(maxEdgeLength));
        // System.out.println(ch.noCombinations(32, 5));
    }
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01();
        // test02();
        long start, end = 0;
        start = time();
        // test03("C:\\Users\\rubandyopadhyay\\Downloads\\clustering_big.txt", 3);
        // test03("C:\\Users\\rubandyopadhyay\\Downloads\\clustering_hamming_01.txt", 2);
        test03("C:\\Users\\rubandyopadhyay\\Downloads\\clustering_hamming_02.txt", 2);
        end = time();
        System.out.println(String.format("Time elapsed = %d ms", (end - start)));
        
    }
}
