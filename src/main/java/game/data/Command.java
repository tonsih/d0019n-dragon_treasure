package game.data;

public enum Command implements K
{
    NORTH(n),
    SOUTH(s),
    WEST(w),
    EAST(e),
    PICKUP_ITEM('p'),
    PICKUP_KEY('f'),
    CONSUME_HEALTH_POTION('e'),
    ATTACK('a'),
    USE_GRENADE('g'),
    IDE_OPTION('1'),
    TERMINAL_OPTION('2'),
    EXIT_GAME('x');


    public final char commandValue;

    Command(char command)
    {
        this.commandValue = command;
    }

}
