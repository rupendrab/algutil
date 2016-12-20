package com.alg.graph.bst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
    
    public static void testPredecessor()
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
    
    public static void testSuccessor()
    {
        BinarySearchTree<Integer> bst = test01(false);
        // System.out.println(bst.successor(5).root);
        System.out.println(bst.successor(4).root);
        System.out.println(bst.successor(3).root);
        System.out.println(bst.successor(2).root);
        bst.insert(0);
        System.out.println(bst.successor(1).root);
        System.out.println(bst.successor(0).root);
    }
    
    public static void testComputeHeight()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println(bst.computeHeight());
        bst.insert(0);
        System.out.println(bst.computeHeight());
        bst.insert(6);
        System.out.println(bst.computeHeight());
        bst.insert(10);
        System.out.println(bst.computeHeight());
    }
    
    public static Integer[] createArray(int arrayLen, int max)
    {
        Random rand = new Random();
        Integer[] ret = new Integer[arrayLen];
        for (int i=0; i<arrayLen; i++)
        {
            ret[i] = rand.nextInt(max);
        }
        return ret;
    }
    
    public static Integer[] createArray(int arrayLen)
    {
        return createArray(arrayLen, 100000);
    }

    public static <T extends Comparable<? super T>> T getValue(BinarySearchTree<T> bst)
    {
        if (bst == null)
        {
            return null;
        }
        else
        {
            return bst.getRoot();
        }
    }
    
    public static void testArray()
    {
        Integer[] arr = createArray(10000);
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(arr, 0, arr.length, true);
        System.out.println("Size = " + bst.size);
        System.out.println("Height = " + bst.computeHeight());
        int pos = 200;
        System.out.println(String.format("Value in array position %d is %s", pos, arr[pos]));
        System.out.println(getValue(bst.locate(arr[pos])));
        System.out.println(getValue(bst.predecessor(arr[pos])));
        System.out.println(getValue(bst.successor(arr[pos])));
        // System.out.println(bst);
        System.out.println(arr[pos - 1]);
        System.out.println(arr[pos + 1]);
        System.out.println("Tree OK = " + bst.checkTree());
    }
    
    public static void testSelect()
    {
        Integer[] arr = createArray(10000);
        BinarySearchTree<Integer> bst = new BinarySearchTree<>(arr, 0, arr.length, true);
        boolean allmatched = true;
        for (int i=0; i<arr.length; i++)
        {
            Integer val1 = arr[i];
            Integer val2 = getValue(bst.select(i));
            if (val1 != val2)
            {
                System.out.println(String.format("Mismatch at position %d, array = %d, bst = %d", i, val1, val2));
                allmatched = false;
            }
        }
        if (allmatched)
        {
            System.out.println("All matched!!!");
        }
    }
    
    public static void testDuplicates()
    {
        BinarySearchTree<Integer> bst = test01(false);
        // System.out.println(bst.size);
        bst.insert(5);        
        bst.insert(5);        
        System.out.println(bst);
        System.out.println(bst.locate(5).size);
        // System.out.println(bst.size);
        System.out.println(bst.predecessor(5));
        System.out.println(bst.successor(5));
    }
    
    public static <T extends Comparable<? super T>> void printRank(BinarySearchTree<T> bst, T elem)
    {
        int rank = bst.rank(elem);
        System.out.println(String.format("Rank of %s is %d", elem, rank));
    }
    
    public static void testRank()
    {
        BinarySearchTree<Integer> bst = test01(false);
        for (int i=0; i<7; i++)
        {
            printRank(bst, i);
        }
        bst.insert(5);
        bst.insert(5);
        bst.insert(6);
        System.out.println("=====================");
        for (int i=0; i<7; i++)
        {
            printRank(bst, i);
        }
    }
    
    public static void testInOrderTraversal()
    {
        BinarySearchTree<Integer> bst = test01(false);
        ArrayList<Integer> arr = bst.orderedArray();
        System.out.println(arr);
        bst.insert(5);
        bst.insert(5);
        bst.insert(6);
        arr = bst.orderedArray();
        System.out.println(arr);
    }
    
    public static void testDelete01()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println("OK = " + bst.checkTree());
        ArrayList<Integer> arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.delete(2);
        System.out.println("OK = " + bst.checkTree());
        arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.delete(5);
        System.out.println("OK = " + bst.checkTree());
        arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.delete(3);
        System.out.println("OK = " + bst.checkTree());
        arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.delete(4);
        System.out.println("OK = " + bst.checkTree());
        arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.delete(1);
        System.out.println("OK = " + bst.checkTree());
        arr = bst.orderedArray();
        System.out.println(arr);
        System.out.println(bst);
        
        bst.insert(3);
        bst.insert(1);
        bst.insert(5);
        bst.insert(2);
        bst.insert(4);
        System.out.println(bst);
    }
    
    public static void testDelete02()
    {
        BinarySearchTree<Integer> bst = test01(false);
        bst.delete(3);
        System.out.println(bst);
        System.out.println(bst.orderedArray());
        System.out.println("OK = " + bst.checkTree());
        bst.delete(10000);
        System.out.println(bst);
        System.out.println(bst.orderedArray());
        System.out.println("OK = " + bst.checkTree());
    }
    
    public static boolean testDelete03()
    {
        Integer[] arr = createArray(10, 100);
        System.out.println(Arrays.toString(arr));
        Random rand = new Random();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        for (Integer val :arr)
        {
            bst.insert(val);
        }
        System.out.println("Size of tree = " + bst.size);
        System.out.println("Height of tree = " + bst.computeHeight());
        System.out.println("OK = " + bst.checkTree());
        // Delete 1000 random elements
        int originalSize = bst.size;
        Integer[] arr2 = new Integer[arr.length];
        System.arraycopy(arr, 0, arr2, 0, arr.length);
        int spread = arr2.length; 
        ArrayList<Integer> deleteArray = new ArrayList<>();
        boolean ok = true;
        for (int i=0; i<arr.length; i++)
        {
            int pos = rand.nextInt(spread);
            Integer val = arr2[pos];
            deleteArray.add(val);
            Integer temp = arr2[spread-1];
            arr2[spread-1] = arr2[pos];
            arr2[pos] = temp;
            spread--;
            System.out.println(bst);
            System.out.println(bst.orderedArray());
            System.out.println("Deleting " + val);
            if (bst.locate(val) != null)
            {
                System.out.println("Exists!!!");
            }
            bst.delete(val);
            System.out.println(bst);
            System.out.println(bst.orderedArray());
            if (bst.size != originalSize - (i+1))
            {
                System.out.println(String.format("Delete failed for element %d, size = %d, expected size = %d", val, bst.size, (originalSize - (i+1))));
                ok = false;
                break;
            }
            if (! bst.checkTree())
            {
                System.out.println("Not OK after delete");
                ok = false;
                break;
            }
        }
        System.out.println(deleteArray);
        return ok;
    }
    
    public static boolean testDelete03a()
    {
        Integer[] arr = createArray(1000000, 10000000);
        // System.out.println(Arrays.toString(arr));
        Random rand = new Random();
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        for (Integer val :arr)
        {
            bst.insert(val);
        }
        System.out.println("Size of tree = " + bst.size);
        System.out.println("Height of tree = " + bst.computeHeight());
        System.out.println("Best possible height = " + (int) (Math.ceil(Math.log(bst.size) / Math.log(2))));
        System.out.println("OK = " + bst.checkTree());
        // Delete 1000 random elements
        int originalSize = bst.size;
        Integer[] arr2 = new Integer[arr.length];
        System.arraycopy(arr, 0, arr2, 0, arr.length);
        int spread = arr2.length; 
        ArrayList<Integer> deleteArray = new ArrayList<>();
        boolean ok = true;
        for (int i=0; i<arr.length; i++)
        {
            int pos = rand.nextInt(spread);
            Integer val = arr2[pos];
            deleteArray.add(val);
            Integer temp = arr2[spread-1];
            arr2[spread-1] = arr2[pos];
            arr2[pos] = temp;
            spread--;
            // System.out.println(bst);
            // System.out.println(bst.orderedArray());
            /*
            System.out.println("Deleting " + val);
            if (bst.locate(val) != null)
            {
                System.out.println("Exists!!!");
            }
            */
            bst.delete(val);
            // System.out.println(bst);
            // System.out.println(bst.orderedArray());
            if (bst.size != originalSize - (i+1))
            {
                System.out.println(String.format("Delete failed for element %d, size = %d, expected size = %d", val, bst.size, (originalSize - (i+1))));
                ok = false;
                break;
            }
            if (i % 10000 == 0)
            {
                if (! bst.checkTree())
                {
                    System.out.println("Not OK after delete");
                    ok = false;
                    break;
                }
            }
        }
        // System.out.println(deleteArray);
        return ok;
    }

    public static void testDelete04()
    {
        BinarySearchTree<Integer> bst = test01(false);
        System.out.println(bst.orderedArray());
        bst.insert(5);
        bst.insert(5);
        System.out.println(bst.orderedArray());
        bst.delete(5);
        System.out.println(bst.orderedArray());
        bst.delete(5);
        System.out.println(bst.orderedArray());
        bst.delete(5);
        System.out.println(bst.orderedArray());
    }
    
    public static void testDeleteGeneric(Integer[] arr, Integer[] dels)
    {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        for (Integer val :arr)
        {
            bst.insert(val);
        }
        System.out.println("Size of tree = " + bst.size);
        System.out.println("Height of tree = " + bst.computeHeight());
        System.out.println("OK = " + bst.checkTree());
        int originalSize = bst.size;
        int i = 0;
        for (Integer val : dels)
        {
            System.out.println(bst);
            System.out.println(bst.orderedArray());
            System.out.println("Deleting " + val);
            if (bst.locate(val) != null)
            {
                System.out.println("Exists!!!");
            }
            bst.delete(val);
            System.out.println(bst);
            System.out.println(bst.orderedArray());
            if (bst.size != originalSize - (i+1))
            {
                System.out.println(String.format("Delete failed for element %d, size = %d, expected size = %d", val, bst.size, (originalSize - (i+1))));
                break;
            }
            if (! bst.checkTree())
            {
                System.out.println("Not OK after delete");
                break;
            }
            i++;
        }
    }
    
    public static void testDelete05()
    {
        Integer[] arr = {35196, 14517, 24104, 67281, 93565, 13270, 63023, 49832, 22202, 27049};
        Integer[] dels = {13270, 22202, 67281};
        testDeleteGeneric(arr, dels);
    }
    
    public static void testDelete06()
    {
        Integer[] arr = {4559, 70150, 32230, 74491, 41669, 13601, 49568, 91484, 62833, 80777};
        Integer[] dels = {62833, 74491, 13601, 4559};
        testDeleteGeneric(arr, dels);
    }

    public static void testDelete07()
    {
        Integer[] arr = {28931, 44528, 76998, 37016, 41318, 86831, 15995, 62897, 2795, 58699};
        Integer[] dels = {76998, 2795, 58699};
        testDeleteGeneric(arr, dels);
    }

    public static void testDelete08()
    {
        Integer[] arr = {28, 44, 76, 37, 41, 86, 15, 62, 2, 58};
        Integer[] dels = {76, 2, 58};
        testDeleteGeneric(arr, dels);
    }
    
    public static void testDelete09()
    {
        Integer[] arr = {30067, 42832, 91659, 46362, 34235, 20095, 78288, 38495, 19577, 39894};
        Integer[] dels = {39894, 46362, 78288, 30067, 20095, 19577, 42832};
        testDeleteGeneric(arr, dels);
    }

    public static void testDelete10()
    {
        Integer[] arr = {11, 12, 61, 47, 64, 20, 82, 13, 12, 34};
        Integer[] dels = {20, 13, 61, 47, 12};
        testDeleteGeneric(arr, dels);
    }

    public static void testPredecessors()
    {
        Integer[] arr = createArray(10000);
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        for (Integer val :arr)
        {
            bst.insert(val);
        }
        Arrays.sort(arr);
        // System.out.println(Arrays.toString(arr));
        for (int i=0; i <arr.length; i++)
        {
            if (i < (arr.length - 1) && arr[i+1].equals(arr[i])) // Duplicate entry
            {
                Integer pred1 = arr[i];
                Integer pred2 = getValue(bst.predecessor(arr[i]));
                if (! pred1.equals(pred2))
                {
                    System.out.println(String.format("Failed at position %d, val = %d, expected predecessor = %d, predecessor = %d", i, arr[i], pred1, pred2));
                }
            }
            else if (i == 0)
            {
                Integer pred2 = getValue(bst.predecessor(arr[i]));
                if (pred2 != null)
                {
                    System.out.println("Failed at first predecessor");
                }
            }
            else
            {
                Integer pred1 = arr[i-1];
                /*
                for (int j=i-1; j>=0; j--)
                {
                    if (! arr[j].equals(arr[i]))
                    {
                        pred1 = arr[j];
                        break;
                    }
                }
                */
                Integer pred2 = getValue(bst.predecessor(arr[i]));
                if (! pred1.equals(pred2))
                {
                    System.out.println(String.format("Failed at position %d, val = %d, expected predecessor = %d, predecessor = %d", i, arr[i], pred1, pred2));
                }
            }
        }
    }
    
    
    public static void testMedian()
    {
        Integer[] arr = createArray(1000000, 10000000);
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(null);
        for (Integer val :arr)
        {
            bst.insert(val);
        }
        Integer bstMedian = bst.median();
        System.out.println("BST Median = " + bst.median());
        Arrays.sort(arr);
        int pos = arr.length / 2;
        if (arr.length % 2 == 0)
        {
            pos -= 1;
        }
        Integer actualMedian = arr[pos];
        System.out.println("Actual Median = " + actualMedian);
        System.out.println("Matched = " + (actualMedian.equals(bstMedian)));
    }
    
    public static void main(String[] args)
    {
        // test01(true);
        // test02();
        // test03();
        // test04();
        // testPredecessor();
        // testSuccessor();
        // testComputeHeight();
        // testArray();
        // testSelect();
        // testDuplicates();
        // testRank();
        // testInOrderTraversal();
        // testDelete01();
        // testDelete02();
        
        /*
        for (int i=0; i<1000; i++)
        {
            if (! testDelete03())
            {
                System.out.println("Failed.....");
                break;
            }
        }
        */
        // testDelete03a();
        // testDelete04();
        // testPredecessors();
        // testDelete05();
        // testDelete06();
        // testDelete07();
        // testDelete08();
        // testDelete09();
        // testDelete10();
        testMedian();
    }

}
