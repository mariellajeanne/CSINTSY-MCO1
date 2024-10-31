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
    // private final ArrayDeque<State> queue = new ArrayDeque<>();

    private final PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparing(State::heuristic));

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
            for (int i = 0; i < 4; i++)
            {
                // Checks if the move is redundant.
                if (currState.isRedundant(i))
                    continue;
                    
                // Gets the next state transitioned from the move.
                State nextState;
                try
                {
                    // Sets the move.
                    char move = switch(i)
                    {
                        case 0 -> 'u';
                        case 1 -> 'r';
                        case 2 -> 'd';
                        case 3 -> 'l';
                        default -> 'u';
                    };

                    // Sets the x offset.
                    int xOffset = switch(i)
                    {
                        case 0 -> 0;
                        case 1 -> 1;
                        case 2 -> 0;
                        case 3 -> -1;
                        default -> 0;
                    };

                    // Sets the y offset.
                    int yOffset = switch(i)
                    {
                        case 0 -> -1;
                        case 1 -> 0;
                        case 2 -> 1;
                        case 3 -> 0;
                        default -> -1;
                    };

                    nextState = State.movePlayer(new State(currState), move, xOffset, yOffset, (i + 3) % 4);
                }
                catch (Exception e)
                {
                    continue;
                }
                
                // Checks the next state if it isn't null.
                if (nextState != null)
                {
                    // Adds the next state to the queue if it has not yet been visited.
                    if (visitedStates.add(nextState.getHashCode()))
                        queue.add(nextState);

                    // Returns the path to the last state.
                    if (nextState.boxCoor.equals(State.targetCoor) || queue.isEmpty())
                        return reversePath(nextState);
                }    
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
