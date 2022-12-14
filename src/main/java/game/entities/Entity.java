package game.entities;

import game.general.Room;
import game.items.Item;
import game.items.Key;
import game.items.Keyring;
import game.items.consumables.Consumable;

import java.util.ArrayList;

public abstract class Entity
{
    protected final String name;
    protected int healthPoints;
    protected int maxDamage;
    private final ArrayList<Item> items;

    private final ArrayList<Consumable> consumables;

    private final Keyring keyring;

    public Entity(String name, int healthPoints, int maxDamage)
    {
        this.name = name;
        this.healthPoints = healthPoints;
        this.maxDamage = maxDamage;
        this.items = new ArrayList<>();
        this.keyring = new Keyring();
        this.consumables = new ArrayList<>();
    }

    public Keyring getKeyring()
    {
        return keyring;
    }

    //  A getter for returning a boolean value for if the player has a key or
    //  not.
    public boolean hasKeyForRoom(Room room)
    {
        for (int i = 0; i < this.keyring.size(); i++)
        {
            if (this.keyring.getKeys().get(i).getOpensRoomWithId() ==
                    room.getRoomID())
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Consumable> getConsumables()
    {
        return this.consumables;
    }



    public void useConsumable()
    {
        ArrayList<Consumable> consumed = new ArrayList<>();

        for (Consumable consumable : this.consumables)
        {
            consumable.applyEffect(this);
            consumed.add(consumable);
        }

        if (consumed.size() > 0)
        {
            for (Consumable consumable : consumed)
            {
                this.consumables.remove(consumable);
            }
        }
    }

    public String getName()
    {
        return this.name;
    }

    public int getMaxDamage()
    {
        return this.maxDamage;
    }

    public int getHealthPoints()
    {
        return this.healthPoints;
    }

    public ArrayList<Item> getItems()
    {
        return this.items;
    }

    public void setHealthPoints(int healthPoints)
    {
        this.healthPoints = Math.max(healthPoints, 0);
    }

    public void setMaxDamage(int maxDamage)
    {
        this.maxDamage = maxDamage;
    }

    public void addItem(Item item)
    {
        this.items.add(item);
    }


    public void giveKey(Key key)
    {
        this.keyring.addKey(key);
    }

    public void addConsumable(Consumable consumable)
    {
        this.consumables.add(consumable);
    }


    public void removeConsumable(Consumable consumable)
    {
        this.consumables.remove(consumable);
    }


    public void removeItem(Item item)
    {
        this.items.remove(item);
    }

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
        {
            this.consumables.remove(consumable);
        }
    }


}
