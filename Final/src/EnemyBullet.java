
public class EnemyBullet extends Sprite{
	protected int v = 3;
	
	public EnemyBullet(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		initBullet();
	}
	
	public void move() {
		y += v;
	}
	
	private void initBullet() {
		loadImage("bullet.png");
		getImageDimensions();
	}

}
