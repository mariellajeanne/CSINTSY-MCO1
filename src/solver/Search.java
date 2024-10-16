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
        HashSet<State> visitedStates = new HashSet<>();
        Queue<State> queue = new ArrayDeque<>();

        queue.add(startingState);
        // startingState.visit();
        visitedStates.add(startingState);

        while (!queue.isEmpty())
        {
            State currState = queue.poll();

            if (Status.getInstance().isWin(currState))
            {
                return currState.getActions();
            }


            // go through each of the next states (left, right, up, down):
            // left state
            State leftState = currState.left(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(leftState))
            {
                return leftState.getActions();
            }
            if (!Status.getInstance().isLoss(leftState))
            {
                // do nothing
            }
            else if (!Status.getInstance().isRedundant(leftState) && !visitedStates.contains(leftState));
            {
                queue.add(leftState);
                // leftState.visit();
                visitedStates.add(leftState);
            }

            // right state
            State rightState = currState.right(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(rightState))
            {
                return rightState.getActions();
            }
            if (!Status.getInstance().isLoss(rightState))
            {
                // do nothing
            }
            else if (!Status.getInstance().isRedundant(rightState) && !visitedStates.contains(rightState));
            {
                queue.add(rightState);
                // rightState.visit();
                visitedStates.add(rightState);
            }

            // up state
            State upState = currState.up(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(upState))
            {
                return upState.getActions();
            }
            if (!Status.getInstance().isLoss(upState))
            {
                // do nothing
            }
            else if (!Status.getInstance().isRedundant(upState) && !visitedStates.contains(upState));
            {
                queue.add(upState);
                // upState.visit();
                visitedStates.add(upState);
            }

            // down state
            State downState = currState.down(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(downState))
            {
                return downState.getActions();
            }
            if (!Status.getInstance().isLoss(downState))
            {
                // do nothing
            }
            else if (!Status.getInstance().isRedundant(downState) && !visitedStates.contains(downState));
            {
                queue.add(downState);
                // downState.visit();
                visitedStates.add(downState);
            }
        }

        return "";
    }

    /**
     * TODO: Create helper functions for searching the goal state.
     */
}
