package com.alg.huffman;

import java.util.BitSet;

public class BitRepresentation
{
    BitSet data = new BitSet();
    int writeStart = 0;

    public BitRepresentation()
    {
        super();
    }

    public BitSet getData()
    {
        return data;
    }

    public void setData(BitSet data)
    {
        this.data = data;
    }

    public int getWriteStart()
    {
        return writeStart;
    }

    public void setWriteStart(int writeStart)
    {
        this.writeStart = writeStart;
    }
    
    public int writeNext(boolean val)
    {
        data.set(writeStart++, val);
        return writeStart;
    }
    
    public boolean getValueAtPosition(int i)
    {
        return data.get(i);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i=writeStart-1; i>=0; i--)
        {
            sb.append(data.get(i) ? '1' : '0');
        }
        return sb.toString();
    }
}
