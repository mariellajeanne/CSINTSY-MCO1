/**
 * @author Mariella Jeanne A. Dellosa
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
    public String playerCoor;                 // The player coordinates.
    public HashSet<String> boxCoor;           // The boxes' coordinates.
    public static HashSet<String> targetCoor; // The targets' coordinates.
    public static HashSet<String> wallCoor;   // The walls' coordinates.

    public final State prevState;  // The previous state before executing a move.
    public final char prevMove;    // The previous move that leads to the current state.

    public String boxPushedCoor;   // The coordinates of the pushed box.

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

        this.boxPushedCoor = "";

        this.isVisited = false;
    }

    /**
     * Constructs the next states.
     * 
     * @param map           {char[][]}          The map.
     * @param items         {char[][]}          The items.
     * 
     * @param playerCoor    {String}                The player coordinates.
     * @param boxCoor       {HashSet<C<String>>}    The boxes' coordinates.
     * @param targetCoor    {HashSet<C<String>>}    The targets' coordinates.
     * 
     * @param prevState     {State}             The previous state before executing a move.
     * @param prevMove      {char}              The previous move that leads to the current state.
     */
    public State(String playerCoor, HashSet<String> boxCoor, State prevState, char prevMove)
    {
        this.playerCoor = playerCoor;
        this.boxCoor = boxCoor;
        
        this.prevState = prevState;
        this.prevMove = prevMove;

        this.boxPushedCoor = "";

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
        playerCoor = "";
        boxCoor = new HashSet<>();
        targetCoor = new HashSet<>();
        wallCoor = new HashSet<>();

        // Sets the initial coordinates of the pushed box.
        boxPushedCoor = "";

        // Sets the coordinates of the map and items.

        // Checks each row.
        for (int y = 0; y < map.length; y++)
        {
            // Checks each column.
            for (int x = 0; x < map[y].length; x++)
            {
                // Checks the map data.
                switch (map[y][x])
                {
                    case '#' -> { addCoor(wallCoor, x, y); }
                    case '.' -> { addCoor(targetCoor, x, y); }
                }

                // Checks the items data.
                switch (items[y][x])
                {
                    case '$' -> { addCoor(boxCoor, x, y); }
                    case '@' -> { playerCoor = strCoor(x, y); }
                }
            }
        }
    }

    /**
     * Adds a coordinate to a set.
     * 
     * @param set {HashSet<String>} The coordinate set.
     * @param x {int} The x coordinate.
     * @param y {int} The y coordinate.
     */
    public void addCoor(HashSet<String> set, int x, int y)
    {
        set.add(x + "," + y + ".");
    }

    /**
     * Removes a coordinate from a set.
     * 
     * @param set {HashSet<String>} The coordinate set.
     * @param x {int} The x coordinate.
     * @param y {int} The y coordinate.
     */
    public void remCoor(HashSet<String> set, int x, int y)
    {
        set.remove(x + "," + y + ".");
    }

    /**
     * Checks if a set contains an x and y coordinate pair.
     * 
     * @param set {HashSet<String>} The coordinate set.
     * @param x {int} The x coordinate.
     * @param y {int} The y coordinate.
     * @return {boolean}
     */
    public boolean conCoor(HashSet<String> set, int x, int y)
    {
        return set.contains(x + "," + y + ".");
    }

    /**
     * Returns a string of an x and y coordinate pair.
     * 
     * @param x {int} The x coordinate.
     * @param y {int} The y coordinate.
     * @return {String}
     */
    public String strCoor(int x, int y)
    {
        return (x + "," + y + ".");
    }

    /**
     * Returns the x coordinate of an x and y coordinate pair.
     * 
     * @param coor {String} The x and y coordinate pair.
     * @return {int}
     */
    public int getX(String coor)
    {
        return Integer.parseInt(coor.split(",")[0]);
    }

    /**
     * Returns the y coordinate of an x and y coordinate pair.
     * 
     * @param coor {String} The x and y coordinate pair.
     * @return {int}
     */
    public int getY(String coor)
    {
        return Integer.parseInt(coor.split(",")[1].replace(".", ""));
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
     * Returns the hash code of the state.
     * 
     * @return {String}
     */
    public String getHashCode()
    {
        List<String> boxCoorList = new ArrayList<>(boxCoor);
        StringBuilder sb = new StringBuilder();

        // Sorts the box coordinates.
        Collections.sort(boxCoorList);

        // Concatinates the player's coordinates.
        sb.append(playerCoor);
        
        // Concatinates the boxes' coordinates.
        for (String coor : boxCoorList)
            sb.append(coor);

        // Returns the player's and boxes' coordinates, concatinated.
        return sb.toString();
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
        // Gets the x and y coordinates of the player.
        int playerX = getX(playerCoor);
        int playerY = getY(playerCoor);

        // Sets the coordinates of the next two tiles.
        String nextTile1 = strCoor(playerX + xOffset, playerY + yOffset);
        String nextTile2 = strCoor(playerX + 2 * xOffset, playerY + 2 * yOffset);

        // Checks if the next tile contains a wall.
        if (wallCoor.contains(nextTile1))
        {
            System.out.println("nextTile1 wall");
            return null;
        }

        // Checks if the next tile contains a box.
        if (boxCoor.contains(nextTile1))
        {
            // Checks if the box is immovable.
            if (boxCoor.contains(nextTile2) || wallCoor.contains(nextTile2))
            {
                System.out.println("immovable");
                return null;
            }

            // Updates the coordinates of the pushed box.
            boxCoor.remove(nextTile1);
            boxCoor.add(nextTile2);
            boxPushedCoor = nextTile2;

            // Checks if the move results in a loss.
            if (!targetCoor.contains(boxPushedCoor) && isStuck())
            {
                System.out.println("stuck");
                return null;
            }
        }

        // The next tile does not contain a wall nor a box.
        else
        {
            if (prevState == null) { /* do nothing */ }

            // Checks if the move is redundant.
            else if ((move == 'l' && prevState.prevMove == 'r') ||
                (move == 'r' && prevState.prevMove == 'l') ||
                (move == 'u' && prevState.prevMove == 'd') ||
                (move == 'd' && prevState.prevMove == 'u'))
                {
                    System.out.println("redundant");
                    return null;
                }
        }

        // Updates the player's location.
        playerCoor = nextTile1;

        // Returns the new state otherwise.
        return new State(playerCoor, boxCoor, this, move);
    }

    /**
     * Checks if the recently pushed box can no longer be moved.
     * 
     * @return {boolean}
     */
    private boolean isStuck()
    {
        if (isStuckInCorner())
            return true;
        else if (isClumpedUp())
            return true;

        return false;
    }

    private boolean isStuckInCorner()
    {
        int x = getX(boxPushedCoor);
        int y = getX(boxPushedCoor);
        // checks if box is on a target
        if (conCoor(targetCoor, x, y))
            return false;
        
            // Check if the box is in a corner formed by walls
            if (conCoor(wallCoor, x - 1, y) && conCoor(wallCoor, x, y + 1))
                return true;
            else if (conCoor(wallCoor, x - 1, y) && conCoor(wallCoor, x, y - 1))
                return true;
            else if (conCoor(wallCoor, x + 1, y) && conCoor(wallCoor, x, y - 1))
                return true;
            else if (conCoor(wallCoor, x + 1, y) && conCoor(wallCoor, x, y + 1))
                return true;
            
            //if box is adjacent to wall and another immoveable box
            if ((conCoor(wallCoor, x, y + 1) || conCoor(wallCoor, x, y - 1)) && 
                ((conCoor(boxCoor, x + 1, y) && wallAboveOrBelow(x + 1, y)) || 
                (conCoor(boxCoor, x - 1, y) && wallAboveOrBelow(x - 1, y))))
            {
                    return true;
            }
            else if ((conCoor(wallCoor, x + 1, y) || conCoor(wallCoor, x - 1, y)) && 
            ((conCoor(boxCoor, x, y + 1) && wallBeside(x, y + 1)) || 
            (conCoor(boxCoor, x, y - 1) && wallBeside(x, y - 1))))
            {
                return true;
            }
                
            return false;
    }

    private boolean wallAboveOrBelow(int x, int y)
    {
        return conCoor(wallCoor, x, y + 1) || conCoor(wallCoor, x, y - 1);
    }

    private boolean wallBeside(int x, int y)
    {
        return conCoor(wallCoor, x + 1, y) || conCoor(wallCoor, x - 1, y);
    }

    private boolean isClumpedUp()
    {
        int x = getX(boxPushedCoor);
        int y = getY(boxPushedCoor);

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
            if (conCoor(boxCoor, x + x1, y + y1) &&
                conCoor(boxCoor, x + x2, y + y2) &&
                conCoor(boxCoor, x + x3, y + y3)) 
            {
                //checks if at least one of the clumped up boxes is not on a target
                if (!conCoor(targetCoor, x, y) ||
                    !conCoor(targetCoor, x + x1, y + y1) ||
                    !conCoor(targetCoor, x + x2, y + y2) ||
                    !conCoor(targetCoor, x + x3, y + y3)) 
                {
                    return true; 
                }
            }
        }

        return false;
    }
}
