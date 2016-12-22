package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CreateIntegerFile
{
    public static void createFile(String fileName, int lines) throws IOException
    {
        Random rand = new Random();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        for (int i=0; i<lines; i++)
        {
            int nxt = rand.nextInt(lines) + 1;
            writer.write("" + nxt);
            writer.write("\r\n");
        }
        writer.close();
    }
    
    public static void main(String[] args) throws Exception
    {
        // createFile("C:\\Users\\rubandyopadhyay\\Downloads\\Median_100K.txt", 100000);
        // createFile("C:\\Users\\rubandyopadhyay\\Downloads\\Median_1M.txt", 1000000);
        createFile("C:\\Users\\rubandyopadhyay\\Downloads\\Median_10M.txt", 10000000);
    }

}
