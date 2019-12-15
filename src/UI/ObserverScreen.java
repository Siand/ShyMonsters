package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class ObserverScreen
{
	private static ObserverScreen instance;
	private static DisplayGrid dispGrid;
	private ObserverScreen() {
	}
	
	public static ObserverScreen Instance() {
		return instance;
	}
	
	public static void createInstance(DisplayGrid dg) {
		if(instance == null) { 
			instance = new ObserverScreen();
		}
		dispGrid = dg;
	}
	
	public Scene getScene() {
		int size = (int)(MainMenu.frameHeight* 0.9);
		StackPane root = new StackPane();
		root.getChildren().add(dispGrid);
		StackPane.setAlignment(dispGrid, Pos.CENTER);
		root.setBackground(MainMenu.bg);
		Scene scene = new Scene(root, MainMenu.frameWidth,  MainMenu.frameHeight);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		return scene;
	}
}
