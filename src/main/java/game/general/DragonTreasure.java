package game.general;

import game.data.Command;
import game.data.K;
import game.data.PrintCollection;
import game.data.ValueManager;
import game.entities.Dragon;
import game.entities.Monster;
import game.entities.Player;
import game.items.*;
import game.items.consumables.HealthPotion;
import game.items.treasures.Kitten;
import game.items.treasures.SaltaPinnar;
import game.items.weapons.Grenade;
import game.items.weapons.Sword;
import utils.ArrayManipulator;
import utils.ConsoleCleaner;

import java.io.IOException;
import java.util.Scanner;

/*
Note that the DragonTreasure-class has no constructor (thus a default one
without any included properties will automatically be added) since no values
need to be inserted to the instance from the "outside".

The DragonTreasure class also implements the K-interface for some
constant-values.
*/
public class DragonTreasure implements K
{

    private final Room[] roomArr;
    private final String[] roomDescArr;
    private final Scanner scanner;

    public DragonTreasure()
    {
        this.roomArr = new Room[ROOM_AMOUNT];
        this.roomDescArr = K.roomDescArr;
        this.scanner = new Scanner(System.in);
    }


//  setupGame-method -- Sets up the game for launch, initializing the rooms,
//  connecting them with each other and thereafter starting a new game.
    public void setupGame() throws Exception
    {


/*
    ============================================================================
                               START OF MAP LAYOUT
    ============================================================================

      XX = EXIT                 :::::::::::::::::::       :::::::::::::::::::::
      && = LOCKED               ::               ::       ::                 ::
                                ::    Room 1     ...........    Room 2       ::
                                ::  roomsArr[0]  ...........   roomsArr[1]   ::
                                ::               ::       ::                 ::
                                :: (start room)  ::       :: (key to Room 6) ::
                                :: (grenade)   ::         ::                 ::
                                ::::::::  :::::::::       :::::::::::::::::::::
                                       :  :
                               ::::::::   ::::::::::        ::::::::::::::::::
                               ::                 ::        ::              ::
                               ::    Room 3       ::        ::    Room 4    ::
                               ::   roomsArr[2]   ..........&&  roomsArr[3] ::
                               ::                 ..........XX              ::
                               :: (health potion) ::        ::::::::::::::::::
                               ::                 ::
                               :::::::::  ::::::::::
     :::::::::::::::::::               :  :
     ::               ::        ::::::::  :::::::::        :::::::::::::::::::::
     ::    Room 6     ::        ::               ::        ::                 ::
     ::  roomsArr[5]  &&..........   Room 5      ............     Room 7      ::
     ::               &&..........  roomsArr[4]  ............   roomsArr[6]   ::
     ::   (monster)   ::        ::               ::        ::                 ::
     ::   (treasure)  ::        :::::::::::::::::::        ::   (dragon)      ::
     ::   (weapon)    ::                                   ::   (treasure)    ::
     ::               ::                                   :: (key to Room 4) ::
     :::::::::::::::::::                                   :::::::::::::::::::::

    ============================================================================
                               END OF MAP LAYOUT
    ============================================================================
*/


/*
        New rooms are added to roomsArr. Each room is specified separately due
        to fact that each Room takes a Door-array as a
        constructor argument.

        Each of the Rooms have their unique set of Doors with different
        attributes (more specifically the position pos, locked or unlocked and
        exit or regular/locked door) See the map layout above for a reference to
        how the map is expected to look like.
*/

        this.roomArr[0] =
                new Room(new Door[]{new Door(e), new Door(s)}, new Grenade());

        this.roomArr[1] =
                new Room(new Door[]{new Door(w)}, new Keyring(new Key(6)));

        this.roomArr[2] = new Room(new Door[]{new Door(n), new Door(s),
                new Door(e, true, true)});

        this.roomArr[3] = new Room(new Door[]{new Door(w)});

        this.roomArr[4] = new Room(new Door[]{new Door(n), new Door(w, true),
                new Door(e, true)}, new HealthPotion());

        this.roomArr[5] = new Room(new Door[]{new Door(e)},
                new Keyring(new Key (7)),
                new SaltaPinnar(),
                new Item[]{new Sword()},
                new Monster());

        this.roomArr[6] = new Room(new Door[]{new Door(w)},
                new Keyring(new Key(4)),
                new Kitten(),
                new Dragon());


//      Scrambles the room description array (roomDescArr) and then assigns the
//      descriptions to the rooms in roomArr.
        this.assignDescriptions(ArrayManipulator.scrambleArray(this.roomDescArr));


/*
        After all the rooms are initiated with their Doors above, the doors have
        to be connected with each other. This occurs in the code below.
        The Door instances have a method "setPointsToRoom" in which the door
        will be pointed to another room. The map layout further above
        demonstrates how the Doors inside each room is connected.
*/
        this.roomArr[0].getDoors()[0].setPointsToRoom(this.roomArr[1]);
        this.roomArr[0].getDoors()[1].setPointsToRoom(this.roomArr[2]);

        this.roomArr[1].getDoors()[0].setPointsToRoom(this.roomArr[0]);

        this.roomArr[2].getDoors()[0].setPointsToRoom(this.roomArr[0]);
        this.roomArr[2].getDoors()[1].setPointsToRoom(this.roomArr[4]);
        this.roomArr[2].getDoors()[2].setPointsToRoom(this.roomArr[3]);

        this.roomArr[3].getDoors()[0].setPointsToRoom(this.roomArr[6]);

        this.roomArr[4].getDoors()[0].setPointsToRoom(this.roomArr[2]);
        this.roomArr[4].getDoors()[1].setPointsToRoom(this.roomArr[5]);
        this.roomArr[4].getDoors()[2].setPointsToRoom(this.roomArr[6]);

        this.roomArr[5].getDoors()[0].setPointsToRoom(this.roomArr[4]);

        this.roomArr[6].getDoors()[0].setPointsToRoom(this.roomArr[4]);


//      Sorts all doors in every room in roomArr in a determined order.
        for (Room room : roomArr)
        {
            if (room.getDoors().length > 1) room.sortDoorsByDirection();
        }
/*
        The game "starts" officially and before the user enters the prompt
        inside the playGame-method of the Dungeon-instance, they will be
        prompted for their name in the startPrompt-method which is specified
        inside this class (DragonTreasure).

        The game.general.Dungeon-instance takes the players name, starting room
        and the entrance position as constructor arguments.
*/

        String playerName = this.startPrompt();

        if (playerName == null) return;

        Player player = new Player(playerName);

        Dungeon dungeon = new Dungeon(player, this.roomArr[0], e, scanner);
        dungeon.playGame();


//      The endGame-method below is called after the user exits the
//      Dungeon-instance
        this.endGame(dungeon, player);
    }


//  The sole purpose of endGame is to print a message when the game ends.
    public void endGame(Dungeon dungeon, Player player) throws IOException
    {
        if (dungeon.getGameWon()) PrintCollection.printWinASCII();
        else PrintCollection.printLoseASCII();

        System.out.println(
                "\n" + (dungeon.getGameWon() ? WIN_MSG : LOSE_MSG) + "\n");


        player.printPlayerInfo("Slutstatistik", true);

        this.scanner.close();
    }


/*
    The startPrompt-method prompts the user for their name and returns the
    value, as long as the user doesn't input an empty string or presses
    "Enter" without other input, in which case the loop just starts over until
    it receives proper input (a string)
*/
    private String startPrompt()
    {

        if (!environmentCheckPrompt()) return null;

        String ans;

        do
        {
            PrintCollection.printDragonTreasureLogo();
            System.out.println(NAME_PROMPT_MSG);

            PrintCollection.printConsoleMarker();
            ans = scanner.nextLine().trim().replaceAll(" +", " ");

            ConsoleCleaner.clearConsole();

            if (ans.length() == 1 && ans.toLowerCase().charAt(0) == Command.EXIT_GAME.commandValue)
            {
                return null;
            }


        } while (ans.isBlank());


        return ans;

    }

