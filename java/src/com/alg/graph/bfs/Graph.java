package com.alg.graph.bfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph
{
    HashSet<Integer> nodes = new HashSet<>();
    HashMap<Integer, ArrayList<Integer>> nodesEdges = new HashMap<>();

    public Graph()
    {
        super();
    }
    
    public static Graph readFromFile(String fileName) throws IOException
    {
        Graph g = new Graph();
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            if (line.trim().equals(""))
            {
                continue;
            }
            String[] fields = line.split("\\s+");
            if (fields.length > 0)
            {
                int i = 0;
                Integer startNode = null;
                ArrayList<Integer> nodeEdges = null;
                for (String field : fields)
                {
                    Integer node = Integer.parseInt(field);
                    g.nodes.add(node);
                    if (i == 0)
                    {
                        startNode = node;
                        nodeEdges = g.nodesEdges.get(startNode);
                        if (nodeEdges == null)
                        {
                            nodeEdges = new ArrayList<>();
                            g.nodesEdges.put(startNode, nodeEdges);
                        }
                    }
                    else
                    {
                        nodeEdges.add(node);
                    }
                    i++;
                }
            }
        }
        reader.close();
        return g;
    }
}
