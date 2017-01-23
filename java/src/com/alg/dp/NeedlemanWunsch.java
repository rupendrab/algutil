package com.alg.dp;

public class NeedlemanWunsch
{
    int penaltyGap = 10;
    int penaltyMismatch = 15;
    String value1;
    String value2;
    int[][] scoreMatrix;
    int m;
    int n;
    
    public NeedlemanWunsch(String value1, String value2)
    {
        super();
        this.value1 = value1;
        this.value2 = value2;
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
        char[] c1 = value1.toCharArray();
        char[] c2 = value2.toCharArray();
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
    }
    
    public static void main(String[] args)
    {
        test01("GCATGCU", "GATTACA");
        test01("aadarsh", "aasish");
    }

}
