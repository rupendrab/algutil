package com.alg.graph.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.alg.graph.bfs.Graph;

public class StronglyConnectedComponents
{
    Graph g;
    HashMap<Integer, Integer> leaders = new HashMap<>();
    HashMap<Integer, HashSet<Integer>> leaderGroups = new HashMap<>();
    Integer leader;
    Integer position;
    HashSet<Integer> visited = new HashSet<>();
    Integer[] positions;
    Integer[] computedPositions;

    public StronglyConnectedComponents(Graph g)
    {
        super();
        this.g = g;
    }

    public void compute()
    {
        compute(true);
    }
    
    public void compute(boolean print)
    {
        leader = null;
        position = 0;
        visited = new HashSet<>();
        positions = new Integer[g.getNodes().size()];
        g.getNodes().toArray(positions);
        if (print)
        {
            System.out.println("Positions = " + Arrays.toString(positions));
        }
        computedPositions = new Integer[g.getNodes().size()];
        Graph gRev = g.reversedEdges();
        DFSLoop(gRev, positions, false, true);

        System.out.println("Completed First Pass");
        
        if (print)
        {
            System.out.println(Arrays.toString(computedPositions));
        }
        
        DFSLoop(g, computedPositions, true, false);

        System.out.println("Completed Second Pass");
        if (print)
        {
            System.out.println(leaders);
            System.out.println(leaderGroups);
        }
    }
    
    public void addLeader(Integer node, Integer leader)
    {
        leaders.put(node, leader);
        HashSet<Integer> leaderGroup = leaderGroups.get(leader);
        if (leaderGroup == null)
        {
            leaderGroup = new HashSet<>();
            leaderGroups.put(leader, leaderGroup);
        }
        leaderGroup.add(node);
    }
    
    public void DFS(Graph g, Integer node, boolean setLeader, boolean setPosition)
    {
        // System.out.println("Calling DFS on Node " + node);
        visited.add(node);
        if (setLeader)
        {
            addLeader(node, leader);
        }
        ArrayList<Integer> connectedNodes = g.getNodesEdges().get(node);
        if (connectedNodes != null)
        {
            for (Integer connectedNode : connectedNodes)
            {
                if (! visited.contains(connectedNode))
                {
                    DFS(g, connectedNode, setLeader, setPosition);
                }
            }
        }
        if (setPosition)
        {
            computedPositions[position++] = node;
        }
    }
    
    public void DFSLoop(Graph g, Integer[] nodeOrder, boolean setLeader, boolean setPosition)
    {
        visited = new HashSet<>();
        for (int i=nodeOrder.length-1; i>=0; i--)
        {
            Integer nodeToProcss = nodeOrder[i];
            if (! visited.contains(nodeToProcss))
            {
                if (setLeader)
                {
                    leader = nodeToProcss;
                }
                DFS(g, nodeToProcss, setLeader, setPosition);
            }
        }
    }
    
    public String topConnectedComponentsLengths(int topn)
    {
        StringBuilder sb = new StringBuilder();
        int[] lengths = new int[leaderGroups.size()];
        int i = 0;
        for (Entry<Integer, HashSet<Integer>> entry : leaderGroups.entrySet())
        {
            lengths[i] = entry.getValue().size();
            i++;
        }
        Arrays.sort(lengths);
        int[] tops = new int[topn];
        for (i=lengths.length-1; i>=0; i--)
        {
            tops[lengths.length-1-i] = lengths[i];
            if (topn == lengths.length - i)
            {
                break;
            }
        }
        for (i = 0; i<tops.length; i++)
        {
            if (i > 0)
            {
                sb.append(",");
            }
            sb.append(tops[i]);
        }
        return sb.toString();
    }
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        StronglyConnectedComponents scc = new StronglyConnectedComponents(g);
        scc.compute();
    }
    
    public static void test02(String fileName) throws IOException
    {
        long start, end = 0;
        start = Calendar.getInstance().getTimeInMillis();
        Graph g = Graph.readFromFile(fileName);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Graph loaded in " + (end - start) + " milliceconds");
        System.out.println(String.format("  Nodes = %d, Edges = %d", g.vertexCount(), g.edgesCount()));
        start = Calendar.getInstance().getTimeInMillis();
        StronglyConnectedComponents scc = new StronglyConnectedComponents(g);
        scc.compute(false);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Completed in %d ms", (end - start)));
        System.out.println(scc.topConnectedComponentsLengths(5));
        System.out.println(scc.leaders);
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01("data/SCC_01.txt");
        // test02("C:/Users/rubandyopadhyay/Downloads/SCC.txt");
        // 434821,968,459,313,211
        // test02("data/SCC_06.txt");
        test02("data/SCC_07.txt");
    }
}
