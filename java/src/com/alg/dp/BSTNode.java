package com.alg.dp;

public class BSTNode
{
    Integer node;
    BSTNode left;
    BSTNode right;

    public BSTNode()
    {
        super();
    }

    public BSTNode(Integer node)
    {
        super();
        this.node = node;
    }

    public BSTNode(Integer node, BSTNode left, BSTNode right)
    {
        super();
        this.node = node;
        this.left = left;
        this.right = right;
    }

    public Integer getNode()
    {
        return node;
    }

    public void setNode(Integer node)
    {
        this.node = node;
    }

    public BSTNode getLeft()
    {
        return left;
    }

    public void setLeft(BSTNode left)
    {
        this.left = left;
    }

    public BSTNode getRight()
    {
        return right;
    }

    public void setRight(BSTNode right)
    {
        this.right = right;
    }

    public boolean isLeaf()
    {
        return left == null && right == null;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(left == null ? "" : left);
        sb.append(":" + node + ":");
        sb.append(right == null ? "" : right);
        sb.append("]");
        return sb.toString();
    }

}
