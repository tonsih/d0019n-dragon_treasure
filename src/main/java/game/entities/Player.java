package game.entities;

import game.data.K;
import game.items.treasures.Treasure;

import java.util.ArrayList;

// The game.entities.Player-class contains information about the player.
public class Player extends Entity implements K
{

    private final ArrayList<Treasure> treasures;


    private boolean isAlive;

    private int totalMonstersKilled;


    //  game.entities.Player-constructor -- Takes a name (string) as an
    //  argument and
    //  assigns the
//  value to the corresponding object variable.
    public Player(String name)
    {
        super(name, K.INITIAL_PLAYER_HP, K.INITIAL_PLAYER_DMG);
        this.treasures = new ArrayList<>();
        this.isAlive = true;
        this.totalMonstersKilled = 0;
    }

    public boolean isAlive()
    {
        return this.isAlive;
    }


    public void addTreasure(Treasure treasure)
    {
        this.treasures.add(treasure);
    }



    public ArrayList<Treasure> getTreasures()
    {
        return this.treasures;
    }

    private int getTreasuresTotalValue()
    {
        int totalValue = 0;
        for (Treasure treasure : this.treasures)
        {
            totalValue += treasure.getGoldValue();
        }
        return totalValue;
    }


    private String treasuresString()
    {
        String treasureStr = "";

        for (Treasure treasure : this.treasures)
        {
            treasureStr +=
                    treasure.getName() + " (värde: " + treasure.getGoldValue() +
                            ")\n";
        }

        return treasureStr + "\nTotal värde: " + this.getTreasuresTotalValue();

    }

    public String getStats()
    {

        return String.format(
                "Namn: %s\n" +
                "HP: %s\n"   +
                "Max. DMG: %d\n"  +
                "Dödade monsters: %d\n" +
                "%s",
                super.name,
                super.healthPoints > 0 ? super.healthPoints :
                        super.healthPoints + " (du dog)",
                super.maxDamage,
                this.totalMonstersKilled,
                this.treasures.size() > 0 ?
                        "\nSkatter:\n" + this.treasuresString() :
                        "\nInga skatter"
        );
    }


    public void setAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }


    public void addMonsterKilled()
    {
        this.totalMonstersKilled++;
    }

    public void printName()
    {
        System.out.println(this.name);
    }

}
