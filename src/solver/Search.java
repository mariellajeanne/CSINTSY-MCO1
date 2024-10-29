/**
 * @author Ana Victoria R. Angat
 * 
 * Searches the winning state using Breadth-First Search.
 */

package solver;
import java.util.*;
import java.util.concurrent.*;

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

    // The Queue and HashMap for searching.
    private Queue<State> queue;
    private ConcurrentHashMap<String, Boolean> visitedStates;
    private ExecutorService executorService;

    /**
     * Constructor for the search class.
     * Initializes the executor service with 4 threads.
     */
    private Search()
    {
        this.executorService = Executors.newFixedThreadPool(4);
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
     * Returns the sequence of the goal state's moves using Breadth-First Search.
    * Returns "" otherwise.
    * 
    * @param startingState {State} The starting state.
    * 
    * @return {String}
    */
    public String getSequenceBFS(State startingState)
    {
        // Initialize the queue and visited states.
        queue = new ConcurrentLinkedQueue<>();
        visitedStates = new ConcurrentHashMap<>();
        
        queue.add(startingState);
        visitedStates.put(startingState.getHashCode(), true);

        while (!queue.isEmpty())
        {
            // Dequeues the head of the queue.
            State currState = queue.poll();

            // Checks each move from the current state (i.e., left, right, up, and down).
            List<Future<State>> futures = new ArrayList<>();
            for (int i = 0; i < 4; i++)
            {
                final int moveIndex = i;
                Callable<State> task = () -> {
                    try {
                        return State.movePlayer(new State(currState), moves[moveIndex], xOffset[moveIndex], yOffset[moveIndex]);
                    } catch (Exception e) {
                        return null;
                    }
                };
                futures.add(executorService.submit(task));
            }

            for (Future<State> future : futures)
            {
                try {
                    State nextState = future.get();
                    if (nextState == null || visitedStates.containsKey(nextState.getHashCode()))
                        continue;

                    if (nextState.boxCoor.equals(State.targetCoor))
                    {
                        executorService.shutdownNow();
                        return reversePath(nextState);
                    }

                    queue.add(nextState);
                    visitedStates.put(nextState.getHashCode(), true);

                } catch (Exception e) {
                    continue;
                }
            }
        }

        executorService.shutdownNow();
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