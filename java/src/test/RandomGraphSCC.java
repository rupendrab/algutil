package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class RandomGraphSCC
{
    int numNodes;
    int[] nodes;
    int[] sccGroups;
    boolean isValid;
    HashSet<Edge> edges = new HashSet<>();
    
    Random rand = new Random();
    
    public RandomGraphSCC(int numNodes, int... sccGroups)
    {
        super();
        this.numNodes = numNodes;
        this.sccGroups = sccGroups;
        createNodes();
        isValid = validateSCCGroups();
        if (! isValid)
        {
            System.err.println("Too many nodes in SCC group definition...");
            return;
        }
        createEdges();
    }
    
    public boolean validateSCCGroups()
    {
        int totalNodes = 0;
        for (int groupCount : sccGroups)
        {
            totalNodes += groupCount;
        }
        return (totalNodes <= numNodes);
    }
    
    public void swap(int[] vals, int i, int j)
    {
        int temp = vals[i];
        vals[i] = vals[j];
        vals[j] = temp;
    }
    
    public void createNodes()
    {
        int[] available = new int[numNodes];
        for (int i=0; i< numNodes; i++)
        {
            available[i] = i + 1;
        }
        int toChoose = numNodes;
        nodes = new int[numNodes];
        for (int i=0; i<numNodes; i++)
        {
            int nextPos = rand.nextInt(toChoose);
            int nextVal = available[nextPos];
            nodes[i] = nextVal;
            swap(available, nextPos, toChoose - 1);
            toChoose--;
        }
    }
    
    public Edge randomEdge(int from, int to)
    {
        int pos1 = from + rand.nextInt(to-from);
        int pos2 = from + rand.nextInt(to-from-1);
        int node1 = nodes[pos1];
        int node2 = nodes[pos2];
        if (pos2 >= pos1)
        {
            node2 = nodes[pos2+1];
        }
        Edge e = new Edge(node1, node2);
        return e;
    }
    
    public void createCyclicEdge(int from, int to)
    {
        for (int i=from+1; i<to; i++)
        {
            Edge e = new Edge(nodes[i-1], nodes[i]);
            edges.add(e);
        }
        Edge last = new Edge(nodes[to-1], nodes[from]);
        edges.add(last);
    }
    
    public void createEdges()
    {
        createGroupEdges();
        createEdgesBetweenGroups();
    }
    
    public void createGroupEdges()
    {
        edges = new HashSet<>();
        int nodesHandled = 0;
        for (int groupCount : sccGroups)
        {
            int from = nodesHandled;
            int to = from + groupCount;
            // System.out.println(String.format("%d to %d", from, to));
            if (to - from > 1)
            {
                createCyclicEdge(from, to);
                for (int i=0; i<groupCount * 2; i++)
                {
                    Edge e = randomEdge(from, to);
                    edges.add(e);
                }
            }
            nodesHandled += groupCount;
        }
    }
    
    public void createEdgesBetweenGroups()
    {
        int nodesHandled = 0;
        for (int i=0; i<sccGroups.length-1; i++)
        {
            int groupCount = sccGroups[i]; 
            int from = nodesHandled;
            int to = from + groupCount;
            int fromNext = to;
            int toNext = fromNext + sccGroups[i+1];
            int noEdges = 1 + rand.nextInt(2);
            for (int j=0; j<noEdges; j++)
            {
                int pt1 = from + rand.nextInt(to-from);
                int pt2 = fromNext + rand.nextInt(toNext-fromNext);
                Edge e  = new Edge(nodes[pt1], nodes[pt2]);
                edges.add(e);
            }
            nodesHandled += groupCount;
        }
        int groupCount = sccGroups[sccGroups.length - 1];
        int from = nodesHandled;
        int to = from + groupCount;
        nodesHandled += groupCount;
        for (int i = nodesHandled; i<numNodes; i++)
        {
            int pt1 = from + rand.nextInt(to-from);
            int pt2 = i;
            Edge e  = new Edge(nodes[pt1], nodes[pt2]);
            edges.add(e);
        }
    }
    
    public void printEdges()
    {
        try
        {
            printEdges(null);
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void printEdges(String fileName) throws IOException
    {
        BufferedWriter out = null;
        if (fileName != null)
        {
            out = new BufferedWriter(new FileWriter(new File(fileName)));
        }
        for (Edge e : edges)
        {
            String outData = String.format("%d %d", e.from, e.to);
            if (fileName == null)
            {
                System.out.println(outData);
            }
            else
            {
                out.write(outData);
                out.write("\r\n");
            }
        }
        if (out != null)
        {
            out.close();
        }
    }

    public static void test01()
    {
        RandomGraphSCC rSCC = new RandomGraphSCC(10, 5, 3, 1, 1);
        rSCC.printEdges();
    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }

}
