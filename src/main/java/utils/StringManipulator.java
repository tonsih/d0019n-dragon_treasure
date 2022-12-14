package utils;

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

}