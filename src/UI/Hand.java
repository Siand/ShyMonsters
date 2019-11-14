package UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import items.Board;
import items.TileCard;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import misc.CardHandler;

public class Hand extends VBox implements Observer
{
	private ArrayList<TileCard> cards;
	private Button[] row;
	int cardSize = 0;
	public Hand() {
		Board.Instance().addObserver(this);
	}
	
	public void supply(ArrayList<TileCard> cards) {
		this.cards = cards;
		getChildren().clear();
		row = new Button[cards.size()];
		for(int i=0; i < cards.size(); i++) {
			row[i] = new Button();
			String artwork = cards.get(i).getArtwork();
			Image image = new Image(PlayScene.class.getResourceAsStream(artwork));
			row[i].setGraphic(new ImageView(image));
			final int index = i;
			row[i].setOnAction(e -> {
				if(!cards.get(index).getInUse()) {
					CardHandler.Instance().add(cards.get(index));
				}
				else {
					CardHandler.Instance().remove(cards.get(index));
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
	
	public void resize(int cardSize) {
		this.cardSize = cardSize;
		setMaxHeight(cardSize * (row.length+1)/2);
		setMinHeight(cardSize * (row.length+1)/2);
		setMinWidth(cardSize * 2);
		setMaxWidth(cardSize * 2);
		for(int i = 0; i< row.length ; i++) {
			row[i].setMaxSize(cardSize, cardSize);
			row[i].setMinSize(cardSize, cardSize);
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
		}	
	}
	
}
