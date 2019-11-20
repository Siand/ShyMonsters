package items;

public interface Card
{
	public boolean isUnique();
	public void setInUse(boolean iU);
	public boolean getInUse();
	public void deplete();
	public void replete();
	public boolean canUse();
	public String getArtwork();
}
