package com.alg.datastructures;

public class HashFuncLong implements HashFunc<Long>
{
    int noBuckets;
    int toMod;
    
    public HashFuncLong(int noBuckets)
    {
        super();
        this.noBuckets = noBuckets;
        this.toMod = new HashFuncUtil().nextPrime(noBuckets);
        System.out.println("toMod = " + toMod);
    }

    long abs(long input)
    {
        return (input < 0) ? -input : input;
    }
    
    @Override
    public int hash(Long input)
    {
        return (int) (abs(input) % ((long) toMod));
    }

    @Override
    public int toMod()
    {
        return toMod;
    }

}
