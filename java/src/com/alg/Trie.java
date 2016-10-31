package com.alg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trie implements Serializable
{

    private static final long serialVersionUID = 1L;
    TrieIntermediateNode root;

    public Trie()
    {
        super();
        this.root = new TrieIntermediateNode();
    }
    
    public Trie(TrieIntermediateNode root)
    {
        super();
        assert (root.c == null);
        this.root = root;
    }

    public ArrayList<Integer> traceWord(String word)
    {
        char[] chars = word.toCharArray();
        int pos = 0;
        TrieNode t = root.components.get(chars[pos]); 
        while (t != null && pos < chars.length)
        {
            if (pos == chars.length - 1) // No more to go, check if there is an end node
            {
                TrieNode tLast = ((TrieIntermediateNode) t).components.get((char) 127);
                if (tLast != null)
                {
                    return ((TrieLeaf) tLast).positions;
                }
                else
                {
                    // System.out.println("tLast is not found");
                    return null;
                }
            }
            else
            {
                pos += 1;
                t = ((TrieIntermediateNode) t).components.get(chars[pos]); 
            }
        }
        return null;
    }
    
    public boolean addWord(String word, int wordStart)
    {
        // System.out.println("Adding " + word + " at " + wordStart);
        char[] chars = word.toCharArray();
        int pos = 0;
        TrieIntermediateNode parent = root;
        TrieNode child = root.components.get(chars[pos]); 
        while (child != null && pos < chars.length)
        {
            if (pos == chars.length - 1) // No more to go, check if there is an end node
            {
                TrieNode tLast = ((TrieIntermediateNode) child).components.get((char) 127);
                if (tLast != null)
                {
                    ((TrieLeaf) tLast).positions.add(wordStart);
                    return true;
                }
                else
                {
                    tLast = new TrieLeaf();
                    ((TrieIntermediateNode) child).components.put(tLast.getC(), tLast);
                    return true;
                }
            }
            else
            {
                pos += 1;
                parent = (TrieIntermediateNode) child;
                child = ((TrieIntermediateNode) child).components.get(chars[pos]); 
            }
        }
        if (pos < chars.length)
        {
            while (pos < chars.length)
            {
                child = new TrieIntermediateNode(chars[pos]);
                parent.components.put(chars[pos], child);
                parent = (TrieIntermediateNode) child;
                pos += 1;
            }
            TrieLeaf endComponent = new TrieLeaf();
            endComponent.positions.add(wordStart);
            parent.components.put(endComponent.getC(), endComponent);
        }
        return true;
    }
    
    public HashMap<String, ArrayList<Integer>> wordsStartingWith(String prefix)
    {
        TrieIntermediateNode startNode = root.traverseToNode(prefix);
        if (startNode == null)
        {
            return new HashMap<String, ArrayList<Integer>>();
        }
        HashMap<String, ArrayList<Integer>> ret = startNode.wordsStartingWith(prefix);
        return ret;
    }
    
    public int noWordsUnder()
    {
        return root.noWordsUnder();
    }

    public static Trie getTrie(String text)
    {
        Trie trie = new Trie();
        Pattern p = Pattern.compile("(\\w+)");
        Matcher m = p.matcher(text);
        while (m.find())
        {
            trie.addWord(m.group(1), m.start(1));
        }
        return trie;
    }
    
    public static void main(String[] args) throws Exception
    {
        Trie trie = new Trie();
        System.out.println(trie.root);
        trie.addWord("be", 0);
        System.out.println(trie.root);
        trie.addWord("bet", 2);
        System.out.println(trie.root);
        trie.addWord("set", 5);
        System.out.println(trie.root);
        System.out.println(trie.traceWord("set"));

        String fileName = "C:/tmp/trie_01.txt";
        
        /*
        Trie nt = Trie.getTrie("this is some arbitrary text and this too");
        System.out.println(nt.traceWord("arbitrary"));
        System.out.println(nt.traceWord("text"));
        System.out.println(nt.traceWord("this"));
        System.out.println(nt.root);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
        out.writeObject(nt);
        out.close();
        */
        
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(fileName)));
        Trie nt2 = (Trie) in.readObject();
        in.close();
        System.out.println(nt2.root);
        System.out.println(nt2.traceWord("arbitrary"));
        System.out.println(nt2.traceWord("text"));
        System.out.println(nt2.traceWord("this"));
    }

}

