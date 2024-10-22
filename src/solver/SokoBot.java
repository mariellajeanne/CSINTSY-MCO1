package solver;

public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    
    // javac -d out -cp "src/solver/commons-lang3-3.17.0.jar;out" src/gui/*.java src/main/*.java src/reader/*.java src/solver/*.java
    // java -cp "out;src/solver/commons-lang3-3.17.0.jar" main.Driver

    State state = new State(mapData, itemsData);
    Search search = Search.getInstance();
    return search.getSequenceGBFS(state);
  }

}
