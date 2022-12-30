package game.entities;

import game.data.K;
import game.data.PrintCollection;

/**
 * This class represents an enemy monster which is a dragon.
 */
public class Dragon extends Monster implements K
{
    /**
     * @param name Name of the dragon.
     * @param desc Description about the dragon.
     * @param healthPoints Dragon's health-points.
     * @param maxDamage Dragon's maximal damage output.
     */
    public Dragon(String name, String desc, int healthPoints, int maxDamage)
    {
        super(name, desc, healthPoints, maxDamage);
    }

    public Dragon()
    {
        this(DRAGON_NAME, DRAGON_DESC, DRAGON_HP, DRAGON_MAX_DMG);
    }

    /**
     * Prints an ASCII dragon.
     */
    @Override
    public void printObject()
    {
        PrintCollection.printDragon();
    }
}