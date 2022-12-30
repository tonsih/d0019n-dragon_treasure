package game.data;

import java.util.Map;

/**
 * This class takes care of managing values such as position strings and
 * direction characters by generating different types of output.
 */
public class ValueManager implements K
{
    /**
     * A private no-arg constructor for the class. Prevents initiation of an
     * instance.
     */
    private ValueManager()
    {
    }

    /**
     * Generates a string corresponding to the provided position.
     *
     * @param c Provided position/direction character.
     * @return Position/direction string.
     * @throws Exception If the provided character is not a valid
     *                   position/direction.
     */
    public static String generatePosString(char c) throws Exception
    {
        for (Map.Entry<String, Character> entry : DIRECTIONS.entrySet())
        {
            if (c == entry.getValue()) return entry.getKey();
        }

        throw new Exception(c + " is not a valid direction [" + directionCommandsToString() + "]");
    }

    /**
     * Checks if the provided direction is a valid direction.
     *
     * @param c Provided direction-character.
     * @return {@code true} if the provided character is a direction.
     *         {@code false} otherwise.
     */
    public static boolean charIsDirection(char c)
    {
        for (char direction : DIRECTIONS.values())
        {
            if (direction == c) return true;
        }
        return false;
    }

    /**
     * Generates a string for the established directions.
     *
     * @return A string for all the directions, {@code null} otherwise.
     */
    public static String directionCommandsToString()
    {
        StringBuilder directionString = new StringBuilder();

        for (char c : DIRECTIONS.values()) directionString.append(c)
                .append(",");

        return directionString.length() > 0 ?
                directionString.substring(0, directionString.length() - 1) :
                null;
    }

    /**
     * Returns the corresponding Command for the provided char.
     *
     * @param ans char provided by the user
     * @return Command for the provided char if such exists, returns
     *         {@code null} otherwise.
     */
    public static Command getCommandWithChar(char ans)
    {
        for (Command c : Command.values())
        {
            if (ans == c.commandValue) return c;
        }
        return null;
    }
}