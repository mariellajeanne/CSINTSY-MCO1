/**
 * @author <Full Name>
 * 
 * Searches the winning state using Greedy Best-First Search.
 */

package solver;

/**
 * The search class.
 */
public class Search
{
    // The single instance of the search class.
    private static Search s;

    /**
     * Returns the single instance of the class.
     * 
     * @return {Search}
     */
    public static Search getInstance()
    {
        if (s == null)
            s = new Search();
        return s;
    }

    /**
     * Returns the sequence of the goal state's moves.
     * Returns "" otherwise.
     * 
     * @return {String}
     */
    public String getSequence()
    {
        return ""; // TODO: Please edit this.
    }

    /**
     * TODO: Create helper functions for searching the goal state.
     */
}
