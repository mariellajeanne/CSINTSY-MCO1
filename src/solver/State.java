/**
 * @author <Ronnie M. Abiog Jr.>
 * 
 * Creates and manipulates states.
 */

package solver;

/**
 * The state class.
 */
public class State
{
    private char[][] map; // The map of the state.
    private char[][] items; // The items of the state.
    private int[] playerCoor; // The player coordinates.
    private int[][] boxCoor; // The coordinates of all boxes.
    private State prevState; // The previous state before executing a move.
    private char prevMove; // The previous move that leads to the current state.
    ///////////////////// private String actions; // The action of the player to reach this state.
    private boolean isVisited; // Determines if the state has already been visited.

    /**
     * Constructs the state.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @param playerCoor {int[]} The player coordinates.
     * @param boxCoor {int [][]} The boxes' coordinates.
     * @param prevState {State} The previous state before executing a move.
     * @param prevMove {char} The previous move that leads to the current state.
    /////////////  * @param actions {String} The actions leading to the state.
     */
    public State(char[][] map, char[][] items, int[] playerCoor, int[][] boxCoor,
                State prevState, char prevMove)
    {
        isVisited = false;
        this.map = map;
        this.items = items;
        this.playerCoor = playerCoor;
        this.boxCoor = boxCoor;
        this.prevState = prevState;
        this.prevMove = prevMove;
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
     * @return {char[][]}
     */
    public char[][] getBoxCoor()
    {
        return this.boxCoor;
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
     * Returns the actions leading to the state.
     * 
     * @return {String}
     ///////////////////////////////// may not be needed anymore
    public String getActions()
    {
        return this.actions;
    }*/

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
     * Returns a deep copy of the boxes' coordinates.
     * 
     * @param boxCoor {char[][]} // The original coordinates.
     * @return {char[][]}
     */
    private char[][] copyBoxCoor()
    {
        char[][] copy = new char[boxCoor.length][2];

        for (int i = 0; i < boxCoor.length; i++)
        {
            copy[i][0] = boxCoor[i][0];
            copy[i][1] = boxCoor[i][1];
        }
        
        return copy;
    }

    /**
     * Returns the tile at the specified coordinate.
     * 
     * @param row {int} // The row.
     * @param col {int} // The column.
     * @return {char}
     */
    private char tileAt(int row, int col)
    {
        if (map[row][col] != '#')
        {
            for (int i = 0; i < boxCoor.length; i++)
            {
                if (boxCoor[i][0] == row && boxCoor[i][1] == col)
                {
                    return '$';
                }
            }
        }

        return map[row][col];
    }

    /**
     * Returns the state of the player
     * moving according to the offsets.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param vOffset {int} // The vertical movement. -1 for up, 1 for down, 0 for none
     * @param hOffset {int} // The horizontal movement. -1 for left, 1 for right, 0 for none.
     * @return {State}
     */
    private State movePlayer(int vOffset, int hOffset)
    {
         int playerRow = playerCoor[0];
         int playerCol = playerCoor[1];
         int nextRow = playerRow + vOffset;
         int nextCol = playerCol + hOffset;
         int nextnextRow = nextRow + vOffset;
         int nextnextCol = nextCol + hOffset;

         boolean nextTileBox = false;

         // if next tile is a wall
         if (tileAt(nextRow, nextCol) == '#')
         {
            return null;
         }

         // if next tile is a box
         else if (tileAt(nextRow, nextCol) == '$')
         {
            nextTileBox = true;

            // if box is blocked
            if (tileAt(nextnextRow, nextnextCol) == '$')
            {
                return null;
            }
            else if (tileAt(nextnextRow, nextnextCol) == '#')
            {
                return null;
            }
         }

         char[][] newBoxCoor;

         // if next tile is moveable box
         if (nextTileBox)
         {
            newBoxCoor = copyBoxCoor();

            // move box
            for (int i = 0; i < boxCoor.length; i++)
            {
                if (newBoxCoor[i][0] == nextRow && newBoxCoor[i][1] == nextCol)
                {
                    newBoxCoor[i][0] = nextnextRow;
                    newBoxCoor[i][1] = nextnextCol;
                }
            }
         }

         else
         {
            newBoxCoor = boxCoor;
         }
         
         // move player
         int[] newPlayerCoor = new int[2];
         newPlayerCoor[0] = nextRow;
         newPlayerCoor[1] = nextCol;

         char move;

         // identify move
         if (vOffSet == -1)
         {
            move = 'u';
         }
         else if (vOffset == 1)
         {
            move = 'd';
         }
         else if (hOffset == -1)
         {
            move = 'l';
         }
         else if (hoffSet == 1)
         {
            move = 'r';
         }

         State newState = new State(map, items, newPlayerCoor, newBoxCoor, this, move);
         Status s = Status.getInstance();

         // if state is not desirable
         if (s.isLoss(newState) || s.isRedundant(newState))
         {
            return null;
         }

         return newState;
    }
    /**
     * Returns the state of the player moving up.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State up(char[][] map, char[][] items)// can we just remove the param, its in the attributes anyway
    {
        return movePlayer(-1, 0);
    }

    /**
     * Returns the state of the player moving down.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State down(char[][] map, char[][] items)
    {
        return movePlayer(1, 0);
    }

    /**
     * Returns the state of the player moving left.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State left(char[][] map, char[][] items)
    {
        return movePlayer(0, -1);
    }

    /**
     * Returns the state of the player moving right.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State right(char[][] map, char[][] items)
    {
        return movePlayer(0, 1);
    }
}
