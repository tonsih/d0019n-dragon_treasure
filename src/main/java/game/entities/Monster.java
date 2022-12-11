package game.entities;

import game.data.K;
import game.data.PrintCollection;
import game.interfaces.Printable;

public class Monster extends Entity implements K, Printable
{
    private final String monsterDesc;

    public Monster(String name,
                   int healthPoints,
                   int damage,
                   String monsterDesc)
    {
        super(name, healthPoints, damage);
        this.monsterDesc = monsterDesc;
    }

    public Monster()
    {
        this("Odjur", K.CREATURE_HP, K.CREATURE_MAX_DMG, "En ful varelse");
    }

    public String getMonsterDesc()
    {
        return monsterDesc;
    }

    @Override
    public void printObject()
    {
        PrintCollection.printMonster();
    }


}