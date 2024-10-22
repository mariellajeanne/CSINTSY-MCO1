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
    public final State prevState;  // The previous state before executing a move.
    public final char prevMove;    // The previous move that leads to the current state.

    public static HashSet<String> wallCoor;   // The walls' coordinates.
    public static HashSet<String> targetCoor; // The targets' coordinates.

    public String playerCoor;       // The player coordinates.
    public HashSet<String> boxCoor; // The boxes' coordinates.
    public String boxPushedCoor;    // The coordinates of the pushed box.

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

        this.boxPushedCoor = " ";

        this.isVisited = false;
    }

    /**
     * Creates a deep copy of a state.
     * 
     * @param other {State} The state to copy.
     */
    public State(State other)
    {
        this.playerCoor = other.playerCoor;
        this.boxCoor = new HashSet<>(other.boxCoor);
        
        this.boxPushedCoor = " ";
        this.isVisited = other.isVisited;

        this.prevMove = other.prevMove;

        if (other.prevState != null)
            this.prevState = new State(other.prevState);
        else
            this.prevState = null;
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
        playerCoor = " ";
        boxCoor = new HashSet<>();
        targetCoor = new HashSet<>();
        wallCoor = new HashSet<>();

        // Sets the initial coordinates of the pushed box.
        boxPushedCoor = " ";

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
    public static boolean conCoor(HashSet<String> set, int x, int y)
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
    public static String strCoor(int x, int y)
    {
        return (x + "," + y + ".");
    }

    /**
     * Returns the x coordinate of an x and y coordinate pair.
     * 
     * @param coor {String} The x and y coordinate pair.
     * @return {int}
     */
    public static int getX(String coor)
    {
        return Integer.parseInt(coor.split(",")[0]);
    }

    /**
     * Returns the y coordinate of an x and y coordinate pair.
     * 
     * @param coor {String} The x and y coordinate pair.
     * @return {int}
     */
    public static int getY(String coor)
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
     * @param state      {State} The state to move from.
     * @param move      {char}  The move made (left: 'l', right: 'r', up: 'u', down: 'd').
     * @param xOffset   {int}   The horizontal offset (left: -1, right: 1, none: 0).
     * @param yOffset   {int}   The vertical offset (up: 1, down: -1, none: 0).
     * @return          {State}
     */
    public static State movePlayer(State state, char move, int xOffset, int yOffset)
    {
        // Gets the x and y coordinates of the player.
        int xPlayer = getX(state.playerCoor);
        int yPlayer = getY(state.playerCoor);

        // Sets the coordinates of the next two tiles.
        String nextTile1 = strCoor(xPlayer + xOffset, yPlayer + yOffset);
        String nextTile2 = strCoor(xPlayer + (2 * xOffset), yPlayer + (2 * yOffset));

        // Checks if the next tile contains a wall.
        if (wallCoor.contains(nextTile1))
        {
            System.out.println("! Wall: nextTile1");
            return null;
        }

        // Checks if the next tile contains a box.
        if (state.boxCoor.contains(nextTile1))
        {
            System.out.println("! Box: nextTile1");
            // Checks if the box is immovable.
            if (state.boxCoor.contains(nextTile2) || wallCoor.contains(nextTile2))
            {
                System.out.println("! Box: immovable");
                return null;
            }

            // Updates the coordinates of the pushed box.
            state.boxCoor.remove(nextTile1);
            state.boxCoor.add(nextTile2);
            state.boxPushedCoor = nextTile2;

            // Checks if the move results in a loss.
            if (!targetCoor.contains(state.boxPushedCoor) && isStuck(state))
            {
                System.out.println("! Box: stuck");
                return null;
            }
        }

        // The next tile does not contain a wall nor a box.
        else
        {
            if (state.prevState == null) { /* do nothing */ }

            // Checks if the move is redundant.
            else if ((move == 'l' && state.prevMove == 'r') ||
                (move == 'r' && state.prevMove == 'l') ||
                (move == 'u' && state.prevMove == 'd') ||
                (move == 'd' && state.prevMove == 'u'))
                {
                    System.out.println("! Space: redundant");
                    return null;
                }
        }

        // Updates the player's location.
        state.playerCoor = nextTile1;

        // Returns the new state otherwise.
        System.out.println("! Returned new state");
        return new State(state.playerCoor, state.boxCoor, state, move);
    }

    /**
     * Checks if the recently pushed box can no longer be moved.
     * 
     * @param state {State} The state to check.
     * @return {boolean}
     */
    private static boolean isStuck(State state)
    {
        // Gets the x and y coordinates of the pushed box.
        int x = getX(state.boxPushedCoor);
        int y = getY(state.boxPushedCoor);

        // Offsets of the coordinates above, beside, and below the pushed box.
        int[][] offsets = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0}
        };

        // Loops through each offset coordinate.
        for (int i = 0; i < 4; i++)
        {
            // Gets the index of the next offset.
            int j = (i + 1) % 4;

            // Checks the contents of the current coordinate.
            boolean isCurrWall  = conCoor(wallCoor, x + offsets[i][0], y + offsets[i][1]);
            boolean isCurrBox   = conCoor(state.boxCoor, x + offsets[i][0], y + offsets[i][1]);

            // Checks the contents of the next offset coordinate.
            boolean isNextWall  = conCoor(wallCoor, x + offsets[j][0], y + offsets[j][1]);
            boolean isNextBox   = conCoor(state.boxCoor, x + offsets[j][0], y + offsets[j][1]);

            // Checks if there is a wall/box in between the current and next offset coordinates.
            boolean isCornerObs =   conCoor(state.boxCoor, x + offsets[i][0] + offsets[j][0],
                                                    y + offsets[i][1] + offsets[j][1]) ||
                                    conCoor(wallCoor, x + offsets[i][0] + offsets[j][0],
                                                    y + offsets[i][1] + offsets[j][1]);

            if ((isCurrWall && (isNextWall || (isNextBox && isCornerObs))) ||
                (isCurrBox && isCornerObs && (isNextWall || isNextBox)))
                {
                    System.out.print("! Box: stuck");
                    System.out.print(" i="+i);
                    System.out.print(" j="+j);
                    System.out.print(" isCurrWall="+isCurrWall);
                    System.out.print(" isCurrBox="+isCurrBox);
                    System.out.print(" isNextWall="+isNextWall);
                    System.out.print(" isNextBox="+isNextBox);
                    System.out.println(" isCornerObs="+isCornerObs);
                    return true;
                }
        }

        return false;
    }
}
