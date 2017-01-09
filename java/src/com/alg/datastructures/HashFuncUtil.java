package com.alg.datastructures;

import java.util.ArrayList;
import java.util.Arrays;

public class HashFuncUtil
{
    ArrayList<Integer> primes = new ArrayList<>();
    int evaluatedUntil = 0;
    
    public HashFuncUtil()
    {
        super();
        Integer[] initPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        primes = new ArrayList<>(Arrays.asList(initPrimes));
        evaluatedUntil = primes.get(primes.size() - 1);
    }

    public int prevStoredPrime(int no)
    {
        if (no > evaluatedUntil)
        {
            return -1;
        }
        if (no < primes.get(0))
        {
            return -1;
        }
        return prevStoredPrime(no, 0, primes.size());
    }
    
    public int prevStoredPrime(int no, int from, int to)
    {
        if (from >= to - 1)
        {
            return primes.get(from);
        }
        int mid = (from + to) / 2;
        int midVal = primes.get(mid);
        // System.out.println(String.format("from = %d, to = %d, midVal = %d", from, to, midVal));
        if (midVal == no)
        {
            return midVal;
        }
        else if (midVal < no)
        {
            /*
            if (mid+1 == primes.size())
            {
                System.out.println(String.format("no = %d, from = %d, to = %d, midVal = %d", no, from, to, midVal));            
                System.out.println("Evaluated Until = " + evaluatedUntil);
                System.out.println(primes);
            }
            */
            if (mid + 1 >= to)
            {
                return primes.get(to - 1);
            }
            int nextToMid = primes.get(mid+1);
            if (nextToMid > no)
            {
                return midVal;
            }
            else
            {
                return prevStoredPrime(no, mid+1, to);
            }
        }
        else
        {
            return prevStoredPrime(no, from, mid);
        }
    }
    
    public int nextPrime(int no)
    {
        for (int i=no; ; i++)
        {
            if (isPrime(i))
            {
                return i;
            }
        }
    }
    
    public boolean isPrime(int n)
    {
        if (n == 0)
        {
            return false;
        }
        int sqrt = (int) Math.floor(Math.sqrt(n));
        if (sqrt <= evaluatedUntil)
        {
            int lastPrime = prevStoredPrime(sqrt);
            for (int i : primes)
            {
                if (i > lastPrime)
                {
                    break;
                }
                if (n % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            calculateUntil(sqrt);
            return isPrime(n);
        }
    }
    
    public void calculateUntil(int n)
    {
        for (int i=evaluatedUntil+1; i<=n; i++)
        {
            if (isPrime(i))
            {
                primes.add(i);
            }
            evaluatedUntil = i;
        }
    }
    
    public static void test01(HashFuncUtil hu)
    {
        for (int i=0; i<40; i++)
        {
            System.out.println(i + " = " + hu.prevStoredPrime(i));
        }
    }
    
    public static void test02(HashFuncUtil hu)
    {
        for (int i=0; i<40; i++)
        {
            System.out.println(i + " = " + hu.isPrime(i));
        }
    }
    
    public static void test03(HashFuncUtil hu)
    {
        System.out.println(hu.isPrime(10000000));
        System.out.println(hu.primes.size());
        System.out.println(hu.primes.get(hu.primes.size() - 1));
    }
    
    public static void main(String[] args)
    {
        HashFuncUtil hu = new HashFuncUtil();
        System.out.println(hu.primes);
        // System.out.println(hu.prevStoredPrime(1));
        // test02(hu);
        System.out.println(hu.nextPrime(100000000));        
    }

}
