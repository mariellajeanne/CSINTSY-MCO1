package solver;

public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    
    // javac -d out -cp "src/solver/commons-lang3-3.17.0.jar;out" src/gui/*.java src/main/*.java src/reader/*.java src/solver/*.java
    // java -cp "out;src/solver/commons-lang3-3.17.0.jar" main.Driver

    /*
     * Default stupid behavior: Think (sleep) for 3 seconds, and then return a
     * sequence
     * that just moves left and right repeatedly.
     */
    try {
      Thread.sleep(3000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "lrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlrlr";
  }

}
