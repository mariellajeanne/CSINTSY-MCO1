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

    private Queue<State> queue = new ArrayDeque<>();

    // TODO: Create comparator for heuristic values
    private PriorityQueue<State> pQueue = new PriorityQueue<>();

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
     * Returns the sequence of the goal state's moves using Breadth-First Search.
     * Returns "" otherwise.
     * 
     * @param startingState {State} the starting state
     * 
     * @return {String}
     */
    public String getSequenceBFS(State startingState)
    {
        queue.add(startingState);
        // startingState.visit();
        visitedStates.add(startingState.getHashCode());
        System.out.println("added starting state");

        while (!queue.isEmpty())
        {
            State currState = queue.poll();
            System.out.println("===========");
            System.out.println("dequeue");
            System.out.println("queue size: " + queue.size());

            if (currState.boxCoor.equals(State.targetCoor))
            {
                System.out.println("target reached");
                return reversePath(currState);
            }


            // go through each of the next states (left, right, up, down):
            for (int i = 0; i < 4; i++) {
                System.out.println();
                System.out.println("move: " + moves[i]);
                System.out.println("prev move: " + currState.prevMove);
                System.out.println("current path: " + reversePath(currState) + ".");
                State nextState = currState.movePlayer(moves[i], offsetx[i], offsety[i]);
                if (nextState != null && !visitedStates.contains(nextState.getHashCode()))
                {
                    queue.add(nextState);
                    visitedStates.add(nextState.getHashCode());
                    System.out.println("added next state");
                }
                System.out.println("queue size: " + queue.size());
                System.out.println("queue: " + queue);
                System.out.println("visited states size: " + visitedStates.size());
                System.out.println("visited states: " + visitedStates);
            }
        }

        System.out.println("return empty string");
        return "";
    }

    /**
     * Returns the sequence of the goal state's moves using Greedy Best-First Search.
     * Returns "" otherwise.
     * 
     * @param startingState {State} the starting state
     * 
     * @return {String}
     */
    public String getSequenceGBFS(State startingState)
    {
        pQueue.add(startingState);
        visitedStates.add((startingState.getHashCode()));

        while (!pQueue.isEmpty())
        {
            State currState = pQueue.poll();

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
                if (!visitedStates.contains(nextState.getHashCode()))
                {
                    pQueue.add(nextState);
                    visitedStates.add((nextState.getHashCode()));
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
        StringBuilder path = new StringBuilder();
        path.append(endState.prevMove);

        State currState = endState.prevState;
        while (currState != null)
        {
            path.append(currState.prevMove);
            currState = currState.prevState;
        }

        return path.reverse().toString();
    }
}
