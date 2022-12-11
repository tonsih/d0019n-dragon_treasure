package game.items.weapons;

import game.entities.Player;
import game.items.Item;

public abstract class Weapon extends Item
{

    public Weapon(String name, String itemDesc, boolean instantlyAffectsOnPickup, Character useCommand, boolean usableDuringBattle)
    {
        super(name, itemDesc, instantlyAffectsOnPickup, useCommand, usableDuringBattle);
    }

    public Weapon(String name, String itemDesc)
    {
        this(name, itemDesc, true, null, false);
    }

}
