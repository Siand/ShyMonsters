package items;

import misc.BoardSelector;

public class TileCard implements Card
{
	private Tile t;
	private boolean inUse = false;
	protected int uses = 1;
	private String artwork = "";
	private int x = -1 ,y = -1;
	public TileCard(Tile t) {
		this.t = t;
		this.t.reveal();
		artwork = t.getArtwork();
	}
	
	public TileCard(Tile t, boolean reveal) {
		this.t = t;
		if(reveal) {
			this.t.reveal();
		}
		artwork = t.getArtwork();
	}
	public void play(int x, int y) {
		if(!inUse && Board.Instance().add(x, y, this)) {
			BoardSelector.Instance().select(x,y);
			inUse = true;
			this.x = x;
			this.y = y;
			t.spawn(x, y);	
		}
	}
	
	public Tile getTile() {
		return t;
	}
	
	public void remove() {
		if(inUse) {
			uses = 1;
			Board.Instance().remove(x, y);
			BoardSelector.Instance().select(x,y);
			inUse = false;
		}
	}
	
	public String getArtwork() {
		if(!canUse()) return "cardback.png";
		return t.getArtwork();
	}

	@Override
	public boolean isUnique()
	{
		return true;
	}

	@Override
	public void setInUse(boolean iU)
	{
		//DO NOT USE
		//inUse = iU;
	}

	@Override
	public boolean getInUse()
	{
		return inUse;
	}

	@Override
	public void deplete()
	{
		uses--;
	}

	@Override
	public boolean canUse()
	{
		return uses > 0;
	}

}
