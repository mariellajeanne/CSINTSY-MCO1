/**
 * @author Mariella Jeanne A. Dellosa
 * 
 * Stores important details of the previous state.
 */

package solver;

/**
 * The previous state class.
 */
public class Prev
{
    public char move;               // The move taken.
    public Coor boxPushedCoor;      // The coordinates of the pushed box.
    public Prev prevDetails;        // The previous state's details.

    /**
     * Constructs an instance of the prev class.
     * 
     * @param move {char} The move taken.
     * @param boxPushedCoor {Coor} The coordinates of the pushed box.
     * @param prevDetails {Prev} The previous state's details.
     */
    public Prev(char move, Coor boxPushedCoor, Prev prevDetails)
    {
        this.move = move;
        this.boxPushedCoor = boxPushedCoor;
        this.prevDetails = prevDetails;
    }
}
