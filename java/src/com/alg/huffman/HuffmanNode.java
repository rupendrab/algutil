package com.alg.huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;

public class HuffmanNode implements Comparable<HuffmanNode>
{
    HashSet<Integer> nodes = new HashSet<>();
    HuffmanNode left;
    HuffmanNode right;
    int weight;
    
    public HuffmanNode()
    {
        super();
    }

    public HuffmanNode(int ch, int weight)
    {
        nodes.add(ch);
        this.weight = weight;
    }
    
    public boolean isLeaf()
    {
        return (left == null) && (right == null);
    }
    
    public int getChar()
    {
        if (isLeaf())
        {
            for (Integer ch : nodes)
            {
                return ch;
            }
        }
        return -1;
    }
    
    public boolean charInTree(int ch)
    {
        return nodes.contains(ch);
    }
    
    public boolean charInLeftTree(int ch)
    {
        if (left != null && left.nodes.contains(ch))
        {
            return true;
        }
        return false;
    }
    
    public boolean charInRightTree(int ch)
    {
        if (right != null && right.nodes.contains(ch))
        {
            return true;
        }
        return false;
    }
    
    public ArrayList<Boolean> getPathTo(int ch)
    {
        return getPathTo(ch, new ArrayList<>());
    }
    
    public ArrayList<Boolean> getPathTo(int ch, ArrayList<Boolean> start)
    {
        // System.out.println(String.format("  Node = %s, start = %s", this, start));
        if (nodes.contains(ch))
        {
            if (charInLeftTree(ch))
            {
                // System.out.println(String.format("  In left tree"));
                start.add(false);
                return left.getPathTo(ch, start);
            }
            else if (charInRightTree(ch))
            {
                // System.out.println(String.format("  In right tree"));
                start.add(true);
                return right.getPathTo(ch, start);
            }
            else
            {
                // System.out.println("  In Leaf");
                return start;
            }
        }
        return new ArrayList<Boolean>();
    }
    
    public void getPath(BitRepresentation given, int ch)
    {
        ArrayList<Boolean> path = getPathTo(ch);
        for (int i=0; i<path.size(); i++)
        {
            given.writeNext(path.get(i));
        }
    }
    
    public BitRepresentation getPath(String data)
    {
        BitRepresentation calculated = new BitRepresentation();
        for (int ch : data.toCharArray())
        {
            getPath(calculated, ch);
        }
        return calculated;
    }
    
    public String decode(BitRepresentation data)
    {
        StringBuilder sb = new StringBuilder();
        int pointer = 0;
        HuffmanNode node = this;
        while (pointer < data.getWriteStart())
        {
            boolean val = data.getValueAtPosition(pointer++);
            if (val)
            {
                node = node.right;
            }
            else
            {
                node = node.left;
            }
            if (node.isLeaf())
            {
                sb.append((char) node.getChar());
                node = this;
            }
        }
        return sb.toString();
    }
    
    public static String getStringFromBitSet(BitSet data)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=data.size()-1; i>=0; i--)
        {
            sb.append(data.get(i) ? '1' : '0');
        }
        return sb.toString();
    }
    
    public HuffmanNode merge(HuffmanNode other)
    {
        HuffmanNode parent = new HuffmanNode();
        parent.left = this;
        parent.right = other;
        parent.nodes.addAll(nodes);
        parent.nodes.addAll(other.nodes);
        parent.weight = weight + other.weight;
        return parent;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (left != null)
        {
            sb.append("[" + left + "] ");
        }
        sb.append(nodes);
        sb.append(" : (" + weight + ")");
        if (right != null)
        {
            sb.append(" [" + right + "]");
        }
        sb.append("}");
        return sb.toString();
    }
    
    public static void test01()
    {
        // Test with Bitset
        BitSet bs = new BitSet();
        bs.set(200);
        bs.set(201);
        bs.set(0);
        bs.set(63);
        System.out.println(bs);
        System.out.println(Arrays.toString(bs.toByteArray()));
        System.out.println(Arrays.toString(bs.toLongArray()));
        System.out.println(bs.size());
        StringBuilder sb = new StringBuilder();
        for (int i=bs.size(); i>=0; i--)
        {
            sb.append(bs.get(i) ? "1" : "0");
        }
        System.out.println(sb);
        BitSet bs2 = BitSet.valueOf(bs.toLongArray());
        System.out.println(bs2);
        bs.clear();
        bs.set(2);
        BitSet bs_times_2 = bs.get(1, bs.length());
        System.out.println(Arrays.toString(bs.toLongArray()));
        System.out.println(Arrays.toString(bs_times_2.toLongArray()));
        System.out.println(Long.MAX_VALUE);
    }
    
    public static void test02()
    {
        HuffmanNode A = new HuffmanNode('A', 17);
        HuffmanNode B = new HuffmanNode('B', 7);
        HuffmanNode C = new HuffmanNode('C', 5);
        HuffmanNode D = new HuffmanNode('D', 4);
        HuffmanNode E = new HuffmanNode('E', 3);
        HuffmanNode F = new HuffmanNode('F', 2);
        HuffmanNode G = new HuffmanNode('G', 1);
        HuffmanNode GF = G.merge(F);
        HuffmanNode GFE = GF.merge(E);
        HuffmanNode CD = C.merge(D);
        HuffmanNode GFEB = GFE.merge(B);
        HuffmanNode GFEBCD = GFEB.merge(CD);
        HuffmanNode root = GFEBCD.merge(A);
        System.out.println(root);
        System.out.println(root.nodes);
        System.out.println(root.getPathTo('A'));
        System.out.println(root.getPathTo('B'));
        System.out.println(root.getPathTo('C'));
        System.out.println(root.getPathTo('D'));
        System.out.println(root.getPathTo('E'));
        System.out.println(root.getPathTo('F'));
        System.out.println(root.getPathTo('G'));
        System.out.println(root.getPathTo('H'));
        // System.out.println(Arrays.toString(root.getPath('F')));
        BitRepresentation calc = root.getPath("BADE");
        System.out.println(calc);
        System.out.println("Decoded = " + root.decode(calc));
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01();
        test02();
    }

    @Override
    public int compareTo(HuffmanNode o)
    {
        return weight - o.weight;
    }
}
