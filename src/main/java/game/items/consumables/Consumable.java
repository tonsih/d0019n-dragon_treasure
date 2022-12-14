package game.items.consumables;

import game.items.Item;

/**
 * This class represents a skeleton of an item expected to be consumed by the
 * player.
 */
public abstract class Consumable extends Item
{
    /**
     * @param name Name of the consumable.
     * @param itemDesc Description about the consumable.
     * @param instantlyAffectsOnPickup {@code true} if the consumable has an
     *                                   instant effect when picked up,
     *                                   {@code false} otherwise.
     * @param useCommand Command to use/consume the consumable.
     * @param usableDuringBattle  {@code true} if the consumable is usable
     *                             during battle, {@code false} otherwise.
     */
    public Consumable(String name, String itemDesc, boolean instantlyAffectsOnPickup, Character useCommand, boolean usableDuringBattle)
    {
        super(name, itemDesc, instantlyAffectsOnPickup, useCommand, usableDuringBattle);
    }
}