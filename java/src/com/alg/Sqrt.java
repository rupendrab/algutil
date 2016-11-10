package com.alg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Sqrt
{
    public static BigDecimal sqrt(BigDecimal in, int precision)
    {
        BigDecimal guess = in.divide(new BigDecimal("2.0"));
        // System.out.println("Guess = " + guess);
        StringBuilder maxErrorSB = new StringBuilder();
        maxErrorSB.append("0.");
        for (int i=0; i<precision; i++)
        {
            maxErrorSB.append("0");
        }
        maxErrorSB.append("1");
        BigDecimal maxError = new BigDecimal(maxErrorSB.toString());
        BigDecimal stepResult = guess.multiply(guess);
        // System.out.println("Step Result: " + stepResult);
        BigDecimal stepError = in.subtract(stepResult).abs().divide(in, precision, RoundingMode.HALF_UP);
        // System.out.println("Step Error: " + stepError);
        while (stepError.abs().compareTo(maxError) > 0)
        {
            guess = guess.add(in.divide(guess, precision, RoundingMode.HALF_UP)).divide(new BigDecimal("2.0"));
            // System.out.println("Guess = " + guess);
            stepResult = guess.multiply(guess);
            stepError = in.subtract(stepResult).abs().divide(in, precision, RoundingMode.HALF_UP);
            // System.out.println("Step Error: " + stepError);
        }
        return guess;
    }
    
    public static BigDecimal factorial(int n)
    {
        BigDecimal start = new BigDecimal("1");
        if (n <= 1)
        {
            return start;
        }
        for (int i=2; i<=n; i++)
        {
            start = start.multiply(new BigDecimal(i));
        }
        return start;
    }
    
    public static BigInteger EuclidGCD(BigInteger a, BigInteger b)
    {
        if (b.equals(BigInteger.ZERO))
        {
            return a;
        }
        else
        {
            return EuclidGCD(b, a.mod(b));
        }
    }

    public static void main(String[] args)
    {
        System.out.println(sqrt(new BigDecimal("2"), 1000));
        System.out.println(factorial(100));
        System.out.println(EuclidGCD(new BigInteger("72"), new BigInteger("120")));
    }
}
