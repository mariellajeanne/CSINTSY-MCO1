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
    private final HashSet<String> visitedStates = new HashSet<>();

    private final Queue<State> queue = new ArrayDeque<>();

    // TODO: Paste in a file the test cases (level: pass vs. fail)
    // TODO: Create comparator for heuristic values
    // private PriorityQueue<State> pQueue = new PriorityQueue<>();

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
     * @param startingState {State} The starting state.
     * 
     * @return {String}
     */
    public String getSequenceBFS(State startingState)
    {
        queue.add(startingState);
        visitedStates.add(startingState.getHashCode());

        while (!queue.isEmpty())
        {
            // Dequeues the head of the queue.
            State currState = queue.poll();

            // Checks each move from the current state (i.e., left, right, up, and down).
            for (int i = 0; i < 4; i++) {

                // Gets the next state transitioned from the move.
                State nextState = State.movePlayer(new State(currState), moves[i], offsetx[i], offsety[i]);
                
                // Skips the iteration if the next state is null.
                if (nextState == null)
                    continue;
                
                // Returns the path to the next state if it is a goal state.
                if (nextState.boxCoor.equals(State.targetCoor))
                    return reversePath(nextState);
                
                // Adds the next state to the queue if it has not yet been visited.
                if (!visitedStates.contains(nextState.getHashCode()))
                {
                    queue.add(nextState);
                    visitedStates.add((nextState.getHashCode()));
                }

                // Returns the path to the last state if the queue is empty.
                if (queue.isEmpty())
                    return reversePath(nextState) + moves[i];
            }
        }
        
        return "";
    }

    // /**
    //  * Returns the sequence of the goal state's moves using Greedy Best-First Search.
    //  * Returns "" otherwise.
    //  * 
    //  * @param startingState {State} the starting state
    //  * 
    //  * @return {String}
    //  */
    // public String getSequenceGBFS(State startingState)
    // {
    //     pQueue.add(startingState);
    //     visitedStates.add((startingState.getHashCode()));

    //     while (!pQueue.isEmpty())
    //     {
    //         State currState = pQueue.poll();

    //         if (currState.boxCoor.equals(State.targetCoor))
    //         {
    //             return reversePath(currState);
    //         }

    //         // go through each of the next states (left, right, up, down):
    //         for (int i = 0; i < 4; i++) {
    //             State nextState = State.movePlayer(new State(currState), moves[i], offsetx[i], offsety[i]);
    //             if (nextState == null)
    //             {
    //                 continue;
    //             }
    //             if (nextState.boxCoor.equals(State.targetCoor))
    //             {
    //                 return reversePath(nextState);
    //             }
    //             if (!visitedStates.contains(nextState.getHashCode()))
    //             {
    //                 pQueue.add(nextState);
    //                 visitedStates.add((nextState.getHashCode()));
    //             }
    //         }
    //     }
        
    //     return "";
    // }

    /**
     * Reconstructs the path taken from the given end state to the starting state.
     * 
     * @param lastMove {char} The last move.
     * @param endState {State} The end state.
     * @return {String}
     */
    private String reversePath(State endState)
    {
        StringBuilder path = new StringBuilder().append(endState.prevMove);

        State currState = endState.prevState;
        while (currState != null)
        {
            path.append(currState.prevMove);
            currState = currState.prevState;
        }

        return path.reverse().toString();
    }
}
