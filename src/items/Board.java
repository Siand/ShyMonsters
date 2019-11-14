package items;

import java.util.ArrayList;
import java.util.Observable;

import javafx.util.Pair;
import moves.Move;
import moves.Position;

public class Board extends Observable
{
	private static Board instance = null;
	private static Tile[][] board;
	public static final int WIDTH = 7, HEIGHT = 7;
	private static HeroPawn pawn;
	private Board() {
		board = new Tile[7][7];
		reset();
	}
	
	
	public static Board Instance() {
		if(instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	public Tile get(int x, int y) {
		return board[y][x];
	}
	
	public boolean add(int x, int y, TileCard t) {
		if(board[y][x] instanceof DefaultTile) {
			t.deplete();
			board[y][x] = t.getTile();
			board[y][x].hasChanged = true;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}
	
	public void reset() {
		for(int i = 0; i < HEIGHT; i++ ) {
			for(int j = 0; j < WIDTH; j++ ) {
				board[i][j] = new DefaultTile();
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void remove(int x, int y) {
		if(x < 0 || y < 0) return;
		board[y][x] = new DefaultTile();
		board[y][x].hasChanged = true;
		setChanged();
		notifyObservers();
	}
	
	public void play(Move m) {
		Position nextMove;
		if(m.isJump) {
			nextMove = m.through;
			pawn.jumpOver(board[nextMove.y][nextMove.x]);
		}
		nextMove = m.to;
		if(m.isAttack) {
			pawn.attack(board[nextMove.y][nextMove.x]);
		} else {
			pawn.move(board[nextMove.y][nextMove.x]);
		}
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<Pair<Integer,Integer>> getSurroundings(int x, int y) {
		int sX, sY, eX, eY;
		ArrayList<Pair<Integer,Integer>> list = new ArrayList<>();
		sX = x > 0? x-1: 0;
		sY = y > 0? y-1: 0;
		eX = x < WIDTH-1? x+1: WIDTH-1;  
		eY = y < HEIGHT-1? y+1: HEIGHT-1;
		for(int i = sX ; i < eX; i++ )
			for(int j = sY; j < eY; j++) {
				if(!(board[j][i] instanceof DefaultTile)) {
					list.add(new Pair<Integer, Integer>(i - x, j - y));
				}
			}
		return list;
	}
	
	public boolean isShy(int x, int y) {
		boolean left = x == 0? 
				false : (board[y][x-1].isMonster() && board[y][x-1].isRevealed()) ;
		boolean right = x == WIDTH -1 ?
				false : (board[y][x+1].isMonster() && board[y][x+1].isRevealed()) ;
		boolean up = y == 0? 
				false : (board[y-1][x].isMonster() && board[y-1][x].isRevealed()) ;
		boolean down = y == HEIGHT?
				false : (board[y+1][x].isMonster() && board[y+1][x].isRevealed()) ;
		return left || right || up || down;
	}
	


	public void createPawnAt(int x, int y)
	{
		pawn = new HeroPawn(x,y);		
	}

	public void gameOver()
	{
		
	}

	public boolean isSteppable(int x, int y)
	{
		return board[y][x].isSteppable();
	}
	
}
