package com.alg.datastructures;

public class HashFuncString implements HashFunc<String>
{
    int noBuckets;
    int toMod;
    
    
    public HashFuncString(int noBuckets)
    {
        super();
        this.noBuckets = noBuckets;
        this.toMod = new HashFuncUtil().nextPrime(noBuckets);
    }

    @Override
    public int hash(String input)
    {
        int x = 0;
        for (char c : input.toCharArray())
        {
            x = x * 17 + (int) c;
            if (x > toMod)
            {
                x = x % toMod;
            }
        }
        return x;
    }
    
    @Override
    public int toMod()
    {
        return toMod;
    }
    
    public static void main(String[] args)
    {
        int noBuckets = 10000;
        HashFuncString x = new HashFuncString(noBuckets);
        System.out.println(x.hash("Rupendra"));
        System.out.println(x.hash("Bandyopadhyay"));
        System.out.println(x.hash("Nabanita"));
        System.out.println(x.hash("Raul"));
        System.out.println(x.hash("Ronnie"));
    }

}
