package game.items.treasures;

import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;

public class Kitten extends Treasure implements K
{
    public Kitten(String name, String itemDesc, String lockedDesc, int goldValue)
    {
        super(name, itemDesc, lockedDesc, goldValue);
    }

    public Kitten()
    {
        this("En kattunge", "En söt kattunge", "En hårboll som rullar.", K.KITTEN_VALUE);
    }

    @Override
    public void printObject()
    {
        PrintCollection.printKitten();
    }

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
