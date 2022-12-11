package game.items.weapons;

import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

public class Sword extends Weapon implements K, Printable
{
    private final int increaseDamage;

    public Sword(String name, String itemDesc, int increaseDamage)
    {
        super(name, itemDesc);
        this.increaseDamage = increaseDamage;
    }

    public Sword()
    {
        this("Ett svärd", "Ett coolt svärd", K.SWORD_DMG);
    }

    public void applyEffect(Entity entity)
    {

        entity.setMaxDamage(entity.getMaxDamage() + this.increaseDamage);

        System.out.printf(
                "%s DMG ökar med %d (Ny Total DMG: %d DMG)" +
                        "\n",
                entity.getName(),
                this.increaseDamage,
                entity.getMaxDamage());
    }

    @Override public void printObject()
    {
        PrintCollection.printSword();
    }
}
