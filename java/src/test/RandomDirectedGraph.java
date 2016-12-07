package test;

import java.util.Arrays;
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
    
    public static void main(String[] args) throws Exception
    {
        // test01(1000);
        test02(10);
    }

}
