package game.items;

import game.entities.Entity;

/**
 * This class represents a skeleton for game-items.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public abstract class Item
{
    /**
     * Name of the item.
     */
    protected final String name;

    /**
     * Item description.
     */
    protected final String itemDesc;

    /**
     * {@code true} if the item has an instant effect on pickup, {@code false}
     * if not.
     */
    private final boolean instantlyAffectsOnPickup;

    /**
     * Command to use the item.
     */
    private final Character useCommand;

    /**
     * {@code true} if item is usable during battle. {@code false} otherwise.
     */
    private final boolean usableDuringBattle;

    /**
     * @param name Name of the item.
     * @param itemDesc Description of the item.
     * @param instantlyAffectsOnPickup {@code true} if item has an
     *                                   instant effect on pickup.
     * @param useCommand Command to use the item.
     * @param usableDuringBattle {@code true} if the item is usable during
     *                            battle, {@code false} if not.
     */
    public Item(String name,
                String itemDesc,
                boolean instantlyAffectsOnPickup,
                Character useCommand,
                boolean usableDuringBattle)
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

    /**
     * Apply the effect of the item on the provided entity. Method required to
     * be overridden by subclasses.
     *
     * @param entity Entity on which to apply the item effect.
     */
    public abstract void applyEffect(Entity entity);

    /**
     * @return Name of the item.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return Item description.
     */
    public String getItemDesc()
    {
        return this.itemDesc;
    }

    /**
     * @return {@code true} if item has an instant effect on pickup.
     *         {@code false} if not.
     */
    public boolean instantlyAffectsOnPickup()
    {
        return this.instantlyAffectsOnPickup;
    }

    /**
     * @return Command to use the item.
     */
    public Character getUseCommand()
    {
        return this.useCommand;
    }

    /**
     * @return {@code true} if item is usable during battle, otherwise
     *         {@code false}.
     */
    public boolean getUsableDuringBattle()
    {
        return this.usableDuringBattle;
    }
}