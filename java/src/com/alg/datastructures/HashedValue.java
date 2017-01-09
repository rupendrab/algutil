package com.alg.datastructures;

public class HashedValue<K,V>
{
    K key;
    V value;
    
    public HashedValue(K key, V value)
    {
        super();
        this.key = key;
        this.value = value;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (! (o instanceof HashedValue<?, ?>))
        {
            return false;
        }
        @SuppressWarnings("unchecked")
        HashedValue<K, V> other = (HashedValue<K, V>) o;
        return other.key.equals(key);
    }

    public K getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }

}
