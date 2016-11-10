package com.alg;

import java.util.Calendar;

public class Timer implements Runnable
{
    int interval;
    long accumulator;
    boolean stop = false;
    
    public Timer(int interval)
    {
        super();
        this.interval = interval;
    }
    
    
    public boolean isStop()
    {
        return stop;
    }

    public void setStop(boolean stop)
    {
        this.stop = stop;
    }


    @Override
    public void run()
    {
        accumulator = 0;
        while (! stop)
        {
            try
            {
                long start = Calendar.getInstance().getTimeInMillis();
                Thread.sleep(interval);
                long end = Calendar.getInstance().getTimeInMillis();
                accumulator += (end - start);
                System.out.println(String.format("%10.2f seconds", accumulator / 1000.0));
            }
            catch (InterruptedException ie)
            {
            }
        }
    }

}
