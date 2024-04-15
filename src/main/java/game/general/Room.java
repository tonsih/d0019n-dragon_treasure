package game.general;

import game.battle.Battle;
import game.data.K;
import game.entities.Monster;
import game.entities.Player;
import game.items.Item;
import game.items.Keyring;
import game.items.treasures.Treasure;
import utils.VisualEffectManager;

import java.util.*;

/**
 * This class represents one room. Every room has a unique ID and a room can
 * have various attributes, such as a room description, connected doors, a
 * monster, items, a treasure etc...
 */
public class Room implements K {
    /**
     * Amount of rooms created. Used to identify each room with a unique ID.
     */
    private static int roomCount = 0;

    /**
     * A unique ID for the room
     */
    private final int roomID;
    /**
     * Stores an array of doors.
     */
    private final Door[] doors;
    /**
     * A room has its own keyring which can contain zero to many keys.
     */
    private final Keyring keyring;
    /**
     * Stores a room description.
     */
    private String roomDesc;
    /**
     * Room's treasure. Expected to be null if there's no treasure in the room.
     */
    private Treasure treasure;

    /**
     * Stores the room's items.
     */
    private ArrayList<Item> items;

    /**
     * Represents the monster in the room, if one exists.
     */
    private Monster monster;

    /**
     * @param roomDesc A room description.
     * @param doors    Doors connected to the room.
     * @param keyring  Keyring for the room.
     * @param treasure The room's treasure.
     * @param items    Items in the room.
     * @param monster  A monster in the room.
     */
    public Room(String roomDesc,
                Door[] doors,
                Keyring keyring,
                Treasure treasure,
                ArrayList<Item> items,
                Monster monster) {
        this.roomID = generateRoomID();
        this.roomDesc = roomDesc;
        if (doors.length > DIRECTIONS.size())
            throw new IllegalArgumentException(String.format(
                    "The max amount of doors war exceeded (%d)",
                    DIRECTIONS.size()));
        this.doors = Objects.requireNonNullElseGet(doors, () -> new Door[0]);
        this.keyring = Objects.requireNonNullElseGet(keyring, Keyring::new);
        this.treasure = treasure;
        this.items = Objects.requireNonNullElseGet(items, ArrayList::new);
        this.monster = monster;
    }

    public Room(Door[] doors, Keyring keyring) {
        this(null, doors, keyring, null, null, null);
    }

    public Room(Door[] doors) {
        this(null, doors, null, null, null, null);
    }

    public Room(Door[] doors, Item item) {
        this(null, doors, null, null, new ArrayList<>(List.of(item)), null);
    }

    public Room(Door[] doors,
                Keyring keyring,
                Treasure treasure,
                Item[] items,
                Monster monster) {
        this(null,
                doors,
                keyring,
                treasure,
                new ArrayList<>(Arrays.asList(items)),
                monster);
    }

    public Room(Door[] doors,
                Keyring keyring,
                Treasure treasure,
                Monster monster) {
        this(null, doors, keyring, treasure, null, monster);
    }

    /**
     * @return A unique roomID
     */
    private static int generateRoomID() {
        return ++roomCount;
    }

    /**
     * Sorts the doors in the room according to their direction in a
     * pre-determined order.
     */
    public void sortDoorsByDirection() {
        Arrays.sort(this.doors,
                Comparator.comparingInt(door -> DIRECTION_ORDER.indexOf(door.getPosition())));
    }

    /**
     * Starts a battle starts in the room.
     *
     * @param player              The player of the game.
     * @param scanner             A scanner used for user input.
     * @param visualEffectManager Used for visual effects i.e. clearing
     *                            the console.
     * @return {@code true} if player wins, otherwise {@code false}.
     * @throws Exception If something goes wrong while clearing the console.
     */
    public boolean doBattle(Player player,
                            Scanner scanner,
                            VisualEffectManager visualEffectManager)
            throws Exception {
        boolean battleResult = new Battle(player,
                this.monster,
                scanner,
                visualEffectManager).doBattle();

        if (battleResult) {
            this.monster = null;
            return true;
        }
        return false;
    }

    /**
     * Prints out the description for the room.
     */
    public void printRoomDesc() {
        System.out.println(this.roomDesc);
    }

    /**
     * @return The room's ID.
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * @return A string with the room's ID included.
     */
    public String getRoomIDString() {
        return String.format("rum nummer %d", this.roomID);
    }

    /**
     * @return Doors connected to the room.
     */
    public Door[] getDoors() {
        return this.doors;
    }

    /**
     * @return Room's keyring.
     */
    public Keyring getKeyring() {
        return this.keyring;
    }

    /**
     * @return Room's treasure.
     */
    public Treasure getTreasure() {
        return this.treasure;
    }

    /**
     * @return Items in the room.
     */
    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * @return {@code true} if a treasure exists in the room, {@code false}
     * otherwise.
     */
    public boolean hasTreasure() {
        return treasure != null;
    }

    /**
     * @return {@code true} if a monster exists in the room, {@code false}
     * otherwise.
     */
    public boolean hasMonster() {
        return this.monster != null;
    }

    /**
     * A setter for the room description.
     *
     * @param roomDesc The description for the room.
     */
    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    /**
     * Removes all the keys from the room's keyring.
     */
    public void removeKeyring() {
        this.keyring.removeKeys();
    }

    /**
     * Removes the treasure from the room.
     */
    public void removeTreasure() {
        this.treasure = null;
    }

    /**
     * Clears room from items.
     */
    public void clearRoomFromItems() {
        this.items = new ArrayList<>();
    }

    /**
     * Clears room from items and empties keyring belonging to the room.
     */
    public void clearRoomFromItemsAndKeys() {
        clearRoomFromItems();
        this.removeKeyring();
    }
}