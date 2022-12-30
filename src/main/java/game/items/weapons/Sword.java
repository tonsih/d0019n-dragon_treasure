package game.items.weapons;

import game.data.K;
import game.data.PrintCollection;
import game.entities.Entity;
import game.interfaces.Printable;

/**
 * This class represents a sword, a weapon used by an entity.
 */
public class Sword extends Weapon implements K, Printable
{
    /**
     * The amount of max. damage the carrier of the sword gains.
     */
    private final int increaseDamage;

    /**
     * @param name Name of the sword.
     * @param itemDesc Description of the sword.
     * @param increaseDamage Amount of max. damage output the sword increases
     *                        its carrier with.
     */
    public Sword(String name, String itemDesc, int increaseDamage)
    {
        super(name, itemDesc);
        this.increaseDamage = increaseDamage;
    }

    public Sword()
    {
        this(SWORD_NAME, SWORD_DESC, SWORD_DMG);
    }

    /**
     * Increases the max. damage output of the provided entity.
     *
     * @param entity Entity on which to apply the item effect.
     */
    public void applyEffect(Entity entity)
    {

        entity.setMaxDamage(entity.getMaxDamage() + this.increaseDamage);

        System.out.printf(
                "\n%s DMG ökar med %d (Ny Total DMG: %d DMG)" +
                        "\n",
                entity.getName(),
                this.increaseDamage,
                entity.getMaxDamage());
    }

    /**
     * Prints an ASCII sword.
     */
    @Override public void printObject()
    {
        PrintCollection.printSword();
    }
}