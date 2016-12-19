package com.alg.graph.bst;

public class TestBST
{
    public static BinarySearchTree<Integer> test01(boolean print)
    {
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<Integer>(2);
        BinarySearchTree<Integer> bst4 = new BinarySearchTree<Integer>(4);
        BinarySearchTree<Integer> bst1 = new BinarySearchTree<Integer>(1, null,  bst2);
        BinarySearchTree<Integer> bst5 = new BinarySearchTree<Integer>(5, bst4,  null);
        BinarySearchTree<Integer> bst3 = new BinarySearchTree<Integer>(3, bst1,  bst5);
        if (print)
        {
            System.out.println(bst3);
        }
        return bst3;
    }
    
    public static void test02()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println(bst.locate(1));
        System.out.println(bst.locate(2));
        System.out.println(bst.locate(5));
        System.out.println(bst.locate(4));
        System.out.println(bst.locate(6));
    }
    
    public static void test03()
    {
        BinarySearchTree<Integer> bst = test01(false);
        bst.insert(10);
        System.out.println(bst);
        bst.insert(10);
        System.out.println(bst);
    }
    
    public static void test04()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println(bst.max().root);
        System.out.println(bst.min().root);
        bst.insert(10);
        System.out.println(bst.max().root);
        System.out.println(bst.min().root);
        bst.insert(0);
        System.out.println(bst.max().root);
        System.out.println(bst.min().root);
    }
    
    public static void test05()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println(bst.predecessor(5).root);
        System.out.println(bst.predecessor(4).root);
        System.out.println(bst.predecessor(3).root);
        System.out.println(bst.predecessor(2).root);
        bst.insert(0);
        System.out.println(bst.predecessor(1).root);
        // System.out.println(bst.predecessor(0).root);
    }
    
    public static void main(String[] args)
    {
        // test01(true);
        // test02();
        // test03();
        // test04();
        test05();
    }

}
