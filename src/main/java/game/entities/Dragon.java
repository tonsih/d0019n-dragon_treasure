package game.entities;

import game.data.K;
import game.data.PrintCollection;

public class Dragon extends Monster
{
    public Dragon(String name, int healthPoints, int damage, String monsterDesc)
    {
        super(name, healthPoints, damage, monsterDesc);
    }

    public Dragon()
    {
        this("Drake", K.DRAGON_HP, K.DRAGON_MAX_DMG, "Skrämmande drake");
    }

    @Override
    public void printObject()
    {
        PrintCollection.printDragon();
    }
}
