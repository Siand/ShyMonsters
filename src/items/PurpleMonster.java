package items;

public class PurpleMonster extends Monster
{
	public PurpleMonster(int x, int y) {
		super(x,y);
		init();
	}
	public PurpleMonster() {
		super();
		init();
	}
	
	public void init() {
		frontArtwork = "PurpleMonster.png";
		pattern.addFilled(1, -1);
	}
}
