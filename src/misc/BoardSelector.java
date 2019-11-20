package misc;

import java.util.ArrayList;

import moves.Position;

public class BoardSelector
{

	private static BoardSelector instance = null;
	private static ArrayList<Position> positions;
	
	public static BoardSelector Instance() {
		if(instance == null) {
			instance = new BoardSelector();
			positions = new ArrayList<>();
		}
		return instance;
	}
	
	public void select(int x, int y) {
		//Add the element if not in the list, otherwise remove
		Position newPos = new Position(x, y);
		if(!positions.remove(newPos)) {
			positions.add(newPos);
		}
	}
	
	public boolean isSelected(int x, int y) {
		Position newPos = new Position(x, y);
		return positions.contains(newPos);
	}
	
	public void reset() {
		positions.clear();
	}
	
	public ArrayList<Position> get() { 
		return positions;
	}
	
	public boolean isValid() {
		return explore(positions.get(0), positions) == positions.size();
	}
	
	
	public int explore(Position start, ArrayList<Position> list) {
		ArrayList<Position> explored = new ArrayList<>();
		explored.add(start);
		while(true) {
			int eSize = explored.size();
			for(Position pos1 : list) {
				if(explored.contains(pos1)) continue;
				for(int i=0 ; i < eSize; i++) { 
					if(explored.get(i).isAdjacent(pos1)) {
						explored.add(pos1);
						break;
					}
				}
			}
			if(explored.size() == eSize) break;
		}
		return explored.size();
	}
	
	
}

