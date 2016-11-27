package com.alg;

import java.util.Calendar;

public class Util
{
    public static long getTime()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    
    public static void showElapsed(long start, long end)
    {
        System.out.println(String.format("Time ms = %d", (end - start)));
    }

}
