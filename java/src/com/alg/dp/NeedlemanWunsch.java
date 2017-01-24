package com.alg.dp;

import java.util.ArrayList;
import java.util.LinkedList;

public class NeedlemanWunsch
{
    int penaltyGap = 10;
    int penaltyMismatch = 20;
    String value1;
    String value2;
    char[] c1;
    char[] c2;
    int[][] scoreMatrix;
    int m;
    int n;
    
    public NeedlemanWunsch(String value1, String value2)
    {
        super();
        this.value1 = value1;
        this.value2 = value2;
        this.c1 = value1.toCharArray();
        this.c2 = value2.toCharArray();
        m = value1.length();
        n = value2.length();
        scoreMatrix = new int[m + 1][n + 1];
        solve();
    }
    
    public void solve()
    {
        for (int i=0; i<m+1; i++)
        {
            scoreMatrix[i][0] = i * penaltyGap;
        }
        for (int j=0; j<n+1; j++)
        {
            scoreMatrix[0][j] = j * penaltyGap;
        }
        for (int i=1; i<=m; i++)
        {
            for (int j=1; j<=n; j++)
            {
                int penaltyChars = 0;
                if (c1[i-1] != c2[j-1])
                {
                    penaltyChars = penaltyMismatch;
                }
                int possibility1 = scoreMatrix[i-1][j-1] + penaltyChars;
                int possibility2 = scoreMatrix[i-1][j] + penaltyGap;
                int possibility3 = scoreMatrix[i][j-1] + penaltyGap;
                int bestPossibility = Math.min(possibility3, Math.min(possibility1, possibility2));
                scoreMatrix[i][j] = bestPossibility;
            }
        }
    }
    
    public void printMatrix()
    {
        if (scoreMatrix == null)
        {
            return;
        }
        System.out.println();
        for (int i=0; i<m+1; i++)
        {
            for (int j=0; j<n+1; j++)
            {
                System.out.print(String.format("%4d", scoreMatrix[i][j]));
            }
            System.out.println();
        }
    }
    
    public String convertToString(LinkedList<Integer> data)
    {
        char[] ret = new char[data.size()];
        for (int i=0; i<data.size(); i++)
        {
            ret[i] = (char) data.get(i).intValue();
        }
        return new String(ret);
    }
    
    public ArrayList<String> showMatches()
    {
        ArrayList<String> ret = new ArrayList<>();
        if (scoreMatrix == null)
        {
            return ret;
        }
        LinkedList<Integer> value1M = new LinkedList<>();
        LinkedList<Integer> value2M = new LinkedList<>();
        
        int i = m;
        int j = n;
        while (i >= 1 && j >= 1)
        {
            int penaltyChars = 0;
            if (c1[i-1] != c2[j-1])
            {
                penaltyChars = penaltyMismatch;
            }
            int presentVal = scoreMatrix[i][j];
            int possibility1 = scoreMatrix[i-1][j-1] + penaltyChars;
            int possibility2 = scoreMatrix[i-1][j] + penaltyGap;
            int possibility3 = scoreMatrix[i][j-1] + penaltyGap;
            if (presentVal == possibility1)
            {
                value1M.addFirst((int) c1[i-1]); 
                value2M.addFirst((int) c2[j-1]); 
                i--;
                j--;
            }
            else if (presentVal == possibility2)
            {
                value1M.addFirst((int) c1[i-1]); 
                value2M.addFirst(32); 
                i--;
            }
            else if (presentVal == possibility3)
            {
                value1M.addFirst(32); 
                value2M.addFirst((int) c2[j-1]); 
                j--;
            }
        }
        
        ret.add(convertToString(value1M));
        ret.add(convertToString(value2M));
        return ret;
    }

    public int getPenaltyGap()
    {
        return penaltyGap;
    }

    public void setPenaltyGap(int penaltyGap)
    {
        this.penaltyGap = penaltyGap;
    }

    public int getPenaltyMismatch()
    {
        return penaltyMismatch;
    }

    public void setPenaltyMismatch(int penaltyMismatch)
    {
        this.penaltyMismatch = penaltyMismatch;
    }

    public String getValue1()
    {
        return value1;
    }

    public String getValue2()
    {
        return value2;
    }
    
    public static void test01(String v1, String v2)
    {
        NeedlemanWunsch nm = new NeedlemanWunsch(v1, v2);
        nm.printMatrix();
        ArrayList<String> matches = nm.showMatches();
        System.out.println(matches.get(0));
        System.out.println(matches.get(1));
    }
    
    public static void main(String[] args)
    {
        test01("GCATGCU", "GATTACA");
        test01("aadarsh", "aasish");
    }

}
