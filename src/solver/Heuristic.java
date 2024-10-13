/**
 * @author <Full Name>
 * 
 * Returns the heuristic value of a node.
 */

package solver;

/**
 * The heuristic class.
 */
public class Heuristic
{
    // The single instance of the heuristic class.
    private static Heuristic h; 

    /**
     * Returns the single instance of the class.
     * 
     * @return {Heuristic}
     */
    public static Heuristic getInstance()
    {
        if (h == null)
            h = new Heuristic();
        return h;
    }

    /**
     * Returns the heuristic value of a state.
     * 
     * @param state {State} The state.
     * @return {int}
     */
    public int getHeuristicValue(State state)
    {
        return 1; // TODO: Please edit this.
    }

    /**
     * TODO: Create helper functions for calculating the heuristic value.
     */
}
