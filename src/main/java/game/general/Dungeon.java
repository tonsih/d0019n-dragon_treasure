package game.general;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.data.ValueManager;
import game.entities.Player;
import game.interfaces.Printable;
import game.items.*;
import game.items.consumables.Consumable;
import utils.VisualEffectManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the player navigation and the core gameplay (apart from
 * the monster battles) as well as the UI related to the gameplay.
 */
public class Dungeon implements K
{
    /**
     * Stores a reference to the player. Represents an entity which the user
     * navigates and plays the game with.
     */
    private final Player player;

    /**
     * Represents the room in which the player currently resides.
     */
    private Room currentRoom;

    /**
     * Stores the entrance position. Determines the direction in which the user
     * has to go when the game starts.
     */
    private final char entrancePos;

    /**
     * Stores a reference to a {@code Scanner}-instance. The purpose is to take
     * and store data inputted by the user.
     */
    private final Scanner scanner;

    /**
     * Stores a reference to a {@code VirtualEffectManager}-instance. Used for
     * visual effects (clearing the console between pre-determined stages)
     */
    private final VisualEffectManager visualEffectManager;

    /**
     * Stores the game result (game won/lost).
     */
    private boolean gameWon;

    /**
     * @param player The player-entity for the game.
     * @param startingRoom The room in which the player starts the
     *         game.
     * @param entrancePos The position of the first entrance.
     * @param scanner A scanner, used to take user input in this case.
     * @param visualEffectManager Used to take care of clearing the
     *         console.
     */
    public Dungeon(Player player,
                   Room startingRoom,
                   char entrancePos,
                   Scanner scanner,
                   VisualEffectManager visualEffectManager)
    {
        this.player = player;
        this.currentRoom = startingRoom;
        this.entrancePos = entrancePos;
        this.scanner = scanner;
        this.visualEffectManager = visualEffectManager;
        this.gameWon = false;
    }

    /**
     * @return {@code true} if gameWon is {@code true}. Returns {@code false}
     * otherwise.
     */
    public boolean getGameWon()
    {
        return this.gameWon;
    }

