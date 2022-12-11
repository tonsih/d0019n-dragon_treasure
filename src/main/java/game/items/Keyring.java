package game.items;

import game.entities.Entity;
import game.entities.Player;
import game.items.Key;

import java.util.ArrayList;
import java.util.List;

public class Keyring extends Item
{
    private ArrayList<Key> keys;

    public Keyring(String name, String itemDesc, ArrayList<Key> keys)
    {
        super(name, itemDesc);
        this.keys = keys;
    }

    public Keyring(ArrayList<Key> keys)
    {
        this("Nyckelring", "Ring med nycklar", keys);
    }

    public Keyring(Key key)
    {
        this(new ArrayList<>(List.of(key)));
    }

    public Keyring()
    {
        this(new ArrayList<>());
    }

    public ArrayList<Key> getKeys()
    {
        return this.keys;
    }

    public void addKey(Key key)
    {
        this.keys.add(key);
    }


    public void removeKeys()
    {
        this.keys = new ArrayList<>();
    }

    public int size()
    {
        return this.keys.size();
    }

    @Override public void applyEffect(Entity entity)
    {
        for (Key key : this.keys)
        {
            key.applyEffect(entity);
        }
    }
}
