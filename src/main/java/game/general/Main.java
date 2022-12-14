package game.general;

/**
 * The sole purpose of this class this is to start the program. It calls the
 * method, which sets up the game before the actual gameplay starts.
 */
public class Main
{
    /**
     * Contains the call to the method which sets up the game.
     *
     * @param args An array of string provided as arguments when program
     *         is executed. Not really utilized in this program.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        // Calls a method of a new instance of DragonTreasure, which sets up the
        // game.
        new DragonTreasure().setupGame();
    }

}
