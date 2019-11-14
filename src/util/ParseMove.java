package util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import items.Board;
import items.Tile;
import items.TileCard;
import javafx.util.Pair;
import moves.Move;
import moves.Position;

public class ParseMove
{
	JSONObject data = null;
	public ParseMove(String raw) {
		try
		{
			data = (JSONObject)((new JSONParser()).parse(raw));
		} catch (ParseException e) {
			System.err.println("Invalid JSON");
		}
	}
	
	public void parse() {
		if(data == null) return;
		if(data.containsKey("Tiles")) {
			setBoard();
		} else if(data.containsKey("Move")) {
			updateBoard();
		}
	}
	
	private void setBoard() {
		Board.Instance().reset();
		TileFactory mf = new TileFactory();
		JSONArray tiles = (JSONArray)data.get("Tiles");
		for(Object o : tiles) {
			JSONObject tobj = (JSONObject)o;
			String type = (String)tobj.get("Name");
			int x = (Integer)tobj.get("X");
			int y = (Integer)tobj.get("Y");
			Tile t = mf.create(type,x,y);
			Board.Instance().add(x,y,new TileCard(t,false));
		}
		
	}
	
	private void updateBoard() {
		JSONObject move = (JSONObject)data.get("Move");
		Move m = new Move();
		if(move.containsKey("Through")) {
			JSONObject through =  (JSONObject)move.get("Through");
			int x = (Integer)through.get("X");
			int y = (Integer)through.get("Y");
			m.through = new Position(x, y);
			m.isJump = true;
		}
		//From
		JSONObject from =  (JSONObject)move.get("From");
		int fromx = (Integer)from.get("X");
		int fromy = (Integer)from.get("Y");
		m.from = new Position(fromx, fromx);
		// To
		JSONObject to =  (JSONObject)move.get("To");
		int x = (Integer)to.get("X");
		int y = (Integer)to.get("Y");
		m.to = new Position(x, y);
		if(move.containsKey("isAttack")) {
			m.isAttack = true;
		}
		// play the move
		Board.Instance().play(m);
	}
	
}
