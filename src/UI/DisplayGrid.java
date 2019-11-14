package UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import items.Board;
import items.Card;
import items.DefaultTile;
import items.MoveCard;
import items.Tile;
import items.TileCard;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import misc.CardHandler;
import misc.MoveBuilder;
import misc.BoardSelector;

public class DisplayGrid extends VBox implements Observer
{
	public Button[][] grid = null;
	
	public DisplayGrid() {
		grid = new Button[Board.HEIGHT][Board.WIDTH];
		for(int j = 0; j < Board.HEIGHT ; j++ ) {
			HBox rowBox = new HBox();
			for(int i = 0; i < Board.WIDTH ; i++ ) {
				grid[j][i] = new Button();
				final int x = i;
				final int y = j;
				grid[j][i].setOnAction(e ->{
					ArrayList<Card> cards = CardHandler.Instance().get();
					if( cards.size() == 0) return;
					if(cards.get(0) instanceof TileCard)
					{
						if(!Board.Instance().isSteppable(x,y)) {
							((TileCard)cards.get(0)).play(x, y);
							CardHandler.Instance().deselect();
						}
					} else {
						BoardSelector.Instance().select(x, y);
					}
				});
				rowBox.getChildren().add(grid[j][i]);
			}
			getChildren().add(rowBox);
		}
		Board.Instance().addObserver(this);
		resetView();
	}
	
	public void resize(int size) {
		setMaxSize(size, size);
		setMinSize(size, size);
		for(Button[] row : grid) {
			for(Button b : row ) {
				b.setMaxSize(size/Board.WIDTH, size/Board.HEIGHT);
				b.setMinSize(size/Board.WIDTH, size/Board.HEIGHT);
			}
		}
		onUpdate();
	}
	
	public void onUpdate() {
		int size = (int)getMinHeight() / Board.HEIGHT;
		size -=2;
		for(int i = 0; i < Board.WIDTH ; i++ ) {
			for(int j = 0; j < Board.HEIGHT ; j++ ) {
				Tile tile = Board.Instance().get(i, j);
				if(!tile.hasChanged) continue;
				String artwork = tile.getArtwork();
				Image image = new Image(DisplayGrid.class.getResourceAsStream(artwork), size, size, false, true);
				grid[j][i].setGraphic(new ImageView(image));
				tile.acceptChange();
			}
		}
	}
	
	public void resetView() {
		MoveBuilder.Instance().reset();
		DefaultTile dt = new DefaultTile();
		String artwork = dt.getArtwork();
		int size = (int)getMinHeight() / Board.HEIGHT;
		Image image = new Image(DisplayGrid.class.getResourceAsStream(artwork), size, size, false, true);
		for(Button[] row : grid) {
			for(Button b : row ) {
				b.setGraphic(new ImageView(image));
			}
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		onUpdate();		
	}
}


