package UI;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import items.Board;
import items.Card;
import items.DefaultTile;
import items.Tile;
import items.TileCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import misc.BoardSelector;
import misc.CardHandler;
import misc.MoveBuilder;

public class DisplayGrid extends VBox implements Observer
{
	public Button[][] grid = null;
	private int reveals = 0;
	public DisplayGrid(int reveals) {
		this.reveals = reveals;
		grid = new Button[Board.HEIGHT][Board.WIDTH];
		getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		for(int j = 0; j < Board.HEIGHT ; j++ ) {
			HBox rowBox = new HBox();
			for(int i = 0; i < Board.WIDTH ; i++ ) {
				grid[j][i] = new Button();
				grid[j][i].getStyleClass().add("gridButton");
				final int x = i;
				final int y = j;
				grid[j][i].setOnAction(e -> {
					if(reveals > 0) {
						Board.Instance().get(x, y).reveal();
						return;
					}
					ArrayList<Card> cards = CardHandler.Instance().get();
					if( cards.size() == 0) return;
					if(cards.get(0) instanceof TileCard)
					{
						if(!Board.Instance().isSteppable(x,y)) {
							((TileCard)cards.get(0)).play(x, y);
							CardHandler.Instance().deselect();
						}
					} else {
						if(Board.Instance().isSteppable(x,y)) {
							BoardSelector.Instance().select(x, y);
						}
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
		size -=4;
		for(int i = 0; i < Board.WIDTH ; i++ ) {
			for(int j = 0; j < Board.HEIGHT ; j++ ) {
				Tile tile = Board.Instance().get(i, j);
				if(!tile.hasChanged) continue;
				if(tile instanceof DefaultTile) {
					removeBorder(i,j);
				} else {
					addBorder(i,j);
				}
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
	
	private void removeBorder(int x, int y) {
		grid[y][x].getStyleClass().removeAll("gridButtonToggled");
		grid[y][x].getStyleClass().add("gridButtonUntoggled");
	}
	
	private void addBorder(int x, int y) {
		grid[y][x].getStyleClass().removeAll("gridButtonUntoggled");
		grid[y][x].getStyleClass().add("gridButtonToggled");
	}
}