    /**
     * Navigation and core gameplay is handled in this method (apart from the
     * battles)
     *
     * @throws Exception If something goes wrong while clearing the console.
     */
    public void playGame() throws Exception
    {
        // Returns void if the player chose to quit the game.
        if (!this.welcomePrompt()) return;

        /*
         * Stores a boolean value determining if the roomDescription should be
         * printed out during the next iteration, or not.
         */
        boolean printRoomDescEnabled = true;

        whileLoop:
        while (true)
        {
            if (this.currentRoom.hasMonster())

                // Start a battle and return "void" if player loses the battle.
                if (!this.currentRoom.doBattle(this.player,
                        this.scanner,
                        this.visualEffectManager)) return;

            /*
             * Handle finding the treasure appropriately with the findTreasure-
             * method if the current room has a treasure inside of it.
             */
            if (this.currentRoom.hasTreasure()) this.findTreasure();

            /*
             * Print the current room description if there's no treasure in
             * the current room and printRoomDescEnabled is true.
             */
            else if (printRoomDescEnabled) this.currentRoom.printRoomDesc();

            /*
             * Assign printRoomDescEnabled to true in the beginning of every
             * iteration of whileLoop.
             */
            printRoomDescEnabled = true;

            // Prints an empty line for aesthetic purposes.
            System.out.println();

            // Prints a box with player information, such as HP, Max. DMG etc.
            this.player.printPlayerInfo(K.CONTAINER_LABELS.get(
                    "PLAYER_INFORMATION"));

            // Prints out the ID of the current room.
            System.out.printf("\n(Du befinner dig i %s)\n",
                    this.currentRoom.getRoomIDString());
            System.out.println();

            this.printItemsInRoom();
            this.printConsumablesOnPlayer();
            this.printAvailableDoors();

            System.out.println();
            PrintCollection.printEscapeOption();
            PrintCollection.printConsoleMarker();

            String ansStr = scanner.nextLine().trim().toLowerCase();

            this.visualEffectManager.clearConsole();
            PrintCollection.printLinesWithPlusCorners();

            if (ansStr.isBlank()) continue;

            char ansChar = ansStr.charAt(0);
            this.player.useConsumablesWithCommand(ansChar);
            Command commandValueOfAns =
                    ValueManager.getCommandValueWithChar(ansChar);
            if (commandValueOfAns != null)
            {
                switch (commandValueOfAns)
                {
                    case EXIT_GAME:
                        this.visualEffectManager.clearConsole();
                        break whileLoop;
                    case PICKUP_ITEM:
                        if (this.currentRoom.getItems().size() > 0 ||
                                this.currentRoom.getKeyring().size() > 0)
                        {
                            printRoomDescEnabled = false;
                            this.processPickedUpItems();
                            continue;
                        }
                }
            }
            else continue;

            /*
             * Checks if the entered value is a direction before the loop below
             * which iterates through the doors in currentRoom. There's no need
             * to iterate through the doors and check for input which
             * corresponds with the door position, if the inputted value is not
             * a direction.
             */
            if (!ValueManager.charIsDirection(ansChar)) continue;


            // Loops through all the available doors in the current room.
            for (Door currentDoor : this.currentRoom.getDoors())
            {

                /*
                 * Checks if user input corresponds with the location of a door
                 * in the current room
                 */
                if (ansChar == currentDoor.getPosition())
                {

                    /*
                     * If the door is not locked or the player has a key to the
                     * room in which the door is pointing at.
                     */
                    if (!currentDoor.isLocked() ||
                            this.player.hasKeyForRoom(currentDoor.getPointsToRoom()))
                    {

                        if (currentDoor.isExit())
                        {
                            if (this.player.isAlive()) this.gameWon = true;
                            return;
                        }

                        // Move to another room.
                        this.move(currentDoor.getPointsToRoom());

                        /*
                         * Decide to show the room description of a new room.
                         * When the player has moved.
                         */
                        break;

                    // If the door is locked and the player has no key.
                    } else
                    {
                        printRoomDescEnabled = false;

                        if (currentDoor.getPointsToRoom().hasTreasure())
                        {
                            PrintCollection.printTreasureChest();
                            System.out.println(
                                    "Du kikar genom nyckelhålet och ser en " +
                                            "kista med " +
                                            currentDoor.getPointsToRoom()
                                                    .getTreasure()
                                                    .getLockedDesc()
                                                    .toLowerCase());
                        } else
                            System.out.println("Du har ingen nyckel som " +
                                    "passar.");
                    }
                }
            }
        }
    }

    /**
     * Move to another room.
     *
     * @param room The room which becomes the new current room when
     *         moved.
     */
    private void move(Room room)
    {
        this.currentRoom = room;
    }

    /**
     * A prompt which welcomes the player during the start of the game. Excepts
     * the user to exit a cave in a pre-determined direction.
     *
     * @return {@code true} if the player enters the cave. {@code false}
     *         otherwise, if the
     *         player chooses to exit the game.
     * @throws Exception If something goes wrong while clearing the console.
     */
    private boolean welcomePrompt() throws Exception
    {
        while (true)
        {
            PrintCollection.printLinesWithPlusCorners();

            String enterCaveOptionString =
                    String.format("Gå in i grottan [%c]", this.entrancePos);
            System.out.printf("Välkommen %s till din skattjakt.\n" +
                            "Du står utanför en grotta. Det luktar svavel " +
                            "från öppningen.\n" + "Grottöppningen är %s.\n\n" + "%s\n" +
                            "%s\n",
                    this.player.getName(),
                    ValueManager.generatePosString(this.entrancePos)
                            .toLowerCase(),
                    enterCaveOptionString,
                    K.ESCAPE_OPTION_MSG);

            PrintCollection.printConsoleMarker();
            String ans = scanner.nextLine();
            this.visualEffectManager.clearConsole();
            if (ans.isBlank()) continue;

            char ansChar = ans.trim().toLowerCase().charAt(0);
            if (ansChar == this.entrancePos) break;
            else if (ansChar == Command.EXIT_GAME.commandValue)
            {
                this.visualEffectManager.clearConsole();
                return false;
            }
        }
        PrintCollection.printLinesWithPlusCorners();
        System.out.println(K.LEFT_CAVE_MSG);
        return true;
    }

