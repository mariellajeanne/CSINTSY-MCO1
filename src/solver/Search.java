/**
 * @author Ana Victoria R. Angat
 * 
 * Searches the winning state using Greedy Best-First Search.
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
        List<State> visitedStates = new ArrayList<>();
        
        // The comparator for the heuristic values.
        Comparator<State> heuristicComparator = (s1, s2) -> 
            Integer.compare(Heuristic.getInstance().getHeuristicValue(s1), Heuristic.getInstance().getHeuristicValue(s2));

        // The priority queue for the states, comparing the heuristic values of each state.
        PriorityQueue<State> pQueue = new PriorityQueue<>(heuristicComparator);

        visitedStates.add(startingState);
        startingState.visit();
        pQueue.add(startingState);

        while (!pQueue.isEmpty())
        {
            State currState = pQueue.poll();
            
            // goes through each of the next states, checking if it results in a win, or if it has not been visited yet.
            State leftState = currState.left(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(leftState))
            {
                return leftState.getActions();
            }
            if (!visitedStates.contains(leftState))
            {
                pQueue.add(leftState);
                visitedStates.add(leftState);
            }

            State rightState = currState.right(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(rightState))
            {
                return rightState.getActions();
            }
            if (!visitedStates.contains(rightState))
            {
                pQueue.add(rightState);
                visitedStates.add(rightState);
            }

            State upState = currState.up(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(upState))
            {
                return upState.getActions();
            }
            if (!visitedStates.contains(upState))
            {
                pQueue.add(upState);
                visitedStates.add(upState);
            }

            State downState = currState.down(currState.getMap(), currState.getItems());
            if (Status.getInstance().isWin(downState))
            {
                return downState.getActions();
            }
            if (!visitedStates.contains(downState))
            {
                pQueue.add(downState);
                visitedStates.add(downState);
            }
        }

        return "";
    }

    /**
     * TODO: Create helper functions for searching the goal state.
     */
}
