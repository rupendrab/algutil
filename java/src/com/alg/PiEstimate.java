package com.alg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Random;

public class PiEstimate
{
    public static String estimateByArea(int maxPrecision)
    {
        if (maxPrecision % 2 != 0)
        {
            maxPrecision += 1;
        }
        BigInteger size = (new BigInteger("10")).pow(maxPrecision/2);
        BigInteger total = (new BigInteger("10")).pow(maxPrecision);
        // System.out.println(size);
        BigInteger i = new BigInteger("0");
        BigInteger j = new BigInteger("0");
        Random rand = new Random();
        BigInteger count = new BigInteger("0");
        while (i.compareTo(size) < 0)
        {
            // System.out.println("i = " + i);
            j = new BigInteger("0");
            while (j.compareTo(size) < 0)
            {
                // System.out.println("j = " + j);
                double x = rand.nextDouble();
                double y = rand.nextDouble();
                double dist = (x - 0.5) * (x - 0.5) + (y - 0.5) * (y - 0.5);
                if (dist <= 0.25)
                {
                    count = count.add(BigInteger.ONE);
                }
                j = j.add(BigInteger.ONE);
            }
            i = i.add(BigInteger.ONE);
        }
        return "" + new BigDecimal(count).multiply(new BigDecimal(4)).divide(new BigDecimal(total)); 
    }
    
    public static void main(String[] args)
    {
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println(estimateByArea(8));
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Time elapsed = %d ms", (end - start)));
    }

}
