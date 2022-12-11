package game.data;

public enum Command implements K
{
    NORTH(n),
    SOUTH(s),
    WEST(w),
    EAST(e),
    PICKUP_ITEM('p'),
    PICKUP_KEY('f'),
    CONSUME_HEALTH_POTION('d'),
    ATTACK('a'),
    USE_GRENADE('g'),
    EXIT_GAME('x');


    public final char commandValue;

    Command(char command)
    {
        this.commandValue = command;
    }

}
