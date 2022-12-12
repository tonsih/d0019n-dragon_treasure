package game.general;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.data.ValueManager;
import game.entities.Player;
import game.interfaces.Printable;
import game.items.*;
import game.items.consumables.Consumable;
import game.items.treasures.Treasure;
import utils.ConsoleCleaner;
import utils.StringManipulator;

import java.util.ArrayList;
import java.util.Scanner;

public class Dungeon implements K
{

    //  player-variable -- Stores the player.
    private final Player player;


    //  currentRoom-variable -- Stores the current room in which the player
    //  is in.
    private Room currentRoom;


    //  entrancePos-variable -- Stores the entrance position.
    private final char entrancePos;

    private final Scanner scanner;

    private boolean gameWon;


    /*
        game.general.Dungeon-constructor -- Takes the player, starting room
        and the
        entrance position as constructor arguments and assigns those values
        to the
        corresponding values represented by the instance-variables.
    */
    public Dungeon(Player player,
                   Room startingRoom,
                   char entrancePos,
                   Scanner scanner)
    {
        this.player = player;
        this.currentRoom = startingRoom;
        this.entrancePos = entrancePos;
        this.scanner = scanner;

    }

    public boolean getGameWon()
    {
        return this.gameWon;
    }


    /*
        playGame-method -- Contains the integral part of the game. Most of the
        gameplay occurs inside this function, aside from what happens inside the
        startPrompt- and the endGame-method from the DungeonTreasure-instance.
    */
    public void playGame() throws Exception
    {

//      welcomePrompt is called in the beginning. Read more about it in the
//      commented description included above the method itself.
        if (!this.welcomePrompt()) return;

        boolean narrativeFlag = true;

        whileLoop:
        while (true)
        {

            if (this.currentRoom.hasMonster())
            {
                if (!this.currentRoom.doBattle(this.player, this.scanner)) return;
            }

            if (this.currentRoom.hasTreasure())
            {
                this.findTreasure();
            }

            else if (narrativeFlag)
            {
                this.currentRoom.doNarrative();
            }

            narrativeFlag = false;

            System.out.println();

            this.player.printPlayerInfo("Spelarinformation");

            System.out.printf("\n(Du befinner dig i %s)\n", this.currentRoom.getRoomIdString());
            System.out.println();
            this.printItemsInRoom();
            this.printConsumablesOnPlayer();
            this.printAvailableDoors();
            System.out.println();
            PrintCollection.printEscapeOption();
            System.out.println();

            PrintCollection.printConsoleMarker();

            String ansStr = scanner.nextLine().trim().toLowerCase();

            ConsoleCleaner.clearConsole();

            PrintCollection.printLinesWithPlusCorners();



//          If the user inputs only whitespace or presses enter without other
//          input, the loop starts all over from the beginning.
            if (ansStr.isBlank())
            {
                continue;
            }


            char ansChar = ansStr.charAt(0);

            this.player.useConsumablesWithCommand(ansChar);


            Command commandValueOfAns =
                    ValueManager.getCommandValueWithChar(ansChar);


            if (commandValueOfAns != null)
            {
                switch (commandValueOfAns)
                {
                    case EXIT_GAME:
                        ConsoleCleaner.clearConsole();
                        break whileLoop;
                    case PICKUP_ITEM:
                        if (this.currentRoom.getItems().size() > 0 ||
                                this.currentRoom.getKeyring().size() > 0)
                        {
                            this.processPickedUpItems();
                            continue;
                        }

                }

            } else
            {
                continue;
            }

/*
          Checks if the entered value is a direction before the loop below
          which iterates through the doors in currentRoom. There's no need to
          iterate through the doors and check for input which corresponds with
          the door position, if the inputted value is not a direction.
*/
            if (!ValueManager.charIsDirection(ansChar))
            {
                continue;
            }


//          Loops through all the available doors in the room.
            for (Door currentDoor : this.currentRoom.getDoors())
            {

//              Checks if user input corresponds with the location of a door
//              in the current room
                if (ansChar == currentDoor.getPosition())
                {
/*
                    Checks if a door in the current room is an exit; is locked
                    or is available and neither locked nor an exit. Note that
                    due to the order of operations, the actions taken from the
                    fact that the door is an exit has precedence over the
                    actions taken if the door is locked. This is done on purpose
                    and is a preventive measure against situations where all
                    doors which are exits, are also locked. This measure
                    eliminates the chance that there's no exit in the previously
                    mentioned scenario.

                    If the door is an exit, the loop is exited (i.e. the
                    playGame-method is exited)

                    If the door is not locked (and not an exit) then the current
                    room will become the one to which the door points to.

                    If the door is locked a message will be printed and the loop
                    starts from the beginning
*/

                    if (!currentDoor.isLocked() ||
                            this.player.hasKeyForRoom(currentDoor.getPointsToRoom()))
                    {

                        if (currentDoor.isExit())
                        {
                            if (this.player.isAlive()) this.gameWon = true;
                            return;
                        }

                        this.currentRoom = currentDoor.getPointsToRoom();
                        narrativeFlag = true;
                        break;

                    } else
                    {


                        if (currentDoor.getPointsToRoom().hasTreasure())
                        {
                            PrintCollection.printTreasureChest();
                            System.out.println(
                                    "Du kikar genom nyckelhålet och ser en kista med " +
                                            currentDoor.getPointsToRoom()
                                                    .getTreasure()
                                                    .getLockedDesc()
                                                    .toLowerCase());
                        } else
                        {
                            System.out.println("Du har ingen nyckel som passar.");
                        }


                    }
                }
            }


        }
    }


