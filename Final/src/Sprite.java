import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Sprite {
	protected int x;
	protected int y;
	protected int width;
    protected int height;
	protected boolean vis;
	protected Image image;
	
	public Sprite(int x,int y) {
		this.x =x;
		this.y = y;
		vis = true;
	}
	
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	protected void loadImage(String imageName) {
		image = new ImageIcon(imageName).getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean isVisible() {
		return vis;
	}
	
	public void setVisible(boolean b) {
		vis = b;
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
		
	}
	
}
