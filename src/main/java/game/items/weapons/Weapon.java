package game.items.weapons;

import game.items.Item;

/**
 * This class represents a skeleton used to identify and specify attributes for
 * an item which is a weapon used by an entity.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public abstract class Weapon extends Item
{
    /**
     * @param name Name of the weapon.
     * @param itemDesc Description of the weapon.
     * @param instantlyAffectsOnPickup {@code true} if the weapon has an
     *        instant effect when picked up by an entity, {@code false} if not.
     * @param useCommand Command to use the weapon.
     * @param usableDuringBattle {@code true} if the weapon is usable during
     *        battle, {@code false} otherwise.
     */
    public Weapon(String name, String itemDesc, boolean instantlyAffectsOnPickup, Character useCommand, boolean usableDuringBattle)
    {
        super(name, itemDesc, instantlyAffectsOnPickup, useCommand, usableDuringBattle);
    }

    public Weapon(String name, String itemDesc)
    {
        this(name, itemDesc, true, null, false);
    }

}