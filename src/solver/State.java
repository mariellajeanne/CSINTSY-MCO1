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
    public final Prev prev; // Details of the previous state.

    public static HashSet<Coor> wallCoor;   // The walls' coordinates.
    public static HashSet<Coor> targetCoor; // The targets' coordinates.

    public Coor playerCoor;       // The player's coordinates.
    public TreeSet<Coor> boxCoor; // The boxes' coordinates.
    public Coor boxPushedCoor;    // The recently pushed box's coordinates.

    public boolean isVisited; // Determines if the state has already been visited.

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
        this.isVisited = false;
    }

    /**
     * Constructs the next states.
     * 
     * @param playerCoor    {String}            The player's coordinates.
     * @param boxCoor       {TreeSet<Coor>}     The boxes' coordinates.
     * @param prev          {Prev}              The previous state's details.
     */
    public State(Coor playerCoor, TreeSet<Coor> boxCoor, Prev prev)
    {
        this.playerCoor = playerCoor;
        this.boxCoor = boxCoor;
        this.boxPushedCoor = null;

        this.isVisited = false;

        this.prev = prev;
    }

    /**
     * Creates a deep copy of a state.
     * 
     * @param state {State} The state to copy.
     */
    public State(State state)
    {
        this.playerCoor = state.playerCoor;
        this.boxCoor = new TreeSet<>(state.boxCoor);
        this.boxPushedCoor = null;

        this.isVisited = false;

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
        // Initialization of coordinate variables.
        boxCoor = new TreeSet<>();
        targetCoor = new HashSet<>();
        wallCoor = new HashSet<>();
        boxPushedCoor = null;

        // Sets the coordinates of the map and items.

        // Checks each row.
        for (int y = 0; y < map.length; y++)
        {
            // Checks each column.
            for (int x = 0; x < map[y].length; x++)
            {
                // Creates the coordinate pair.
                Coor coor = new Coor(x, y);

                // Checks the map data.
                switch (map[y][x])
                {
                    case '#' -> { wallCoor.add(coor); }
                    case '.' -> { targetCoor.add(coor); }
                }

                // Checks the items data.
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
     * Returns null if the move cannot be made.
     * 
     * @param state     {State} The state to move from.
     * @param move      {char}  The move made.
     * @param xOffset   {int[]} The horizontal offset (left: -1, right: 1, none: 0).
     * @param yOffset   {int[]} The vertical offset (up: 1, down: -1, none: 0).
     * @param offsetID  {int}   The offset ID.
     * 
     * @return          {State}
     */
    public static State movePlayer(State state, char move, int[] xOffset, int[] yOffset, int offsetID)
    {
        // Gets the x and y coordinates of the player.
        int xPlayer = state.playerCoor.getX();
        int yPlayer = state.playerCoor.getY();

        // Sets the coordinates of the next two tiles.
        Coor nextTile1 = new Coor(xPlayer + xOffset[offsetID], yPlayer + yOffset[offsetID]);
        Coor nextTile2 = new Coor(xPlayer + (2 * xOffset[offsetID]), yPlayer + (2 * yOffset[offsetID]));

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

            // Checks if the move results in a loss.
            if (!targetCoor.contains(state.boxPushedCoor))
            {
                // Sets the offset ID.
                offsetID = (offsetID + 3) % 4;

                // Gets the x and y coordinates of the pushed box.
                int x = state.boxPushedCoor.getX();
                int y = state.boxPushedCoor.getY();

                // Loops through each offset coordinate to be checked.
                for (int i = offsetID; i != (offsetID + 2) % 4; i = (i + 1) % 4)
                {
                    // Gets the index of the next offset.
                    int j = (i + 1) % 4;

                    // Gets the coordinates to be checked.
                    Coor currCoor = new Coor(x + xOffset[i], y + yOffset[i]);
                    Coor nextCoor = new Coor(x + xOffset[j], y + yOffset[j]);
                    Coor cornerCoor = new Coor(x + xOffset[i] + xOffset[j],
                                    y + yOffset[i] + yOffset[j]);

                    // Checks the contents of the current offset coordinate.
                    boolean isCurrWall  = wallCoor.contains(currCoor);
                    boolean isCurrBox   = state.boxCoor.contains(currCoor);

                    // Checks the contents of the next offset coordinate.
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

        // The next tile does not contain a wall nor a box.
        else
        {
            if (state.prev.prevDetails == null ||
                state.prev.boxPushedCoor != null)
            { /* do nothing */ }

            // Checks if the move is redundant.
            else if ((move == 'u' && state.prev.move == 'd') ||
                (move == 'r' && state.prev.move == 'l') ||
                (move == 'd' && state.prev.move == 'u') ||
                (move == 'l' && state.prev.move == 'r'))
                return null;
        }

        // Updates the player's location.
        state.playerCoor = nextTile1;

        // Returns the new state.
        return new State(state.playerCoor, state.boxCoor,
                new Prev(move, state.boxPushedCoor, state.prev));
    }
}
