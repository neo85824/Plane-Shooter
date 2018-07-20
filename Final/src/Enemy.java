import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Enemy extends Sprite{
	protected int v ;
	protected ArrayList<EnemyBullet> bullets;
	protected int FireDelay = 1500;
	
	public Enemy(int x, int y,int v) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.v = v;
		initEnemy();
		Fire();
	}
	
	public void initEnemy() {
		loadImage("enemy.png");
		getImageDimensions();
		bullets = new ArrayList<EnemyBullet>();
	}
	
	public void Fire() {
		Timer timer = new Timer();
		
		TimerTask FireTask = new TimerTask() {   //子彈發射控制
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SetBullets();
			}
			
		};
		
		timer.schedule(FireTask, 0, FireDelay);
	}
	
	public ArrayList getBullets() {
		return bullets;
	}
	
	public void SetBullets() {
		bullets.add(new EnemyBullet(x + width/2, y ));
	}
	
	public void setVelocity(int v) {
		this.v = v;
	}
	
	public void move() {
		y += v;
		x += v;
	}
	
}
