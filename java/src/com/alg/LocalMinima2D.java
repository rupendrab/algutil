package com.alg;

import java.util.Arrays;
import java.util.Random;

public class LocalMinima2D
{
    int[][] arr;
    int xlen;
    int ylen;

    public LocalMinima2D(int[][] arr)
    {
        super();
        this.arr = arr;
        if (arr != null)
        {
            ylen = arr.length;
            if (arr[0] != null)
            {
                xlen = arr[0].length;
            }
        }
    }
    
    public String toString()
    {
        int maxWidth = 5;
        if (xlen <= 50 && ylen <= 50)
        {
            String formatStr = "%" + (maxWidth+1) + "d";
            StringBuilder tot = new StringBuilder();
            for (int y=0; y<ylen; y++)
            {
                StringBuilder row = new StringBuilder();
                for (int x=0; x<xlen; x++)
                {
                    row.append(String.format(formatStr, arr[y][x]));
                }
                if (y > 0)
                {
                    tot.append("\r\n");
                }
                tot.append(row);
            }
            return tot.toString();
        }
        else
        {
            return "Too large to print";
        }
    }
    
    public int[] findLocalMinima()
    {
        return findLocalMinima(0, xlen, 0, ylen);
    }
    
    public int[] findMinimum(int startX, int endX, int startY, int endY)
    {
        // System.out.println(String.format("findMinimum: x %d to %d, y %d to %d", startX, endX, startY, endY));
        int[] ret = new int[3];
        boolean first = true;
        for (int y=startY; y<endY; y++)
        {
            for (int x=startX; x<endX; x++)
            {
                if (first)
                {
                    ret[0] = y;
                    ret[1] = x;
                    ret[2] = arr[y][x];
                    first = false;
                }
                else
                {
                    if (arr[y][x] < ret[2])
                    {
                        ret[0] = y;
                        ret[1] = x;
                        ret[2] = arr[y][x];
                    }
                }
            }
        }
        return ret;
    }
    
    public int[] offsetNeighbor(int[] pos, int xOffset, int yOffset)
    {
        int newX = pos[1] + xOffset;
        int newY = pos[0] + yOffset;
        if (newX < 0 || newX >= xlen)
        {
            return null;
        }
        else if (newY < 0 || newY >= ylen)
        {
            return null;
        }
        else
        {
            return new int[] {newY, newX, arr[newY][newX]};
        }
    }
    
    public int[] someLowerNeighbor(int[] pos)
    {
        int[] ret = null;
        int val = arr[pos[0]][pos[1]];
        
        int[] next = offsetNeighbor(pos, -1, 0);
        if (next != null && next[2] < val)
        {
            return next;
        }

        next = offsetNeighbor(pos, 1, 0);
        if (next != null && next[2] < val)
        {
            return next;
        }

        next = offsetNeighbor(pos, 0, -1);
        if (next != null && next[2] < val)
        {
            return next;
        }
        
        next = offsetNeighbor(pos, 0, 1);
        if (next != null && next[2] < val)
        {
            return next;
        }
        
        return ret;
    }
    
    public int[] findLocalMinima(int startX, int endX, int startY, int endY)
    {
        System.out.println(String.format("x = %d to %d, y = %d to %d", startX, endX, startY, endY));
        int midCol = (startX + endX) / 2;
        int midRow = (startY + endY) / 2;
        int[] midRowMin = findMinimum(startX, endX, midRow, midRow+1);
        int[] midColMin = findMinimum(midRow, midRow+1, startY, endY);
        // System.out.println(Arrays.toString(midRowMin));
        // System.out.println(Arrays.toString(midColMin));
        int[] min = midColMin;
        if (midRowMin[2] < midColMin[2])
        {
            min = midRowMin;
        }
        System.out.println("Min is : " + Arrays.toString(min));
        int[] ln = someLowerNeighbor(min); 
        if (ln == null)
        {
            return min;
        }
        System.out.println(Arrays.toString(ln));
        int newStartX = ln[1] > midCol ? ln[1] : startX;
        int newEndX = ln[1] > midCol ? endX : midCol;
        int newStartY = ln[0] > midRow ? ln[0] : startY;
        int newEndY = ln[0] > midRow ? endY : midRow;
        return findLocalMinima(newStartX, newEndX, newStartY, newEndY);
    }
    
    /*
     * Finds a local minima for a 6 x 6 array
     */
    public static void test01()
    {
        int[][] arr = {
                {1,2,3,4,5,6},
                {7,8,9,10,11,12},
                {13,14,15,16,17,18},
                {19,20,21,22,23,24},
                {25,26,27,28,29,30},
                {31,32,33,34,35,36}
        };
        LocalMinima2D lm = new LocalMinima2D(arr);
        System.out.println(lm);
        int[] localMinima = lm.findLocalMinima();
        System.out.println("Local Minima = " + Arrays.toString(localMinima));
    }
    
    /*
     * Creates a random n x n integer matrix with numbers from 1 to n x n and then finds a local minima
     */
    public static void test02(int n)
    {
        int[][] arr = new int[n][n];
        Random rand = new Random();
        int maxVal = n * n;
        for (int y=0; y<n; y++)
        {
            for (int x=0; x<n; x++)
            {
                arr[y][x] = 1 + rand.nextInt(maxVal + 1);
            }
        }
        LocalMinima2D lm = new LocalMinima2D(arr);
        System.out.println(lm);
        int[] localMinima = lm.findLocalMinima();
        System.out.println("Local Minima = " + Arrays.toString(localMinima));
    }
    
    /*
     * Finds a local minima for a 10 x 10 array
     */
    public static void test03()
    {
        int[][] arr = {
                {53,     9,    49,    59,    88,    43,   101,    20,    97,    87},
                {27,    96,    52,    32,    54,    39,    28,    41,    29,    47},
                {47,     9,    43,     8,     5,    14,    80,    63,    52,    96},
                {91,    85,    29,    42,    21,    57,    15,    40,    26,    27},
                {24,    61,    96,    75,    21,    11,    89,    87,    81,    53},
                {39,    66,    29,    68,    43,    32,    83,    38,    68,    34},
                {53,    19,    60,    28,    41,     9,     5,     9,    28,    91},
                {18,    19,    86,    94,    48,    99,    37,    58,    45,    35},
                {58,   100,    12,    78,    42,    45,    58,    13,    75,    15},
                {92,    59,     9,    77,    13,    65,    74,    43,    98,    22}
        };
        LocalMinima2D lm = new LocalMinima2D(arr);
        System.out.println(lm);
        int[] localMinima = lm.findLocalMinima();
        System.out.println("Local Minima = " + Arrays.toString(localMinima));
    }
    public static void main(String[] args)
    {
        // test01();
        test02(10);
        // test03();
    }

}
