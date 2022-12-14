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


    public void setAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }


    public void addMonsterKilled()
    {
        this.totalMonstersKilled++;
    }


    public void printPlayerInfo(String title, boolean treasuresIncluded)
    {
        String containerPipe = "|";
        String[] containerRows = new String[]{String.format("%s", title),
                String.format("%s: %s",
                        K.CONTAINER_LABELS.get("NAME"),
                        this.name), String.format("%s: %d",
                K.CONTAINER_LABELS.get("HP"),
                this.healthPoints), String.format("%s: %d",
                K.CONTAINER_LABELS.get("MAX_DMG"),
                this.maxDamage), String.format("%s: %d",
                K.CONTAINER_LABELS.get("MONSTERS_KILLED"),
                this.totalMonstersKilled)};

        for (int i = 0; i < containerRows.length; i++)
        {
            containerRows[i] = containerPipe + " " + containerRows[i];
        }

        if (treasuresIncluded)
        {
            String[] tempContainers;
            int tempLength = containerRows.length + this.treasures.size();

            if (this.treasures.size() > 0)
            {
                tempContainers = new String[tempLength + 4];
                tempContainers[tempContainers.length - 2] = containerPipe;
                tempContainers[tempContainers.length - 1] = String.format(
                        "%s %s: %d %s",
                        containerPipe,
                        K.CONTAINER_LABELS.get("TOTAL_VALUE"),
                        this.getTreasuresTotalValue(),
                        K.CONTAINER_LABELS.get("CURRENCY"));
            } else tempContainers = new String[tempLength + 2];

            tempContainers[containerRows.length] = containerPipe;
            tempContainers[containerRows.length + 1] =
                    this.treasures.size() > 0 ? containerPipe + " " +
                            K.CONTAINER_LABELS.get("TREASURES") + ":" :
                            containerPipe + " " +
                                    K.CONTAINER_LABELS.get("NO_TREASURES");

            for (int i = 0; i < containerRows.length; i++)
            {
                tempContainers[i] = containerRows[i];
            }

            for (int i = 0; i < this.treasures.size(); i++)
            {
                Treasure treasure = this.treasures.get(i);
                tempContainers[containerRows.length + 2 + i] = String.format(
                        "%s %s (%s: %d %s)",
                        containerPipe,
                        treasure.getName(),
                        K.CONTAINER_LABELS.get("VALUE"),
                        treasure.getGoldValue(),
                        K.CURRENCY);
            }


            containerRows = tempContainers;

        }
        int boxWidth = containerRows[0].length() + containerPipe.length();

        for (String s : containerRows)
        {
            if (s.length() + containerPipe.length() > boxWidth)
            {
                boxWidth = s.length() + containerPipe.length();
            }
        }

        PrintCollection.printLinesWithPlusCorners(boxWidth - 1);
        int amountOfSpaces = boxWidth - containerRows[0].length();
        System.out.print(containerRows[0]);
        PrintCollection.printAmountOfSpaces(amountOfSpaces);
        System.out.println(containerPipe);
        PrintCollection.printLinesWithPlusCorners(boxWidth - 1);

        for (int i = 1; i < containerRows.length; i++)
        {
            amountOfSpaces = boxWidth - containerRows[i].length();
            System.out.print(containerRows[i]);
            PrintCollection.printAmountOfSpaces(amountOfSpaces);
            System.out.println(containerPipe);
        }
        PrintCollection.printLinesWithPlusCorners(boxWidth - 1);
    }


    public void printPlayerInfo(String title)
    {
        printPlayerInfo(title, false);
    }


}
