package game.data;

import java.util.Map;

public class ValueManager implements K
{
    /**
     * A private no-arg constructor for the class. Prevents initiation of an
     * instance.
     */
    private ValueManager()
    {
    }

//  generatePosString class function -- Generates a string corresponding
//  with the direction represented by the pos-parameter and returns it.
    public static String generatePosString(char c) throws Exception
    {
        for (Map.Entry<String, Character> entry : DIRECTIONS.entrySet())
        {
            if (c == entry.getValue()) return entry.getKey();
        }

        throw new Exception(c + " is not a valid direction [" + directionCommandsToString() + "]");
    }



    public static boolean charInCommands(char c)
    {
        for (Command commandValue : Command.values())
        {
            if (commandValue.commandValue == c) return true;
        }

        return false;

    }

    public static boolean charIsDirection(char c)
    {
        for (char direction : DIRECTIONS.values())
        {
            if (direction == c)
            {
                return true;
            }
        }
        return false;
    }

    public static String directionCommandsToString()
    {
        String directionString = "";

        for (char c : DIRECTIONS.values())
        {
            directionString += c + ",";
        }

        return directionString.length() > 0 ?
                directionString.substring(0, directionString.length() - 1) :
                null;
    }



    public static Command getCommandValueWithChar(char ans)
    {
        for (Command c : Command.values())
        {

            if (ans == c.commandValue)
            {
                return c;
            }

        }

        return null;
    }


}