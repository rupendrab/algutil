package com.alg;

public class BinaryTree
{
    int value;
    BinaryTree left;
    BinaryTree right;
    
    public BinaryTree()
    {
    }
    
    public BinaryTree(int value)
    {
        super();
        this.value = value;
    }


    public BinaryTree(int value, BinaryTree left, BinaryTree right)
    {
        super();
        this.value = value;
        this.left = left;
        this.right = right;
    }


    public int getValue()
    {
        return value;
    }


    public void setValue(int value)
    {
        this.value = value;
    }


    public BinaryTree getLeft()
    {
        return left;
    }


    public void setLeft(BinaryTree left)
    {
        this.left = left;
    }


    public BinaryTree getRight()
    {
        return right;
    }


    public void setRight(BinaryTree right)
    {
        this.right = right;
    }


    public boolean isLeaf()
    {
        return getRight() == null && getLeft() == null;
    }
    
    public int level()
    {
        if (isLeaf())
        {
            return 0;
        }
        int leftLevel = 0;
        if (getLeft() != null)
        {
            leftLevel = getLeft().level();
        }
        int rightLevel = 0;
        if (getRight() != null)
        {
            rightLevel = getRight().level();
        }
        return 1 + Math.max(leftLevel, rightLevel);
    }
    
    public static void main(String[] args)
    {
        BinaryTree root = new BinaryTree(1, new BinaryTree(2), new BinaryTree(3));
        System.out.println(root.level());
        root.left.setLeft(new BinaryTree(4));
        root.left.setRight(new BinaryTree(5));
        System.out.println(root.level());
        root.right.setLeft(new BinaryTree(6));
        root.right.setRight(new BinaryTree(7));
        System.out.println(root.level());
    }

}
