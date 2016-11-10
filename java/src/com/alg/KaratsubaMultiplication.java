package com.alg;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KaratsubaMultiplication
{
    String num1;
    String num2;
    int len1;
    int len2;
    int len;
    
    Pattern zeroPattern =  Pattern.compile("^[\\-+]?0+$"); 
    
    public KaratsubaMultiplication(String num1, String num2)
    {
        super();
        this.num1 = num1;
        this.num2 = num2;
        this.len1 = num1.length();
        this.len2 = num2.length();
        this.len = Math.max(len1, len2);
        // System.out.println("pre : " + num1 + " ... " + num2 + " *** " + len);
        if (len > len1)
        {
            this.num1 = padByZeros(num1, len);
        }
        if (len > len2)
        {
            this.num2 = padByZeros(num2, len);
        }
        // System.out.println(this.num1 + " ... " + this.num2 + " *** " + len);
    }
    
    String padByZeros(String str, int len)
    {
       if (str.length() >= len)
       {
           return str;
       }
       else
       {
           StringBuilder sb = new StringBuilder(len);
           for (int i=0; i <len - str.length(); i++)
           {
               sb.append("0");
           }
           sb.append(str);
           return sb.toString();
       }
    }
    
    String addZerosToRight(String str, int numberOfZeros)
    {
        StringBuilder sb = new StringBuilder(str.length() + numberOfZeros);
        sb.append(str);
        for (int i=0; i<numberOfZeros; i++)
        {
            sb.append("0");
        }
        return sb.toString();   
    }
    
    public String negative(String s1)
    {
        if (s1.startsWith("-"))
        {
            return s1.substring(1);
        }
        else if (s1.startsWith("+"))
        {
            return "-" + s1.substring(1);
        }
        else
        {
            return "-" + s1;
        }
    }
    
    public int sign(String s1)
    {
        Matcher m = zeroPattern.matcher(s1);
        if (m.find())
        {
            return 0;
        }
        if (s1.startsWith("-"))
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
    
    public String abs(String s1)
    {
        if (sign(s1) == -1)
        {
            return negative(s1);
        }
        else
        {
            return s1;
        }
    }
    
    public String removeLeadingSignAndZeros(String s1)
    {
        char[] chars = s1.toCharArray();
        int i = 0;
        while (i < chars.length && (chars[i] == '-' || chars[i] == '+' || chars[i] == '0'))
        {
            i++;
        }
        return new String(chars, i, chars.length -i);
    }
    
    public int absCompare(String s1, String s2)
    {
        s1 = removeLeadingSignAndZeros(s1);
        s2 = removeLeadingSignAndZeros(s2);
        int slen1 = s1.length();
        int slen2 = s2.length();
        if (slen1  > slen2)
        {
            return 1;
        }
        else if (slen1 < slen2)
        {
            return -1;
        }
        else
        {
            for (int i=0; i<slen1; i++)
            {
                if (s1.charAt(i) > s2.charAt(i))
                {
                    return 1;
                }
                else if (s1.charAt(i) < s2.charAt(i))
                {
                    return -1;
                }
            }
        }
        return 0;
    }
    
    public String subtract(String s1, String s2)
    {
        String s11 = abs(s1);
        String s12 = abs(s2);
        switch(sign(s1))
        {
        case -1:
            switch(sign(s2))
            {
            case -1:
                return subtractNoSign(s12, s11);
            case 1:
                return negative(add(s11, s11));
            case 0:
                return s1;
            }
            break;
        case 1:
            switch(sign(s2))
            {
            case -1:
                return add(s11, s12);
            case 1:
                return subtractNoSign(s11, s12);
            case 0:
                return s1;
            }
            break;
        default:
            return negative(s2);
        }
        return "";
    }
    
    public String subtractNoSign(String s1, String s2)
    {
        int cmp = absCompare(s1, s2);
        if (cmp == 0)
        {
            return "0";
        }
        else if (cmp == 1)
        {
            return subtractProper(s1, s2);
        }
        else
        {
            return negative(subtractProper(s2, s1));
        }
    }
    
    public String subtractProper(String s1, String s2)
    {
        int slen1 = s1.length();
        int slen2 = s2.length();
        int slen = Math.max(slen1, slen2);
        char[] sub = new char[slen + 1];
        char[] p1 = s1.toCharArray();
        char[] p2 = s2.toCharArray();
        int carryOver = 0;
        int p1Pointer = p1.length - 1;
        int p2Pointer = p2.length - 1;
        for (int i=slen; i>=0; i--)
        {
            int n1 = 0;
            int n2 = 0;
            if (p1Pointer >= 0)
            {
                n1 = p1[p1Pointer] - '0';
            }
            if (p2Pointer >= 0)
            {
                n2 = p2[p2Pointer] - '0';
            }
            // System.out.println(String.format("i = %d, n1 = %d, n2 = %d, carry = %d", i, n1, n2, carryOver));
            n2 = n2 + carryOver;
            int result = 0;
            if (n1 >= n2)
            {
                result = n1 - n2;
                carryOver = 0;
            }
            else
            {
                carryOver = 1;
                result = n1 + 10 - n2;
            }
            sub[i] = (char) ('0' + (result % 10));
            // System.out.println(Arrays.toString(sum));
            p1Pointer--;
            p2Pointer--;
        }
        int offset = 0;
        while (offset < sub.length && sub[offset] == '0')
        {
            offset += 1;
        }
        String sSub = new String(sub, offset, slen + 1 - offset);
        if (sSub == null || sSub.equals(""))
        {
            return "0";
        }
        else
        {
            return sSub;
        }
    }
    
    public String add(String s1, String s2)
    {
        String s11 = abs(s1);
        String s12 = abs(s2);
        switch(sign(s1))
        {
        case -1:
            switch(sign(s2))
            {
            case -1:
                return negative(addProper(s12, s11));
            case 1:
                return subtract(s12, s11);
            case 0:
                return s1;
            }
            break;
        case 1:
            switch(sign(s2))
            {
            case -1:
                return subtract(s11, s12);
            case 1:
                return addProper(s11, s12);
            case 0:
                return s1;
            }
            break;
        default:
            return s2;
        }
        return "";
        
    }
    
    public String addProper(String s1, String s2)
    {
        // System.out.println("add " + s1 + " to " + s2);
        int slen1 = s1.length();
        int slen2 = s2.length();
        int slen = Math.max(slen1, slen2);
        char[] sum = new char[slen + 1];
        char[] p1 = s1.toCharArray();
        char[] p2 = s2.toCharArray();
        int carryOver = 0;
        int p1Pointer = p1.length - 1;
        int p2Pointer = p2.length - 1;
        for (int i=slen; i>=0; i--)
        {
            int n1 = 0;
            int n2 = 0;
            if (p1Pointer >= 0)
            {
                n1 = p1[p1Pointer] - '0';
            }
            if (p2Pointer >= 0)
            {
                n2 = p2[p2Pointer] - '0';
            }
            // System.out.println(String.format("i = %d, n1 = %d, n2 = %d, carry = %d", i, n1, n2, carryOver));
            int result = n1 + n2 + carryOver;
            carryOver = result / 10;
            sum[i] = (char) ('0' + (result % 10));
            // System.out.println(Arrays.toString(sum));
            p1Pointer--;
            p2Pointer--;
        }
        int offset = 0;
        while (offset < sum.length && sum[offset] == '0')
        {
            offset += 1;
        }
        String sAdd = new String(sum, offset, slen + 1 - offset);
        if (sAdd == null || sAdd.equals(""))
        {
            return "0";
        }
        else
        {
            return sAdd;
        }
    }
    
    public String multiply()
    {
        // System.out.println("num1 = " + num1 + ", num2 = " + num2 + ", len = " + len);
        if (len == 1)
        {
            int n1 = num1.charAt(0) - '0';
            int n2 = num2.charAt(0) - '0';
            return "" + (n1 * n2);
        }
        else
        {
            int divider = len/2;
            String a = num1.substring(0, divider);
            String b = num1.substring(divider);
            String c = num2.substring(0, divider);
            String d = num2.substring(divider);
            // System.out.println(String.format("a = %s, b = %s, c = %s, d= %s", a, b, c, d));
            String p1 = new KaratsubaMultiplication(a, c).multiply();
            String p2 = new KaratsubaMultiplication(b, d).multiply();
            String a_plus_b = add(a,b);
            String c_plus_d = add(c,d);
            // System.out.println(String.format("a + b = %s, c + d = %s", a_plus_b, c_plus_d));
            String p3 = new KaratsubaMultiplication(a_plus_b, c_plus_d).multiply();
            // p3 = "" + (Long.parseLong(p3) - Long.parseLong(p1) - Long.parseLong(p2));
            p3 = subtract(p3, add(p1,p2));
            p1 = addZerosToRight(p1, 2 * (len - divider)); 
            p3 = addZerosToRight(p3, (len - divider));
            // return "" + (Long.parseLong(p1) + Long.parseLong(p2) + Long.parseLong(p3));
            return add(add(p1, p2), p3);
        }
    }

    public static void testSign(KaratsubaMultiplication km)
    {
        System.out.println(km.sign("-123"));
        System.out.println(km.sign("234"));
        System.out.println(km.sign("+234"));
        System.out.println(km.sign("0"));
        System.out.println(km.sign("0000"));
        System.out.println(km.sign("+0000"));
        System.out.println(km.sign("-0000"));
        System.out.println(km.removeLeadingSignAndZeros("-0123"));
        System.out.println(km.removeLeadingSignAndZeros("0000123"));
    }
    
    public static void testCompare(KaratsubaMultiplication km)
    {
        System.out.println(km.absCompare("123", "123"));
        System.out.println(km.absCompare("123", "-123"));
        System.out.println(km.absCompare("123", "-12"));
        System.out.println(km.absCompare("123", "213"));
    }
    
    public static void testSubtract(KaratsubaMultiplication km)
    {
        System.out.println(km.subtract("23", "14"));
        System.out.println(km.subtract("100", "1"));
        System.out.println(km.subtract("1234", "955"));
    }
    
    public static void main(String[] args)
    {
        long start = 0;
        long end = 0;
        String p1 = "123456789123456789123456789123456789999999999123456789123456789123456789123456789999999999";
        String p2 = "987654321987654321987654321987654321987654321123456789123456789123456789123456789999999999";
        start = Calendar.getInstance().getTimeInMillis();
        KaratsubaMultiplication km = new KaratsubaMultiplication(p1, p2);
        String kmResult = km.multiply();
        end = Calendar.getInstance().getTimeInMillis();
        long kmTime = end - start;
        System.out.println(kmResult);
        start = Calendar.getInstance().getTimeInMillis();
        BigDecimal bd1 = new BigDecimal(p1);
        BigDecimal bd2 = new BigDecimal(p2);
        String bdResult = bd1.multiply(bd2).toString();
        end = Calendar.getInstance().getTimeInMillis();
        long bdTime = end - start;
        System.out.println(bdResult);
        System.out.println("Matched = " + kmResult.equals(bdResult));
        System.out.println(String.format("KM = %d, BD = %d", kmTime, bdTime));
        // System.out.println(km.add("92", "13"));
        // testSign(km);
        // testCompare(km);
        // testSubtract(km);
    }
}
