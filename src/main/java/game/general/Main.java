package game.general;

// The only purpose of the game.general.Main class is to initiate a new DragonTreasure-
// object and call its method setupGame, which purpose is to set up the game.
public class Main
{

    public static void main(String[] args) throws Exception
    {
//       Creates a new game.general.DragonTreasure-instance and calls its method "setupGame"
         new DragonTreasure().setupGame();
    }

}
