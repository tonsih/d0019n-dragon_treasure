package game.general;

import game.data.ValueManager;

/**
 * This class represents a door which holds attributes such as its position
 * (direction), if the door is locked or not, if the door leads to an exit.
 *
 * <p>A door can also be connected to a room.
 */
public class Door
{
    /**
     * The position/direction in which the door resides in a room.
     */
    private final char position;

    /**
     * Stores the state of the door. {@code true} if locked, {@code false}
     * otherwise.
     */
    private final boolean locked;

    /**
     * {@code true} if the door leads to an exit. {@code false} otherwise.
     */
    private final boolean exit;

    /**
     * The room which the door leads to.
     */
    private Room pointsToRoom;

    /**
     * @param pos The position of the door inside a room.
     * @param locked If the door is either locked or not.
     * @param exit If the door leads to an exit or not.
     * @throws IllegalArgumentException If the door has an invalid position.
     */
    public Door(char pos, boolean locked, boolean exit) throws IllegalArgumentException
    {
        if (ValueManager.charIsDirection(pos)) this.position = pos;
        else throw new IllegalArgumentException(
                "A door was not defined to be in a valid " + "position [" +
                        ValueManager.directionCommandsToString() + "]");

        this.locked = locked;
        this.exit = exit;
    }

    public Door(char pos) throws IllegalArgumentException
    {
        this(pos, false, false);
    }

    public Door(char pos, boolean locked) throws IllegalArgumentException
    {
        this(pos, locked, false);
    }

    /**
     * @return the position in which the door is in inside a room.
     */
    public char getPosition()
    {
        return this.position;
    }

    /**
     * @return {@code true} if door is locked, otherwise {@code false}.
     */
    public boolean isLocked()
    {
        return this.locked;
    }

    /**
     * @return {@code true} if door leads to an exit, otherwise {@code false}.
     */
    public boolean isExit()
    {
        return this.exit;
    }

    /**
     * @return A room which the door leads to.
     */
    public Room getPointsToRoom()
    {
        return this.pointsToRoom;
    }

    /**
     * A setter for connecting the door with a room.
     *
     * @param room The room to which the door is connected.
     */
    public void setPointsToRoom(Room room)
    {
        this.pointsToRoom = room;
    }
}