package game.items.treasures;

import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;

public class SaltaPinnar extends Treasure
{
    public SaltaPinnar(String name,
                       String itemDesc,
                       String lockedDesc,
                       int goldValue)
    {
        super(name, itemDesc, lockedDesc, goldValue);
    }

    public SaltaPinnar()
    {
        this("Salta pinnar",
                "salta pinnar som ser ut som pretzels",
                "något smaskigt och saltigt.",
                6000000);
    }

    @Override public void printObject()
    {
        PrintCollection.printPretzel();
    }

    @Override public void applyEffect(Entity entity)
    {
        if (entity instanceof Player)
        {
            ((Player) entity).addTreasure(this);
            printObject();
            System.out.println("Du hittar " +
                    this.itemDesc.toLowerCase() +
                    "!");
        }
    }
}
