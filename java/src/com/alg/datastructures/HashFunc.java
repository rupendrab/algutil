package com.alg.datastructures;

public interface HashFunc<T>
{
    public int hash(T input);
    public int toMod();
}
