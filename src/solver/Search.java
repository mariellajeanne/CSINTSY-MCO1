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
    private static Search s;

    private HashSet<char[][]> visitedStates = new HashSet<>();
    private Queue<State> queue = new ArrayDeque<>();

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

            if (Status.getInstance().isWin(currState))
            {
                return getInstance().reversePath(currState);
            }


            // go through each of the next states (left, right, up, down):
            // left state
            State leftState = currState.left(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(leftState))
            {
                return getInstance().reversePath(leftState);
            }
            getInstance().processState(leftState);

            // right state
            State rightState = currState.right(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(rightState))
            {
                return getInstance().reversePath(rightState);
            }
            getInstance().processState(rightState);

            // up state
            State upState = currState.up(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(upState))
            {
                return getInstance().reversePath(upState);
            }
            getInstance().processState(upState);

            // down state
            State downState = currState.down(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(downState))
            {
                return getInstance().reversePath(downState);
            }
            getInstance().processState(downState);
        }

        return "";
    }

    /**
     * TODO: Create helper functions for searching the goal state.
     */


    /**
     * Checks for the state's status and adds it to the queue if it's not visited.
     * 
     * @param state {State} the state to be processed
     */
    private void processState(State state)
    {
        if (!Status.getInstance().isLoss(state))
        {
            // do nothing
        }
        else if (!Status.getInstance().isRedundant(state) && !visitedStates.contains(state.getItems()));
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
