package com.alg.counting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class NumArray
{
    TreeNode root = null;
    
    public NumArray(int[] nums)
    {
        if (nums == null || nums.length == 0)
        {
            return;
        }
        this.root = buildTree(nums, 0, nums.length-1);
        // System.out.println(String.format("%d %d %s", root.start, root.end, root.sum));
    }
    
    void update(int i, int val)
    {
        updateHelper(root, i, val);
    }
    
    void updateHelper(TreeNode root, int i, int val)
    {
        if (root == null)
        {
            return;
        }
        int mid = root.start + (root.end - root.start) / 2;
        if (i <= mid)
        {
            updateHelper(root.leftChild, i, val);
        }
        else
        {
            updateHelper(root.rightChild, i, val);
        }
        
        if (root.start == root.end && root.start == i)
        {
            root.sum = val;
            return;
        }
        root.sum = root.leftChild.sum + root.rightChild.sum;
    }
    
    public long sumRange(int i, int j)
    {
        return sumRangeHelper(root, i, j);
    }
    
    public long sumRangeHelper(TreeNode root, int i, int j)
    {
        if (root == null || j < root.start || i > root.end)
        {
            return 0;
        }
        if (i <= root.start && j >= root.end)
        {
            return root.sum;
        }
        int mid = root.start + (root.end - root.start)/2;
        long result = sumRangeHelper(root.leftChild, i, Math.min(mid, j)) +
                     sumRangeHelper(root.rightChild, Math.max(mid+1, i), j);
        return result;
    }
    
    public TreeNode buildTree(int[] nums, int i, int j)
    {
        if (i == j)
        {
            return new TreeNode(i, j, nums[i]);
        }
        int mid = i + (j - i) / 2;
        TreeNode current = new TreeNode(i, j);
        current.leftChild = buildTree(nums, i, mid);
        current.rightChild = buildTree(nums, mid+1, j);
        current.sum = current.leftChild.sum + current.rightChild.sum;
        return current;
    }
    
    public static int[] createArray(int arrayLength)
    {
        int[] ret = new int[arrayLength];
        Random rand = new Random();
        for (int i=0; i<arrayLength; i++)
        {
            ret[i] = rand.nextInt(1000000);
        }
        return ret;
    }
    
    public static ArrayList<int[]> getPairs(int noPairs, int arrayLength)
    {
        Random rand = new Random();
        ArrayList<int[]> ret = new ArrayList<>();
        for (int i=0; i<noPairs; i++)
        {
            int p1 = rand.nextInt(arrayLength - 1); 
            int p2 = rand.nextInt(arrayLength - p1);
            int[] vals = {p1, p1+p2};
            ret.add(vals);
        }
        return ret;
    }

    public static ArrayList<int[]> getUpdates(int noUpdates, int arrayLength)
    {
        Random rand = new Random();
        ArrayList<int[]> ret = new ArrayList<>();
        for (int i=0; i<noUpdates; i++)
        {
            int p1 = rand.nextInt(arrayLength - 1); 
            int p2 = rand.nextInt(1000000);
            int[] vals = {p1, p2};
            ret.add(vals);
        }
        return ret;
    }
    
    public static long time()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static long getSum(int[] nums, int i, int j)
    {
        long sum = 0;
        for (int k = i; k<=j; k++)
        {
            sum += nums[k];
        }
        return sum;
    }
    
    public static boolean isEqual(long[] a1, long[] a2)
    {
        if (a1 == null || a2 == null)
        {
            return false;
        }
        if (a1.length != a2.length)
        {
            return false;
        }
        for (int i=0; i<a1.length; i++)
        {
            if (a1[i] != a2[i])
            {
                return false;
            }
        }
        return true;
    }
    
    public static void test01()
    {
        int arrayLength = 10000000;
        int noPairs = 10000;
        int noUpdates = 1000;
        long start, end = 0;
        
        start = time();
        int[] nums = createArray(arrayLength);
        end = time();
        System.out.println(String.format("Number array created in %d ms", (end-start)));
        
        start = time();
        NumArray numArray = new NumArray(nums);
        end = time();
        System.out.println(String.format("Number array Tree created in %d ms", (end-start)));
        
        ArrayList<int[]> pairs = getPairs(noPairs, arrayLength);
        long[] sums1 = new long[pairs.size()];
        start = time();
        for (int i=0; i<pairs.size(); i++)
        {
            int[] pair = pairs.get(i);
            sums1[i] = getSum(nums, pair[0], pair[1]);
        }
        end = time();
        System.out.println(String.format("Number array sums computed in %d ms", (end-start)));

        long[] sums2 = new long[pairs.size()];
        start = time();
        for (int i=0; i<pairs.size(); i++)
        {
            int[] pair = pairs.get(i);
            sums2[i] = numArray.sumRange(pair[0], pair[1]);
        }
        end = time();
        System.out.println(String.format("Number array tree sums computed in %d ms", (end-start)));
        System.out.println(String.format("Sums matched = %s", isEqual(sums1, sums2)));
        
        ArrayList<int[]> updates = getUpdates(noUpdates, arrayLength);
        start = time();
        for (int i=0; i<updates.size(); i++)
        {
            int[] update = updates.get(i);
            nums[update[0]] = update[1];
        }
        end = time();
        System.out.println(String.format("Array updated in %d ms", (end-start)));
        
        start = time();
        for (int i=0; i<updates.size(); i++)
        {
            int[] update = updates.get(i);
            numArray.update(update[0], update[1]);
        }
        end = time();
        System.out.println(String.format("Array tree updated in %d ms", (end-start)));
        
        System.out.println(numArray.sumRange(0, arrayLength-1));
        System.out.println(getSum(nums, 0, arrayLength-1));
    }
    
    public static void main(String[] args) throws Exception
    {
        test01();
    }
}

class TreeNode
{
    int start;
    int end;
    long sum;
    
    TreeNode leftChild;
    TreeNode rightChild;
    
    public TreeNode(int start, int end, long sum)
    {
        super();
        this.start = start;
        this.end = end;
        this.sum = sum;
    }
    
    public TreeNode(int start, int end)
    {
        this(start, end, 0);
    }

}
