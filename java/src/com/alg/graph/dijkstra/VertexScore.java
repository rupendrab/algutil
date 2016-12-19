package com.alg.graph.dijkstra;

public class VertexScore implements Comparable<VertexScore>
{
    int sourceVertex;
    int vertex;
    int score;
    
    public VertexScore(int vertex, int score)
    {
        super();
        this.vertex = vertex;
        this.score = score;
    }

    public VertexScore(int sourceVertex, int vertex, int score)
    {
        super();
        this.sourceVertex = sourceVertex;
        this.vertex = vertex;
        this.score = score;
    }
    
    public int getSourceVertex()
    {
        return sourceVertex;
    }

    public void setSourceVertex(int sourceVertex)
    {
        this.sourceVertex = sourceVertex;
    }

    public int getVertex()
    {
        return vertex;
    }

    public void setVertex(int vertex)
    {
        this.vertex = vertex;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
    @Override
    public VertexScore clone()
    {
        return new  VertexScore(sourceVertex, vertex, score);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (! (obj instanceof VertexScore))
        {
            return false;
        }
        VertexScore other = (VertexScore) obj;
        return score == other.score;
    }

    @Override
    public int compareTo(VertexScore other)
    {
        return score - other.score;
    }
    
    @Override
    public String toString()
    {
        return sourceVertex + "->" + vertex + ":" + score;
    }

}
