package com.alg;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

public class Matrix
{
  BigDecimal[][] data;
  int xMax;
  int yMax;
  
  public Matrix(BigDecimal[][] data)
  {
      this.data = data;
      this.xMax = data[0].length;
      this.yMax = data.length;
  }
  
  public Matrix(BigDecimal[] data, int xMax, int yMax)
  {
      assert(data.length == xMax * yMax);
      this.data = new BigDecimal[xMax][yMax];
      for (int i=0; i<xMax; i++)
      {
          for (int j=0; j<yMax; j++)
          {
              this.data[i][j] = data[i * yMax + j];
          }
      }
      this.xMax = xMax;
      this.yMax = yMax;
  }
 
  public BigDecimal getValue(int x, int y)
  {
      return data[x][y]; 
  }
  
  @Override
  public String toString()
  {
      StringBuilder sb = new StringBuilder(4 * xMax * yMax);
      int maxLen = 0;
      for (int i=0; i<xMax; i++)
      {
          for (int j=0; j<yMax; j++)
          {
              maxLen = Math.max(maxLen, data[i][j].toString().length());
          }
      }
      String fmt = "%-" + (maxLen+2) + "s";
      for (int i=0; i<xMax; i++)
      {
          for (int j=0; j<yMax; j++)
          {
              sb.append(String.format(fmt, data[i][j].toString()));
          }
          sb.append("\n");
      }
      return sb.toString();
  }
  
  private BigDecimal compute(Matrix m1, Matrix m2, int i, int j)
  {
      BigDecimal result = new BigDecimal(0);
      for (int counter = 0; counter < m1.yMax; counter++)
      {
          result = result.add(m1.getValue(i, counter).multiply(m2.getValue(counter, j))); 
      }
      return result;
  }
  
  public Matrix multiply(Matrix that) throws Exception
  {
      if (this.yMax != that.xMax)
      {
          throw new Exception(String.format("Cannot multply matrix %d x %s by %d x %d", this.xMax, this.yMax, that.xMax, that.yMax));
      }
      BigDecimal[][] data2 = new BigDecimal[this.xMax][that.yMax];
      for (int i=0; i<this.xMax; i++)
      {
          for (int j=0; j<that.yMax; j++)
          {
              data2[i][j] = compute(this, that, i, j);
          }
      }
      return new Matrix(data2);
  }
  
  public Matrix powerBruteForce(int n) throws Exception
  {
      int noMultiply = 0;
      if (xMax != yMax)
      {
          throw new Exception("Can only raise a square matrix to power of n");
      }
      if (n == 0 || n == 1)
      {
          return this;
      }
      Matrix startMatrix = this;
      for (int i=2; i<=n; i++)
      {
          startMatrix = startMatrix.multiply(this);
          noMultiply += 1;
      }
      System.out.println(String.format("Number of multiplications in brute force power of %d = %d", n, noMultiply));
      return startMatrix;
  }

  public Matrix power(int n) throws Exception
  {
      int noMultiply = 0;
      if (xMax != yMax)
      {
          throw new Exception("Can only raise a square matrix to power of n");
      }
      Matrix startMatrix = null;
      Matrix soFar = this;
      int newn = n;
      while (newn > 1)
      {
          int rem = newn % 2;
          newn = newn / 2;
          if (rem == 1)
          {
              if (startMatrix == null)
              {
                  startMatrix = soFar;
              }
              else
              {
                  startMatrix = startMatrix.multiply(soFar); 
                  noMultiply += 1;
              }
          }
          soFar = soFar.multiply(soFar);
          noMultiply += 1;
      }
      if (newn == 1)
      {
          if (startMatrix == null)
          {
              startMatrix = soFar;
          }
          else
          {
              startMatrix = startMatrix.multiply(soFar); 
              noMultiply += 1;
          }
      }
      System.out.println(String.format("Number of multiplications in power of %d = %d", n, noMultiply));
      return startMatrix;
  }
  
  public static BigDecimal[] fromIntArray(int[] arr)
  {
      BigDecimal[] ret = new BigDecimal[arr.length];
      for (int i=0; i<arr.length; i++)
      {
          ret[i] = new BigDecimal(arr[i]);
      }
      return ret;
  }
  
  @Override
  public boolean equals(Object other)
  {
      if (other == null)
      {
          return false;
      }
      if (! (other instanceof Matrix))
      {
          return false;
      }
      Matrix m2 = (Matrix) other;
      if (this.xMax != m2.xMax)
      {
          return false;
      }
      if (this.yMax != m2.yMax)
      {
          return false;
      }
      for (int i=0; i<xMax; i++)
      {
          for (int j=0; j<yMax; j++)
          {
              if (! data[i][j].equals(m2.getValue(i, j)))
              {
                  return false;
              }
          }
      }
      return true;
  }
  
  public static void powerTest() throws Exception
  {
      Matrix m1 = new Matrix(fromIntArray(new int[] {0,1,1,1}), 2, 2);
      // System.out.println(m1);
      System.out.println(m1.power(1));
      System.out.println(m1.power(2));
      System.out.println(m1.power(3));
      System.out.println(m1.power(4));
      System.out.println(m1.power(5));
      System.out.println(m1.power(6));
      System.out.println(m1.power(7));
      long start = 0;
      long end = 0;
      
      int power = 2017;

      start = Calendar.getInstance().getTimeInMillis();
      Matrix m1_200 = m1.power(power);
      end = Calendar.getInstance().getTimeInMillis();
      long t1 = end - start;
      System.out.println(String.format("Time for power = %d ms", t1));

      start = Calendar.getInstance().getTimeInMillis();
      Matrix m1_200_2 = m1.powerBruteForce(power);
      end = Calendar.getInstance().getTimeInMillis();
      long t2 = end - start;
      System.out.println(String.format("Time for brute force power = %d ms", t2));

      System.out.println(m1_200.equals(m1_200_2));
      System.out.println(m1_200);
      System.out.println(m1_200_2);
  }
  
  public static void main(String[] args) throws Exception
  {
      Random rand = new Random();
      BigDecimal[] data = new BigDecimal[6];
      for (int i=0; i<data.length; i++)
      {
          data[i] =new BigDecimal(rand.nextInt(10));
      }
      // System.out.println(Arrays.toString(data));
      Matrix m = new Matrix(data, 2, 3);
      // System.out.println(m);
      // System.out.println(m.getValue(5, 4));
      BigDecimal[] data2 = new BigDecimal[6];
      for (int i=0; i<data2.length; i++)
      {
          data2[i] =new BigDecimal(rand.nextInt(10));
      }
      Matrix m2 = new Matrix(data2, 3, 2);
      // System.out.println(m2);
      // System.out.println(m.multiply(m2));
      powerTest();
  }

}
