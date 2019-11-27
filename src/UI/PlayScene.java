package UI;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import items.Board;
import items.Card;
import items.MoveCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import misc.BoardSelector;
import misc.CardHandler;
import misc.CardPile;
import misc.Constants;
import misc.MoveBuilder;
import moves.Move;
import net.ClientFactory;

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
	
	public Scene getScene(int role, int reveals) {
		int size = (int)(MainMenu.frameHeight* 0.9);
		StackPane root = new StackPane();
		
		// Grid
		DisplayGrid grid;

		
		Hand hand = new Hand();
		Button lockIn = new Button("Lock In");
		lockIn.getStyleClass().add("UIButton");
		
		// DM specifics
		if(role == Constants.DM) {
			grid = new DisplayGrid(0);
			hand.supply(CardPile.Instance().drawHand());
			lockIn.setOnAction(e -> {
				if(BoardSelector.Instance().isValid()) {
					GameObserver.Instance().onChange();
				}
			});
		}
		// Hero specifics
		else {
			grid = new DisplayGrid(reveals);
			ArrayList<Card> cards = new ArrayList<>();
			cards.add(new MoveCard(true, false));
			cards.add(new MoveCard(false, true));
			cards.add(new MoveCard(false, false));
			hand.supply(cards);
			lockIn.setOnAction(e -> {
				MoveBuilder.Instance().reset();
				for(Card c : CardHandler.Instance().get()) {
					MoveBuilder.Instance().apply((MoveCard) c);
				}
				if(MoveBuilder.Instance().applyPositions(Board.Instance().getPawnPos(), BoardSelector.Instance().get())) {
					CardHandler.Instance().depleteAll();
					Move m = MoveBuilder.Instance().get();
					Board.Instance().play(m);
					JSONObject mobj = new JSONObject();
					ClientFactory.getClient().send(m.toJSONString());
				}
				
			});
		}
		// 
		hand.resize( size / Board.HEIGHT);
		grid.resize(size);
		root.getChildren().add(grid);
		StackPane.setAlignment(grid, Pos.CENTER_LEFT);

		root.getChildren().add(hand);
		StackPane.setAlignment(hand, Pos.TOP_RIGHT);
		VBox infoCorner = new VBox();
		Image im = new Image(PlayScene.class.getResourceAsStream("rules.jpg"), size / 4, size /4, false, true);
		ImageView rulesView = new ImageView(im);
		Label image = new Label("", rulesView);
		Label tilesToReveal = new Label(reveals +" Reveals this turn.");
		tilesToReveal.getStyleClass().add("whiteText");
		lockIn.setAlignment(Pos.CENTER_RIGHT);
		tilesToReveal.setAlignment(Pos.CENTER_RIGHT);
		image.setAlignment(Pos.CENTER_RIGHT);
		infoCorner.getChildren().addAll(lockIn,tilesToReveal,image);
		infoCorner.setMaxSize(size/3, size/3);
		root.getChildren().add(infoCorner);
		StackPane.setAlignment(infoCorner, Pos.BOTTOM_RIGHT);
		Scene scene = new Scene(root, MainMenu.frameWidth,  MainMenu.frameHeight);
		root.setBackground(MainMenu.bg);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		return scene;
	}
}
