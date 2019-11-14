package UI;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import misc.Constants;

public class Game implements Observer
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
		
		update(null, null);
	}
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		if(turns == currentTurn) {
			gameOver(); 
		}
		if(!actives[currentTurn]) {
			MainMenu.mainStage.setScene(waiting.getScene());
		} else {
			MainMenu.mainStage.setScene(PlayScene.Instance().getScene(role, reveals));
			reveals--;
		}
		currentTurn++;
	}
	
	public void gameOver() {
		if(role == Constants.DM) {
			// LOSE
		} else {
			// win
		}
	}
}
