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
    private String actions; // The action of the player to reach this state.
    private boolean isVisited; // Determines if the state has already been visited.

    /**
     * Constructs the state.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @param actions {String} The actions leading to the state.
     */
    public State(char[][] map, char[][] items, String actions)
    {
        isVisited = false;
        this.map = map;
        this.items = items;
        this.actions = actions;
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
     * Returns the actions leading to the state.
     * 
     * @return {String}
     */
    public String getActions()
    {
        return this.actions;
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
     * Returns the state of the player moving up.
     * Returns null if the move cannot or should not
     * be made.
     * 
     * @param map {char[][]} The map.
     * @param items {char[][]} The items.
     * @return {State}
     */
    public State up(int width, int height, char[][] map, char[][] items)
    {
        /**
         * TODO: code here
         * 
         * locate player
         * 
         * check if invalid move, return null if yes
         * check if next move isLoss(), return null if yes
         * check if next move is redundant, return null if yes
         * 
         * make the move, update items
         */

         // current player position
         int playerRow = -1;
         int playerCol = -1;

         // locate player
         for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                if (items[row][col] == '@'){
                    playerRow = row;
                    playerCol = col;
                }
            }
         }

         // next row if move is made
         int nextRow = playerRow - 1;
         int nextnextRow = playerRow - 2;

         // check if next tile is a wall
         if (map[nextRow][playerCol] == '#'){
            return null;
         }
         // check if crate is not moveable from the current state
         else if (items[nextRow][playerCol] == '$'){
            if (map[nextnextRow][playerCol] == '#'){
                return null;
            }
            else if (items[nextnextRow][playerCol] == '$'){
                return null;
            }
         }

         char[][]nextStateItems = new char[height][width];

         for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                nextStateItems[row][col] = items[row][col];
            }
         }

         // if next move pushes a crate
         if (items[nextRow][playerCol] == '$'){
            nextStateItems[nextnextRow][playerCol] = '$';
            nextStateItems[nextRow][playerCol] = '@';
            nextStateItems[playerRow][playerCol] = ' ';
         }
         else{
            nextStateItems[nextRow][playerCol] = '@';
            nextStateItems[playerRow][playerCol] = ' ';
         }

         State nextState = new State(map, nextStateItems, actions + "u");
         return nextState; // placeholder return TODO: delete this
         
         // check if next move is desirable
        /* if (isLoss(nextState) || isRedundant(nextState)){ // need clarification on how to use Status methods
            return null;
         }

         else{
            return nextState;
         }*/
    }

    /**
     * TODO: do the same for other directions
     * 
     * 
     * figure out how to use Status class then proceed
     * need that to use isLoss() and isRedundant() methods.
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
