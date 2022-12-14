package utils;

import game.data.K;

/**
 * This class represents a manager expected to be utilized for managing visual
 * effects.
 */
public class VisualEffectManager implements K
{
    /**
     * {@code true} if console should be cleared, {@code false} otherwise.
     */
    private boolean clearConsoleEnabled;

    public VisualEffectManager()
    {
        this.clearConsoleEnabled = false;
    }

    /**
     * @param isEnabled If the console should be cleared or not.
     */
    public void setClearConsole(boolean isEnabled)
    {
        this.clearConsoleEnabled = isEnabled;
    }

    /**
     * Clears the console or terminal running this program.
     *
     * <p>Seems to work with a UNIX-system terminal. Has varying results with
     * Windows CMD/Powershell.
     *
     * @throws Exception If something goes wrong while the console is being
     *                   cleared.
     */
    public void clearConsole() throws Exception
    {
        if (clearConsoleEnabled)
        {
            try
            {
                if (System.getProperty("os.name").contains("Windows"))
                {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
                            .waitFor();
                } else
                {
//                    System.out.print("\033\143");
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            } catch (Exception e)
            {
                throw new Exception(e);
            }
        }
    }

    /**
     * @return {@code true} if console clearing is enabled, {@code false}
     *                       otherwise.
     */
    public boolean isClearConsoleEnabled()
    {
        return this.clearConsoleEnabled;
    }
}