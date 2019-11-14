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
import misc.Constants;
import net.ClientFactory;
import net.HostClient;


public class HostScreen
{
	private Scene scene;
	static int role = Constants.DM;
	public HostScreen() {
		Text text = new Text("Select your role:");
		ComboBox<String> roles = new ComboBox<String>();
		roles.getItems().add("DM");
		roles.getItems().add("Hero");
		roles.getSelectionModel().select(0);
		
		roles.getSelectionModel().selectedItemProperty().addListener(
				(options, oldValue, newValue) -> { 
					role = newValue.equals("DM")? Constants.DM : Constants.HERO;
				}
		);
		Button start = new Button("Start");
		start.setDisable(true);
		start.setOnAction(e -> {
			if(HostClient.hasConnection) {
				Game game = new Game(role);
			}
		});
		VBox items = new VBox();
		items.setSpacing(20);
		items.getChildren().add(text);
		items.getChildren().add(roles);
		items.getChildren().add(start);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		scene = new Scene(items, bounds.getWidth(), bounds.getHeight());
		items.setBackground(MainMenu.bg);
		ClientFactory.create();
		start.setDisable(false);
	}
	
	
	
	public Scene getScene() {
		return scene;
	}
}
