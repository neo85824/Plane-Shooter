import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel{
	private static Plane plane;
	private static int IPLANE_X = 200; //飛機初始位置
	private static int IPLANE_Y = 200;
	private static int DELAY = 10;	//畫面更新速率
	private static int FIRE_DELAY = 300; //子彈發射速度
	private static boolean FIRE = false; //子彈發射參數
	private static ArrayList<Enemy> enemys;
	private static int Count =0 ;
	private static Image Background;
	
	public Board() {
		initBoard();
		Update(); 
		SetEnemy();
		plane.Fire();		
	}
	
	private void initBoard() {
		this.setPreferredSize(new Dimension(700 , 700));
		plane = new Plane(IPLANE_X,IPLANE_Y);
		
	}
	
	public void paintComponent(Graphics g) {
		drawObject(g);
		
	}
	
	
	
	public void SetEnemy() {
		enemys = new ArrayList<Enemy>();

		Timer timer = new Timer();
		TimerTask EnemyWave1Task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(Count <10) {
					enemys.add(new Enemy(0,-100,1));
					Count += 1;
				}
				else {
					this.cancel();
					Count = 0;
				}
			}
			
		};

		TimerTask EnemyWave2Task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(Count <10) {
					enemys.add(new Enemy(100,-100,1));
					Count += 1;
				}
				else {
					this.cancel();
					Count = 0;
				}

			}
			
		};
		
		timer.schedule(EnemyWave1Task, 0, 1000);
		timer.schedule(EnemyWave2Task, 12000, 1000);
	}
	
	public void Update() {
		Timer timer = new Timer();
		
		TimerTask RepaintTask = new TimerTask() {   //畫面更新
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(plane.isVisible()) {
				UpdatePlane();
				UpdateBullets();
				UpdateEnemys();
				checkCollisions();
				checkOutOfRange();
				repaint();
				}
				else {
					//timer.cancel();
				}
			}
			
		};
		timer.schedule(RepaintTask, 0,DELAY);		
	}
	
	private void drawObject(Graphics g) {
		Background = new ImageIcon("Background.jpg").getImage();
		g.drawImage(Background, 0, 0, this);
		
		if(plane.isVisible()) {
			g.drawImage(plane.getImage(), plane.getX(), plane.getY(), this); //畫飛機
		}
	
		
		ArrayList<Bullet> bs = plane.getBullets();   //畫子彈
		for (Bullet b : bs) {
            if (b.isVisible()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
		
		for(Enemy e : enemys ) {
			if(e.isVisible()) {
				g.drawImage(e.getImage(), e.getX(), e.getY(), this);
			}
			ArrayList<EnemyBullet> e_bs = e.getBullets();
			for(EnemyBullet e_b : e_bs) {
				if(e_b.isVisible()) {
					g.drawImage(e_b.getImage(), e_b.getX(), e_b.getY(), this);
				}
			}
		}
		
		
	}
	
	public void checkCollisions() {
		Rectangle r_plane = plane.getBounds();  //設定飛機矩形
		
		ArrayList<Bullet> bs = plane.getBullets();
		for(Bullet b : bs) {  
			Rectangle r_bullet = b.getBounds();
			for(Enemy e : enemys) {
				
				Rectangle r_enemy = e.getBounds(); //判斷敵人與子彈是否碰撞
				if( r_bullet.intersects(r_enemy) ) {
					e.setVisible(false);
					b.setVisible(false);
				}
				
				if( r_enemy.intersects(r_plane)) { //判斷敵人與飛機是否碰撞
					e.setVisible(false); 
					plane.setVisible(false);
				}
			}
		}
		
		
	}
	
	public void checkOutOfRange() {
		ArrayList<Bullet> bs = plane.getBullets();   //畫子彈
		for (Bullet b : bs) {
            if (b.getY() < (-b.getHeight())) {
            	b.setVisible(false);
            }
        }
		
		for(Enemy e : enemys ) {
			if(e.getX() > (300 + e.getWidth() ) ) {
				e.setVisible(false);
			}
		}
	}
	
	public void UpdatePlane() {  //更新飛機 飛機移動
		plane.move();
	}
	
	public void UpdateBullets() {
		ArrayList<Bullet> bs = plane.getBullets();
		
		for(int i=0 ; i < bs.size(); i++) {  //逐一判斷子彈 存在則移動 不存在移除
			Bullet b = bs.get(i);
			if(b.isVisible()) {
				b.move();
			}
			else {
				bs.remove(i);
			}
		}
		
		for(int i=0; i < enemys.size(); i++) {
			ArrayList<EnemyBullet> e_bs = enemys.get(i).getBullets();
			for(EnemyBullet e_b : e_bs) {
				if(e_b.isVisible()) {
					e_b.move();
				}
			}
		}
		
	}
	
	public void UpdateEnemys() {
		for(int i=0; i<enemys.size(); i++) {  //逐一判斷敵人 同子彈
			Enemy e = enemys.get(i);
			if(e.isVisible()) {
				e.move();
			}
			else {
				enemys.remove(i);
			}
		}
		
	}
	
	public void keyPressed(KeyEvent e) {   //鍵盤控制
		// TODO Auto-generated method stub
		if(e.getKeyCode() ==  KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			plane.Right();
			//System.out.println("RRR");
		}

		if(e.getKeyCode() ==  KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			plane.Left();
			//System.out.println("LLL");

		}

		if(e.getKeyCode() ==  KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			plane.Up();
			//System.out.println("UUUUUU");

		}

		if(e.getKeyCode() ==  KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			plane.Down();
			//System.out.println("DDDD");

		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		plane.KeyReleased();
	
	}

}
