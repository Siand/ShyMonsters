package UI;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import items.Board;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
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
		GameObserver.Instance().running = false;
		String string;
		if(role == Constants.DM) {
			if(killed) {
				// WIN
				string = "YOU WON!";
				
			} else {
				string = "YOU LOST!";
			}
		} else {
			if(!killed) {
				string = "YOU WON!";
			} else {
				string = "YOU LOST!";
			}
		}
		showEnd(string);
	}
	
	public void showEnd(String string) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Game Over");
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}
}
