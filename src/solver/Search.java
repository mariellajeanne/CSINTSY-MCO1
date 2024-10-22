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
    private PriorityQueue<State> pQueue = new PriorityQueue<State>((s1, s2) -> s2.heuristic.heuristicVal - s1.heuristic.heuristicVal);

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
        // System.out.println("added starting state");

        ArrayList<String> wallCoorList = new ArrayList<>(State.wallCoor);
        Collections.sort(wallCoorList);
        System.out.println("Wall Coors: " + wallCoorList);
        System.out.println("Target Coors: " + State.targetCoor);

        while (!queue.isEmpty())
        {
            State currState = queue.poll();
            System.out.println("\n===========");
            // System.out.println("dequeue");
            System.out.println("queue size: " + queue.size());
            System.out.println("\nBox Coors: " + currState.boxCoor);
            System.out.println("Player Coor: " + currState.playerCoor);

            if (currState.boxCoor.equals(State.targetCoor))
            {
                System.out.println("target reached");
                return reversePath(currState);
            }


            // go through each of the next states (left, right, up, down):
            for (int i = 0; i < 4; i++) {
                
                System.out.println("");
                System.out.println("+ move: " + moves[i]);    
                State nextState = State.movePlayer(new State(currState), moves[i], offsetx[i], offsety[i]);
                
                
                if (nextState == null)
                {
                    System.out.println("nextState: null");
                    continue;
                }
                System.out.println("@ Box Coors: " + nextState.boxCoor);
                System.out.println("@ Player Coor: " + nextState.playerCoor);
                
                System.out.println("prev moves: " + reversePath(nextState));
                
                if (nextState.boxCoor.equals(State.targetCoor))
                {
                    System.out.println("nextState: target");
                    return reversePath(nextState);
                }
                
                if (!visitedStates.contains(nextState.getHashCode()))
                {
                    queue.add(nextState);
                    visitedStates.add((nextState.getHashCode()));
                    System.out.println("nextState: added to queue & visited");
                }
                
                System.out.print("> queue size: " + queue.size() + " | ");
                System.out.println("visited states size: " + visitedStates.size());
            }
        }

        System.out.println("\nreturn empty string");
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
                State nextState = State.movePlayer(new State(currState), moves[i], offsetx[i], offsety[i]);
                if (nextState == null)
                {
                    continue;
                }
                if (nextState.boxCoor.equals(State.targetCoor))
                {
                    return reversePath(nextState) + moves[i];
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
        StringBuilder path = new StringBuilder(endState.prevMove);

        State currState = endState.prevState;
        while (currState != null)
        {
            path.append(currState.prevMove);
            currState = currState.prevState;
        }

        return path.reverse().toString();
    }
}
