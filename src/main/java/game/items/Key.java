package game.items;

import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

/**
 * This class represents a key used by the player to open doors or gain access
 * to pass through them.
 */
public class Key extends Item implements Printable
{
    /**
     * Identifier of the room connected to the door which the key can open.
     */
    private final int roomId;

    /**
     * @param name Name of the key.
     * @param roomId Identifier of the room connected to the door which
     *                the key can open.
     */
    public Key(String name, int roomId)
    {
        super(name, "En nyckel som öppnar rum nummer " + roomId);
        this.roomId = roomId;
    }

    public Key(int roomId)
    {
        this("En nyckel", roomId);
    }

    /**
     * @return Identifier of the room connected to the door which the key can
     *         open.
     */
    public int getRoomId()
    {
        return this.roomId;
    }

    /**
     * Prints an ASCII key.
     */
    @Override public void printObject()
    {
        PrintCollection.printKey();
    }

    /**
     * Gives the key to the provided entity.
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        entity.giveKey(this);
    }
}