    private boolean environmentCheckPrompt()
    {
        while (true)
        {
            PrintCollection.printLinesWithPlusCorners();

            System.out.printf("Jag kör programmet via en IDE [%c]\n" +
                            "Jag kör programmet via en Unix terminal eller Windows CMD [%c]\n"
                    , Command.IDE_OPTION.commandValue, Command.TERMINAL_OPTION.commandValue);

            System.out.println();
            PrintCollection.printEscapeOption();

            PrintCollection.printConsoleMarker();

            String ansString = this.scanner.nextLine().trim().toLowerCase();

            if (ansString.isBlank())
            {
                continue;
            }

            char ansChar = ansString.charAt(0);

            Command charCommand = ValueManager.getCommandValueWithChar(ansChar);

            if (charCommand != null)
            {
                switch (charCommand)
                {
                    case IDE_OPTION:
                        ConsoleCleaner.setClear_console_enabled(false);
                        PrintCollection.printLinesWithPlusCorners(110);
                        return true;
                    case TERMINAL_OPTION:
                        ConsoleCleaner.setClear_console_enabled(true);
                        ConsoleCleaner.clearConsole();
                        return true;
                    case EXIT_GAME:
                        return false;
                }
            }

        }
    }


//  Assigns descriptions for all the rooms in roomArr. Takes in a description
//  array as an argument so that scrambled arrays can be utilized also.
    private void assignDescriptions(String[] roomDescArr)
    {
        if (roomDescArr.length >= this.roomArr.length)
        {
            for (int i = 0; i < this.roomArr.length; i++)
            {
                this.roomArr[i].setNarrative(roomDescArr[i]);
            }
        }
    }


}
