/**
 * @author <Ronnie M. Abiog Jr.>
 * 
 * Creates and manipulates states.
 */

package solver;
import java.util.*;

/**
 * The state class.
*/
public class State
{
    private final char[][] map;     // The map of the state.
    private final char[][] items;   // The items of the state.

    private int[] playerCoor;               // The player coordinates.
    private HashSet<int[]> boxCoor;         // The boxes' coordinates.
    private HashSet<int[]> targetCoor;      // The targets' coordinates.
    private static HashSet<int[]> wallCoor; // The walls' coordinates.

    private final State prevState;  // The previous state before executing a move.
    private final char prevMove;    // The previous move that leads to the current state.

    private int[] boxPushed;        // The coordinates of the pushed box.

    private boolean isVisited;      // Determines if the state has already been visited.

    /**
     * Constructs the initial state.
     * 
     * @param map   {char[][]}  The map.
     * @param items {char[][]}  The items.
     */
    public State(char[][] map, char[][] items)
    {
        this.map = map;
        this.items = items;

        setCoordinates();

        this.prevState = null;
        this.prevMove = ' ';

        this.isVisited = false;
    }

    /**
     * Constructs the next states.
     * 
     * @param map           {char[][]}          The map.
     * @param items         {char[][]}          The items.
     * 
     * @param playerCoor    {int[]}             The player coordinates.
     * @param boxCoor       {HashSet<int[]>}    The boxes' coordinates.
     * @param targetCoor    {HashSet<int[]>}    The targets' coordinates.
     * 
     * @param prevState     {State}             The previous state before executing a move.
     * @param prevMove      {char}              The previous move that leads to the current state.
     */
    public State(char[][] map, char[][] items, int[] playerCoor, HashSet<int[]> boxCoor,
                 HashSet<int[]> targetCoor, State prevState, char prevMove)
    {
        this.map = map;
        this.items = items;
        
        this.playerCoor = playerCoor;
        this.boxCoor = boxCoor;
        this.targetCoor = targetCoor;
        
        this.prevState = prevState;
        this.prevMove = prevMove;

        isVisited = false;
    }

    /**
     * Returns the map of the state.
     * 
     * @return {char[][]}
     */
    public char[][] getMap()
    {
        return this.map;
    }

    /**
     * Returns the items of the state.
     * 
     * @return {char[][]}
     */
    public char[][] getItems()
    {
        return this.items;
    }

    /**
     * Returns the player's coordinates.
     * 
     * @return {int[]}
     */
    public int[] getPlayerCoor()
    {
        return this.playerCoor;
    }

    /**
     * Returns the boxes' coordinates.
     * 
     * @return {HashSet<int[]>}
     */
    public HashSet<int[]> getBoxCoor()
    {
        return this.boxCoor;
    }

    /**
     * Returns the targets' coordinates.
     * 
     * @return {HashSet<int[]>}
     */
    public HashSet<int[]> getTargetCoor()
    {
        return this.targetCoor;
    }

    /**
     * Returns the walls' coordinates.
     * 
     * @return {HashSet<int[]>}
     */
    public HashSet<int[]> getWallCoor()
    {
        return State.wallCoor;
    }

    /**
     * Returns the previous state.
     * 
     * @return {State}
     */
    public State getPrevState()
    {
        return this.prevState;
    }

    /**
     * Returns the previous mmove.
     * 
     * @return {char}
     */
    public char getPrevMove()
    {
        return this.prevMove;
    }

    /**
     * Sets the coordinates of each tile.
     */
    private void setCoordinates()
    {
        // Initialization of coordinate variables.
        playerCoor = new int[2];
        boxCoor = new HashSet<>();
        targetCoor = new HashSet<>();
        wallCoor = new HashSet<>();

        // Sets the initial coordinates of the pushed box to (-1, -1).
        boxPushed[0] = -1;
        boxPushed[1] = -1;

        // Sets the coordinates of the map and items.

        // Checks each row.
        for (int y = 0; y < 100; y++)
        {
            // Checks each column.
            for (int x = 0; x < 100; x++)
            {
                // Checks the items data.
                switch (items[y][x])
                {
                    case '$' -> { addCoor(boxCoor, x, y); }
                    case '*' -> { addCoor(boxCoor, x, y); }
                    case '@' -> { playerCoor[0] = x;
                                  playerCoor[1] = y;      }
                }

                // Checks the map data.
                switch (map[y][x])
                {
                    case '#' -> { addCoor(wallCoor, x, y);   }
                    case '.' -> { addCoor(targetCoor, x, y); }
                    case '*' -> { addCoor(targetCoor, x, y); }
                }
            }
        }
    }

    /**
     * Adds a coordinate to a set coordinates.
     * 
     * @param set   {HashSet<int[]>}    The set of coordinates.
     * @param x     {int}               The x-coordinate.
     * @param y     {int}               The y-coordinate.
     */
    private void addCoor(HashSet<int[]> set, int x, int y)
    {
        int[] coor = {x, y};
        set.add(coor);
    }

    /**
     * Removes a coordinate to a set coordinates.
     * 
     * @param set   {HashSet<int[]>}    The set of coordinates.
     * @param x     {int}               The x-coordinate.
     * @param y     {int}               The y-coordinate.
     */
    private void removeCoor(HashSet<int[]> set, int x, int y)
    {
        int[] coor = {x, y};
        set.remove(coor);
    }

    /**
     * Checks if a coordinate exists in a set of coordinates.
     * 
     * @param set   {HashSet<int[]>}    The set of coordinates.
     * @param x     {int}               The x-coordinate.
     * @param y     {int}               The y-coordinate.
     * @return      {boolean}
     */
    private boolean containsCoor(HashSet<int[]> set, int x, int y)
    {
        int[] coor = {x, y};
        return set.contains(coor);
    }

    /**
     * Marks the state as visited.
     */
    public void visit()
    {
        this.isVisited = true;
    }

    /**
     * Determines if the state is visited.
     * 
     * @return {boolean}
     */
    public boolean isVisited()
    {
        return this.isVisited;
    }

    /**
     * Returns a new state resulting from player movement.
     * Returns null if the could not or should not be made.
     * 
     * @param move {char} The move made (left: 'l', right: 'r', up: 'u', down: 'd').
     * @param offsetx {int} The x-coordinate offset.
     * @param offsety {int} The y-coordinate offset.
     * @return {State}
     */
    public State movePlayer(char move, int offsetx, int offsety)
    {
        return new State(map, items, playerCoor, boxCoor, targetCoor, prevState, prevMove);
    }
}
