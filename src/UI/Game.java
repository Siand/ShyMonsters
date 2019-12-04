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
	
	int turns = 6;
	boolean[] actives;
	int currentTurn = 0;
	int reveals = 2;
	int role;
	public Game(int role) {
		TextScene.Instance().Instantiate("Waiting for your opponent...");
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
		               @Override  public void run() {
		            	   MainMenu.mainStage.setScene(ObserverScreen.Instance().getScene());
		           		   Board.Instance().tagAll();
		                 }
					});
			} else {
				Platform.runLater(new Runnable(){
		               @Override public void run() {
		            	   MainMenu.mainStage.setScene(TextScene.Instance().getScene());
		           		   Board.Instance().tagAll();
		                 }
					});
			}

		} else {
			Platform.runLater(new Runnable(){
	               @Override public void run() {
	            	   MainMenu.mainStage.setScene(PlayScene.Instance().getScene(role, reveals--));
	            	   if(role == Constants.DM) {
	            		   Board.Instance().reset();
	            	   } else {
	            		   Board.Instance().tagAll();
	            	   }
	           		   
	                 }
				});
		}
		currentTurn++;
	}
	
	public void gameOver(boolean killed) {
		GameObserver.Instance().running = false;
		if(role == Constants.DM) {
			if(killed) {
				// WIN
				TextScene.Instance().setText("YOU WON!");
				
			} else {
				TextScene.Instance().setText("YOU LOST!");
			}
		} else {
			if(!killed) {
				TextScene.Instance().setText("YOU WON!");
			} else {
				TextScene.Instance().setText("YOU LOST!");
			}
		}
		MainMenu.mainStage.setScene(TextScene.Instance().getScene());
	}
	
}
