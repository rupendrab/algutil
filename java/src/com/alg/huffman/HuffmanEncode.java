package com.alg.huffman;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;

import com.alg.datastructures.MinHeap;

public class HuffmanEncode
{
    Hashtable<Integer, Integer> characterFrequency = new Hashtable<>();
    String referenceFileName;
    ArrayList<Integer> charactersOrderedByFrequency = new ArrayList<>();
    HuffmanNode rootNode;
    
    public HuffmanEncode()
    {
        super();
    }
    
    
    public HuffmanEncode(String referenceFileName) throws IOException
    {
        super();
        this.referenceFileName = referenceFileName;
        computeCharacterFrequency();
        rootNode = computeTree();        
    }
    
    public HuffmanEncode(Hashtable<Integer, Integer> characterFrequency)
    {
        this.characterFrequency = characterFrequency;
        sortCharacters();
        rootNode = computeTree();
    }

    public void computeCharacterFrequency() throws IOException
    {
        characterFrequency = new Hashtable<>();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(referenceFileName));
        int c;
        while ((c = reader.read()) != -1)
        {
            Integer freq = characterFrequency.get(c);
            if (freq == null)
            {
                characterFrequency.put(c, 1);
            }
            else
            {
                characterFrequency.put(c, freq+1);
            }
        }
        reader.close();
        sortCharacters();
    }
    
    public void sortCharacters()
    {
        charactersOrderedByFrequency = new ArrayList<>(characterFrequency.keySet());
        Collections.sort(charactersOrderedByFrequency, new Comparator<Integer>() {

            @Override
            public int compare(Integer ch1, Integer ch2)
            {
                return characterFrequency.get(ch1) - characterFrequency.get(ch2);
            }
        });   
    }
    
    public HuffmanNode getMinimum(LinkedList<Integer> nodes, LinkedList<HuffmanNode> mergedNodes)
    {
        Integer bestNode = nodes.peek();
        int weightOfBestNode = 0;
        if (bestNode != null)
        {
            weightOfBestNode = characterFrequency.get(bestNode);
        }
        HuffmanNode bestMergedNode = mergedNodes.peek();
        if (bestNode == null)
        {
            mergedNodes.poll();
            return bestMergedNode;
        }
        else if (bestMergedNode == null || weightOfBestNode <= bestMergedNode.weight)
        {
            nodes.poll();
            return new HuffmanNode(bestNode, weightOfBestNode);
        }
        else
        {
            mergedNodes.poll();
            return bestMergedNode;
        }
    }
    
    public HuffmanNode getMinimumHeap(LinkedList<Integer> nodes, MinHeap<HuffmanNode> mergedNodes)
    {
        Integer bestNode = nodes.peek();
        // System.out.println(String.format("Best Node: %d, weight = %d", bestNode, characterFrequency.get(bestNode)));
        int weightOfBestNode = 0;
        if (bestNode != null)
        {
            weightOfBestNode = characterFrequency.get(bestNode);
        }
        HuffmanNode bestMergedNode = mergedNodes.first();
        if (bestNode == null)
        {
            mergedNodes.takeFirst();
            return bestMergedNode;
        }
        else if (bestMergedNode == null || weightOfBestNode <= bestMergedNode.weight)
        {
            nodes.poll();
            return new HuffmanNode(bestNode, weightOfBestNode);
        }
        else
        {
            mergedNodes.takeFirst();
            return bestMergedNode;
        }
    }
    
    public HuffmanNode computeTree()
    {
        LinkedList<Integer> nodes = new LinkedList<>(charactersOrderedByFrequency);
        LinkedList<HuffmanNode> mergedNodes = new LinkedList<>();
        if (nodes.size() == 1)
        {
            Integer ch = nodes.peek();
            return new HuffmanNode(ch, characterFrequency.get(ch));
        }
        HuffmanNode first = getMinimum(nodes, mergedNodes);
        HuffmanNode second = getMinimum(nodes, mergedNodes);
        HuffmanNode root = null;
        while (first != null && second != null)
        {
            root = first.merge(second);
            mergedNodes.add(root);
            first = getMinimum(nodes, mergedNodes);
            second = getMinimum(nodes, mergedNodes);
        }
        return root;
    }
    
    public HuffmanNode computeTreeHeap()
    {
        LinkedList<Integer> nodes = new LinkedList<>(charactersOrderedByFrequency);
        MinHeap<HuffmanNode> mergedNodes = new MinHeap<>(new HuffmanNode[] {});
        if (nodes.size() == 1)
        {
            Integer ch = nodes.peek();
            return new HuffmanNode(ch, characterFrequency.get(ch));
        }
        HuffmanNode first = getMinimumHeap(nodes, mergedNodes);
        HuffmanNode second = getMinimumHeap(nodes, mergedNodes);
        HuffmanNode root = null;
        while (first != null && second != null)
        {
            root = first.merge(second);
            mergedNodes.insertItem(root);
            first = getMinimumHeap(nodes, mergedNodes);
            second = getMinimumHeap(nodes, mergedNodes);
        }
        return root;
    }
    
    public BitRepresentation encode(String someData)
    {
        return rootNode.getPath(someData);
    }
    
    public String decode(BitRepresentation compressed)
    {
        return rootNode.decode(compressed);
    }
    
    public static void test01(String fileName) throws IOException
    {
        HuffmanEncode encoder = new HuffmanEncode(fileName);
        for (int ch : encoder.charactersOrderedByFrequency)
        {
            System.out.println(ch + ": " + encoder.characterFrequency.get(ch));
        }
        System.out.println(encoder.rootNode);
    }
    
    public static void test02(String fileName) throws IOException
    {
        HuffmanEncode encoder = new HuffmanEncode(fileName);
        String data = "Insurance team to work on Inauguration Day";
        BitRepresentation encoded = encoder.encode(data);
        System.out.println(encoded);
        System.out.println(encoder.decode(encoded));
    }
    
    public static void main(String[] args) throws Exception
    {
        // test01("C:\\Users\\rubandyopadhyay\\Downloads\\huffman_01.txt");
        test02("C:\\Users\\rubandyopadhyay\\Downloads\\huffman_01.txt");
    }

}
