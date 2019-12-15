package UI;

import java.util.Observable;

public class GameObserver
{

	private static GameObserver instance;
	private static Game game;
	public boolean running = true;
	
	private GameObserver() {
		
	}
	
	public static GameObserver Instance() {
		if(instance == null) {
			instance = new GameObserver();
		}
		return instance;
	}

	public void onChange()
	{
		game.update();
	}
	
	public void subscribe(Game g) {
		game = g;
	}
	
	public void gameOver(boolean killed) {
		game.gameOver(killed);
	}
}
