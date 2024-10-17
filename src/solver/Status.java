/**
 * @author <Full Name>
 * 
 * Determines a state's winning status.
 */

package solver;

/**
 * The status class.
 */
public class Status
{
    // The single instance of the status class.
    private static Status s;

    /**
     * Returns the single instance of the status class.
     * 
     * @return {Status}
     */
    public Status getInstance()
    {
        if (s == null)
            s = new Status();
        return s;
    }

    /**
     * Determines if it's impossible for a state
     * to branch to a winning state.
     * 
     * @return {boolean}
     */
    public boolean isLoss(State state)
    {
        char[][] map = state.getMap();

        for (int[] box : state.getBoxCoor()) 
        {
            int wallTouches = getWallTouches(map, box);
            if( wallTouches >= 2)
            {
                return true;
            }
            else if(wallTouches == 1 && !isBoxInLineWithATarget(map, box, )) // TODO: add targetCoor getter here
            {
                return true;
            }
                
        }
        
        return false; // TODO: Please edit this.
    }

    /**
     * Determines if the player has won.
     * Checks if all the boxes are located at the targets.
     * 
     * @return {boolean}
     */
    public boolean isWin(State state)
    {
        char[][] map = state.getMap();

        for (int[] box : state.getBoxCoor()) 
        {
            if (map[box[0]][box[1]] != '.') 
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the player's move cycles back to a
     * previous state without changing the boxes' positions.
     * 
     * @return {boolean}
     */
    public boolean isRedundant(State state)
    {
        return false; // TODO: Please edit this.
    }

    /**
     * TODO: Create helper functions for isLoss() and isWin()
     */

     /**
      * Determines if the box is touching two walls.
      *
      * @param map
      * @param boxCoor
      * @return {boolean}
      */
    private int getWallTouches(char[][] map, int[] boxCoor)
    {
        int touches = 0;
        
        if (map[boxCoor[0] - 1][boxCoor[1]] == '#')
        {
            touches++;
        }
        if (map[boxCoor[0] + 1][boxCoor[1]] == '#')
        {
            touches++;
        }
        if (map[boxCoor[0]][boxCoor[1] - 1] == '#')
        {
            touches++;
        }
        if (map[boxCoor[0]][boxCoor[1] + 1] == '#')
        {
            touches++;
        }

        return touches;
    }

    private boolean isBoxInLineWithATarget(char[][] map, int[] boxCoor, int[][] targetCoor)
    {
        if (map[boxCoor[0] + 1][boxCoor[1]] == '#' || map[boxCoor[0] - 1][boxCoor[1]] == '#')
        {
            for (int[] target : targetCoor) {
                if (boxCoor[0] == target[0])
                {
                    return true;
                }
            }
        }
        if (map[boxCoor[0]][boxCoor[1] + 1] == '#' || map[boxCoor[0]][boxCoor[1] - 1] == '#')
        {
            for (int[] target : targetCoor)
            {
                if(boxCoor[1] == target[1])
                {
                    return true;
                }
            }
        }
        return false;
    }
}

/*
 * Pseudocode for isLoss():
 * check if box is touching 2 walls: return true
 * 
 * check if box is touching 1 wall and is not in line with a target: return true (FLAWED)
 * 
 * check if boxes are diagonally touching
 *  if touching a wall
 *      if the player is not in the pocket the boxes made 
 *          if they arent in line with a target
 */
