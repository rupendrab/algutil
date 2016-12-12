package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class RandomDirectedGraph
{
    int numNodes;
    int[] nodes;
    Random rand = new Random();
    
    public RandomDirectedGraph(int numNodes)
    {
        super();
        this.numNodes = numNodes;
        createNodes();
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
    
    public int[] randomEdge()
    {
        int pos1 = rand.nextInt(numNodes);
        int pos2 = rand.nextInt(numNodes - 1);
        int[] ret = new int[2];
        ret[0] = nodes[pos1];
        if (pos2 < pos1)
        {
            ret[1] = nodes[pos2]; 
        }
        else
        {
            ret[1] = nodes[pos2 + 1];
        }
        return ret;
    }
    
    public void createEdges(int edgeCount)
    {
        try
        {
            createEdges(edgeCount, null);
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void createEdges(int edgeCount, String fileName) throws IOException
    {
        HashSet<Edge> created = new HashSet<>();
        BufferedWriter out = null;
        if (fileName != null)
        {
            out = new BufferedWriter(new FileWriter(new File(fileName)));
        }
        for (int i=0; i<edgeCount; i++)
        {
            int[] edge = randomEdge();
            Edge e = new Edge(edge[0], edge[1]);
            while ( created.contains(e))
            {
                edge = randomEdge();
                e = new Edge(edge[0], edge[1]);
            }
            String output = String.format("%d %d", edge[0], edge[1]);
            if (fileName == null)
            {
                System.out.println(output);
            }
            else
            {
                out.write(output);
                out.write("\r\n");
            }
            created.add(e);
        }
        if (out != null)
        {
            out.close();
        }
    }
    
    public static void test01(int numNodes)
    {
        RandomDirectedGraph rdg = new RandomDirectedGraph(numNodes);
        System.out.println(Arrays.toString(rdg.nodes));
        Arrays.sort(rdg.nodes);
        System.out.println(Arrays.toString(rdg.nodes));
        for (int i=0; i<rdg.numNodes; i++)
        {
            if (i > 0 && rdg.nodes[i] == rdg.nodes[i-1])
            {
                System.out.println("Duplicate " + rdg.nodes[i]);
            }
        }
    }
    
    public static void test02(int numNodes)
    {
        RandomDirectedGraph rdg = new RandomDirectedGraph(numNodes);
        for (int i=0; i<10; i++)
        {
            System.out.println(Arrays.toString(rdg.randomEdge()));
        }
    }
    
    public static void test03(int numNodes, int numEdges)
    {
        RandomDirectedGraph rdg = new RandomDirectedGraph(numNodes);
        rdg.createEdges(numEdges);
    }

    public static void main(String[] args) throws Exception
    {
        // test01(1000);
        // test02(10);
        test03(50, 150);
    }

}
