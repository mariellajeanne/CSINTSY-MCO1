/**
 * @author Ronnie M. Abiog Jr.
 * @author Ana Victoria R. Angat
 * @author Mariella Jeanne A. Dellosa
 * @author Anthony Andrei C. Tan
 * 
 * Solves a SokoBot game automatically.
 */

package solver;

/**
* The SokoBot class.
*/
public class SokoBot {

  /**
  * Solves a Sokoban level automatically.
  * Returns a path to the winning state.
  * 
  * @param width     {int} The width of the map.
  * @param height    {int} The height of the map.
  * @param mapData   {char[][]} The map data.
  * @param itemsData {char[][]} The items data.
  * 
  * @return {String}
  */
  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData)
  {
    /**
    * Compile and run.
    * 
    * javac src/gui/*.java src/main/*.java src/reader/*.java src/solver/*.java -d out/ -cp out
    * java -classpath out main.Driver
    */

    // Creates the starting state.
    State state = new State(mapData, itemsData);

    // Gets the single instance of the search class.
    Search search = Search.getInstance();

    // Returns the path to the winning state.
    return search.getSequenceBFS(state);
  }
}