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

    // The player's moves and their corresponding offsets.
    private final char[] moves = {'l', 'r', 'u', 'd'};
    private final int[] xOffset = {-1, 1, 0, 0};
    private final int[] yOffset = {0, 0, -1, 1};

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
        // The queue of states to be visited.
        Queue<State> queue = new ArrayDeque<>();

        // The set of the visited states' hash codes.
        Set<String> visitedStates = new HashSet<>();
        
        queue.add(startingState);
        visitedStates.add(startingState.getHashCode());

        while (!queue.isEmpty())
        {
            // Dequeues the head of the queue.
            State currState = queue.poll();

            // Checks each move from the current state (i.e., left, right, up, and down).
            for (int i = 0; i < 4; i++) {

                // Gets the next state transitioned from the move.
                State nextState;
                try {
                    nextState = State.movePlayer(new State(currState), moves[i], xOffset[i], yOffset[i]);
                } catch (Exception e) {
                    continue;
                }
                
                // Skips the iteration if the next state is null or if it is already visited.
                if (nextState == null || visitedStates.contains(nextState.getHashCode()))
                    continue;
                
                // Returns the path to the next state if it is a goal state.
                if (nextState.boxCoor.equals(State.targetCoor))
                    return reversePath(nextState);
                
                queue.add(nextState);
                visitedStates.add((nextState.getHashCode()));
            }
        }
        
        return "";
    }

    /**
     * Reconstructs the path taken from the given end state to the starting state.
    * 
    * @param endState {State} The end state.
    * 
    * @return {String}
    */
    private String reversePath(State endState)
    {   
        // Starts building the path with the last move.
        StringBuilder path = new StringBuilder().append(endState.prevMove);

        // Gets the previous state of the end state.
        State currState = endState.prevState;

        // Constructs the path in reverse.
        while (currState != null)
        {
            path.append(currState.prevMove);
            currState = currState.prevState;
        }

        // Returns the reversed constructed path.
        return path.reverse().toString();
    }
}