package game.general;

import game.data.ValueManager;

// The game.general.Door-class contains information about a door.
public class Door
{

//  position-variable -- Stores the position of the door [nsöv].
    private final char position;


//  locked-variable -- Stores a boolean value: true if the door is locked,
//  false if unlocked.
    private final boolean locked;


//  exit-variable -- Stores a boolean value: true if the door leads to an
//  exit, false otherwise.
    private final boolean exit;


//  pointsToRoom-variable -- Stores the room which the door points to.
    private Room pointsToRoom;


//  game.general.Door-constructor -- Takes the position, locked- and exit-values and assigns
//  the values to the corresponding object variables.
    public Door(char pos, boolean locked, boolean exit) throws Exception
    {
        if (ValueManager.charIsDirection(pos))
        {
            this.position = pos;
        } else
        {

            throw new IllegalArgumentException("A door was not defined to be in a valid " +
                    "position [" + ValueManager.directionCommandsToString() + "]");
        }
        this.locked = locked;
        this.exit = exit;
    }


/*
   game.general.Door-constructor -- Takes advantage of constructor overloading. Requires
   only the first parameter pos, and the other values (locked and false) are
   entered automatically as arguments by the constructor, by calling the
   constructor above with those values and the entered pos.
*/
    public Door(char pos) throws Exception
    {
        this(pos, false, false);
    }

    public Door(char pos, boolean locked) throws Exception
    {
        this(pos, locked, false);
    }


    //  isLocked-method -- Returns true if door is locked, false otherwise.
    public boolean isLocked()
    {
        return this.locked;
    }


//  isExit-method -- Returns true if door is an exit, false otherwise.
    public boolean isExit()
    {
        return this.exit;
    }


//  getPosition-method -- Returns the position of the game.general.Door-object.
    public char getPosition()
    {
        return this.position;
    }


//  getPointsToRoom -- Returns the room to which the game.general.Door-object is pointing at.
    public Room getPointsToRoom()
    {
        return this.pointsToRoom;
    }


//  setPointsToRoom -- Takes a room as an argument and assigns the game.general.Door-object
//  to point to it.
    public void setPointsToRoom(Room room)
    {
        this.pointsToRoom = room;
    }

}
