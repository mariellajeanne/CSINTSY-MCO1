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

    // The set of the visited states' hash codes.
    private final HashSet<String> visitedStates = new HashSet<>();

    // The queue of states to be visited.
    private final PriorityQueue<State> queue =
        new PriorityQueue<>(Comparator.comparing(State::getHeuristic));

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

            // Gets the last index checked.
            int lastChecked = 0;

            // Checks each move from the current state (i.e., left, right, up, and down).
            for (int i = 0; i < 4; i++)
            {
                // Checks if the move is redundant.
                if (currState.isRedundant(i))
                    continue;
                    
                // Gets the next state transitioned from the move.
                State nextState;
                try
                {
                    nextState = State.movePlayer(new State(currState), i);
                }
                catch (Exception e)
                {
                    continue;
                }
                
                // Checks the next state if it isn't null.
                if (nextState != null)
                {
                    // Returns the path to the goal state.
                    if (nextState.boxCoor.equals(State.targetCoor))
                        return reversePath(nextState);

                    // Adds the next state to the queue if it has not yet been visited.
                    if (visitedStates.add(nextState.getHashCode()))
                        queue.add(nextState);

                    lastChecked = i;
                }    
            }

            // Returns the path to the last state checked.
            if (queue.isEmpty())
                return reversePath(State.movePlayer(new State(currState), lastChecked));
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
        StringBuilder path = new StringBuilder().append(endState.prev.move);

        // Gets the details of the state previous to the end state.
        Prev prevDetails = endState.prev.prevDetails;

        // Constructs the path in reverse.
        while (prevDetails != null)
        {
            path.append(prevDetails.move);
            prevDetails = prevDetails.prevDetails;
        }

        // Returns the reversed constructed path.
        return path.reverse().toString();
    }
}