abstract class TrieNode implements Serializable
{
    private static final long serialVersionUID = 1L;
    Character c;

    public TrieNode()
    {
        super();
    }
    
    public TrieNode(Character c)
    {
        super();
        this.c = c;
    }

    public Character getC()
    {
        return c;
    }

    public void setC(Character c)
    {
        this.c = c;
    }
    
    public abstract boolean isEnd();
    
    public String toString()
    {
        if (this instanceof TrieLeaf)
        {
            TrieLeaf leaf = (TrieLeaf) this;
            StringBuilder sb = new StringBuilder();
            sb.append("Node(" + leaf.getC() + ", End : ");
            for (int pos : leaf.positions)
            {
                sb.append(pos + ",");
            }
            sb.append(")");
            return sb.toString();
        }
        else
        {
            TrieIntermediateNode inter = (TrieIntermediateNode) this;
            StringBuilder sb = new StringBuilder();
            sb.append("Node(" + getC()); 
            for (TrieNode t : inter.components.values())
            {
                sb.append("," + t.toString());
            }
            sb.append(")");
            return sb.toString();
        }
    }
}

class TrieIntermediateNode extends TrieNode
{
    private static final long serialVersionUID = 1L;
    HashMap<Character, TrieNode> components;

    public TrieIntermediateNode()
    {
        super();
        components = new HashMap<Character, TrieNode>();
    }

    public TrieIntermediateNode(Character c)
    {
        super(c);
        components = new HashMap<Character, TrieNode>();
    }
    
    public boolean isEnd()
    {
        return false;
    }
    
    public HashMap<String, ArrayList<Integer>> wordsStartingWith(String prefix)
    {
        // System.out.println("in wordsStartingWith: " + this);
        HashMap<String, ArrayList<Integer>> ret = new HashMap<String, ArrayList<Integer>>();
        TrieNode tLast = components.get((char) 127);
        if (tLast != null)
        {
            ret.put(prefix, ((TrieLeaf) tLast).positions);
        }
        for (Entry<Character, TrieNode> entry : components.entrySet())
        {
            TrieNode t = entry.getValue();
            Character prefixChar = entry.getKey();
            // System.out.println("Prefixchar is " + prefixChar);
            if (! t.isEnd())
            {
                HashMap<String, ArrayList<Integer>> retChild = ((TrieIntermediateNode) t).wordsStartingWith(prefix + prefixChar);
                ret.putAll(retChild);
            }
        }
        return ret;
    }

    public TrieIntermediateNode traverseToNode(String prefix)
    {
        char[] chars = prefix.toCharArray();
        if (chars.length == 0)
        {
            return this;
        }
        int pos = 0;
        TrieNode child = components.get(chars[pos]);
        while (pos < chars.length - 1)
        {
            if (child == null)
            {
                return null;
            }
            else
            {
                pos += 1;
                child = ((TrieIntermediateNode) child).components.get(chars[pos]);
            }
        }
        return (TrieIntermediateNode) child;
    }
    
    public int noWordsUnder()
    {
        int noWords = 0;
        for (Entry<Character, TrieNode> entry : components.entrySet())
        {
            TrieNode t = entry.getValue();
            if (! t.isEnd())
            {
                noWords += ((TrieIntermediateNode) t).noWordsUnder();
            }
            else
            {
                noWords += 1;
            }
        }
        return noWords;
    }
}

class TrieLeaf extends TrieNode
{
    private static final long serialVersionUID = 1L;
    Character c = (char) 127;
    ArrayList<Integer> positions = new ArrayList<Integer>();
    
    public TrieLeaf()
    {
        super();
        setC((char) 127); 
    }
    
    public void addPosition(int pos)
    {
        positions.add(pos);
    }
    
    public boolean isEnd()
    {
        return true;
    }
}
