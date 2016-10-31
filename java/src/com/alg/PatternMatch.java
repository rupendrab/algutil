package com.alg;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatch
{
    static boolean DEBUG = false;
    static boolean DEBUG2 = false;
    
    static void printDebug(String str)
    {
        if (DEBUG)
        {
            System.out.println(str);
        }
    }
    
    static void printDebug2(String str)
    {
        if (DEBUG2)
        {
            System.out.println(str);
        }
    }
    
    public static int findBrute(char[] text, char[] pattern)
    {
        int n = text.length;
        int m = pattern.length;
        int noop = 0;
        for (int i=0; i < n-m; i++)
        {
            int k = 0;
            noop++;
            while (k < m && text[i+k] == pattern[k])
            {
                noop++;
                k++;
            }
            if (k == m)
            {
                printDebug("noop = " + noop);
                return i;
            }
        }
        printDebug("noop = " + noop);
        return -1;
    }
    
    public static int get(Map<Character, Integer> last, char c)
    {
        Integer ret = last.get(c);
        return ret == null ? -1 : ret;
    }
    
    public static int findBoyerMoore(char[] text, char[] pattern)
    {
        int n = text.length;
        int m = pattern.length;
        if (m == 0) return 0;
        
        Map<Character, Integer> last = new HashMap<Character, Integer>();
        
        int k = 0;
        for (char c : pattern)
        {
            last.put(c, k);
            k++;
        }
        
        int i = m - 1;
        k = m - 1;
        int noop = 0;
        while (i < n)
        {
            noop += 1;
            if (text[i] == pattern[k])
            {
                // System.out.println(String.format("Matched : i = %d, k = %d", i, k));
                if (k == 0)
                {
                    printDebug("noop = " + noop);
                    return i;
                }
                i--;
                k--;
            }
            else
            {
                i += m - Math.min(k, 1 + get(last, text[i]));
                // i += m - Math.min(k, 1 + last.get(text[i]));
                k = m - 1;
            }
        }
        printDebug("noop = " + noop);
        return -1;
    }
    
    public static int[] computeFailKMP(char[] pattern)
    {
        int m = pattern.length;
        int[] fail = new int[m];            // By default, all overlaps are zero
        int j = 1;
        int k = 0;
        while (j < m)                       // compute fail[j] during this pass, if nonzero
        {
            /*
            if (j < m)
            {
                System.out.println(String.format("char = %c, j = %d, k = %d", pattern[j], j, k));
            }
            */
            if (pattern[j] == pattern[k])   // k+1 characters match thus far
            {
                fail[j] = k + 1;
                j++;
                k++;
            }
            else if (k > 0)                 // k follows a matching prefix
            {
                k = fail[k-1];
            }
            else                            // No match found at j
            {
                j++;
            }
        }
        return fail;
    }
    
    public static int findKMP(char[] text, char[] pattern)
    {
        int n = text.length;
        int m = pattern.length;
        if (m == 0) return 0;
        int[] fail = computeFailKMP(pattern);
        int j = 0;
        int k = 0;
        int noop = 0;
        printDebug2(Arrays.toString(fail));
        while (j < n)
        {
            // printDebug2(String.format("j = %d, k = %d, char = %c, patternchar = %c", j, k, text[j], pattern[k]));
            noop++;
            if (text[j] == pattern[k])
            {
                // printDebug2("Matched");
                if (k == m - 1) 
                {
                    printDebug("noop = " + noop);
                    return j - m + 1;
                }
                j++;
                k++;
            }
            else if (k > 0)
            {
                k = fail[k-1];
                // printDebug2(String.format("k reassigned to %d", k));
            }
            else {
                // printDebug2("Increment j");
                j++;
            }
        }
        printDebug("noop = " + noop);
        return -1;
    }
    
    public static char[] generateRandomChars(int len)
    {
        char[] data = new char[len];
        Random rand = new Random();
        int start = 32;
        int end = 120;
        for (int i = 0; i < len; i++)
        {
            data[i] = (char) (rand.nextInt(end + 1 - start) + start);
        }
        return data;
    }
    
    public static char[] subChars(char[] main, int start, int len)
    {
        char[] ret = new char[len];
        for (int i = 0; i<len; i++)
        {
            ret[i] = main[start+i];
        }
        return ret;
    }
    
    public static void perfTest()
    {
        int textLen = 100000000;
        int patternLen = 100;
        char[] text = generateRandomChars(textLen);
        // char[] pattern = generateRandomChars(patternLen);
        char[] pattern = subChars(text, 90000000, 100);
        timedTest(text, pattern);
    }
    
    public static void timedTest(char[] text, char[] pattern)
    {
        long start = Calendar.getInstance().getTimeInMillis();
        int pos = findBrute(text, pattern);
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Brute force: result = %d, time in milliseconds = %d", pos, (end - start)));

        start = Calendar.getInstance().getTimeInMillis();
        pos = findBoyerMoore(text, pattern);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Boyer Moore: result = %d, time in milliseconds = %d", pos, (end - start)));

        start = Calendar.getInstance().getTimeInMillis();
        pos = findKMP(text, pattern);
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("KMP: result = %d, time in milliseconds = %d", pos, (end - start)));

        String str = new String(text);
        start = Calendar.getInstance().getTimeInMillis();
        Pattern p = Pattern.compile(new String(pattern), Pattern.LITERAL);
        Matcher m = p.matcher(str);
        pos = -1;
        if (m.find())
        {
            pos = m.start();
        }
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Java Regex: result = %d, time in milliseconds = %d", pos, (end - start)));
        
        start = Calendar.getInstance().getTimeInMillis();
        pos = str.indexOf(new String(pattern));
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println(String.format("Java indexOf: result = %d, time in milliseconds = %d", pos, (end - start)));
}
    
    public static void basicTest()
    {
        System.out.println(findBrute("abacaabaccabacabaabb".toCharArray(), "abacab".toCharArray()));
        System.out.println(findBrute("abacaabaccabacabaabb".toCharArray(), "abacad".toCharArray()));
        System.out.println(findBoyerMoore("abacaabaccabacabaabb".toCharArray(), "abacab".toCharArray()));
        System.out.println(findBoyerMoore("abacaabaccabacabaabb".toCharArray(), "abacad".toCharArray()));
        
        // Using Java Regex Pattern matching with Literal
        Pattern p = Pattern.compile("abacab", Pattern.LITERAL);
        Matcher m = p.matcher("abacaabaccabacabaabb abacab abacab");
        if (m.find())
        {
            System.out.println(m.start());
        }

        System.out.println(findKMP("abacaabaccabacabaabb".toCharArray(), "abacab".toCharArray()));
        System.out.println(findKMP("abacaabaccabacabaabb".toCharArray(), "abacad".toCharArray()));

    }
    
    public static void testKMP()
    {
        String text = "actamalgamalttt";
        String pattern = "amalgamation";
        DEBUG2 = true;
        int n = findKMP(text.toCharArray(), pattern.toCharArray());
    }
    
    public static void main(String[] args)
    {
        perfTest();
        // int[] fail = computeFailKMP("amalgamamaon".toCharArray());
        // System.out.println(Arrays.toString(fail));
        // basicTest();
        testKMP();
    }

}
