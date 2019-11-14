package UI;

import items.CyanMonster;
import items.Board;
import UI.PlayScene;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import misc.Constants;

public class MainMenu extends Application
{
	public static Stage mainStage;
	public static void main(String[] args){
		launch(args);
	}
	static double frameWidth = 468, frameHeight = 360;
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		//mainStage.setResizable(false);
		mainStage.sizeToScene(); // Prevent extra area in the border area
		//primaryStage.getIcons().add(new Image(DMClient.class.getResourceAsStream("logo.png")));
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		Background blackBG = new Background(new BackgroundFill(Color.BLACK,new CornerRadii(0),new Insets(0)));

		mainStage.setX(bounds.getMinX());
		mainStage.setY(bounds.getMinY());
		frameWidth = bounds.getWidth();
		frameHeight = bounds.getHeight();
		mainStage.setWidth(frameWidth);
		mainStage.setHeight(frameHeight);
		
	/*	// Items
		Button host = new Button("HOST");
		host.getStyleClass().add("mainMenuButton");
		Button join = new Button("JOIN");
		join.getStyleClass().add("mainMenuButton");
		Label title  = new Label("Shy Monsters");
		title.getStyleClass().add("title");
		*/
		
		VBox scenePanel = new VBox();
		scenePanel.setAlignment(Pos.CENTER);
		scenePanel.setSpacing(20);
		scenePanel.setBackground(blackBG);
		Scene mainScene = new Scene(scenePanel, frameWidth, frameHeight);
		mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		
		
		/*//Add items to panel
		scenePanel.getChildren().add(title);
		scenePanel.getChildren().add(host);
		scenePanel.getChildren().add(join);
		
		host.setOnAction(e -> {
			
		});
		
		join.setOnAction(e -> {
			
		});
		*/
		
		//DisplayGrid grid = new DisplayGrid();
		//grid.resize((int)(frameHeight* 0.9));
		//scenePanel.getChildren().add(grid);
		CyanMonster bm = new CyanMonster();
		bm.reveal();
		//Board.Instance().add(0, 0, bm);
		mainStage.setScene(PlayScene.Instance().getScene(Constants.DM));
		mainStage.setMaximized(true);
		mainStage.show();
	}

}
