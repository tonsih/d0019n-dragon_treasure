package game.entities;

import game.general.Room;
import game.interfaces.Printable;
import game.items.Item;
import game.items.Key;
import game.items.Keyring;
import game.items.consumables.Consumable;

import java.util.ArrayList;

/**
 * Represents an entity in the game with various attributes expected to be
 * inherited by non-abstract classes.
 */
public abstract class Entity implements Printable
{
    /**
     * Name of the entity.
     */
    protected final String name;

    /**
     * The entity's health in points.
     */
    protected int healthPoints;

    /**
     * The maximal damage output of the entity.
     */
    protected int maxDamage;

    /**
     * Description of the entity.
     */
    private final String desc;

    /**
     * {@code true} if the entity is alive. Otherwise {@code false}.
     */
    private boolean isAlive;

    /**
     * The items which the entity possesses.
     */
    private final ArrayList<Item> items;

    /**
     * The consumables which the entity possesses.
     */
    private final ArrayList<Consumable> consumables;

    /**
     * The keyring which the entity possesses.
     */
    private final Keyring keyring;

    /**
     * @param name Name of the entity.
     * @param desc Description of the entity.
     * @param healthPoints The entity's health-points in natural
     *         numbers.
     * @param maxDamage Maximal damage output of the entity.
     * @param ownsConsumables {@code true} if entity owns consumables.
     * @param ownsKeyring {@code true} if entity owns keyring.
     * @throws IllegalArgumentException If a value equal to or less than zero is
     *                                  entered as value for the health-points
     *                                  or max. damage.
     */
    public Entity(String name,
                  String desc,
                  int healthPoints,
                  int maxDamage,
                  boolean ownsConsumables,
                  boolean ownsKeyring) throws IllegalArgumentException
    {
        this.name = name;
        this.desc = desc;

        if (healthPoints <= 0) throw new IllegalArgumentException(
                "The entity must have more than zero health-points");
        else this.healthPoints = healthPoints;

        this.maxDamage = Math.max(0, maxDamage);
        this.isAlive = true;
        this.items = new ArrayList<>();

        if (ownsConsumables) this.consumables = new ArrayList<>();
        else this.consumables = null;

        if (ownsKeyring) this.keyring = new Keyring();
        else this.keyring = null;
    }

    public Entity(String name, String desc, int healthPoints, int maxDamage)
            throws IllegalArgumentException
    {
        this(name, desc, healthPoints, maxDamage, false, false);
    }

    /**
     * Apply the effect of a consumable with the corresponding command.
     *
     * @param c Command assigned to the consumable for use.
     */
    public void useConsumablesWithCommand(char c)
    {
        ArrayList<Consumable> consumedItems = new ArrayList<>();
        for (Consumable consumable : this.consumables)
        {
            if (c == consumable.getUseCommand())
            {
                consumable.applyEffect(this);
                consumedItems.add(consumable);
            }
        }
        for (Consumable consumable : consumedItems)
            this.consumables.remove(consumable);
    }

    /**
     * Checks the entity's keyring for a key that opens the door, which leads to
     * the room in question.
     *
     * @param room The room in which the door expected to be opened with
     *         the key leads to.
     * @return {@code true} if the entity has a key for opening a door to the
     *         expected room. {@code false} otherwise.
     */
    public boolean hasKeyForRoom(Room room)
    {
        for (int i = 0; i < this.keyring.size(); i++)
            if (this.keyring.getKeys().get(i).getRoomId() ==
                    room.getRoomID()) return true;
        return false;
    }

    /**
     * @return Name of the entity.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return Description of the entity.
     */
    public String getDesc()
    {
        return this.desc;
    }

    /**
     * @return Entity's health-points.
     */
    public int getHealthPoints()
    {
        return this.healthPoints;
    }

    /**
     * @return Entity's max damage output.
     */
    public int getMaxDamage()
    {
        return this.maxDamage;
    }

    /**
     * @return {@code true} if entity is alive. {@code false} otherwise.
     */
    public boolean isAlive()
    {
        return this.isAlive;
    }

    /**
     * @return Items owned by the entity.
     */
    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    /**
     * @return Consumables owned by the entity.
     */
    public ArrayList<Consumable> getConsumables()
    {
        return this.consumables;
    }

    /**
     * @param healthPoints Entity's new health-point amount.
     */
    public void setHealthPoints(int healthPoints)
    {
        this.healthPoints = Math.max(healthPoints, 0);
        if (this.healthPoints <= 0) this.isAlive = false;
    }

    /**
     * @param maxDamage Entity's new max damage output.
     */
    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }

    /**
     * @param item Item to be added to entity's list of items.
     */
    public void addItem(Item item)
    {
        this.items.add(item);
    }

    /**
     * @param item Item to be removed from entity's list of items.
     */
    public void removeItem(Item item)
    {
        this.items.remove(item);
    }

    /**
     * @param consumable Consumable to be added to entity's list of
     *         consumables.
     */
    public void addConsumable(Consumable consumable)
    {
        this.consumables.add(consumable);
    }

    /**
     * @param key Key to be added to entity's keyring.
     */
    public void giveKey(Key key)
    {
        this.keyring.addKey(key);
    }
}