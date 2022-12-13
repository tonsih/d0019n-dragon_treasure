package utils;

import game.data.K;

public class VisualEffectManager implements K
{
    private boolean clearConsoleEnabled;
    private boolean animationEnabled;


    public VisualEffectManager()
    {
        this.animationEnabled = false;
        this.clearConsoleEnabled = false;
    }

    public void setClearConsole(boolean isEnabled)
    {
        this.clearConsoleEnabled = isEnabled;
    }

    public void setAnimation(boolean isEnabled)
    {
        this.animationEnabled = isEnabled;
    }

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

    public boolean isClearConsoleEnabled()
    {
        return this.clearConsoleEnabled;
    }


    public void animateString(boolean animate, String string, int speed)
    {
        if (this.animationEnabled && animate)
        {
            for (char c : string.toCharArray())
            {
                TimeManipulator.wait(speed);
                System.out.print(c);
            }
        } else System.out.println(string);
    }

    public void animateString(boolean animate, String string)
    {
        animateString(animate, string, K.ANIMATE_TEXT_SPEED);
    }

}
