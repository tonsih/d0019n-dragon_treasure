package game.items.treasures;

import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;

/**
 * This class represents a specific treasure, salty sticks, which the player
 * can pick up.
 */
public class SaltaPinnar extends Treasure implements K
{
    /**
     * @param name Name of the salty sticks.
     * @param itemDesc Description about the salty sticks.
     * @param lockedDesc Description about the salty sticks when observed from
     *                    behind a locked door.
     * @param value Value of the salty sticks.
     */
    public SaltaPinnar(String name,
                       String itemDesc,
                       String lockedDesc,
                       int value)
    {
        super(name, itemDesc, lockedDesc, value);
    }

    public SaltaPinnar()
    {

        this(SALTA_PINNAR_NAME, SALTA_PINNAR_DESC, SALTA_PINNAR_LOCKED_DESC, SALTA_PINNAR_VALUE);
    }

    /**
     * Prints ASCII art of a pretzel.
     */
    @Override public void printObject()
    {
        PrintCollection.printPretzel();
    }

    /**
     * Adds the treasure to the player's inventory, prints an ASCII art image
     * of a pretzel and prints a message notifying about the found treasure.
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        if (entity instanceof Player)
        {
            ((Player) entity).addTreasure(this);
            printObject();
            System.out.printf("%s hittar %s!\n",
                    entity.getName(),
                    this.itemDesc.toLowerCase());
        }
    }
}