    /**
     * Applies an effect specific for the treasure on the player. Prints the
     * value of the treasure and removes the treasure from the room.
     */
    private void findTreasure()
    {
        this.currentRoom.getTreasure().applyEffect(this.player);
        System.out.printf("%s: %d %s\n",
                CONTAINER_LABELS.get("VALUE"),
                this.currentRoom.getTreasure().getValue(),
                CURRENCY
                );
        this.currentRoom.removeTreasure();
    }

    /**
     * Prints all the items (including the keys in the room's keyring) of the
     * current room.
     */
    private void printItemsInRoom()
    {
        ArrayList<Item> tempItems =
                new ArrayList<>(this.currentRoom.getItems());
        tempItems.addAll(this.currentRoom.getKeyring().getKeys());
        for (Item item : tempItems)
        {
            System.out.printf("Du ser %s på golvet. Ta upp föremålet [%c]\n",
                    item.getName().toLowerCase(),
                    Command.PICKUP_ITEM.commandValue);
        }
    }

    /**
     * Prints all the consumables which the player possesses.
     */
    public void printConsumablesOnPlayer()
    {
        for (Consumable consumable : this.player.getConsumables())
        {
            System.out.printf("Du kan förbruka %s [%c]\n",
                    consumable.getName().toLowerCase(),
                    consumable.getUseCommand());
        }
    }

    /**
     * Prints the directions in which the player can go in from the current room
     * and information regarding the doors - if they are unlocked, locked, lead
     * to an exit or both.
     *
     * @throws Exception If a {@code Door}-object with an invalid direction
     *                   exists.
     */
    private void printAvailableDoors() throws Exception
    {

        for (Door currentDoor : this.currentRoom.getDoors())
        {
            char doorPositionChar = currentDoor.getPosition();
            String doorPositionStr =
                    ValueManager.generatePosString(doorPositionChar)
                            .toLowerCase();

            if (currentDoor.isLocked())
            {
                String tempStr = "Du ser en låst dörr";
                if (currentDoor.isExit()) tempStr += " mot en utgång";
                System.out.printf("%s i %s [%c]",
                        tempStr,
                        doorPositionStr,
                        doorPositionChar);

                if (this.player.hasKeyForRoom(currentDoor.getPointsToRoom()))
                {
                    System.out.print(" (Du har en nyckel till dörren!)");
                }
            } else
            {
                if (currentDoor.isExit()) System.out.printf(
                        "Du ser en utgång %sut [%c]",
                        doorPositionStr,
                        doorPositionChar);
                else System.out.printf("Du kan gå %sut [%c]",
                        doorPositionStr,
                        doorPositionChar);
            }
            System.out.println();
        }
    }

    /**
     * Handles the processing of the items in the current room, including
     * {@code Key}-objects in the room's keyring if the player has chosen to
     * pick them up.
     *
     * @throws Exception If something goes wrong while clearing the console.
     */
    private void processPickedUpItems() throws Exception
    {
        this.visualEffectManager.clearConsole();
        if (this.visualEffectManager.isClearConsoleEnabled())
        {
            PrintCollection.printLinesWithPlusCorners();
        }
        ArrayList<Item> items = new ArrayList<>(this.currentRoom.getItems());
        items.addAll(this.currentRoom.getKeyring().getKeys());
        for (Item item : items)
        {
            if (item instanceof Printable) ((Printable) item).printObject();

            System.out.printf("Du tar upp %s\n", item.getName().toLowerCase());
            System.out.printf("\nBeskrivning:\n%s\n", item.getItemDesc());

            if (!(item instanceof Consumable))
            {
                this.player.addItem(item);
                if (item.instantlyAffectsOnPickup()) item.applyEffect(this.player);
            } else this.player.addConsumable((Consumable) item);

            PrintCollection.printLinesWithPlusCorners();
        }
        this.currentRoom.clearRoomFromItemsAndKeys();
    }
}