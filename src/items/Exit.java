package items;


public class Exit extends Tile
{

	public Exit() {
		frontArtwork = "exit.png";
	}
	@Override
	public void stepOn(HeroPawn h)
	{
		reveal();
		h.setPos(x, y);
		Board.Instance().gameOver();
	}

	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
		hasChanged = true;
	}

}
