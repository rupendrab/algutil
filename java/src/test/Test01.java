package test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test01
{
    public static void test01()
    {
        ArrayList<Integer> artest = new ArrayList<>();
        artest.add(1);
        artest.add(2);
        artest.add(2);
        artest.add(1);
        System.out.println(artest);
        artest.remove(new Integer(1));
        System.out.println(artest);
        artest.remove(new Integer(1));
        System.out.println(artest);
        artest.remove(new Integer(2));
        System.out.println(artest);
        artest.remove(new Integer(2));
        System.out.println(artest);
    }
    
    public static void testQueue()
    {
        int[] arr = {1,3,4,5,6,7,8};
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>();
        for (int i : arr)
        {
            q.add(i);
        }
        Integer x = null;
        while ((x = q.poll()) != null)
        {
            System.out.println(x);
        }
    }
    
    public static void main(String[] args)
    {
        // test01();
        testQueue();
    }

}
