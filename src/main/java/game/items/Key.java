package game.items;

import game.data.PrintCollection;
import game.entities.Entity;
import game.entities.Player;
import game.interfaces.Printable;

public class Key extends Item implements Printable
{
    private final int roomId;

    public Key(String name, int roomId)
    {
       super(name, "en nyckel som öppnar rum nummer " + roomId);
       this.roomId = roomId;
    }

    public Key(int roomId)
    {
        this("En nyckel", roomId);
    }

    public int getOpensRoomWithId()
    {
       return this.roomId;
    }

    @Override
    public void applyEffect(Entity entity)
    {
        entity.giveKey(this);
    }

    @Override public void printObject()
    {
        PrintCollection.printKey();
    }
}
