package utils;

/**
 * This class contains class methods used to control time-related events.
 */
public class TimeManipulator
{
    /**
     * A private no-arg constructor for the class. Prevents initiation of an
     * instance.
     */
    private TimeManipulator()
    {
    }

    /**
     * Wait or delay the execution process by the provided amount of
     * milliseconds.
     *
     * @param ms Time to wait in milliseconds.
     */
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}