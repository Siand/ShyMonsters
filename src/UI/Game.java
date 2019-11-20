package UI;

import java.util.Observable;
import java.util.Observer;

import items.Board;
import javafx.application.Platform;
import javafx.scene.Scene;
import misc.Constants;
import net.ClientFactory;
import util.ParseMove;

public class Game
{
	Scene scene;
	WaitingScreen waiting = WaitingScreen.Instance();
	int turns = 6;
	boolean[] actives;
	int currentTurn = 0;
	int reveals = 2;
	int role;
	public Game(int role) {
		this.role = role;
		if(role == Constants.DM) { 
			actives = new boolean[] {true, false, true, false, true, false};
		} else {
			actives = new boolean[] {false, true, false, true, false, true};
		}
		GameObserver.Instance().subscribe(this);
		update();
	}
	
	public void update()
	{
		if(turns == currentTurn) {
			gameOver(false); 
		}
		if(!actives[currentTurn]) {
			if(role == Constants.DM) {
				String boardState = Board.Instance().toJSONString();
				DisplayGrid dg = new DisplayGrid(0);
				int size = (int)(MainMenu.frameHeight* 0.9);
				dg.resize(size);
				ParseMove pm = new ParseMove(boardState);
				pm.setObservingBoard();
				ClientFactory.getClient().send(boardState);
				ObserverScreen.createInstance(dg);
				Platform.runLater(new Runnable(){
		               @Override public void run() {
		            	   MainMenu.mainStage.setScene(ObserverScreen.Instance().getScene());
		                 }
					});
			} else {
				Platform.runLater(new Runnable(){
		               @Override public void run() {
		            	   MainMenu.mainStage.setScene(waiting.getScene());
		                 }
					});
			}

		} else {
			if(role == Constants.DM) { Board.Instance().reset(); }
			Platform.runLater(new Runnable(){
	               @Override public void run() {
	            	   MainMenu.mainStage.setScene(PlayScene.Instance().getScene(role, reveals--));
	                 }
				});
		}
		currentTurn++;
	}
	
	public void gameOver(boolean killed) {
		System.out.println("Game Over!");
		if(role == Constants.DM) {
			if(killed) {
				// WIN
			} else {
				// LOSE
			}
		} else {
			if(killed) {
				// win
			} else {
				// lose
			}
		}
	}
}
