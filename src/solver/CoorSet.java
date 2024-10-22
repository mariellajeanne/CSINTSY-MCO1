package solver;

import java.util.*;

/**
 * The coorSet class.
 * Stores the player and box coordinates into one class.
 */
public class CoorSet {
    public String coors; // Format: "playerCoor:boxCoor1,boxCoor2,..."

    public CoorSet(int[] playerCoor, HashSet<C<int[]>> boxCoor) {
        this.coors = "" + playerCoor[0] + playerCoor[1] + ":";
        for (C<int[]> box : boxCoor) {
            this.coors += box[0] + box[1] + ",";
        }
    }
}
