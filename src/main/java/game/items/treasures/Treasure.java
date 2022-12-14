package game.items.treasures;

import game.interfaces.Printable;
import game.items.Item;

/**
 * This class represents a treasure which the player can find and collect.
 */
public abstract class Treasure extends Item implements Printable
{
    /**
     * Treasure's value.
     */
    private final int value;

    /**
     * Description of the treasure when the player peeks through the keyhole
     * of a locked door.
     */
    private final String lockedDesc;

    /**
     * @param name Name of the treasure.
     * @param itemDesc Description about the treasure.
     * @param lockedDesc Description about the treasure, when observed from
     *                    behind a locked door.
     * @param value Treasure's value.
     */
    public Treasure(String name, String itemDesc, String lockedDesc, int value)
    {
        super(name, itemDesc);
        this.value = value;
        this.lockedDesc = lockedDesc;
    }

    /**
     * @return The description about the treasure when observed from behind
     *         a locked door.
     */
    public String getLockedDesc()
    {
        return this.lockedDesc;
    }

    /**
     * @return Treasure's value.
     */
    public int getValue()
    {
        return this.value;
    }
}