package game.entities;

import game.data.K;
import game.data.PrintCollection;
import game.items.treasures.Treasure;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player-entity with which the user navigates through
 * the game layout and engages in battles with.
 */
public class Player extends Entity implements K {
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
    public Player(String name) {
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
     * @param title             The title printed out on the title row in the box.
     * @param treasuresIncluded Option to also print player's treasures,
     *                          their values and the total value inside the printed box.
     *                          {@code true} to print the treasures {@code false} to not do
     *                          that.
     */
    public void printPlayerInfo(String title, boolean treasuresIncluded) {
        List<String> containerRows = new ArrayList<>();
        containerRows.add(formatRow(title));
        containerRows.add(formatRow(String.format("%s: %s",
                CONTAINER_LABELS.get("NAME"),
                this.name)));
        containerRows.add(formatRow(String.format("%s: %d",
                CONTAINER_LABELS.get("HP"),
                this.healthPoints)));
        containerRows.add(formatRow(String.format("%s: %d",
                CONTAINER_LABELS.get("MAX_DMG"),
                this.maxDamage)));
        containerRows.add(formatRow(String.format("%s: %d",
                CONTAINER_LABELS.get("MONSTERS_KILLED"),
                this.totalMonstersKilled)));

        if (treasuresIncluded) {
            addTreasureInfo(containerRows);
        }

        int boxWidth = calculateMaxWidth(containerRows) + 2;
        printBox(containerRows, boxWidth);
    }

    /**
     * Formats the given content into a row by prefixing it with a vertical bar.
     *
     * @param content The content to format as a row in the box.
     * @return A formatted string representing a row in the box.
     */
    private String formatRow(String content) {
        return "| " + content;
    }

    /**
     * Adds information about the player's treasures to the list of rows.
     * This includes each treasure's name and value, and optionally the total value.
     *
     * @param rows The list of rows to which treasure information will be added.
     */
    private void addTreasureInfo(List<String> rows) {
        if (this.treasures.isEmpty()) {
            rows.add(formatRow(CONTAINER_LABELS.get("NO_TREASURES")));
            return;
        }

        rows.add(formatRow(CONTAINER_LABELS.get("TREASURES") + ":"));
        for (Treasure treasure : this.treasures) {
            String treasureInfo = String.format("%s (%s: %d %s)",
                    treasure.getName(),
                    CONTAINER_LABELS.get("VALUE"),
                    treasure.getValue(),
                    CURRENCY);
            rows.add(formatRow(treasureInfo));
        }
        rows.add(formatRow(String.format("%s: %d %s",
                CONTAINER_LABELS.get("TOTAL_VALUE"),
                getTreasuresTotalValue(),
                CURRENCY)));
    }

    /**
     * Calculates the maximum width required for the box based on the length of the longest row.
     *
     * @param rows The list of rows to be displayed inside the box.
     * @return The maximum width required for the box.
     */
    private int calculateMaxWidth(List<String> rows) {
        int maxWidth = 0;
        for (String row : rows) {
            maxWidth = Math.max(maxWidth, row.length());
        }
        return maxWidth;
    }

    /**
     * Prints a box around the provided rows of text, adjusting the width to the widest row.
     * The box is printed with '+' at the corners and '-' for the horizontal lines.
     *
     * @param rows     The rows of text to enclose in the box.
     * @param boxWidth The calculated width of the box to ensure all text fits.
     */
    public void printBox(List<String> rows, int boxWidth) {
        PrintCollection.printLinesWithPlusCorners(boxWidth);
        for (int i = 0; i < rows.size(); i++) {
            System.out.print(rows.get(i));
            PrintCollection.printSpaces(boxWidth - rows.get(i).length() + 1);
            System.out.println("|");
            if (i == 0) {
                PrintCollection.printCharAmountOfTimes('+', 1);
                PrintCollection.printCharAmountOfTimes('-', boxWidth);
                System.out.println('+');
            }
        }
        PrintCollection.printLinesWithPlusCorners(boxWidth);
    }

    /**
     * Overloaded method to print player information without including treasures by default.
     *
     * @param title The title printed out on the title row in the box.
     */
    public void printPlayerInfo(String title) {
        printPlayerInfo(title, false);
    }

    /**
     * @return Total value of all player's treasures.
     */
    public int getTreasuresTotalValue() {
        return this.treasures.stream()
                .mapToInt(Treasure::getValue)
                .sum();
    }

    /**
     * @param treasure Treasure to be added to the player's treasure
     *                 array.
     */
    public void addTreasure(Treasure treasure) {
        this.treasures.add(treasure);
    }

    /**
     * Increment the total amount of monsters killed by one.
     */
    public void addMonsterKilled() {
        this.totalMonstersKilled++;
    }

    /**
     * An empty implementation of the printObject method from the base class.
     * Intended to be overridden by subclasses if specific print functionality is needed.
     */
    @Override
    public void printObject() {
        // Intentionally left empty.
    }
}