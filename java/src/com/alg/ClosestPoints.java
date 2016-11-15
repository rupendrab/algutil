package com.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ClosestPoints
{
    Point[] points;
    Random rand = new Random();

    public ClosestPoints(Point[] points)
    {
        super();
        this.points = points;
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
    }
    
    public Point[] sortedByX()
    {
        Point[] pointsX = new Point[points.length];
        for (int i=0; i<points.length; i++)
        {
            pointsX[i] = new Point(points[i].x, points[i].y);
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
            pointsY[i] = new Point(points[i].x, points[i].y);
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

    public Point[] closestPair()
    {
        Point[] Px = sortedByX();
        Point[] Py = sortedByY();
        int from = 0;
        int to = points.length;
        return closestPair(Px, Py, from, to);
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
    
    public Point[] closestPair(Point[] Px, Point[] Py, int from, int to)
    {
        Point[] result = new Point[2];
        // Base case; length of both arrays = 1
        if (to - from == 1) // Only one point, no pair possible
        {
            return null;
        }
        if (to - from == 2) // Return the only pair
        {
            result[0] = Px[from];
            result[1] = Py[from+1];
            return result;
        }
        // There are at least three points in the set
        int mid = (from + to) / 2;
        Point[] resultQ = closestPair(Px, Py, from, mid);
        Point[] resultR = closestPair(Px, Py, mid, to);
        double distQ = resultQ[0].distance(resultQ[1]);
        double distR = resultR[0].distance(resultR[1]);
        double delta = Math.min(distQ, distR);
        Point[] resultS = closestSplitPair(Px, Py, from, to, delta);
        return closer(closer(resultQ, resultR), resultS);
    }
    
    public Point[] closestSplitPair(Point[] Px, Point[] Py, int from, int to, double delta)
    {
        int mid = (from + to) / 2;
        double xBar = Px[mid].x;
        ArrayList<Point> Sy = new ArrayList<Point>();
        for (Point p : Py)
        {
            if (p.x >= xBar - delta && p.x <= xBar + delta)
            {
                Sy.add(p);
            }
        }
        return null;
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

        public double distance(Point other)
        {
            double distance = Math.pow(Math.pow((other.x - x), 2) + Math.pow((other.y - y), 2), 0.5);
            return distance;
        }
        
        @Override
        public String toString()
        {
            return String.format("[%.2f,%.2f]", x, y);
        }
    }

    public static void printPoints(Point[] points)
    {
        for (Point p : points)
        {
            System.out.println(p);
        }
    }
    
    public static void main(String[] args)
    {
        ClosestPoints cp = new ClosestPoints(10, 0, 10, 0, 10);
        int[] closest = cp.computeClosestBruteForce();
        System.out.println(
                String.format("Closest %d %s to %d %s ; distance = %.2f", closest[0], cp.getAt(closest[0]), 
                        closest[1], cp.getAt(closest[1]),
                        cp.getAt(closest[0]).distance(cp.getAt(closest[1]))));
        System.out.println("Original");
        System.out.println("--------");
        printPoints(cp.points);
        System.out.println("P x");
        System.out.println("--------");
        printPoints(cp.sortedByX());
        System.out.println("P y");
        System.out.println("--------");
        printPoints(cp.sortedByY());
    }

}
