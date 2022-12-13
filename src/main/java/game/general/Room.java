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

// The game.general.Room-class contains information about a room.
public class Room implements K
{
    private static int roomCount = 0;

    //  roomDesc-variable -- Stores the room description.
    private String roomDesc;

    // Unique identifier for the room.
    private final int roomId;


    //  doorsArr-variable -- Stores an array of doors.
    private final Door[] doorsArr;


    private final Keyring keyring;

    private Treasure treasure;

    private ArrayList<Item> items;

    private Monster monster;



    //  game.general.Room constructor -- Takes a room description and a door
    //  array as
    //  arguments
//  assigns them to the corresponding variables in the game.general.Room object.
    public Room(String roomDesc,
                Door[] doorsArr,
                Keyring keyring,
                Treasure treasure,
                ArrayList<Item> items,
                Monster monster)
    {
        this.roomDesc = roomDesc;
        this.doorsArr =
                Objects.requireNonNullElseGet(doorsArr, () -> new Door[0]);
        this.keyring = Objects.requireNonNullElseGet(keyring, Keyring::new);
        this.treasure = treasure;
        this.items = Objects.requireNonNullElseGet(items, ArrayList::new);
        this.monster = monster;
        this.roomId = generateRoomId();


    }

    void sortDoorsByDirection()
    {
        Arrays.sort(this.doorsArr,
                Comparator.comparingInt(door -> DIRECTION_ORDER.indexOf(door.getPosition())));
    }

    public Room(Door[] doorsArr, Keyring keyring)
    {
        this(null, doorsArr, keyring, null, null, null);
    }


    public Room(Door[] doorsArr)
    {
        this(null, doorsArr, null, null, null, null);
    }

    public Room(Door[] doorsArr, ArrayList<Item> items)
    {
        this(null, doorsArr, null, null, items, null);
    }

    public Room(Door[] doorsArr, Item item)
    {
        this(null, doorsArr, null, null, new ArrayList<>(List.of(item)), null);
    }


    public Room(Door[] doorsArr, Treasure treasure, Item[] items, Monster monster)
    {
        this(null, doorsArr, null, treasure,
                new ArrayList<>(Arrays.asList(items)), monster);
    }

    public Room(Door[] doorsArr, Keyring keyring, Treasure treasure, Item[] items, Monster monster)
    {
        this(null, doorsArr, keyring, treasure,
                new ArrayList<>(Arrays.asList(items)), monster);
    }

    public Room(Door[] doorsArr, Treasure treasure, Item item, Monster monster)
    {
        this(null, doorsArr, null, treasure, new ArrayList<>(List.of(item)), monster);
    }

    public Room(Door[] doorsArr, Treasure treasure, Monster monster)
    {
        this(null, doorsArr, null, treasure, null, monster);
    }

    public Room(Door[] doors, Keyring keyring, Treasure treasure, Monster monster)
    {
        this(null, doors, keyring, treasure, null, monster);
    }


    //  printRoomDesc-method -- Prints out the room description of the current
    //  Room-object.
    public void printRoomDesc()
    {
        System.out.println(this.roomDesc);
    }

    public String getRoomDesc()
    {
        return this.roomDesc;
    }


    public int getRoomId()
    {
        return roomId;
    }

    public String getRoomIdString()
    {
        return String.format("rum nummer %d", this.roomId);
    }

    //  getDoors-method -- Returns the door array of the game.general.Room
    //  object.
    public Door[] getDoors()
    {
        return this.doorsArr;
    }


    //  hasTreasure-method -- Returns a boolean-value telling if the room has a
//  treasure in it.
    public boolean hasTreasure()
    {
        return treasure != null;
    }

    public Treasure getTreasure()
    {
        return this.treasure;
    }


    public Keyring getKeyring()
    {
        return this.keyring;
    }

    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    public boolean hasMonster()
    {
        return this.monster != null;
    }

    public void setNarrative(String roomDesc)
    {
        this.roomDesc = roomDesc;
    }


    public void addTreasure(Treasure treasure)
    {
        this.treasure = treasure;
    }

    public void removeTreasure()
    {
        this.treasure = null;
    }

    public void removeKeyring()
    {
        this.keyring.removeKeys();
    }

    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public void removeItem(Item item)
    {
        this.items.remove(item);
    }

    public void clearRoomFromItems()
    {
        this.items = new ArrayList<>();
    }

    public void clearRoomFromItemsAndKeys()
    {
        clearRoomFromItems();
        this.removeKeyring();
    }

    public boolean doBattle(Player player, Scanner scanner, VisualEffectManager visualEffectManager)
            throws Exception
    {
        boolean battleResult = new Battle(player, this.monster, scanner, visualEffectManager).newBattle();

        if (battleResult)
        {
            this.monster = null;
            return true;
        }

        return false;
    }

    private static int generateRoomId()
    {
        return ++roomCount;
    }

}
