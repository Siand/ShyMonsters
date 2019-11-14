package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import misc.Constants;
import net.ClientFactory;
import net.HostClient;


public class HostScreen
{
	private Scene scene;
	static int role = Constants.DM;
	public HostScreen() {
		Label text = new Label("Select your role:");
		text.getStyleClass().add("title");
		ComboBox<String> roles = new ComboBox<String>();
		roles.getItems().add("DM");
		roles.getItems().add("Hero");
		roles.getSelectionModel().select(0);
		roles.getSelectionModel().selectedItemProperty().addListener(
				(options, oldValue, newValue) -> { 
					role = newValue.equals("DM")? Constants.DM : Constants.HERO;
				}
		);
		roles.getStyleClass().add("comboBox");
		Button start = new Button("Start");
		start.getStyleClass().add("mainMenuButton");
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
		items.setBackground(MainMenu.bg);
		items.setAlignment(Pos.CENTER);
		scene = new Scene(items, MainMenu.frameWidth, MainMenu.frameHeight);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		ClientFactory.create();
		start.setDisable(false);
	}
	
	
	
	public Scene getScene() {
		return scene;
	}
}
