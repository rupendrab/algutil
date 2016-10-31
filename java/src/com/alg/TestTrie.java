package com.alg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTrie
{
    static String readFile(String fileName) throws IOException
    {
        byte[] b = Files.readAllBytes(Paths.get(fileName));
        return new String(b);
    }
    
    static void showSome(Trie trie, String text, String word, int before, int after)
    {
        ArrayList<Integer> positions = trie.traceWord(word);
        if (positions != null)
        {
            for (int pos : positions)
            {
                int start = pos - before;
                if (pos < 0) pos = 0;
                int end = pos + after;
                if (end > text.length()) end = text.length();
                char[] dst = new char[end-start];
                text.getChars(start, end, dst, 0);
                System.out.println(new String(dst));
                System.out.println();
                System.out.println("------------------------------");
            }
        }
    }
    
    static int countWords(String text)
    {
        return getWords(text).size();
    }
    
    static HashSet<String> getWords(String text)
    {
        Pattern p = Pattern.compile("(\\w+)");
        Matcher m = p.matcher(text);
        HashSet<String> words = new HashSet<String>();
        while (m.find())
        {
            words.add(m.group(1));
        }
        return words;
    }
    
    static void printSomeExceptionWords(Trie trie, String text, int limit)
    {
        int exceptions = 0;
        for (String word : getWords(text))
        {
            if (trie.traceWord(word) == null)
            {
                exceptions++;
                System.out.println(word);
            }
            if (exceptions >= limit)
            {
                break;
            }
        }
    }

    static void printWordsStartingWith(Trie trie, String startsWith)
    {
        for (Entry<String, ArrayList<Integer>> entry : trie.wordsStartingWith(startsWith).entrySet())
        {
            System.out.println(String.format("%-20s\t%s", entry.getKey(), entry.getValue()));
        }
        System.out.println("------------------------------");
    }
    
    static ArrayList<Integer> getWordPositions(String text, String word)
    {
        ArrayList<Integer> pos = new ArrayList<Integer>();
        Pattern p = Pattern.compile("(\\w+)");
        Matcher m = p.matcher(text);
        while (m.find())
        {
            String textWord = m.group(1);
            if (word.equals(textWord))
            {
                pos.add(m.start(1));
            }
        }
        return pos;
    }
    
    static void perfTest(Trie trie, String text)
    {
        String[] words = {"Holmes", "Watson", "BaskerVilles", "Selden", "Lyons"};
        long startTime = Calendar.getInstance().getTimeInMillis();
        for (int i=0; i<1000; i++)
        {
            for (String word : words)
            {
                trie.traceWord(word);
            }
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        long trieTime = endTime - startTime;
        System.out.println(String.format("Time elapsed in trie = %d ms", trieTime));

        startTime = Calendar.getInstance().getTimeInMillis();
        for (int i=0; i<1000; i++)
        {
            for (String word : words)
            {
                getWordPositions(text, word);
            }
        }
        endTime = Calendar.getInstance().getTimeInMillis();
        long seqTime = endTime - startTime;
        System.out.println(String.format("Time elapsed in sequential = %d ms", seqTime));
        System.out.println(String.format("Gain in trie = %10.2f", seqTime * 1.0 / trieTime));
    }
    
    public static void main(String[] args) throws Exception
    {
        String fileName = "C:/tmp/hb.txt";
        String text = readFile(fileName);
        Trie trie = Trie.getTrie(text);
        System.out.println(trie.traceWord("Holmes"));
        System.out.println(trie.traceWord("Watson"));
        System.out.println(trie.traceWord("Baskervilles"));
        System.out.println(trie.traceWord("Selden"));
        System.out.println(trie.traceWord("Lyons"));
        System.out.println(trie.traceWord("Watson"));
        printWordsStartingWith(trie, "Wats");
        // showSome(trie, text, "Selden", 30, 200);
        // printWordsStartingWith(trie, "ag");
        printWordsStartingWith(trie, "Wa");
        printWordsStartingWith(trie, "wa");
        System.out.println(trie.noWordsUnder());
        System.out.println("---------------");
        System.out.println(countWords(text));
        printSomeExceptionWords(trie, text, 5);
        System.out.println("---------------");
        // System.out.println(trie.addWord("ten", -1));
        printWordsStartingWith(trie, "ten");
        System.out.println(trie.wordsStartingWith("").size());
        perfTest(trie, text);
    }

}
