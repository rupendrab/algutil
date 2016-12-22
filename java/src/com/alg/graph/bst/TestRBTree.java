package com.alg.graph.bst;

public class TestRBTree
{
    public static void test01()
    {
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>(null);
        for (int i=0; i<3; i++)
        {
            rbt.insert(i);
            // System.out.println(rbt);
        }
        // System.out.println(rbt.computeHeight());
        System.out.println(rbt.size);
    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }

}
