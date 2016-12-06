package com.alg.graph.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
        leader = null;
        position = 0;
        visited = new HashSet<>();
        positions = new Integer[g.getNodes().size()];
        g.getNodes().toArray(positions);
        computedPositions = new Integer[g.getNodes().size()];
        Graph gRev = g.reversedEdges();
        DFSLoop(gRev, positions, false, true);
        System.out.println(Arrays.toString(computedPositions));
        DFSLoop(g, computedPositions, true, false);
        System.out.println(leaders);
        System.out.println(leaderGroups);
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
    
    public static void test01(String fileName) throws IOException
    {
        Graph g = Graph.readFromFile(fileName);
        StronglyConnectedComponents scc = new StronglyConnectedComponents(g);
        scc.compute();
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("data/SCC_01.txt");
    }
}
