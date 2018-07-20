import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Plane extends Sprite {
	protected int dx;
	protected int dy;
	protected int v = 7;
	protected ArrayList<Bullet> bullets;
	protected int FireDelay = 300;
	protected int GameHeight = 700;
	protected int GameWidth = 700;
	
	public Plane(int x, int y) {
		super(x, y);
		bullets = new ArrayList<Bullet>();
		initPlane();
		// TODO Auto-generated constructor stub
	}
	
	protected void initPlane() {
		loadImage("warrior2_0.png");
		getImageDimensions();
	}
	
	public ArrayList getBullets() {
		return bullets;
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
	
	public void SetBullets() {
		bullets.add(new Bullet(x + width/2, y ));
	}
	
	public void move() {
		x += dx;
		y += dy;
	}
	
	public void Right() {
		if((x+v)<= (GameWidth - this.getWidth() )) {
			dx += v;
		}
	}
	
	public void Left() {
		if((x-v) > 0) {
			dx -= v;
		}
	}
	
	public void Up() {
		if((y-v) > 0 ) {
			dy -= v;
		}
	}
	
	public void Down() {
		if((y+v)< (GameHeight-this.getHeight() )) {
			dy += v;
		}
	}
	
	public void KeyReleased() {
		dx = 0;
		dy = 0;
	}
	
	
	
}
