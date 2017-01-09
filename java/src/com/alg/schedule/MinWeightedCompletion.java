package com.alg.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class MinWeightedCompletion
{
    int noJobs;
    Job[] jobs;
    
    public MinWeightedCompletion()
    {
        super();
    }
    
    public MinWeightedCompletion(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String line = null;
        int lineNo = 0;
        while ((line = reader.readLine()) != null)
        {
            lineNo++;
            if (lineNo == 1)
            {
                noJobs = Integer.parseInt(line.trim());
                jobs = new Job[noJobs];
            }
            else
            {
                if (! line.trim().equals(""))
                {
                    String[] fields= line.split("\\s+");
                    if (fields != null && fields.length == 2)
                    {
                        int jobWeight = Integer.parseInt(fields[0]);
                        int jobLength = Integer.parseInt(fields[1]);
                        jobs[lineNo - 2] = new Job(lineNo - 2, jobWeight, jobLength);
                    }
                }
            }
        }
        reader.close();
    }
    
    public void arrange()
    {
        Arrays.sort(jobs, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2)
            {
                double ratio1 = o1.getJobWeight() * 1.0 / o1.getJobLength();
                double ratio2 = o2.getJobWeight() * 1.0 / o2.getJobLength();
                if (ratio2 > ratio1)
                {
                    return 1;
                }
                else if (ratio2 < ratio1)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        });
    }
    
    public void arrangeByDiff()
    {
        Arrays.sort(jobs, new Comparator<Job>() {

            @Override
            public int compare(Job o1, Job o2)
            {
                int diff1 = o1.getJobWeight() - o1.getJobLength();
                int diff2 = o2.getJobWeight() - o2.getJobLength();
                if (diff1 == diff2)
                {
                    return o2.getJobWeight() - o1.getJobWeight();
                }
                else
                {
                    return diff2 - diff1;
                }
            }
        });
    }
    
    public long weightedCompletion()
    {
        long result = 0;
        long completionTime = 0;
        for (Job job : jobs)
        {
            completionTime += job.getJobLength();
            result += completionTime * job.getJobWeight();
        }
        return result;
    }
    
    public static void test01(String fileName) throws IOException
    {
        MinWeightedCompletion mwc = new MinWeightedCompletion(fileName);
        System.out.println("Weighted completion (Original) = " + mwc.weightedCompletion());
        mwc.arrangeByDiff();
        System.out.println("Weighted completion (Sorted by difference) = " + mwc.weightedCompletion());
        // 69119377652 Reported correct
        mwc.arrange();
        // 68344714323 Reported wrong
        // 67311454237
        System.out.println("Weighted completion (Sorted by ratio) = " + mwc.weightedCompletion());
    }
    
    public static void main(String[] args) throws Exception
    {
        test01("C:\\Users\\rubandyopadhyay\\Downloads\\jobs.txt");
    }

}
