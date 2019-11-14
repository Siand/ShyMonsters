package items;
import java.util.ArrayList;

import javafx.util.Pair;

public class Pattern
{

	private ArrayList<Pair<Integer,Integer>> empty;
	private ArrayList<Pair<Integer,Integer>> filled;
	public Pattern()
	{
		empty = new ArrayList<>();
		filled = new ArrayList<>();
	}
	
	public boolean isActive(ArrayList<Pair<Integer,Integer>> active)
	{
		if(active.size() < filled.size()) {
			return false;
		}
		for(Pair<Integer,Integer> a : active) {
			boolean exists = false;
			for(Pair<Integer,Integer> b : filled) {
				if(b.getKey() == a.getKey() && b.getValue() == a.getValue()) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				return false;
			}
			for(Pair<Integer,Integer> b : empty	) {
				if(b.getKey() == a.getKey() && b.getValue() == a.getValue()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addEmpty(Integer x, Integer y)
	{
		empty.add(new Pair<Integer,Integer>(x,y));
	}
	
	public void addFilled(Integer x, Integer y)
	{
		filled.add(new Pair<Integer,Integer>(x,y));
	}
}
