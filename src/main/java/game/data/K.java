package game.data;

import java.util.Map;

// Interface "K" -- contains constants with their values.
public interface K
{

    //  Stores the value for amount of rooms
    int ROOM_AMOUNT = 7;

    String CONSOLE_MARKER = "> ";


    //  roomDescArr-variable -- An array of descriptions for rooms.
    String[] roomDescArr = new String[]{
            "Rummet är upplyst av några ljus som sitter på ett bord framför " +
                    "dig.",

            "Du ser något värre än en död kropp på golvet. Du ser en " +
                    "systemvetare.",

            "Du hör ett ljud. Det är en äkta Fender Stratocaster med single " +
                    "coil pickups",

            "Du kommer in i ett rum och framför dig ser du Rick Astley. Han " +
                    "har gett up dig.",

            "Du kommer in i ett rum med en LTU-professor som svarar på sina " +
                    "mejl. Du blir rädd.",

            "Du kommer in i ett fuktigt rum med vatten sipprandes längs den " +
                    "västra väggen.",

            "Du ser en brinnande fackla i rummets ena hörn och känner en " +
                    "motbjudande stank."};


    String WELCOME_MSG = "Välkommen till DragonTreasure";

    String LEFT_CAVE_MSG =
            "När du går in i grottan kollapsar ingången bakom dig.";

    String WIN_MSG =
            "Du lämnar grottan med livet i behåll. Grattis, du förlorade inte!";

    String LOSE_MSG = "Du förlorar och borde skämmas";


    //  Values for player
    int INITIAL_PLAYER_HP = 10;
    int INITIAL_PLAYER_DMG = 2;


    //  Values for items
    int SWORD_DMG = 6;
    int POTION_HP = 6;

    //  Values for creatures
    int CREATURE_HP = 8;
    int CREATURE_MAX_DMG = 2;

    int DRAGON_HP = 15;
    int DRAGON_MAX_DMG = 3;

    char n = 'n', s = 's', w = 'v', e = 'o';

    String DIRECTION_ORDER = "" + n + e + s + w;

    Map<String, Character> DIRECTIONS =
            Map.of("NORR", n, "SÖDER", s, "VÄSTER", w, "ÖSTER", e);


    int KITTEN_VALUE = 500000;


    int AMOUNT_OF_LINES = 99;
    int BOMB_DMG = 8;


    String NAME_PROMPT_MSG =
            String.format(
                    "%s\nSkriv ditt namn och tryck på [Enter] för att " +
                            "starta ett nytt spel... (Skriv \"%c\" för att " +
                            "avsluta spelet)\n",
                    WELCOME_MSG,
                    Command.EXIT_GAME.commandValue
            );


    String ESCAPE_OPTION_MSG =
            String.format(
                    "Avsluta spelet [%c]\n",
                Command.EXIT_GAME.commandValue
            );



    String ENVIRONMENT_CHECK_MSG = String.format("Jag kör programmet via en IDE [%c]\n" +
                    "Jag kör programmet via en Unix terminal eller Windows CMD [%c]\n"
                    , Command.IDE_OPTION.commandValue, Command.TERMINAL_OPTION.commandValue);


    int ANIMATE_TEXT_SPEED = 33;
}
