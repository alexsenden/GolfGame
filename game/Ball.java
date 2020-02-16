package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import game.ImageLoader.Visual;

public class Ball extends Component {
	
	private int radius;
	private Vector velocity;
	private boolean stalled;
	private boolean alive;
	
	public Ball(int x, int y) {
		super(x, y);
		
		//testing
		alive = true;
		radius = 8;
		stalled = false;
		velocity = new Vector(0, 0);
	}

	public void draw(Graphics g) {
		g.drawImage(ImageLoader.getImage(Visual.BALL), (int)x, (int)(Main.HEIGHT - y), null);
		//g.setColor(Color.BLACK);
		//g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}
	
	public void hit(double theta, double power) {
		double x, y;
		stalled = false;
		y = power * Math.sin(theta);
		x = power * Math.cos(theta);
		velocity = new Vector(x, y);
		System.out.println("New vector; x: " + x + " y: " + y);
		//this.y += 32;
		//this.x += velocity.getX();
	}
	
	public boolean isAlive() {
		if(y < -200) {
			alive = false;
		}
		return alive;
	}
	
	public void move() {
		x += velocity.getX();
		y += velocity.getY();
	}
	
	public void applyForces(){
		velocity.applyGravity();
		velocity.applyFriction(0.001);
	}
	
	public void checkStalled() {
		velocity.checkStalled();
		if(velocity.getStalled()) {
			stalled = true;
		}
	}
	
	public void checkCollision(ArrayList<Component> components) {
		double tempx = x;
		double tempy = y;
		tempx += velocity.getX();
		tempy += velocity.getY();
		
		Point2D.Double stored = null;
		Point2D.Double[] arr = {null, null};
		
		for(Component c : components) {
			if(c instanceof Physical) {
				Physical c2 = (Physical)c;
				arr = intersection(new Ellipse2D.Double(tempx, tempy, radius * 2, radius * 2), (Line2D)(c2.getBounds()));
				if(arr[0] != null) {
					if(arr[1] != null) {
						//System.out.println("BREAK");
						break;
					} else if(stored == null) {
						stored = arr[0];
					} else {
						arr[1] = stored;
						break;
					}
				}
			}
		}
		if(arr[1] != null) {
			//System.out.println("REFLECTED");
			//System.out.println(arr[1].getY() + " " + arr[0].getY());
			//System.out.println(arr[1].getY() - arr[0].getY());
			//System.out.println("passed " + ((arr[1].getY() - arr[0].getY()) / ((arr[1].getX() - arr[0].getX()))));
			velocity.reflect((arr[1].getY() - arr[0].getY()) / ((arr[1].getX() - arr[0].getX())));
			move();
		}
	}
	
	//http://mathworld.wolfram.com/Circle-LineIntersection.html
	//thank sweet baby jesus this exists^
	
	public Line2D shiftLineByOrigin(Line2D l1, double cx, double cy) {
		return new Line2D.Double(l1.getX1() - cx, l1.getY1() - cy, l1.getX2() - cx, l1.getY2() - cy);
	}
	
	public Point2D.Double[] intersection(Ellipse2D.Double e, Line2D l1) {
		
		//x, y
		//75, 92
		
		//cx, cy
		//83, 100
		
		//x1, y1, x2, y2
		//0, 100, 300, 100
		
		//x1, y1, x2, y2
		//-83, 0, 217, 0
		
		//dx = 217 - -83 = 300
		//dy = 100 - 100 = 0
		//dr = 300
		//d = 0
		
		Line2D l = shiftLineByOrigin(l1, e.getCenterX(), e.getCenterY());
		double dx = l.getX2() - l.getX1();
		double dy = l.getY2() - l.getY1();
		double dr = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		double d = l.getX1() * l.getY2() - l.getX2() * l.getY1();
		
		if(Math.pow(d, 2) > Math.pow(radius, 2) * Math.pow(dr, 2)) {
			return new Point2D.Double[] {null, null};
		}
		
		Point2D.Double p1 = new Point2D.Double(
			(d * dy + SSGUtils.sgn(dy) * dx * Math.sqrt(Math.pow(radius, 2) * Math.pow(dr, 2) - Math.pow(d, 2))) / Math.pow(dr, 2),
			(-1 * d * dx + Math.abs(dy) * Math.sqrt(Math.pow(radius, 2) * Math.pow(dr, 2) - Math.pow(d, 2))) / Math.pow(dr, 2)
		);
		
		Point2D.Double p2 = new Point2D.Double(
			(d * dy - SSGUtils.sgn(dy) * dx * Math.sqrt(Math.pow(radius, 2) * Math.pow(dr, 2) - Math.pow(d, 2))) / Math.pow(dr, 2),
			(-1 * d * dx - Math.abs(dy) * Math.sqrt(Math.pow(radius, 2) * Math.pow(dr, 2) - Math.pow(d, 2))) / Math.pow(dr, 2)
		);
		
		//System.out.println(p1.getX() + " " + p1.getY() + " " + p2.getX() + " " + p2.getY());
		
		if(p1.getX() > Math.max(l.getX1(), l.getX2()) || p1.getX() < Math.min(l.getX1(), l.getX2())) {
			p1 = null;
		}
		if(p2.getX() > Math.max(l.getX1(), l.getX2()) || p2.getX() < Math.min(l.getX1(), l.getX2())) {
			p2 = null;
		}
		
		if(p1 != null && (p1.getY() > Math.max(l.getY1(), l.getY2()) || p1.getY() < Math.min(l.getY1(), l.getY2()))) {
			p1 = null;
		}
		if(p2 != null && (p2.getY() > Math.max(l.getY1(), l.getY2()) || p2.getY() < Math.min(l.getY1(), l.getY2()))) {
			p2 = null;
		}
		
		if(p1 == null && p2 != null) {
			return new Point2D.Double[] {p2, null};
		}
		if(p1 == null && p2 == null) {
			return new Point2D.Double[] {null, null};
		}
		if(p1.equals(p2)) {
			return new Point2D.Double[] {p1, null};
		}
		return new Point2D.Double[] {p1, p2};
	}
	
	public boolean getStalled() {
		return stalled;
	}
	
	public void setStalled(boolean s) {
		stalled = s;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)(Main.HEIGHT - y), 16, 16);
	}
}
