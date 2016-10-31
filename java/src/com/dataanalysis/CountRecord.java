package com.dataanalysis;

public class CountRecord implements Comparable<CountRecord>
{
    String word;
    Integer count;

    public CountRecord(String word, Integer count)
    {
        super();
        this.word = word;
        this.count = count;
    }

    @Override
    public int compareTo(CountRecord o)
    {
        return word.compareTo(o.word);
    }

}
