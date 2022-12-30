package game.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains constant values used in the game. An attempt to centralize the data
 * for swifter access and to avoid hardcoding values directly if possible.
 */
public interface K
{
    /**
     * A symbol printed aside and before the user input prompt.
     */
    String CONSOLE_MARKER = "> ";

    /**
     * Default value for amount of lines expected to be printed at once, while
     * printing visual effects representing a change in game stage.
     */
    int AMOUNT_OF_LINES = 99;

    /**
     * Amount of rooms in the game.
     */
    int ROOM_AMOUNT = 7;

    /**
     * Descriptions available to be applied on rooms in the game.
     */
    String[] ROOM_DESCRIPTIONS = new String[]{
            "Rummet är upplyst av några ljus som sitter på ett bord framför " +
                    "dig.",

            "Du ser något värre än en död kropp på golvet. Du ser en " +
                    "systemvetare.",

            "Du hör ett ljud. Det är en äkta Fender Stratocaster med single " +
                    "coil pickups",

            "Du står i ett kallt rum. Framför dig ser du Rick Astley. Han " +
                    "har gett up dig.",

            "Du är in i ett rum med en LTU-professor som svarar på sina " +
                    "mejl. Du blir rädd.",

            "Du är in i ett fuktigt rum med vatten sipprandes längs den " +
                    "västra väggen.",

            "Du ser en brinnande fackla i rummets ena hörn och känner en " +
                    "motbjudande stank."};


    /**
     * Welcome message expected to be printed for the user during the beginning
     * stage of the game.
     */
    String WELCOME_MSG = "Välkommen till DragonTreasure";

    /**
     * Message expected to be printed to the user, when their player leaves the
     * cave and enters the first room.
     */
    String LEFT_CAVE_MSG =
            "När du går in i grottan kollapsar ingången bakom dig.";

    /**
     * Directions and their abbreviations.
     */
    char N = 'n', E = 'o', S = 's', W = 'v';

    /**
     * The order in which the directions are expected to occur in the UI.
     */
    String DIRECTION_ORDER = "" + N + E + S + W;

    /**
     * Directions with their corresponding names and abbreviations.
     */
    LinkedHashMap<String, Character> DIRECTIONS = new LinkedHashMap<>() {{
        put("NORR", N);
        put("ÖSTER", E);
        put("SÖDER", S);
        put("VÄSTER", W);
    }};

    /**
     * Message used for asking the user to input their name and to either enter
     * the game or to exit.
     */
    String NAME_PROMPT_MSG =
            String.format(
                    "%s\nSkriv ditt namn och tryck på [Enter] för att " +
                            "starta ett nytt spel... (Skriv \"%c\" för att " +
                            "avsluta spelet)\n",
                    WELCOME_MSG,
                    Command.EXIT_GAME.commandValue
            );

    /**
     * Text for the escape option expected to be presented in the UI.
     */
    String ESCAPE_OPTION_MSG =
            String.format(
                    "Avsluta spelet [%c]\n",
                    Command.EXIT_GAME.commandValue
            );

    /**
     * Message asking for the user's program execution environment.
     */
    String ENVIRONMENT_CHECK_MSG = String.format("Jag kör programmet via en IDE [%c]\n" +
                    "Jag kör programmet via en Unix terminal eller Windows CMD [%c]\n"
            , Command.IDE_OPTION.commandValue, Command.TERMINAL_OPTION.commandValue);

    /**
     * Name of the currency expected to be used to signal the value of game
     * -items, treasures etc...
     */
    String CURRENCY = "Guld";

    /**
     * Information expected to be printed in the UI, such health points, total
     * monsters killed etc...
     */
    Map<String, String> CONTAINER_LABELS = Map.of(
            "PLAYER_INFORMATION", "Spelarinformation",
            "END_STATS", "Slutstatistik",
            "NAME", "Namn",
            "HP",   "HP",
            "MAX_DMG", "Max DMG",
            "MONSTERS_KILLED", "Dödade monsters",
            "VALUE", "värde",
            "TOTAL_VALUE", "Total värde",
            "TREASURES", "Skatter",
            "NO_TREASURES", "Inga skatter");

    /**
     * A message informing the user about winning the game.
     */
    String WIN_MSG =
            "Du lämnar grottan med livet i behåll. Grattis, du förlorade inte!";

    /**
     * A message informing the user about losing the game.
     */
    String LOSE_MSG = "Du förlorar och borde skämmas";

    /**
     * Description about the player.
     */
    String PLAYER_DESC = "Spelare";

    /**
     * Initial health points expected to be assigned to the player.
     */
    int INITIAL_PLAYER_HP = 10;

    /**
     * Initial max. damage assigned to the player.
     */
    int INITIAL_PLAYER_MAX_DMG = 2;

    /**
     * Default name for a health potion.
     */
    String HEALTH_POTION_NAME = "En hälsodryck";

    /**
     * Default description of a health potion.
     */
    String HEALTH_POTION_DESC = "Ger heal points";

    /**
     * Default value of health points provided by a health potion.
     */
    int HEALTH_POTION_HP = 6;

    /**
     * Default amount of damage expected to be caused by a grenade-item.
     */
    int GRENADE_DMG = 8;

    /**
     * Default name of a sword.
     */
    String SWORD_NAME = "Ett svärd";

    /**
     * Default description for a sword.
     */
    String SWORD_DESC = "Ett coolt svärd";

    /**
     * Default amount of damage caused by a sword.
     */
    int SWORD_DMG = 6;

    /**
     * Default name for a monster.
     */
    String MONSTER_NAME = "Odjur";

    /**
     * Default description for a monster.
     */
    String MONSTER_DESC = "En ful varelse";

    /**
     * Default health points assigned to a regular monster.
     */
    int MONSTER_HP = 8;

    /**
     * Default max. damage value assigned for a regular monster.
     */
    int MONSTER_MAX_DMG = 2;

    /**
     * Default name for a dragon.
     */
    String DRAGON_NAME = "Drake";

    /**
     * Default description for a dragon.
     */
    String DRAGON_DESC = "Skrämmande drake";

    /**
     * Default health points assigned for a dragon.
     */
    int DRAGON_HP = 15;

    /**
     * Default max. damage value assigned for a dragon.
     */
    int DRAGON_MAX_DMG = 3;

    /**
     * Default value of a kitten-treasure.
     */
    int KITTEN_VALUE = 500000;
}