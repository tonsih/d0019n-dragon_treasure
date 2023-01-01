package game.entities;

import game.data.K;
import game.data.PrintCollection;
import game.items.treasures.Treasure;

import java.util.ArrayList;

/**
 * This class represents a player-entity with which the user navigates through
 * the game layout and engages in battles with.
 */
public class Player extends Entity implements K
{
    /**
     * Player's treasures.
     */
    private final ArrayList<Treasure> treasures;

    /**
     * The total amount of monsters killed by the player.
     */
    private int totalMonstersKilled;

    /**
     * @param name Player's name.
     */
    public Player(String name)
    {
        super(name,
                PLAYER_DESC,
                INITIAL_PLAYER_HP,
                INITIAL_PLAYER_MAX_DMG,
                true,
                true);
        this.treasures = new ArrayList<>();
        this.totalMonstersKilled = 0;
    }

    /**
     * Prints a box with player information such as player name, HP, max damage
     * and total monsters killed. Prints out player's treasures and their value
     * also if the option for printing treasures is checked.
     *
     * <p>The method determines its width by comparing all the rows of text,
     * which are to be contained inside the box and finds the row with the
     * biggest length. This length becomes the value used to determine how wide
     * the printed box will be.
     *
     * @param title The title printed out on the title row in the box.
     * @param treasuresIncluded Option to also print player's treasures,
     *         their values and the total value inside the printed box.
     *         {@code true} to print the treasures {@code false} to not do
     *         that.
     */
    public void printPlayerInfo(String title, boolean treasuresIncluded)
    {
        String containerPipe = "|";
        String[] containerRows = new String[]{String.format("%s", title),
                String.format("%s: %s",
                        CONTAINER_LABELS.get("NAME"),
                        this.name), String.format("%s: %d",
                CONTAINER_LABELS.get("HP"),
                this.healthPoints), String.format("%s: %d",
                CONTAINER_LABELS.get("MAX_DMG"),
                this.maxDamage), String.format("%s: %d",
                CONTAINER_LABELS.get("MONSTERS_KILLED"),
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
                        CONTAINER_LABELS.get("TOTAL_VALUE"),
                        this.getTreasuresTotalValue(),
                        CURRENCY);
            } else tempContainers = new String[tempLength + 2];

            tempContainers[containerRows.length] = containerPipe;
            tempContainers[containerRows.length + 1] =
                    this.treasures.size() > 0 ? containerPipe + " " +
                            CONTAINER_LABELS.get("TREASURES") + ":" :
                            containerPipe + " " +
                                    CONTAINER_LABELS.get("NO_TREASURES");

            System.arraycopy(containerRows,
                    0,
                    tempContainers,
                    0,
                    containerRows.length);

            for (int i = 0; i < this.treasures.size(); i++)
            {
                Treasure treasure = this.treasures.get(i);
                tempContainers[containerRows.length + 2 + i] = String.format(
                        "%s %s (%s: %d %s)",
                        containerPipe,
                        treasure.getName(),
                        CONTAINER_LABELS.get("VALUE"),
                        treasure.getValue(),
                        CURRENCY);
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
        PrintCollection.printSpaces(amountOfSpaces);
        System.out.println(containerPipe);
        PrintCollection.printLinesWithPlusCorners(boxWidth - 1);

        for (int i = 1; i < containerRows.length; i++)
        {
            amountOfSpaces = boxWidth - containerRows[i].length();
            System.out.print(containerRows[i]);
            PrintCollection.printSpaces(amountOfSpaces);
            System.out.println(containerPipe);
        }
        PrintCollection.printLinesWithPlusCorners(boxWidth - 1);
    }

    public void printPlayerInfo(String title)
    {
        printPlayerInfo(title, false);
    }

    /**
     * @return Total value of all player's treasures.
     */
    public int getTreasuresTotalValue()
    {
        int totalValue = 0;
        for (Treasure treasure : this.treasures)
        {
            totalValue += treasure.getValue();
        }
        return totalValue;
    }

    /**
     * @param treasure Treasure to be added to the player's treasure
     *         array.
     */
    public void addTreasure(Treasure treasure)
    {
        this.treasures.add(treasure);
    }

    /**
     * Increment the total amount of monsters killed by one.
     */
    public void addMonsterKilled()
    {
        this.totalMonstersKilled++;
    }

    @Override public void printObject()
    {
    }
}