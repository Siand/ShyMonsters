package UI;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import net.HostClient;


public class HostScreen
{
	private Scene scene;
	public HostScreen() {
		Text text = new Text("Select your role:");
		ComboBox<String> profiles = new ComboBox<String>();
		profiles.getItems().add("DM");
		profiles.getItems().add("Hero");
		Button start = new Button("Start");
		start.setDisable(true);
		start.setOnAction(e -> {
			GameScene
		});
		VBox items = new VBox();
		items.setSpacing(20);
		items.getChildren().add(text);
		items.getChildren().add(profiles);
		items.getChildren().add(start);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		scene = new Scene(items, bounds.getWidth(), bounds.getHeight());
		HostClient.createInstance(1237);
		while(!HostClient.hasConnection) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
			}
		}
		start.setDisable(false);
	}
	
	
	
	public Scene getScene() {
		return scene;
	}
}
