/**
 * @author <Full Name>
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
    private int[][] boxCoor; // The coordinates of all crates.
    private State prevState; // The previous state before executing a move.
    private char prevMove; // The previous move that leads to the current state.
    // private String actions; // The action of the player to reach this state.
    private boolean isVisited; // Determines if the state has already been visited.

    /**
     * Constructs the state.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @param playerCoor {int[]} The player coordinates.
     * @param boxCoor {int [][]} The crates' coordinates.
     * @param prevState {State} The previous state before executing a move.
     * @param prevMove {char} The previous move that leads to the current state.
    //  * @param actions {String} The actions leading to the state.
     */
    public State(char[][] map, char[][] items, int[] playerCoor, int[][] boxCoor,
                State prevState, char prevMove)
    {
        isVisited = false;
        this.map = map;
        this.items = items; // getters make // check if shallow copy is enough
        this.playerCoor = playerCoor; // getters make // check if shallow copy is enough
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
     * Returns the crates' coordinates.
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
     // may not be needed anymore
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
     * Returns the state of the player moving up.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State up(char[][] map, char[][] items)
    {
        /**
         * TODO: code here
         * 
         * check if invalid move, return null if yes
         * check if next move isLoss(), return null if yes
         * check if next move is redundant, return null if yes
         * 
         * make the move
         * create copy of coordinates
         * change coordinates of affected
         */

         // current player position
         int playerRow = playerCoor[0];
         int playerCol = playerCoor[1];
         int height = map.length;
         int width = map[0].length;

         // search for this in coordinates array
         // next row if move is made
         int nextRow = playerRow - 1;
         int nextnextRow = playerRow - 2;

         boolean nextTileCrate = false;
         boolean crateBlocked = false;

         //////////////////////////////////////////////////////////////////////////////
         ////////////////////////////////////FIX////////////////////////////////////////
         //////////////////////////////////////////////////////////////////////////////
            // linear search in boxCoor for the nextTile and nextnextTile
            for (int i = 0; i < boxCoor.length; i++)
            {
                if (boxCoor[i][0] == nextRow && boxCoor[i][1] == playerCol)
                {
                    nextTileCrate = true;

                    if(map[nextnextRow][playerCol] == '#')
                    {
                        crateBlocked = true;
                    }
                    
                }
                else if (boxCoor[i][0] == nextnextRow && boxCoor[i][1] == playerCol)
                {
                    crateBlocked = true;
                }
            }

         // check if next tile is a wall
         if (map[nextRow][playerCol] == '#')
         {
            return null;
         }
         // check if crate is not moveable from the current state
         else if (items[nextRow][playerCol] == '$')
         {
            if (map[nextnextRow][playerCol] == '#')
            {
                return null;
            }
            else if (items[nextnextRow][playerCol] == '$')
            {
                return null;
            }
         }

         char[][]nextStateItems = new char[height][width];

         // makes a copy of items
         // create private method for this
         for (int row = 0; row < height; row++)
         {
            for (int col = 0; col < width; col++)
            {
                nextStateItems[row][col] = items[row][col];
            }
         }

         // perform the move in items copy
         // create a private method for this
         if (items[nextRow][playerCol] == '$')
         {
            nextStateItems[nextnextRow][playerCol] = '$';
            nextStateItems[nextRow][playerCol] = '@';
            nextStateItems[playerRow][playerCol] = ' ';
         }
         else
         {
            nextStateItems[nextRow][playerCol] = '@';
            nextStateItems[playerRow][playerCol] = ' ';
         }

         State nextState = new State(map, nextStateItems, this, 'u');
         Status s = Status.getInstance();
         
         // check if next move is desirable
         // create private method for this? nah short lang naman
         if (s.isLoss(nextState) || s.isRedundant(nextState))
         {
            return null;
         }

         else
         {
            return nextState;
         }
    }

    /**
     * TODO: do the same for other directions
     * 
     */

    public State down(char[][] map, char[][] items)
    {
        return new State(map, items, ""); // TODO: Please edit this.
    }

    public State left(char[][] map, char[][] items)
    {
        return new State(map, items, ""); // TODO: Please edit this.
    }

    public State right(char[][] map, char[][] items)
    {
        return new State(map, items, ""); // TODO: Please edit this.
    }
}
