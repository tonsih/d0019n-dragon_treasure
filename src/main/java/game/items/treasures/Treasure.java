package game.items.treasures;

import game.interfaces.Printable;
import game.items.Item;

public abstract class Treasure extends Item implements Printable
{
    private final int goldValue;
    private final String lockedDesc;

    public Treasure(String name, String itemDesc, String lockedDesc, int goldValue)
    {
        super(name, itemDesc);
        this.goldValue = goldValue;
        this.lockedDesc = lockedDesc;
    }

    public int getGoldValue()
    {
        return this.goldValue;
    }

    public String getLockedDesc()
    {
        return this.lockedDesc;
    }

    @Override
    public abstract void printObject();

}
