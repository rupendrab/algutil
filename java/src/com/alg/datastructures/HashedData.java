package com.alg.datastructures;

import java.util.ArrayList;

public class HashedData<K,V>
{
    HashFunc<K> hf;
    int initSize;
    ArrayList<ArrayList<HashedValue<K,V>>> data;
    int size;
    
    public HashedData(HashFunc<K> hf, int initSize)
    {
        super();
        this.hf = hf;
        this.initSize = initSize;
        data = new ArrayList<>(initSize);
        for (int i=0; i<initSize; i++)
        {
            data.add(new ArrayList<>());
        }
    }
    
    public HashedData(HashFunc<K> hf)
    {
        super();
        this.hf = hf;
        this.initSize = hf.toMod();
        data = new ArrayList<>(initSize);
        for (int i=0; i<initSize; i++)
        {
            data.add(new ArrayList<>());
        }
    }
    
    public void put(K key, V value)
    {
        int hashPosition = hf.hash(key);
        ArrayList<HashedValue<K,V>> subset = data.get(hashPosition);
        if (subset == null)
        {
            subset = new ArrayList<>();
            data.set(hashPosition, subset);
        }
        HashedValue<K, V> toAdd = new HashedValue<K, V>(key, value);
        if (subset.indexOf(toAdd) == -1)
        {
            subset.add(toAdd);
            size++;
        }
    }

    public V get(K key)
    {
        int hashPosition = hf.hash(key);
        ArrayList<HashedValue<K,V>> subset = data.get(hashPosition);
        if (subset == null)
        {
            return null;
        }
        else
        {
            HashedValue<K,V> toSearch = new HashedValue<K,V>(key, null);
            int pos = subset.indexOf(toSearch);
            if (pos == -1)
            {
                return null;
            }
            else
            {
                return subset.get(pos).getValue();
            }
        }
    }
    
    public V delete(K key)
    {
        int hashPosition = hf.hash(key);
        ArrayList<HashedValue<K,V>> subset = data.get(hashPosition);
        if (subset == null)
        {
            return null;
        }
        else
        {
            HashedValue<K,V> toSearch = new HashedValue<K,V>(key, null);
            int pos = subset.indexOf(toSearch);
            if (pos == -1)
            {
                return null;
            }
            else
            {
                V ret = subset.get(pos).getValue();
                subset.remove(pos);
                return ret;
            }
        }
    }
    
    public int size()
    {
        return size;
    }
    
    public int maxBucketLength()
    {
        int max = 0;
        for (ArrayList<HashedValue<K, V>> subset : data)
        {
            if (subset != null)
            {
                int sz = subset.size();
                max = Math.max(max, sz);
            }
        }
        return max;
    }
    
    public double avgBucketLength()
    {
        double sum = 0;
        int cnt = 0;
        for (ArrayList<HashedValue<K, V>> subset : data)
        {
            if (subset != null)
            {
                int sz = subset.size();
                sum += sz;
                cnt += 1;
            }
        }
        return sum / cnt;
    }
    
    public static void main(String[] args)
    {
        HashedData<String, String> hd = new HashedData<>(new HashFuncString(1000), 1000);
        hd.put("Rupendra", "Bandyopadhyay");
        // System.out.println(hd.data);
        hd.put("Nabanita", "Raul");
        // System.out.println(hd.data);
        System.out.println(hd.get("Rupendra"));
        System.out.println(hd.get("Nabanita"));
        System.out.println(hd.get("Aadarsh"));
        hd.delete("Rupendra");
        System.out.println(hd.get("Rupendra"));
        System.out.println(hd.get("Nabanita"));
        System.out.println(hd.get("Aadarsh"));
    }
    
}
