
package solver;
import java.util.*;

public class Heuristic
{	
	// essentials
	public HashSet<String> unoccupiedTargets;
	public HashSet<String> nonTargetBoxes;
	public HashMap<String, Integer> boxDistances;

	public int playerDistance;
	public int totalBoxDistance;
	public int heuristicVal;
	
	/**
	 * Constructs the Heuristic
	 * 
	 * @param state
	 */
	public Heuristic(State state)
	{
		// first call when the previous state is null
		if(state.prevState == null)
		{
			setCoors(state);
			setBoxDistances(state);
			setPlayerDistance(state);

			// computes the heuristic value
			this.heuristicVal = this.playerDistance + this.totalBoxDistance;
		}

		// subsequent calls
		else
		{
			State prevState = state.prevState;

			copyPrevCoors(state.prevState);

			this.playerDistance = prevState.heuristic.playerDistance;
			this.totalBoxDistance = prevState.heuristic.totalBoxDistance;
			this.heuristicVal = prevState.heuristic.heuristicVal;
			
			if (state.boxPushedCoor.equals(""))
				updatePlayerDistance(state);
			else
				updateBoxDistances(state);
		}
	}
	
	/**
	 * Updates the distances of boxes to the nearest unoccupied targets.
	 * 
	 * @param state
	 */
	private void updateBoxDistances(State state)
	{
		 String playerCoor = state.playerCoor;
		 String boxPushed = state.boxPushedCoor;

		// playerCoor is the previous coordinate of the box pushed
		// update coordinates
		if (State.targetCoor.contains(playerCoor))
			unoccupiedTargets.add(playerCoor);
		else
			nonTargetBoxes.remove(playerCoor);

		if (State.targetCoor.contains(boxPushed))
			unoccupiedTargets.remove(boxPushed);
		else
			nonTargetBoxes.add(boxPushed);
		
		int minDistance = -1;
		int distance;

		// find nearest unoccupied target
		for (String target : unoccupiedTargets)
		{
			distance = Math.abs(State.getX(target) - State.getX(boxPushed)) + 
					   Math.abs(State.getY(target) - State.getY(boxPushed));
			
			if (distance < minDistance ||
						   minDistance == -1)
				minDistance = distance;
		}

		this.totalBoxDistance -= boxDistances.get(playerCoor);
		this.totalBoxDistance += minDistance;

		boxDistances.remove(playerCoor);
		boxDistances.put(boxPushed, Integer.valueOf(minDistance));
	}
	
	/**
	 * Updates the player's distance to the nearest box.
	 * 
	 * @param state
	 */
	private void updatePlayerDistance(State state)
	{
		int minDistance = -1;
		int distance;
		
		// finds the nearest box
		for (String box : nonTargetBoxes)
		{
			distance = Math.abs(State.getX(box) - State.getX(state.playerCoor)) + 
					   Math.abs(State.getY(box) - State.getY(state.playerCoor));
			
			if (distance < minDistance ||
						   minDistance == -1)
				minDistance = distance;
		}
		
		this.heuristicVal -= this.playerDistance;
		this.heuristicVal += minDistance;
		this.playerDistance = minDistance;
	}

	/**
	 * Sets the coordinates of unoccupied targets and
	 * boxes that are not in targets.
	 * 
	 * @param state
	 */
	private void setCoors(State state)
	{
		unoccupiedTargets = new HashSet<>();
		nonTargetBoxes = new HashSet<>();
		boxDistances = new HashMap<>();

		// unoccupied targets
		for (String target : State.targetCoor)
			if (!state.boxCoor.contains(target))
				unoccupiedTargets.add(target);
		
		// non target boxes
		for (String box : state.boxCoor)
			if (!state.targetCoor.contains(box))
				nonTargetBoxes.add(box);
	}

	/**
	 * Deep copies the attributes of the
	 * previous state's Heuristic.
	 * 
	 * @param prevState
	 */
	private void copyPrevCoors(State prevState)
	{
		this.unoccupiedTargets = new HashSet<>();
		this.nonTargetBoxes = new HashSet<>();
		this.boxDistances = new HashMap<>();

		this.unoccupiedTargets.addAll(prevState.heuristic.unoccupiedTargets);
		this.nonTargetBoxes.addAll(prevState.heuristic.nonTargetBoxes);
		this.boxDistances.putAll(prevState.heuristic.boxDistances);	
	}

	/**
	 * Sets the distance of non-target boxes to
	 * the nearest unoccupied targets.
	 * 
	 * @param state
	 */
	private void setBoxDistances(State state)
	{
		this.totalBoxDistance = 0;
		
		for (String box : nonTargetBoxes)
		{
			int minDistance = -1;
			int distance;
			
			// finds the nearest target
			for (String target : unoccupiedTargets)
			{
				distance = Math.abs(State.getX(box) - State.getX(target)) +
						   Math.abs(State.getY(box) - State.getY(target));
				
				if (distance < minDistance ||
							   minDistance == -1)
					minDistance = distance;
			}
			this.totalBoxDistance += minDistance;
		}
	}

	/**
	 * Sets the distance of the player to the
	 * nearest box.
	 * 
	 * @param state
	 */
	private void setPlayerDistance(State state)
	{
		// sets the player's distance to the nearest non-target box
		for (String box : nonTargetBoxes)
		{
			int minDistance = -1;
			int distance;

			distance = Math.abs(State.getX(box) - State.getX(state.playerCoor)) + 
					Math.abs(State.getY(box) - State.getY(state.playerCoor));
			
			if (distance < minDistance ||
						minDistance == -1)
				minDistance = distance;
			
			this.playerDistance = minDistance;
		}
	}
}