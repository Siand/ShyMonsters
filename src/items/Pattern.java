package items;
import java.util.ArrayList;

import moves.Position;

public class Pattern
{

	private ArrayList<Position> empty;
	private ArrayList<Position> filled;
	public Pattern()
	{
		empty = new ArrayList<>();
		filled = new ArrayList<>();
	}
	
	public boolean isActive(ArrayList<Position> active)
	{
		if(active.size() < filled.size()) {
			return false;
		}
		for(Position b : filled) {
			boolean exists = false;
			for(Position a : active) {
				if(a.equals(b)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				return false;
			}
		} 
		for(Position a : active) {
			for(Position b : empty	) {
				if(a.equals(b)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addEmpty(Integer x, Integer y)
	{
		empty.add(new Position(x,y));
	}
	
	public void addFilled(Integer x, Integer y)
	{
		filled.add(new Position(x,y));
	}
}
