package game.items.consumables;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

/**
 * This class represents one type of consumable expected to be consumed by the
 * player, a health potion. The health potion gives the player health after
 * consumption.
 */
public class HealthPotion extends Consumable implements K, Printable
{
    /**
     * Amount of healing done by the health potion.
     */
    private final int healing;

    /**
     * @param name Name of the health potion.
     * @param itemDescription Description about the health potion.
     * @param useCommand Command to consume the health potion.
     * @param healing Healing done by the health potion.
     */
    public HealthPotion(String name, String itemDescription, char useCommand, int healing)
    {
        super(name, itemDescription, false, useCommand, true);
        this.healing = healing;

    }

    public HealthPotion()
    {
        this(HEALTH_POTION_NAME, HEALTH_POTION_DESC, Command.CONSUME_HEALTH_POTION.commandValue,
                HEALTH_POTION_HP);
    }

    /**
     * @return Amount of health provided by the health potion.
     */
    public int getHealingAmount()
    {
        return this.healing;
    }

    /**
     * Prints an ASCII health potion.
     */
    @Override public void printObject()
    {
        PrintCollection.printHealthPotion();
    }

    /**
     * "Heals" the entity used upon and prints a message with information
     * regarding the increased health of the entity.
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        entity.setHealthPoints(
                entity.getHealthPoints() + this.getHealingAmount());
        System.out.printf(
                "%s dricker %s och får %d HP - (%s HP: %d)\n",
                entity.getName(),
                this.name.toLowerCase(),
                this.getHealingAmount(),
                entity.getName(),
                entity.getHealthPoints());
        PrintCollection.printLinesWithPlusCorners();
    }
}