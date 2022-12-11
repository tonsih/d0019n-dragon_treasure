package game.items;

import game.entities.Entity;
import game.entities.Player;

public abstract class Item
{
    protected final String name;
    protected final String itemDesc;
    protected final boolean instantlyAffectsOnPickup;
    protected final boolean usableDuringBattle;

    protected final Character useCommand;

    public Item(String name, String itemDesc, boolean instantlyAffectsOnPickup, Character useCommand, boolean usableDuringBattle)
    {
        this.name = name;
        this.itemDesc = itemDesc;
        this.useCommand = useCommand;
        this.usableDuringBattle = usableDuringBattle;
        this.instantlyAffectsOnPickup = instantlyAffectsOnPickup;
    }

    public Item(String name, String itemDesc)
    {
        this(name, itemDesc, true, null, false);
    }

    public String getName()
    {
        return this.name;
    }

    public String getItemDesc()
    {
        return this.itemDesc;
    }

    public boolean getUsableDuringBattle()
    {
        return this.usableDuringBattle;
    }

    public boolean instantlyAffectsOnPickup()
    {
        return this.instantlyAffectsOnPickup;
    }

    public Character getUseCommand()
    {
        return this.useCommand;
    }


    public abstract void applyEffect(Entity entity);
}
