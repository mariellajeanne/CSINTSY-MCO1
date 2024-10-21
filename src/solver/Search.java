/**
 * @author Ana Victoria R. Angat
 * 
 * Searches the winning state using Breadth-First Search.
 */

package solver;
import java.util.*;

/**
 * The search class.
*/
public class Search
{
    // The single instance of the search class.
    private static Search search;

    // The single instance of the status class.
    private static Status status;

    // The set of visited box coor states.
    private HashSet<HashSet<int[]>> visitedStates = new HashSet<>();

    private Queue<State> queue = new ArrayDeque<>();

    private final char[] moves = {'l', 'r', 'u', 'd'};
    private final int[] offsetx = {-1, 1, 0, 0};
    private final int[] offsety = {0, 0, -1, 1};

    /**
     * Constructs the single search instance.
     */
    private Search()
    {
        status = Status.getInstance();
    }

    /**
     * Returns the single instance of the class.
     * 
     * @return {Search}
     */
    public static Search getInstance()
    {
        if (search == null)
            search = new Search();
        return search;
    }

    /**
     * Returns the sequence of the goal state's moves.
     * Returns "" otherwise.
     * 
     * @param startingState {State} the starting state
     * 
     * @return {String}
     */
    public String getSequence(State startingState)
    {
        queue.add(startingState);
        // startingState.visit();
        visitedStates.add(startingState.getBoxCoor());

        while (!queue.isEmpty())
        {
            State currState = queue.poll();

            if (status.isWin(currState))
            {
                return reversePath(currState);
            }


            // go through each of the next states (left, right, up, down):
            for (int i = 0; i < 4; i++) {
                State nextState = currState.movePlayer(moves[i], offsetx[i], offsety[i]);
                if (nextState == null)
                {
                    continue;
                }
                if (status.isWin(nextState))
                {
                    return reversePath(nextState);
                }
                if (!visitedStates.contains(nextState.getBoxCoor()))
                {
                    queue.add(nextState);
                    visitedStates.add(nextState.getBoxCoor());
                }
            }
        }

        return "";
    }

    /**
     * Reconstructs the path taken from the given end state to the starting state.
     * 
     * @param endState {State} the end state
     * @return {String}
     */
    private String reversePath(State endState)
    {
        StringBuilder path = new StringBuilder(endState.getPrevMove());

        State currState = endState.getPrevState();
        while (currState != null)
        {
            path.append(currState.getPrevMove());
            currState = currState.getPrevState();
        }

        return path.reverse().toString();
    }
}
