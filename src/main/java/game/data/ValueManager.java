package game.data;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class takes care of managing values such as position strings and
 * direction characters by generating different types of output.
 */
public class ValueManager implements K {
    /**
     * A private no-arg constructor for the class. Prevents initiation of an
     * instance.
     */
    private ValueManager() {
    }

    /**
     * Generates a string corresponding to the provided position.
     *
     * @param c Provided position/direction character.
     * @return Position/direction string.
     * @throws Exception If the provided character is not a valid
     *                   position/direction.
     */
    public static String generatePosString(char c) throws Exception {
        return DIRECTIONS.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == c)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new InvalidDirectionException(c));
    }

    /**
     * Checks if the provided direction is a valid direction.
     *
     * @param c Provided direction-character.
     * @return {@code true} if the provided character is a direction.
     * {@code false} otherwise.
     */
    public static boolean charIsDirection(char c) {
        return DIRECTIONS.values()
                .stream()
                .anyMatch(direction -> direction == c);
    }

    /**
     * Generates a string for the established directions.
     *
     * @return A string for all the directions, {@code null} otherwise.
     */
    public static String directionCommandsToString() {
        String result = DIRECTIONS.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return result.isEmpty() ? null : result;
    }

    /**
     * Returns the corresponding Command for the provided char.
     *
     * @param ans char provided by the user
     * @return Command for the provided char if such exists, returns
     * {@code null} otherwise.
     */
    public static Command getCommandWithChar(char ans) {
        return Arrays.stream(Command.values())
                .filter(c -> c.commandValue == ans)
                .findFirst()
                .orElse(null);
    }

    private static class InvalidDirectionException extends Exception {
        public InvalidDirectionException(char c) {
            super(c + " is not a valid direction [" + directionCommandsToString() + "]");
        }
    }
}