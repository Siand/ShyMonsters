package items;

public class MoveCard implements Card
{
	private boolean isAttack;
	private boolean isJump;
	private boolean inUse = false;
	protected int uses = 1;
	protected String artwork = "";
	
	public MoveCard(boolean iA, boolean iJ) {
		isAttack = iA;
		isJump = iJ;
		if(isJump) {
			artwork = "jump.png";
		} else if(isAttack) {
			artwork = "attack.png";
		} else {
			artwork = "cardback.png";
		}
	}
	
	@Override
	public boolean isUnique()
	{
		return (!isAttack && !isJump);
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
		if(isAttack || isJump ) uses--;
		if(uses == 0)  { artwork	= "cardback.png"; }
	}
	
	public boolean isAttack() {
		return isAttack;
	}
	public boolean isJump() {
		return isJump;
	}

	@Override
	public String getArtwork()
	{
		return artwork;
	}
}
