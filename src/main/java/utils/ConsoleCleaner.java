package utils;

import game.data.K;

import java.io.IOException;

public class ConsoleCleaner implements K
{
    private static boolean clear_console_enabled = true;

    public static void setClear_console_enabled(boolean enabled)
    {
        clear_console_enabled = enabled;
    }

    public static void clearConsole() {

        if (clear_console_enabled)
        {
            try {
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }
                else {
//                    System.out.print("\033\143");
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
