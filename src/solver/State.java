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
    public State up(char[][] map, char[][] items)
    {
        /**
         * TODO: code here
         * 
         */

        return new State(map, items, ""); // TODO: Please edit this.
    }

    /**
     * TODO: do the same for other directions
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
