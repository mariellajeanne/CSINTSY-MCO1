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
    public int[] playerCoor;                    // The player coordinates.
    public HashSet<C<int[]>> boxCoor;           // The boxes' coordinates.
    public static HashSet<C<int[]>> targetCoor; // The targets' coordinates.
    public static HashSet<C<int[]>> wallCoor;   // The walls' coordinates.

    public final State prevState;  // The previous state before executing a move.
    public final char prevMove;    // The previous move that leads to the current state.

    public int[] boxPushed;   // The coordinates of the pushed box.

    public boolean isVisited; // Determines if the state has already been visited.

    /**
     * Constructs the initial state.
     * 
     * @param map   {char[][]}  The map.
     * @param items {char[][]}  The items.
     */
    public State(char[][] map, char[][] items)
    {
        setCoordinates(map, items);

        this.prevState = null;
        this.prevMove = ' ';

        this.boxPushed = new int[2];
        this.boxPushed[0] = -1;
        this.boxPushed[1] = -1;

        this.isVisited = false;
    }

    /**
     * Constructs the next states.
     * 
     * @param map           {char[][]}          The map.
     * @param items         {char[][]}          The items.
     * 
     * @param playerCoor    {int[]}                      The player coordinates.
     * @param boxCoor       {HashSet<C<int[]>>}    The boxes' coordinates.
     * @param targetCoor    {HashSet<C<int[]>>}    The targets' coordinates.
     * 
     * @param prevState     {State}             The previous state before executing a move.
     * @param prevMove      {char}              The previous move that leads to the current state.
     */
    public State(int[] playerCoor, HashSet<C<int[]>> boxCoor, State prevState, char prevMove)
    {
        this.playerCoor = playerCoor;
        this.boxCoor = boxCoor;
        
        this.prevState = prevState;
        this.prevMove = prevMove;

        this.boxPushed = new int[2];
        this.boxPushed[0] = -1;
        this.boxPushed[1] = -1;

        this.isVisited = false;
    }

    /**
     * Sets the coordinates of each tile.
     * 
     * @param map   {char[][]}  The map.
     * @param items {char[][]}  The items.
     */
    private void setCoordinates(char[][] map, char[][] items)
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
                // Checks the map data.
                switch (map[y][x])
                {
                    case '#' -> { addCoor(wallCoor, x, y);   }
                    case '.' -> { addCoor(targetCoor, x, y); }
                }

                // Checks the items data.
                switch (items[y][x])
                {
                    case '$' -> { addCoor(boxCoor, x, y); }
                    case '@' -> { playerCoor[0] = x;
                                  playerCoor[1] = y;      }
                }
            }
        }
    }

    

    /**
     * Adds a coordinate to a set coordinates.
     * 
     * @param set   {HashSet<C<int[]>>} The set of coordinates.
     * @param x     {int} The x-coordinate.
     * @param y     {int} The y-coordinate.
     */
    private void addCoor(HashSet<C<int[]>> set, int x, int y)
    {
        int[] coor = {x, y};
        set.add(new C<>(coor));
    }

    // /**
    //  * Removes a coordinate to a set coordinates.
    //  * 
    //  * @param set   {HashSet<int[]>}    The set of coordinates.
    //  * @param x     {int}               The x-coordinate.
    //  * @param y     {int}               The y-coordinate.
    //  */
    // private void removeCoor(HashSet<int[]> set, int x, int y)
    // {
    //     int[] coor = {x, y};
    //     set.remove(coor);
    // }

    /**
     * Checks if a coordinate exists in a set of coordinates.
     * Uses the x and y coordinates individually.
     * 
     * @param set   {HashSet<C<int[]>>} The set of coordinates.
     * @param x     {int}               The x-coordinate.
     * @param y     {int}               The y-coordinate.
     * @return      {boolean}
     */
    private boolean containsCoor(HashSet<C<int[]>> set, int x, int y)
    {
        int[] coor = {x, y};
        return set.contains(new C<>(coor));
    }

    /**
     * Checks if a coordinate exists in a set of coordinates.
     * Uses the x and y coordinate pair.
     * 
     * @param set   {HashSet<C<int[]>>} The set of coordinates.
     * @param coor  {int[]} The x and y coordinate pair.
     * @return      {boolean}
     */
    private boolean containsCoor(HashSet<C<int[]>> set, int[] coor)
    {
        return set.contains(new C<>(coor));
    }

    /**
     * Marks the state as visited.
     */
    public void visit()
    {
        this.isVisited = true;
    }

    // check Search if we need an unvisit method

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
     * Returns a new state resulting from the player moving.
     * Returns null if the move cannot be made.
     * 
     * @param move      {char}  The move made (left: 'l', right: 'r', up: 'u', down: 'd').
     * @param xOffset   {int}   The horizontal offset (left: -1, right: 1, none: 0).
     * @param yOffset   {int}   The vertical offset (up: 1, down: -1, none: 0).
     * @return          {State}
     */
    public State movePlayer(char move, int xOffset, int yOffset)
    {
        // Sets the coordinates of the next two tiles.
        int[][] nextTiles = {{playerCoor[0] + xOffset, playerCoor[1] + yOffset},
            {playerCoor[0] + (xOffset * 2), playerCoor[1] + (yOffset * 2)}};

        // Checks if the next tile contains a wall.
        if (wallCoor.contains(new C<>(nextTiles[0])))
            return null;

        // Checks if the next tile contains a box.
        if (boxCoor.contains(new C<>(nextTiles[0])))
        {
            // Checks if the box is immovable.
            if (boxCoor.contains(new C<>(nextTiles[1])) || wallCoor.contains(new C<>(nextTiles[1])))
                return null;

            // Updates the coordinates of the pushed box.
            boxCoor.remove(new C<>(nextTiles[0]));
            boxCoor.add(new C<>(nextTiles[1]));
            boxPushed[0] = nextTiles[1][0];
            boxPushed[1] = nextTiles[1][1];

            // Checks if the move results in a loss.
            if (!containsCoor(targetCoor, boxPushed) && isLoss())
                return null;
        }

        // The next tile does not contain a wall nor a box.
        else
        {
            // Checks if the move is redundant.
            if ((move == 'l' && prevState.prevMove == 'r') ||
                (move == 'r' && prevState.prevMove == 'l') ||
                (move == 'u' && prevState.prevMove == 'd') ||
                (move == 'd' && prevState.prevMove == 'u'))
                return null;
        }

        // Updates the player's location.
        playerCoor[0] = nextTiles[0][0];
        playerCoor[1] = nextTiles[0][1];

        // Returns the new state otherwise.
        return new State(playerCoor, boxCoor, this, move);
    }

    /**
     * Checks if the state results in a loss.
     * 
     * @return {boolean}
     */
    private boolean isLoss()
    {
        if (isStuckInCorner())
            return true;
        // else if (isAtEdgeWithNoGoal())
        //     return true;
        else if (isClumpedUp())
            return true;

        return false;
    }

    private boolean isStuckInCorner()
    {
        // checks if box is on a target
        if (containsCoor(targetCoor, boxPushed))
            return false;
            int x = boxPushed[0];
            int y = boxPushed[1];
        
            // Check if the box is in a corner formed by walls
            if (containsCoor(wallCoor, x - 1, y) && containsCoor(wallCoor, x, y + 1))
                return true;
            else if (containsCoor(wallCoor, x - 1, y) && containsCoor(wallCoor, x, y - 1))
                return true;
            else if (containsCoor(wallCoor, x + 1, y) && containsCoor(wallCoor, x, y - 1))
                return true;
            else if (containsCoor(wallCoor, x + 1, y) && containsCoor(wallCoor, x, y + 1))
                return true;
            
            //if box is beside another immoveable box
            if ((containsCoor(wallCoor, x, y + 1) || containsCoor(wallCoor, x, y - 1)) && 
                ((containsCoor(boxCoor, x + 1, y) && wallAboveOrBelow(x + 1, y)) || 
                (containsCoor(boxCoor, x - 1, y) && wallAboveOrBelow(x - 1, y))))
            {
                    return true;
            }
            else if ((containsCoor(wallCoor, x + 1, y) || containsCoor(wallCoor, x - 1, y)) && 
            ((containsCoor(boxCoor, x, y + 1) && wallBeside(x, y + 1)) || 
            (containsCoor(boxCoor, x, y - 1) && wallBeside(x, y - 1))))
            {
                return true;
            }
                
            return false;
    }

    private boolean wallAboveOrBelow(int x, int y)
    {
        return containsCoor(wallCoor, x, y + 1) || containsCoor(wallCoor, x, y - 1);
    }

    private boolean wallBeside(int x, int y)
    {
        return containsCoor(wallCoor, x + 1, y) || containsCoor(wallCoor, x - 1, y);
    }

    private boolean isClumpedUp()
    {
        int x = boxPushed[0];
        int y = boxPushed[1];

        int[][][] squares = {{{1, 0}, {1, 1}, {0, 1}},
                                {{-1, 0}, {-1, 1}, {0, 1}},
                                {{0, -1}, {1, -1}, {1, 0}},
                                {{0, -1}, {-1, -1}, {-1, 0}}};
        for (int[][] square: squares)
        {
            int x1 = square[0][0];
            int y1 = square[0][1];
            int x2 = square[1][0];
            int y2 = square[1][1];
            int x3 = square[2][0];
            int y3 = square[2][1];
            
            //check if the 4 boxes are clumped up
            if (containsCoor(boxCoor, x + x1, y + y1) &&
                containsCoor(boxCoor, x + x2, y + y2) &&
                containsCoor(boxCoor, x + x3, y + y3)) 
            {
                //checks if at least one of the clumped up boxes is not on a target
                if (!containsCoor(targetCoor, x, y) ||
                    !containsCoor(targetCoor, x + x1, y + y1) ||
                    !containsCoor(targetCoor, x + x2, y + y2) ||
                    !containsCoor(targetCoor, x + x3, y + y3)) 
                {
                    return true; 
                }
            }
        }

        return false;
    }
}

/**
 * isLose pseudo:
 *     for each box:
 *          if stuck in corner(touching 2 walls)
 *              lose
 *          if 4 boxes are clumped up
 *              lose
 */