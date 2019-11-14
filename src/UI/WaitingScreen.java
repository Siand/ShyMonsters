package UI;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class WaitingScreen
{
	private Scene scene;
	private static  WaitingScreen instance = null;

	private WaitingScreen()
	{
		Text text = new Text("Waiting for opponent...");
		StackPane root = new StackPane();
		root.getChildren().add(text);
		StackPane.setAlignment(text, Pos.CENTER);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
	}
	public static WaitingScreen Instance() {
		if(instance == null) {
			instance = new WaitingScreen();
		}
		return instance;
	}
	
	public Scene getScene() {
		return scene;
	}
	
}
