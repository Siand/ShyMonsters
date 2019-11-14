package util;

import items.BlueMonster;
import items.Board;
import items.CyanMonster;
import items.Entrance;
import items.Exit;
import items.GreenMonster;
import items.Monster;
import items.OrangeMonster;
import items.PurpleMonster;
import items.RedMonster;
import items.Tile;
import items.Wall;

public class TileFactory
{
	public Tile create(String className, int x, int y) {
		Tile t;
		switch(className) {
			case "Blue":
				t = new BlueMonster();
				break;
			case "Cyan":
				t =  new CyanMonster();
				break;
			case "Green":
				t =  new GreenMonster();
				break;
			case "Orange":
				t =  new OrangeMonster();
				break;
			case "Purple":
				t =  new PurpleMonster();
				break;
			case "Red":
				t =  new RedMonster();
				break;
			case "Wall":
				t =  new Wall();
				break;
			case "Entrance":
				Board.Instance().createPawnAt(x,y);
				t =  new Entrance();
				break;
			case "Exit":
				t =  new Exit();
				break;
			default:
				t =  new Wall();
				break;
		}
		t.spawn(x,y);
		return t;
	}

}
