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

    // The set of visited playerCoor and boxCoor states.
    private HashSet<String> visitedStates = new HashSet<>();

    // The queue of states.
    private Queue<State> queue = new ArrayDeque<>();

    // The player's moves and their corresponding offsets.
    private final char[] moves = {'l', 'r', 'u', 'd'};
    private final int[] offsetx = {-1, 1, 0, 0};
    private final int[] offsety = {0, 0, -1, 1};

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
        addToVisitedStates(startingState);

        while (!queue.isEmpty())
        {
            State currState = queue.poll();

            if (currState.boxCoor.equals(State.targetCoor))
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
                if (nextState.boxCoor.equals(State.targetCoor))
                {
                    return reversePath(nextState);
                }
                if (!isVisited(nextState))
                {
                    queue.add(nextState);
                    addToVisitedStates(nextState);
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
        StringBuilder path = new StringBuilder(endState.prevMove);

        State currState = endState.prevState;
        while (currState != null)
        {
            path.append(currState.prevMove);
            currState = currState.prevState;
        }

        return path.reverse().toString();
    }

    /**
     * Adds the state to the visited states.
     * 
     * @param state {State} The state to add.
     */
    private void addToVisitedStates(State state)
    {
        visitedStates.add(state.getHashCode());
    }

    /**
     * Checks if the state has already been visited.
     * 
     * @param state {State} The state to check.
     * @return {boolean}
     */
    private boolean isVisited(State state)
    {
        return visitedStates.contains(state.getHashCode());
    }
}
