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

    private HashSet<char[][]> visitedStates = new HashSet<>();
    private Queue<State> queue = new ArrayDeque<>();

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
        visitedStates.add(startingState.getItems());

        while (!queue.isEmpty())
        {
            State currState = queue.poll();

            if (status.isWin(currState))
            {
                return reversePath(currState);
            }


            // go through each of the next states (left, right, up, down):
            // left state
            State leftState = currState.movePlayer('l');
            if (status.isWin(leftState))
            {
                return reversePath(leftState);
            }
            processState(leftState);

            // right state
            State rightState = currState.movePlayer('r');
            if (status.isWin(rightState))
            {
                return reversePath(rightState);
            }
            processState(rightState);

            // up state
            State upState = currState.movePlayer('u');
            if (status.isWin(upState))
            {
                return reversePath(upState);
            }
            processState(upState);

            // down state
            State downState = currState.movePlayer('d');
            if (status.isWin(downState))
            {
                return reversePath(downState);
            }
            processState(downState);
        }

        return "";
    }

    /**
     * Checks for the state's status and adds it to the queue if it's not visited.
     * 
     * @param state {State} the state to be processed
     */
    private void processState(State state)
    {
        if (!status.isLoss(state))
        {
            // do nothing
        }
        else if (!status.isRedundant(state) && !visitedStates.contains(state.getItems()));
        {
            queue.add(state);
            // state.visit();
            visitedStates.add(state.getItems());
        }
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
