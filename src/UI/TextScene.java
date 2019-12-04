package UI;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public class TextScene
{
	private static TextScene instance = null;
	protected String text = "";
	private TextScene()
	{
	}
	
	public void Instantiate(String s) {
		text = s;
	}
	
	public static TextScene Instance() {
		if(instance == null) {
			instance = new TextScene();
		}
		return instance;
	}
	
	public Scene getScene() {
		Label label = new Label(text);
		label.getStyleClass().add("title");
		StackPane root = new StackPane();
		root.getChildren().add(label);
		root.setBackground(MainMenu.bg);
		StackPane.setAlignment(label, Pos.CENTER);
		Scene scene = new Scene(root, MainMenu.frameWidth, MainMenu.frameHeight);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		return scene;
	}
	
	public void setText(String s) {
		text = s;
	}
	
}
