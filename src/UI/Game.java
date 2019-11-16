package UI;

import java.util.Observable;
import java.util.Observer;

import items.Board;
import javafx.scene.Scene;
import misc.Constants;
import net.ClientFactory;

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
				ClientFactory.getClient().send(Board.Instance().toJSONString());
			}
			MainMenu.mainStage.setScene(waiting.getScene());
		} else {
			MainMenu.mainStage.setScene(PlayScene.Instance().getScene(role, reveals));
			reveals--;
		}
		currentTurn++;
	}
	
	public void gameOver(boolean killed) {
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
