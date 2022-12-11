package utils;

import java.sql.Time;

public class StringManipulator
{
    public static String stripEnOrEtt(String word)
    {
        String en = "en ";
        String ett = "ett ";

        if (word.toLowerCase().startsWith(en))
            return word.substring(en.length());
        else if (word.toLowerCase().startsWith(ett))
            return word.substring(ett.length());

        return word;

    }


    public static void animateString(String string, int speed)
    {
        for (char c : string.toCharArray())
        {
            TimeManipulator.wait(speed);
            System.out.print(c);
        }
    }

    public static void animateString(String string)
    {
        animateString(string, 33);
    }

}
