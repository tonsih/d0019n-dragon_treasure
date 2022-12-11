package game.entities;

import game.data.K;
import game.data.PrintCollection;
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

    public int getTreasuresTotalValue()
    {
        int totalValue = 0;
        for (Treasure treasure : this.treasures)
        {
            totalValue += treasure.getGoldValue();
        }
        return totalValue;
    }


    public String treasuresString()
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

    public int getMonstersKilled()
    {
        return this.totalMonstersKilled;
    }


    public void printPlayerInfo(String title, boolean treasuresIncluded)
    {

        String[] containers = new String[]
                {
                        String.format("| %s ", title),
                        String.format("| Name: %s", this.name),
                        String.format("| HP: %d", this.healthPoints),
                        String.format("| Max DMG: %d", this.maxDamage),
                        String.format("| Dödade monsters: %d", this.totalMonstersKilled)
                };

        if (treasuresIncluded)
        {

            String[] tempContainers;
            int tempLength = containers.length+this.treasures.size();

            if (this.treasures.size() > 0)
            {
                tempContainers = new String[tempLength+4];
                tempContainers[tempContainers.length-2] = "|";
                tempContainers[tempContainers.length-1] = String.format("| Total värde: %d Guld", this.getTreasuresTotalValue());
            }
            else tempContainers = new String[tempLength+2];

            tempContainers[containers.length] = "|";
            tempContainers[containers.length+1] = this.treasures.size() > 0 ? "| Skatter:" : "| Inga Skatter";

            for (int i = 0; i < containers.length; i++)
            {
                tempContainers[i] = containers[i];
            }

            for (int i = 0; i < this.treasures.size(); i++)
            {
                Treasure treasure = this.treasures.get(i);
                tempContainers[containers.length+2+i] = String.format("| %s (Värde: %d Guld)", treasure.getName(), treasure.getGoldValue());
            }


            containers = tempContainers;

        }

        String containerEnd = " |";

        int boxWidth = containers[0].length() + containerEnd.length();

        for (String s : containers)
        {
            if (s.length() + containerEnd.length() > boxWidth)
            {
                boxWidth = s.length() + containerEnd.length();
            }
        }

        PrintCollection.printLinesWithPlusCorners(boxWidth);

        int amountOfSpaces = boxWidth - containers[0].length();

        System.out.print(containers[0]);
        PrintCollection.printAmountOfSpaces(amountOfSpaces);
        System.out.println(containerEnd);

        PrintCollection.printLinesWithPlusCorners(boxWidth);

        for (int i = 1; i < containers.length; i++)
        {
            amountOfSpaces = boxWidth - containers[i].length();
            System.out.print(containers[i]);
            PrintCollection.printAmountOfSpaces(amountOfSpaces);
            System.out.println(containerEnd);
        }

        PrintCollection.printLinesWithPlusCorners(boxWidth);

    }


    public void printPlayerInfo(String title)
    {
        printPlayerInfo(title, false);
    }




}
