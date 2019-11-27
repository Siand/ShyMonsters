package UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import items.Board;
import items.Card;
import items.TileCard;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import misc.CardHandler;

public class Hand extends VBox implements Observer
{
	private ArrayList<Card> cards;
	public static int size = 0;
	private Button[] row;
	int cardSize = 0;
	public Hand() {
		Board.Instance().addObserver(this);
		getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	}
	
	public void supply(ArrayList<Card> cards) {
		this.cards = cards;
		size = cards.size();
		getChildren().clear();
		row = new Button[cards.size()];
		for(int i=0; i < cards.size(); i++) {
			row[i] = new Button();
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(PlayScene.class.getResourceAsStream(artwork));
			row[i].setGraphic(new ImageView(image));
			row[i].getStyleClass().add("gridButton");
			final int index = i;
			row[i].setOnAction(e -> {
				if(!GameObserver.Instance().running) { return; }
				CardHandler.Instance().select(cards.get(index));
				if(cards.get(index).getInUse()) {
					addBorder(index);
				} else {
					removeBorder(index);
				}
			});
		}
		for(int i = 0; i < cards.size(); i+=2) {
			HBox rBox = new HBox();
			rBox.getChildren().add(row[i]);
			if(row.length > i+1) {
				rBox.getChildren().add(row[i+1]);
			}
			getChildren().add(rBox);
		}
	}
	
	public void resize(int cSize) {
		this.cardSize = cSize - 4;
		setMaxHeight(cSize * (row.length+1)/2);
		setMinHeight(cSize * (row.length+1)/2);
		setMinWidth(cSize * 2);
		setMaxWidth(cSize * 2);
		for(int i = 0; i< row.length ; i++) {
			row[i].setMaxSize(cSize, cSize);
			row[i].setMinSize(cSize, cSize);
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(PlayScene.class.getResourceAsStream(artwork), cardSize, cardSize, false, true);
			row[i].setGraphic(new ImageView(image));
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(row == null) return;
		for(int i = 0; i< row.length ; i++) {
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(PlayScene.class.getResourceAsStream(artwork), cardSize, cardSize, false, true);
			row[i].setGraphic(new ImageView(image));
			if(cards.get(i).getInUse()) {
				addBorder(i);
			} else {
				removeBorder(i);
			}
		}	
	}
	
	private void removeBorder(int pos) {
		row[pos].getStyleClass().removeAll("gridButtonToggled");
		row[pos].getStyleClass().add("gridButtonUntoggled");
	}
	
	private void addBorder(int pos) {
		row[pos].getStyleClass().removeAll("gridButtonUntoggled");
		row[pos].getStyleClass().add("gridButtonToggled");
	}
}
