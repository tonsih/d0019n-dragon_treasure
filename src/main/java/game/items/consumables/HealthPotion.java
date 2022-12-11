package game.items.consumables;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;
import game.interfaces.Printable;

public class HealthPotion extends Consumable implements K, Printable
{
    private final int healing;

    public HealthPotion(String name, String itemDescription, char useCommand, int healing)
    {
        super(name, itemDescription, false, useCommand, true);
        this.healing = healing;

    }

    public HealthPotion()
    {
        this("en hälsodryck", "Ger heal points", Command.CONSUME_HEALTH_POTION.commandValue,  POTION_HP);
    }

    public int getHealingAmount()
    {
        return this.healing;
    }

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
    }

    @Override public void printObject()
    {
        PrintCollection.printHealthPotion();
    }
}