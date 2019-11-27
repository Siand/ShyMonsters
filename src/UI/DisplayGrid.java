package UI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

import items.Board;
import items.Card;
import items.DefaultTile;
import items.Monster;
import items.Tile;
import items.TileCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;

import misc.BoardSelector;
import misc.CardHandler;
import misc.MoveBuilder;
import moves.Position;
import net.ClientFactory;

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
					if(!GameObserver.Instance().running) { return; }
					if(this.reveals > 0) {
						Board.Instance().reveal(x, y);
						JSONObject reveal = new JSONObject();
						JSONObject pos = new JSONObject();
						pos.put("X", x);
						pos.put("Y", y);
						reveal.put("Reveal", pos);
						ClientFactory.getClient().send(reveal.toJSONString());
						this.reveals--;
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
							if(BoardSelector.Instance().isSelected(x, y)) {
								addBorder(x, y);
							} else {
								removeBorder(x, y);
							}
							onUpdate();
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
		boolean pawnPlaced = false;
		size -=4;
		for(int i = 0; i < Board.WIDTH ; i++ ) {
			for(int j = 0; j < Board.HEIGHT ; j++ ) {
 				Tile tile = Board.Instance().get(i, j);
 				if(BoardSelector.Instance().isSelected(i, j)){
					addBorder(i,j);
				} else {
					removeBorder(i,j);
				}
				if(!tile.hasChanged) continue;
				Image image;
				if(tile instanceof Monster && !((Monster)tile).isAlive()) {
					Position pawnpos = Board.Instance().getPawnPos();
					if(pawnpos != null) {
						pawnPlaced = true;
					}
					String overlapImg = pawnpos == null? "dead.png" : "deadWithPawn.png";
					image = overlap(i,j,overlapImg,size);
				} else {
					String artwork = tile.getArtwork();
					image = new Image(DisplayGrid.class.getResourceAsStream(artwork), size, size, false, true);
				}
				final int x = i;
				final int y = j;
				Platform.runLater(new Runnable(){
		               @Override public void run() {
		            	   grid[y][x].setGraphic(new ImageView(image));
		                 }
					});
				tile.acceptChange();
			}
		}
		Position pawnpos = Board.Instance().getPawnPos();
		if(!pawnPlaced && pawnpos != null) {
			Image oImg = overlap(pawnpos.x, pawnpos.y, "pawn.png", size);
			Platform.runLater(new Runnable(){
	               @Override public void run() {
	   					grid[pawnpos.y][pawnpos.x].setGraphic(new ImageView(oImg));
	                 }
				});
		}
	}
	
	public void resetView() {
		DefaultTile dt = new DefaultTile();
		String artwork = dt.getArtwork();
		int size = (int)getMinHeight() / Board.HEIGHT;
		Image image = new Image(DisplayGrid.class.getResourceAsStream(artwork), size, size, false, true);
		for(Button[] row : grid) {
			for(Button b : row ) {
				Platform.runLater(new Runnable(){
		               @Override public void run() {
		   				b.setGraphic(new ImageView(image));
		                 }
				});
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
	
	private Image getFXImage(BufferedImage image) {
	    WritableImage wr = null;
	    if (image != null) {
	        wr = new WritableImage(image.getWidth(), image.getHeight());
	        PixelWriter pw = wr.getPixelWriter();
	        for (int x = 0; x < image.getWidth(); x++) {
	            for (int y = 0; y < image.getHeight(); y++) {
	                pw.setArgb(x, y, image.getRGB(x, y));
	            }
	        }
	    }

	    return new ImageView(wr).getImage();
	}
	
	private Image overlap(int xPos, int yPos, String foreground ,int size) {
		final BufferedImage finalImage = new BufferedImage(size, size,
		        BufferedImage.TYPE_INT_RGB);
		 Graphics2D g = finalImage.createGraphics();
		 BufferedImage foreg;
		try
		{
			foreg = ImageIO.read(DisplayGrid.class.getResourceAsStream(foreground));
			String artwork = Board.Instance().get(xPos, yPos).getArtwork();
			BufferedImage bg = ImageIO.read(DisplayGrid.class.getResourceAsStream(artwork));
			g.drawImage(bg,0,0,size,size,0,0,bg.getWidth(),bg.getHeight(), null);
			g.drawImage(foreg,0,0,size,size,0,0,foreg.getWidth(),foreg.getHeight(), null);
			g.dispose();
			Image oImg = SwingFXUtils.toFXImage(finalImage, null);

			return oImg;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}


