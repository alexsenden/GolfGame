package game;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public abstract class Component {
	
	protected double x, y;
	
	public abstract void draw(Graphics g);
	
	public Component(double x, double y) {
		this.x = x + Screen.xshift;
		this.y = y +  Screen.yshift;
	}
	
	public void move(double deltax, double deltay) {
		x += deltax;
		y += deltay;
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D.Double getLocation() {
		return new Point2D.Double(x, y);
	}
}
