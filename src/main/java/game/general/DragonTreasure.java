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
import utils.VisualEffectManager;

import java.util.Scanner;

/**
 * Sets up the game by creating rooms with different attributes such as doors,
 * items etc.; asks the user of their current environment (IDE or UNIX terminal
 * / Windows CMD) and prompts the user for a player name. Initiates a
 * Dungeon-instance which starts the game. Handles the ending of the game.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public class DragonTreasure implements K
{
    /** Represents an array containing objects of the type "Room". */
    private final Room[] roomArr;

    /** An array of room descriptions. */
    private final String[] roomDescArr;

    /**
     * Represents a manager which takes care of visual effects (Clearing the
     * console in particular)
     */
    private final VisualEffectManager visualEffectManager;

    /** A scanner which is used mainly in this project to take user input. */
    private final Scanner scanner;

    /**
     * Constructor. Initializes roomArr with the pre-determined size;
     * roomDescArr is assigned to an array consisting of room descriptions; a
     * new instance of scanner is initiated with "System.in" given as an
     * argument for preparation for reading user input; a new instance of
     * VisualEffectManager is initiated.
     */
    public DragonTreasure()
    {
        this.roomArr = new Room[ROOM_AMOUNT];
        this.roomDescArr = K.roomDescArr;
        this.scanner = new Scanner(System.in);
        this.visualEffectManager = new VisualEffectManager();
    }

    /**
     * Sets up the game by assigning roomArr with Room-objects. The Room-objects
     * contain information about their attributes, such as which "Door"- and
     * "Item"-objects are assigned for them. The method also takes care of
     * starting a new game once one has been set up, and lastly handles the
     * ending of the game.
     *
     * @throws Exception If Door-instances are initiated with an invalid
     *                   position as an argument.
     */
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
         * New rooms are added to roomsArr. Each room is specified separately
         * with information relating to the specific individual room. Each Room
         * -object takes an array of Door-objects as a constructor. The Door
         * -objects take a position as an argument which are used to determine
         * the position of the doors in the particular room they are specified
         * in.
         *
         * The initialized Room-objects can also take other arguments aside from
         * a Door-array; such as Item-objects, a KeyRing-object and a Monster
         * -object.
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
                new Keyring(new Key(7)),
                new SaltaPinnar(),
                new Item[]{new Sword()},
                new Monster());
        this.roomArr[6] = new Room(new Door[]{new Door(w)},
                new Keyring(new Key(4)),
                new Kitten(),
                new Dragon());

        // Scrambles the room description array (roomDescArr) and then assigns
        // the descriptions to the rooms in roomArr.
        this.assignDescriptions(ArrayManipulator.scrambleArray(this.roomDescArr));

        /*
         * After all the rooms are initiated with their Doors above, the
         * doors have
         * to be connected with each other. This occurs in the code below.
         * The Door instances have a method "setPointsToRoom" in which the door
         * will be pointed to another room. The map layout further above
         * demonstrates how the Doors inside each room is connected.
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

        /*
         * Sorts all the doors in every room in roomArr in a determined
         * order. Used to make sure that the navigation menu later on shows the
         * available directions in the same order independent of in which order
         * the doors were initiated in the room and regardless of which room the
         * player is currently positioned in..
         */
        for (Room room : roomArr)
        {
            if (room.getDoors().length > 1) room.sortDoorsByDirection();
        }

        // If the user chooses to exit when prompted, the setupGame()-method
        // returns void.
        if (!this.environmentCheckPrompt()) return;

        // Stores the value returned by the startPrompt-method, as the name of
        // the player.
        String playerName = this.startPrompt();

        // Returns void if playerName is null. The returned null value means
        // that the user chose to quit the game (or possibly something else went
        // wrong)
        if (playerName == null) return;

        // A new instance of Player is initiated with playerName as an argument
        // to specify the name of the player.
        Player player = new Player(playerName);

        /*
         * Stores a reference to the created new instance of Dungeon with
         * player, a starting room (first one in the array in this case, though
         * any room works), a scanner and a reference to the
         * visualEffectManager-instance as arguments is.
         */
        Dungeon dungeon = new Dungeon(player,
                this.roomArr[0],
                e,
                this.scanner,
                this.visualEffectManager);

        dungeon.playGame();

        /*
         * The endGame-method which takes in a reference to a Dungeon- and a
         * Player-instance as arguments. Expected to be the last thing to run
         * and handles the ending of the program/game.
         */
        this.endGame(dungeon, player);
    }

    /**
     * Prints out the result of the game: A win- or lose-message with
     * corresponding ASCII-art and the player stats.
     *
     * @param dungeon The dungeon to be referred to for determining the
     *         game result (win/lose).
     * @param player The player which the statistics will refer to.
     */
    public void endGame(Dungeon dungeon, Player player)
    {
        if (dungeon.getGameWon()) PrintCollection.printWinASCII();
        else PrintCollection.printLoseASCII();

        System.out.println(
                "\n" + (dungeon.getGameWon() ? WIN_MSG : LOSE_MSG) + "\n");

        player.printPlayerInfo("Slutstatistik", true);

        this.scanner.close();
    }

    /**
     * Prompts the user for a player name and provides them with an option to
     * quit the program.
     *
     * @return Returns the name of the player inputted by the user, or null.
     *         Trims the beginning and the end of the returned string from
     *         spaces and replaces multiple spaces with just one from anywhere
     *         besides the beginning or the end of the string. Returns null if
     *         user decides to exit the program by providing the exit-command as
     *         input.
     * @throws Exception If something goes wrong with clearing the console.
     */
    private String startPrompt() throws Exception
    {
        String ans;

        do
        {
            PrintCollection.printDragonTreasureLogo();
            System.out.println(NAME_PROMPT_MSG);

            PrintCollection.printConsoleMarker();
            ans = scanner.nextLine().trim().replaceAll(" +", " ");

            this.visualEffectManager.clearConsole();

            if (ans.length() == 1 && ans.toLowerCase().charAt(0) ==
                    Command.EXIT_GAME.commandValue) return null;

        } while (ans.isBlank());

        return ans;
    }

    /**
     * Asks the user if they are running the program from an IDE or from a UNIX
     * terminal or Windows CMD. This is done to determine if console clearing
     * should be turned on or off, since console clearing doesn't necessarily
     * work when the program is run from an IDE.
     *
     * @return Returns false if the user chooses to exit the game. Returns true
     *         otherwise if the user chooses one of the two first provided
     *         alternatives.
     * @throws Exception If something goes wrong with clearing the console.
     */
    private boolean environmentCheckPrompt() throws Exception
    {
        while (true)
        {
            PrintCollection.printLinesWithPlusCorners();
            System.out.println(K.ENVIRONMENT_CHECK_MSG);
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
                        PrintCollection.printLinesWithPlusCorners(110);
                        this.visualEffectManager.setAnimation(false);
                        this.visualEffectManager.setClearConsole(false);
                        return true;
                    case TERMINAL_OPTION:
                        this.visualEffectManager.setAnimation(true);
                        this.visualEffectManager.setClearConsole(true);
                        this.visualEffectManager.clearConsole();
                        return true;
                    case EXIT_GAME:
                        return false;
                }
            }
        }
    }

    /**
     * Assigns descriptions to the room-array.
     *
     * @param roomDescArr Descriptions assigned to the room-array.
     */
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
