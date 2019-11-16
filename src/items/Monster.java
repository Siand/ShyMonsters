package items;

import UI.GameObserver;

public class Monster extends Tile
{

	private boolean isAlive;
	Pattern pattern;
	public Monster(int x, int y) {
		pattern = new Pattern();
		spawn(x, y);
	}
	
	public Monster() {
		pattern = new Pattern();
	}
	
	@Override
	public void spawn(int x, int y)
	{
		this.x = x;
		this.y = y;
		isAlive = true;
		hasChanged = true;
	}
	
	@Override
	public void stepOn(HeroPawn h)
	{
		h.setPos(x, y);
		reveal();
		if(isAlive && pattern.isActive(Board.Instance().getSurroundings(x,y)) && !Board.Instance().isShy(x,y)) {
			GameObserver.Instance().gameOver(true);
		}
	}

	@Override
	public boolean isMonster()
	{
		return true;
	}
	
	public void kill() {
		isAlive = false;
	}
}
