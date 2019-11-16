package items;


public abstract class Tile
{
	public String backArtwork = "cardback.png";
	public String frontArtwork = "";
	protected String name = "";
	protected int x;
	protected int y;
	protected boolean revealed;
	public boolean hasChanged = true;
	public abstract void stepOn(HeroPawn h);
	public abstract void spawn(int x ,int y);
	public String getName() {
		return name;
	}
	public boolean isSteppable() {
		return true;
	}
	
	public void reveal() {
		revealed = true;
		hasChanged = true;
	}
	
	public String getArtwork()
	{
		return revealed?  frontArtwork : backArtwork;
	}
	public Tile() {
		revealed = false;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isRevealed() {
		return revealed;
	}
	public void setX(int newx) {
		x = newx;
	}
	public void setY(int newy) {
		y = newy;
	}
	public boolean isMonster() {
		return false;
	}
	public void acceptChange() {
		hasChanged = false;
	}
}
