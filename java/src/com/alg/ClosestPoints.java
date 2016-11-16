package com.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;

public class ClosestPoints
{
    Point[] points;
    Random rand = new Random();

    private void addPosition()
    {
        if (points == null)
        {
            return;
        }
        for (int i=0; i<points.length; i++)
        {
            points[i].originalPosition = i;
        }
    }
    public ClosestPoints(Point[] points)
    {
        super();
        this.points = points;
        addPosition();
    }
    
    public ClosestPoints(double[][] data)
    {
        points = new Point[data.length];
        for (int i=0; i<data.length; i++)
        {
            points[i] = new Point(data[i][0], data[i][1]);
        }
        addPosition();
    }

    Point getRandomPoint(double minX, double maxX, double minY, double maxY)
    {
        return new Point(minX + rand.nextDouble() * (maxX - minX), minY + rand.nextDouble() * (maxY - minY));
    }

    public ClosestPoints(int count, double minX, double maxX, double minY, double maxY)
    {
        points = new Point[count];
        for (int i = 0; i < count; i++)
        {
            points[i] = getRandomPoint(minX, maxX, minY, maxY);
        }
        addPosition();
    }
    
    public Point[] sortedByX()
    {
        Point[] pointsX = new Point[points.length];
        for (int i=0; i<points.length; i++)
        {
            pointsX[i] = new Point(points[i].x, points[i].y, points[i].originalPosition);
        }
        Arrays.sort(pointsX, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2)
            {
                if (p1.x > p2.x)
                {
                    return 1;
                }
                else if (p1.x < p2.x)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
        return pointsX;
    }

    public Point[] sortedByY()
    {
        Point[] pointsY = new Point[points.length];
        for (int i=0; i<points.length; i++)
        {
            pointsY[i] = new Point(points[i].x, points[i].y, points[i].originalPosition);
        }
        Arrays.sort(pointsY, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2)
            {
                if (p1.y > p2.y)
                {
                    return 1;
                }
                else if (p1.y < p2.y)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
        return pointsY;
    }
    
    public void printPointsWithPosition(Point[] data)
    {
        printPointsWithPosition(data, null);
    }
    
    public void printPointsWithPosition(Point[] data, String prefix)
    {
        StringBuilder sb = new StringBuilder();
        if (prefix != null)
        {
            sb.append(prefix + ": ");
        }
        if (data != null && data.length > 0)
        {
            sb.append("{");
            int i = 0;
            for (Point p: data)
            {
                if (i > 0)
                {
                    sb.append(", ");
                }
                sb.append("{");
                sb.append(p.x + ", " + p.y + " : " + p.originalPosition);
                sb.append("}");
                i++;
            }
            sb.append("}");
        }
        System.out.println(sb.toString());
    }
    
    public void getLeftAndRightSortedArrays(Point[] sortedPoints, Point[] sortedLeft, Point[] sortedRight, int from, int to)
    {
        // System.out.println("In getLeftAndRightSortedArrays");
        // printPointsWithPosition(sortedPoints);
        // System.out.println(sortedLeft.length + " : " + sortedRight.length);
        int leftPos = 0;
        int rightPos = 0;
        int mid = (from + to) / 2;
        for (Point p : sortedPoints)
        {
            if (p.originalPosition < mid)
            {
                Point np = new Point(p.x, p.y, p.originalPosition);
                sortedLeft[leftPos++] = np;
            }
            else
            {
                Point np = new Point(p.x, p.y, p.originalPosition);
                sortedRight[rightPos++] = np;
            }
        }
    }

    public Point[] closestPair()
    {
        // printPointsWithPosition(points, "P");
        Point[] Px = sortedByX();
        points = Px;
        addPosition();
        Point[] Py = sortedByY();
        // System.out.println("Px = " + Arrays.toString(Px));
        // System.out.println("Py = " + Arrays.toString(Py));
        return closestPair(Px, Py, 0, Px.length);
    }

    public Point[] closer(Point[] set1, Point[] set2)
    {
        if (set1 == null || set1.length == 0)
        {
            return set2;
        }
        if (set2 == null || set2.length == 0)
        {
            return set1;
        }
        double dist1 = set1[0].distance(set1[1]);
        double dist2 = set2[0].distance(set2[1]);
        if (dist1 <= dist2)
        {
            return set1;
        }
        else
        {
            return set2;
        }
    }
    
    public Point[] closestPairBF(Point[] Px)
    {
        if (Px.length <= 1)
        {
            return null;
        }
        double minDistance = -1;
        Point[] closestPoints = new Point[2];
        for (int i=0; i<Px.length; i++)
        {
            for (int j=i+1; j<Px.length; j++)
            {
                double distance = Px[i].distance(Px[j]);
                if (minDistance == -1)
                {
                    minDistance  = distance;
                    closestPoints[0] = Px[i];
                    closestPoints[1] = Px[j];
                }
                else if (distance < minDistance)
                {
                    minDistance = distance;
                    closestPoints[0] = Px[i];
                    closestPoints[1] = Px[j];
                }
            }
        }
        // printPointsWithPosition(closestPoints, "closest");
        // System.out.println("Distance = " + closestPoints[0].distance(closestPoints[1]));
        return closestPoints;
    }
    
    public Point[] closestPair(Point[] Px, Point[] Py, int from, int to)
    {
        // printPointsWithPosition(Px, "Px");
        // printPointsWithPosition(Py, "Py");
        // Base case; length of both arrays = 1
        if (Px.length <= 3) // # 3 or less, use brute force
        {
            return closestPairBF(Px);
        }
        // There are at four or more points in each set
        int mid = Px.length / 2;
        Point[] Qx = new Point[mid];
        Point[] Rx = new Point[Px.length - mid];
        Point[] Qy = new Point[mid];
        Point[] Ry = new Point[Px.length - mid];
        getLeftAndRightSortedArrays(Px, Qx, Rx, from, to);
        getLeftAndRightSortedArrays(Py, Qy, Ry, from, to);
        // printPointsWithPosition(Qx, "Qx");
        // printPointsWithPosition(Qy, "Qy");
        // printPointsWithPosition(Rx, "Rx");
        // printPointsWithPosition(Ry, "Ry");
        int newMid = (from + to) / 2;
        Point[] resultQ = closestPair(Qx, Qy, from, newMid);
        Point[] resultR = closestPair(Rx, Ry, newMid, to);
        double distQ = resultQ[0].distance(resultQ[1]);
        double distR = resultR[0].distance(resultR[1]);
        double delta = Math.min(distQ, distR);
        Point[] resultS = closestSplitPair(Px, Py, delta);
        return closer(closer(resultQ, resultR), resultS);
    }
    
    public Point[] closestSplitPair(Point[] Px, Point[] Py, double delta)
    {
        // System.out.println("In closestSplitPair");
        // printPointsWithPosition(Px, "Px");
        // printPointsWithPosition(Py, "Py");
        // System.out.println("delta = " + delta);
        int mid = Px.length / 2;
        double xBar = Px[mid-1].x;
        // System.out.println("xBar = " + xBar);
        ArrayList<Point> Sy = new ArrayList<Point>();
        for (Point p : Py)
        {
            if (p.x >= xBar - delta && p.x <= xBar + delta)
            {
                Sy.add(p);
            }
        }
        // System.out.println("Size of Sy = " + Sy.size());
        Point[] resultS = null;
        double minDistance = delta;
        for (int i=0; i<Sy.size(); i++)
        {
            Point p1 = Sy.get(i);
            int remaining = Sy.size() - i;
            remaining = Math.min(remaining, 7);
            for (int j=1; j<remaining; j++)
            {
                Point p2 = Sy.get(i+j);
                double distance = p1.distance(p2);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    resultS = new Point[]{p1,p2};
                }
            }
        }
        return resultS;
    }
    
    public int[] computeClosestBruteForce()
    {
        int[] pair = new int[2];
        double distance = -1;
        for (int i = 0; i < points.length; i++)
        {
            for (int j = i + 1; j < points.length; j++)
            {
                double newDistance = points[i].distance(points[j]);
                if (distance == -1)
                {
                    distance = newDistance;
                }
                else if (newDistance < distance)
                {
                    distance = newDistance;
                    pair[0] = i;
                    pair[1] = j;
                }
            }
        }
        return pair;
    }

    public Point getAt(int pos)
    {
        return points[pos];
    }

    static class Point
    {
        double x;
        double y;
        int originalPosition;

        public Point()
        {
            super();
        }

        public Point(double x, double y)
        {
            super();
            this.x = x;
            this.y = y;
        }

        public Point(double x, double y, int originalPosition)
        {
            super();
            this.x = x;
            this.y = y;
            this.originalPosition = originalPosition;
        }
        
        public double distance(Point other)
        {
            double distance = Math.pow(Math.pow((other.x - x), 2) + Math.pow((other.y - y), 2), 0.5);
            return distance;
        }
        
        @Override
        public String toString()
        {
            return String.format("{%.2f,%.2f}", x, y);
        }
    }

    public static void printPoints(Point[] points)
    {
        for (Point p : points)
        {
            System.out.println(p);
        }
    }
    
    public static void testRandom()
    {
        ClosestPoints cp = new ClosestPoints(10000, 0, 10000, 0, 10000);
        if (cp.points.length <= 100)
        {
            System.out.println(Arrays.toString(cp.points));
        }
        if (! test_cp(cp))
        {
            System.out.println("Failed");
        }
    }
    
    public static boolean test_cp(ClosestPoints cp)
    {
        long start = Calendar.getInstance().getTimeInMillis();
        int[] closest = cp.computeClosestBruteForce();
        long end = Calendar.getInstance().getTimeInMillis();
        long time1 = end - start;
        System.out.println(
                String.format("Closest %s to %s ; distance = %.2f", cp.getAt(closest[0]), cp.getAt(closest[1]),
                        cp.getAt(closest[0]).distance(cp.getAt(closest[1]))));
        double dist1 = cp.getAt(closest[0]).distance(cp.getAt(closest[1]));
        System.out.println(String.format("Time Brute force = %d ms", time1));
        
        start = Calendar.getInstance().getTimeInMillis();
        Point[] closestPoints = cp.closestPair();
        end = Calendar.getInstance().getTimeInMillis();
        long time2 = end - start;
        if (closestPoints != null && closestPoints.length == 2)
        {
            System.out.println(String.format("Closest %s to %s ; distance = %.2f", closestPoints[0], closestPoints[1],
                    closestPoints[0].distance(closestPoints[1])));
        }
        double dist2 = closestPoints[0].distance(closestPoints[1]);
        // System.out.println(dist1 * 100 + ": " + dist2 * 100);
        System.out.println(String.format("Time Divide and Conquer = %d ms", time2));
        return (int) (dist1 * 100) == (int) (dist2 * 100);
    }
    
    public static void test_specific_01()
    {
        double[][] data = {{7.79,0.89}, {8.35,1.75}, {0.92,5.28}, {0.32,5.20}, {8.64,1.72}, {4.82,0.93}, {6.66,9.30}, {3.12,9.13}, {8.94,3.95}, {2.87,4.85}};
        ClosestPoints cp = new ClosestPoints(data);
        test_cp(cp);
    }
    
    public static void test_specific_02()
    {
        double[][] data = {{2.29,3.68}, {6.05,5.87}, {3.15,6.64}, {1.79,5.91}, {8.86,3.85}, {8.13,9.87}, {4.51,2.18}, {0.93,3.13}, {2.80,5.23}, {9.71,4.23}};
        ClosestPoints cp = new ClosestPoints(data);
        test_cp(cp);
    }

    public static void test_specific_03()
    {
        double[][] data = {{0.04,4.06}, {4.09,5.87}, {5.30,3.99}, {6.10,6.65}, {2.97,5.66}, {9.07,6.98}, {3.42,5.84}, {1.25,8.75}, {4.93,3.00}, {9.41,4.58}};
        ClosestPoints cp = new ClosestPoints(data);
        test_cp(cp);
    }

    public static void main(String[] args)
    {
        // test_specific_03();
        testRandom();
    }

}
