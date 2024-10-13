/**
 * @author <Full Name>
 * 
 * Determines a state's winning status.
 */

package solver;

/**
 * The status class.
 */
public class Status
{
    // The single instance of the status class.
    private static Status s;

    /**
     * Returns the single instance of the status class.
     * 
     * @return {Status}
     */
    public static Status getInstance()
    {
        if (s == null)
            s = new Status();
        return s;
    }

    /**
     * Determines if it's impossible for a state
     * to branch to a winning state.
     * 
     * @return {boolean}
     */
    public boolean isLoss(State state)
    {
        return false; // TODO: Please edit this.
    }

    /**
     * Determines if the player has won.
     * Checks if all the boxes are located at the targets.
     * 
     * @return {boolean}
     */
    public boolean isWin(State state)
    {
        return false; // TODO: Please edit this.
    }

    /**
     * Determines if the player's move cycles back to a
     * previous state without changing the boxes' positions.
     * 
     * @return {boolean}
     */
    public boolean isRedundant(State state)
    {
        return false; // TODO: Please edit this.
    }

    /**
     * TODO: Create helper functions for isLoss() and isWin()
     */
}
