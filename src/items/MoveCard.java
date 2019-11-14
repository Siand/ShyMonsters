package items;

import moves.Move;

public class MoveCard implements Card
{
	private Move m;
	private boolean inUse = false;
	protected int uses = 1;
	protected String artwork = "";
	@Override
	public boolean isUnique()
	{
		return (!isAttack() && !isJump());
	}
	
	@Override
	public void setInUse(boolean iU)
	{
		inUse = iU;
	}

	public boolean canUse() {
		return uses > 0; 
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
		artwork	= "cardback.png";	
	}
	
	public boolean isAttack() {
		return m.isAttack;
	}
	public boolean isJump() {
		return m.isJump;
	}
}
