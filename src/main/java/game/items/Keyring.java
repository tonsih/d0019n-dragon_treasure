package game.items;

import game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an item which is a collection of none to many keys.
 *
 * @author Toni Sihvola
 * @author Ludwig Ahnqvist
 */
public class Keyring extends Item
{
    /**
     * Keys contained in the keyring.
     */
    private ArrayList<Key> keys;

    /**
     * @param name Name of the keyring.
     * @param itemDesc Description of the keyring.
     * @param keys Keys contained in the keyring.
     */
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

    /**
     * @return Keys contained in the keyring.
     */
    public ArrayList<Key> getKeys()
    {
        return this.keys;
    }

    /**
     * Adds the provided key to the keyring.
     *
     * @param key Key to be added to the keyring.
     */
    public void addKey(Key key)
    {
        this.keys.add(key);
    }

    /**
     * Removes all the keys from the keyring.
     */
    public void removeKeys()
    {
        this.keys = new ArrayList<>();
    }

    /**
     * @return Amount of keys which the keyring contains currently.
     */
    public int size()
    {
        return this.keys.size();
    }

    /**
     * Applies the effect of all the keys contained in the keyring on the
     * provided entity.
     *
     * @param entity Entity on which to apply the item effect.
     */
    @Override public void applyEffect(Entity entity)
    {
        for (Key key : this.keys)
        {
            key.applyEffect(entity);
        }
    }
}