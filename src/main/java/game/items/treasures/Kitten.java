package game.items.treasures;

import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;

/**
 * This class represents a specific treasure, a kitten, which the player
 * can pick up.
 */
public class Kitten extends Treasure implements K
{
    /**
     * @param name Name of the cat.
     * @param itemDesc Description about the cat.
     * @param lockedDesc Description about the cat when observed from behind a
     *                    locked door.
     * @param value Value of the cat.
     */
    public Kitten(String name, String itemDesc, String lockedDesc, int value)
    {
        super(name, itemDesc, lockedDesc, value);
    }

    public Kitten()
    {
        this(KITTEN_NAME, KITTEN_DESC, KITTEN_LOCKED_DESC, KITTEN_VALUE);
    }

    /**
     * Prints an ASCII kitten.
     */
    @Override
    public void printObject()
    {
        PrintCollection.printKitten();
    }

    /**
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        if (entity instanceof Player)
        {
            ((Player) entity).addTreasure(this);
            printObject();
            System.out.println("Du räddar " +
                    this.itemDesc.toLowerCase() +
                    "!");
        }
    }
}