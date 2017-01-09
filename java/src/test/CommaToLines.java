package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommaToLines
{
    public static void t01() throws IOException
    {
        Pattern p = Pattern.compile("^([0-9]+)->([0-9]+):(.*)$");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        line = line.trim();
        if (line.startsWith("["))
        {
            line = line.substring(1);
        }
        if (line.endsWith("]"))
        {
            line = line.substring(0, line.length()-1);
        }
        String[] fields = line.split(",\\s*");
        if (fields != null)
        {
            for (String field : fields)
            {
                Matcher m = p.matcher(field);
                if (m.find())
                {
                    System.out.println(m.group(1) + "," + m.group(2) + "," + m.group(3));
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        t01();
    }

}
