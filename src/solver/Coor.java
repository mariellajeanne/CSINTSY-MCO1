/**
 * @author Mariella Jeanne A. Dellosa
 * 
 * Representes a coordinate using bit-packing.
 */

package solver;

/**
 * The coordinate class.
 */
public class Coor implements Comparable<Coor>
{
    // The coordinate pair to be packed in a short.
    public short coor;

    /**
     * Constructs the coordinate pair.
     * 
     * @param x {int} The x-coordinate.
     * @param y {int} The y-coordinate.
     */
    public Coor(int x, int y)
    {
        this.coor = (short) ((x << 8) | (y & 0xFF));
    }

    /**
     * Creates a deep copy of a coordinate pair.
     * 
     * @param coor {Coor} The coordinate pair to be copied.
     */
    public Coor(Coor coor)
    {
        this.coor = coor.coor;
    }

    /**
     * Returns the x-coordinate.
     * 
     * @return {int}
     */
    public int getX()
    {
        return (coor >> 8) & 0xFF;
    }

    /**
     * Returns the y-coordinate.
     * 
     * @return {int}
     */
    public int getY()
    {
        return coor & 0xFF;
    }

    /**
     * Returns the coordinate value as the object's hash code.
     * 
     * @return {int}
     */
    @Override
    public int hashCode()
    {
        return coor;
    }

    /**
     * Checks if the passed coordinate pair is equal to
     * this instance's coordinate pair.
     * 
     * @param obj {Object} The coordinate passed.
     * @return {boolean}
     */
    @Override
    public boolean equals(Object obj)
    {
        // Returns true if the references are the same.
        if (this == obj)
            return true;

        // Returns false if the object passed is not of this class.
        if (!(obj instanceof Coor))
            return false;
        
        // Returns true if the coordinate pairs are the same.
        return this.coor == ((Coor)obj).coor;
    }

    /**
     * Compares two objects of this class.
     * 
     * @param that {Coor} The coordinate pair being compared to.
     * @return {int}
     */
    @Override
    public int compareTo(Coor that)
    {
        return this.coor - that.coor;
    }
}
