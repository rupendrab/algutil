package com.alg.schedule;

public class Job
{
    int jobID;
    int jobWeight;
    int jobLength;
    
    public Job()
    {
        super();
    }

    public Job(int jobID, int jobWeight, int jobLength)
    {
        super();
        this.jobID = jobID;
        this.jobWeight = jobWeight;
        this.jobLength = jobLength;
    }

    public int getJobID()
    {
        return jobID;
    }

    public void setJobID(int jobID)
    {
        this.jobID = jobID;
    }

    public int getJobWeight()
    {
        return jobWeight;
    }

    public void setJobWeight(int jobWeight)
    {
        this.jobWeight = jobWeight;
    }

    public int getJobLength()
    {
        return jobLength;
    }

    public void setJobLength(int jobLength)
    {
        this.jobLength = jobLength;
    }
    
}
