package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.ClientFactory;

public class JoinScreen
{
	Scene scene;
	public JoinScreen() {
		VBox items = new VBox();
		items.setSpacing(20);
		Label label = new Label("Enter host address:");
		label.getStyleClass().add("whiteText");
		TextField hostTF = new TextField();
		hostTF.getStyleClass().add("textField");
		Button connect = new Button("Connect");
		connect.getStyleClass().add("mainMenuButton");
		connect.setOnAction(e -> {
			ClientFactory.create(hostTF.getText());
		});
		items.getChildren().addAll(label,hostTF,connect);
		label.setAlignment(Pos.CENTER);
		hostTF.setAlignment(Pos.CENTER);
		connect.setAlignment(Pos.CENTER);
		scene = new Scene(items,MainMenu.frameWidth, MainMenu.frameHeight);
		items.setBackground(MainMenu.bg);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	}
	public Scene getScene() {
		return scene;
	}
}
