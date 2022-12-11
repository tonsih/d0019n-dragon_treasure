package game.items.consumables;

import game.items.Item;

public abstract class Consumable extends Item
{
    public Consumable(String name, String itemDesc, boolean instantlyAffectsOnPickup, Character useCommand, boolean usableDuringBattle)
    {
        super(name, itemDesc, instantlyAffectsOnPickup, useCommand, usableDuringBattle);
    }

}
