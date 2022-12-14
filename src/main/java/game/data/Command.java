package game.data;

/**
 * This enum contains commands expected to be available for use for the player
 * during gameplay.
 */
public enum Command implements K
{
    /*
     * Direction-commands
     */
    NORTH(N),
    SOUTH(S),
    WEST(W),
    EAST(E),

    /*
     * Other commands
     */
    PICKUP_ITEM('p'),
    PICKUP_KEY('f'),
    CONSUME_HEALTH_POTION('d'),
    ATTACK('a'),
    USE_GRENADE('g'),
    IDE_OPTION('1'),
    TERMINAL_OPTION('2'),
    EXIT_GAME('x');

    /**
     * Command value used for specified command.
     */
    public final char commandValue;

    /**
     * @param commandValue Command value used for specified command.
     */
    Command(char commandValue)
    {
        this.commandValue = commandValue;
    }
}