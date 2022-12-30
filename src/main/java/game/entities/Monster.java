package game.entities;

import game.data.K;
import game.data.PrintCollection;

/**
 * This class represents an enemy in the game, which is some kind of monster.
 */
public class Monster extends Entity implements K
{
    /**
     * @param name The name of the monster.
     * @param desc A description about the monster.
     * @param healthPoints Monster's health-points.
     * @param maxDamage The maximal damage output of the monster.
     */
    public Monster(String name, String desc, int healthPoints, int maxDamage)
    {
        super(name, desc, healthPoints, maxDamage);
    }

    public Monster()
    {
        this(MONSTER_NAME, MONSTER_DESC, MONSTER_HP, MONSTER_MAX_DMG);
    }

    /**
     * Prints an ASCII monster.
     */
    @Override public void printObject()
    {
        PrintCollection.printMonster();
    }
}