    /*
        welcomePrompt-method -- Prints out a message welcoming the player and
         then
        prompts the user to enter a specific character for entering i.e.
        exiting the
        loop and the welcomePrompt-method thereafter.
    */
    private boolean welcomePrompt() throws Exception
    {

        while (true)
        {
            PrintCollection.printLinesWithPlusCorners();

            String enterCaveOptionString = String.format("Gå in i grottan [%c]", this.entrancePos);
            String quitGameOptionString = String.format("Avsluta spelet [%c]", Command.EXIT_GAME.commandValue);

            String welcomeMsg = String.format(
                    "Välkommen %s till din skattjakt.\n" +
                            "Du står utanför en grotta. Det luktar svavel från öppningen.\n" +
                            "Grottöppningen är %s.\n\n" +
                            "%s\n" +
                            "%s\n",
                    this.player.getName(),
                    ValueManager.generatePosString(this.entrancePos)
                            .toLowerCase(),
                    enterCaveOptionString,
                    quitGameOptionString
            );


            System.out.println(welcomeMsg);

            PrintCollection.printConsoleMarker();
            String ans = scanner.nextLine();

            ConsoleCleaner.clearConsole();

            if (ans.isBlank()) continue;

            char ansChar = ans.trim().toLowerCase().charAt(0);

            if (ansChar == this.entrancePos) break;

            if (ansChar == Command.EXIT_GAME.commandValue)
            {
                ConsoleCleaner.clearConsole();
                return false;
            }

        }

        PrintCollection.printLinesWithPlusCorners();
        System.out.println(K.LEFT_CAVE_MSG);
        return true;
    }


    //  Prints out a message and the current room won't contain the treasure
    //  anymore
    private void findTreasure()
    {
        this.currentRoom.getTreasure().applyEffect(this.player);
        System.out.printf("Värde: %d Guld\n", this.currentRoom.getTreasure().getGoldValue());
        this.currentRoom.removeTreasure();
    }


    private void printItemsInRoom()
    {
        ArrayList<Item> tempItems = new ArrayList<>(this.currentRoom.getItems());
        tempItems.addAll(this.currentRoom.getKeyring().getKeys());

        for (Item item : tempItems)
        {
            System.out.printf("Du ser %s på golvet. Ta upp föremålet [%c]\n",
                    item.getName().toLowerCase(),
                    Command.PICKUP_ITEM.commandValue);
        }

    }


    public void printConsumablesOnPlayer()
    {
        for (Consumable consumable : this.player.getConsumables())
        {

            System.out.printf(
                    "Du kan förbruka %s [%c]\n",
                    consumable.getName()
                            .toLowerCase(),
                    consumable.getUseCommand());
        }

    }


/*
    printAvailableDoors-method -- Prints the doors which are available for the
    current room.

    Prints out different output depending on the state of the door's attributes.
    If the door is locked; a different string will be printed. The same applies
    if the door is an exit: a different string from the default will be printed.
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

//            System.out.printf(" - Leder till rum %d", currentDoor.getPointsToRoom().getRoomId());

            System.out.println();
        }

    }


    public static void printEscapeOption()
    {
        System.out.printf("Avsluta spelet [%c]\n",
                Command.EXIT_GAME.commandValue);
    }



    private void processPickedUpItems()
    {
        ConsoleCleaner.clearConsole();
        PrintCollection.printLinesWithPlusCorners();

        ArrayList<Item> items = new ArrayList<>(this.currentRoom.getItems());
        items.addAll(this.currentRoom.getKeyring().getKeys());

        for (Item item : items)
        {
            if (item instanceof Printable)
            {
                ((Printable) item).printObject();
            }

            System.out.printf("Du tar upp %s\n", item.getName().toLowerCase());
            System.out.printf("\nBeskrivning:\n%s\n", item.getItemDesc());

            if (!(item instanceof Consumable))
            {
                this.player.addItem(item);

                if (item.instantlyAffectsOnPickup())
                {
                    item.applyEffect(this.player);
                }
            } else
            {
                this.player.addConsumable((Consumable) item);
            }

            PrintCollection.printLinesWithPlusCorners();

        }

        this.currentRoom.clearRoomFromItemsAndKeys();
    }





}