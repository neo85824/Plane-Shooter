
public class Bullet extends Sprite{
	
	protected int v = 3;
	
	
	public Bullet(int x, int y) {
		super(x,y);
		initBullet();
	} 
	
	public void move() {
		y -= v;
	}
	
	private void initBullet() {
		loadImage("bullet.png");
		getImageDimensions();
	}
	
}
