/**
 * @author Mariella Jeanne A. Dellosa
 * @author Ronnie M. Abiog Jr.
 * @author Anthony Andrei C. Tan
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
    public Prev prev; // Details of the previous state.

    public static HashSet<Coor> wallCoor;   // The walls' coordinates.
    public static HashSet<Coor> targetCoor; // The targets' coordinates.

    public Coor playerCoor;       // The player's coordinates.
    public TreeSet<Coor> boxCoor; // The boxes' coordinates.
    public Coor boxPushedCoor;    // The recently pushed box's coordinates.

    public static int xTargetSum; // Sum of the targets' x coordinates.
    public static int yTargetSum; // Sum of the targets' y coordinates.

    public int xBoxSum; // Sum of the boxes' x coordinates.
    public int yBoxSum; // Sum of the boxes' y coordinates.

    public static final char[] moves = {'u', 'r', 'd', 'l'}; // The player's moves.

    public static final int[] xOffsets = {0, 1, 0, -1}; // The horizontal offsets.
    public static final int[] yOffsets = {-1, 0, 1, 0}; // The vertical offsets.

    /**
     * Constructs the initial state.
     * 
     * @param map    {char[][]}  The map.
     * @param items  {char[][]}  The items.
     */
    public State(char[][] map, char[][] items)
    {
        setCoordinates(map, items);
        this.prev = new Prev(' ', null, null);
    }

    /**
     * Creates a deep copy of a state.
     * 
     * @param state {State} The state to copy.
     * @return {State}
     */
    public State(State state)
    {
        this.playerCoor = state.playerCoor;
        this.boxCoor = new TreeSet<>(state.boxCoor);
        this.boxPushedCoor = null;

        this.xBoxSum = state.xBoxSum;
        this.yBoxSum = state.yBoxSum;

        this.prev = state.prev;
    }

    /**
     * Sets the coordinates of each tile.
     * 
     * @param map    {char[][]}  The map.
     * @param items  {char[][]}  The items.
     */
    private void setCoordinates(char[][] map, char[][] items)
    {
        // Initializes the coordinate variables.
        boxCoor = new TreeSet<>();
        targetCoor = new HashSet<>();
        wallCoor = new HashSet<>();
        boxPushedCoor = null;
        xTargetSum = 0;
        yTargetSum = 0;
        xBoxSum = 0;
        yBoxSum = 0;

        // Sets the coordinates of the map and items.

        // Checks each row.
        for (int y = 0; y < map.length; y++)
        {
            // Checks each column.
            for (int x = 0; x < map[y].length; x++)
            {
                // Creates the coordinate pair.
                Coor coor = new Coor(x, y);

                // Stores the map and target tiles.
                switch (map[y][x])
                {
                    case '#' -> { wallCoor.add(coor); }
                    case '.' -> { targetCoor.add(coor);
                                  xTargetSum += x;
                                  yTargetSum += y;}
                }

                // Stores the player and box tiles.
                switch (items[y][x])
                {
                    case '$' -> { boxCoor.add(coor); }
                    case '@' -> { playerCoor = coor; }
                }
            }
        }
    }

    /**
     * Returns the hash code of the state.
     * 
     * @return {String}
     */
    public String getHashCode()
    {
        // Creates a string builder for the hash code.
        StringBuilder sb = new StringBuilder();

        // Concatinates the player's coordinates to the string.
        sb.append(playerCoor.coor).append(' ');
        
        // Concatinates the boxes' coordinates to the string.
        for (Coor coor : boxCoor)
            sb.append(coor.coor).append(' ');

        // Returns the player's and boxes' coordinates, concatinated.
        return sb.toString();
    }

    /**
     * Returns a new state resulting from the player moving.
     * Returns null if the move is redundant or leads to a deadlock.
     * 
     * @param state     {State} The state to move from.
     * @param offsetID  {int}   The offset ID (0: up, 1: right, 2: down, 3: left).
     * @return          {State}
     */
    public static State movePlayer(State state, int offsetID)
    {
        // Gets the x and y coordinates of the player.
        int xPlayer = state.playerCoor.getX();
        int yPlayer = state.playerCoor.getY();

        // Sets the coordinates of the next two tiles.
        Coor nextTile1 = new Coor(xPlayer + xOffsets[offsetID], yPlayer + yOffsets[offsetID]);
        Coor nextTile2 = new Coor(xPlayer + (2 * xOffsets[offsetID]), yPlayer + (2 * yOffsets[offsetID]));

        // Checks if the next tile contains a wall.
        if (wallCoor.contains(nextTile1))
            return null;

        // Checks if the next tile contains a box.
        if (state.boxCoor.contains(nextTile1))
        {
            // Checks if the box is immovable.
            if (state.boxCoor.contains(nextTile2) || wallCoor.contains(nextTile2))
                return null;

            // Updates the coordinates of the pushed box.
            state.boxCoor.remove(nextTile1);
            state.boxCoor.add(nextTile2);
            state.boxPushedCoor = nextTile2;

            // Updates the sum of the boxes' coordinates.
            state.xBoxSum += (nextTile2.getX() - nextTile1.getX());
            state.yBoxSum += (nextTile2.getY() - nextTile1.getY());

            // Checks if the move results in a loss.
            if (!targetCoor.contains(state.boxPushedCoor))
            {
                // Gets the x and y coordinates of the pushed box.
                int x = state.boxPushedCoor.getX();
                int y = state.boxPushedCoor.getY();

                // Loops through each offset coordinate to be checked.
                for (int i = (offsetID + 3) % 4; i != (offsetID + 1) % 4; i = (i + 1) % 4)
                {
                    // Gets the index of the next offset.
                    int j = (i + 1) % 4;

                    // Gets the coordinates to be checked.
                    Coor currCoor = new Coor(x + xOffsets[i], y + yOffsets[i]);
                    Coor nextCoor = new Coor(x + xOffsets[j], y + yOffsets[j]);
                    Coor cornerCoor = new Coor(x + xOffsets[i] + xOffsets[j],
                                    y + yOffsets[i] + yOffsets[j]);

                    // Checks the content of the current offset's coordinates.
                    boolean isCurrWall  = wallCoor.contains(currCoor);
                    boolean isCurrBox   = state.boxCoor.contains(currCoor);

                    // Checks the content of the next offset's coordinate.
                    boolean isNextWall  = wallCoor.contains(nextCoor);
                    boolean isNextBox   = state.boxCoor.contains(nextCoor);

                    // Checks if there is a wall/box in between the current and next offset coordinates.
                    boolean isCurrCornerObs = wallCoor.contains(cornerCoor) ||
                                        state.boxCoor.contains(cornerCoor);

                    // Checks if the box is stuck in a corner of immovable obstacles.
                    if ((isCurrWall && ((isNextBox && isCurrCornerObs) || isNextWall)) ||
                        (isCurrBox && isCurrCornerObs && (isNextWall || isNextBox)))
                        return null;
                }
            }
        }

        // Updates the player's location.
        state.playerCoor = nextTile1;

        // Sets the details of the previous state.
        state.prev = new Prev(moves[offsetID], state.boxPushedCoor, state.prev);

        // Returns the new state.
        return state;
    }

    /**
     * Checks if a move is redundant.
     * A move is considered redundant if the player moves
     * towards the opposite direction without pushing a box.
     * 
     * @param i {int} The index of the move taken.
     * @return {boolean}
     */
    public boolean isRedundant(int i)
    {
        return prev.prevDetails != null &&
            prev.boxPushedCoor == null &&
            ((i == 0 && prev.move == 'd') ||
            (i == 1 && prev.move == 'l') ||
            (i == 2 && prev.move == 'u') ||
            (i == 3 && prev.move == 'r'));
    }

    /**
     * Returns the state's heuristic value.
     * 
     * @return {int}
     */
    public int getHeuristic()
    {
        // Returns the sum of all manhattan distances between each box and target.
        return Math.abs(xTargetSum - xBoxSum) + Math.abs(yTargetSum - yBoxSum);
    }
}
