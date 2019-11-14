package UI;

import items.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import misc.CardPile;
import misc.Constants;

public class PlayScene
{
	private static PlayScene instance;
	private PlayScene() {
	}
	
	public static PlayScene Instance() {
		if(instance == null) { 
			instance = new PlayScene();
		}
		return instance;
	}
	
	public Scene getScene(int role) {
		int size = (int)(MainMenu.frameHeight* 0.9);
		StackPane root = new StackPane();
		
		// Grid
		DisplayGrid grid = new DisplayGrid();
		grid.resize(size);
		root.getChildren().add(grid);
		StackPane.setAlignment(grid, Pos.CENTER_LEFT);
		
		Hand hand = new Hand();
		
		// DM specifics
		if(role == Constants.DM) {
			hand.supply(CardPile.Instance().drawHand());
		}
		// Hero specifics
		else {
			// hero cards
		}
		hand.resize( size / Board.HEIGHT);
		
		root.getChildren().add(hand);
		StackPane.setAlignment(hand, Pos.TOP_RIGHT);
		Scene scene = new Scene(root, MainMenu.frameWidth,  MainMenu.frameHeight);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		return scene;
	}
}
