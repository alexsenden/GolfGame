package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class LevelGeometry extends Component implements Physical{

	private double x2, y2;
	
	public LevelGeometry(double x, double y, double x2, double y2) {
		super(x, y);
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void move(double deltax, double deltay) {
		super.move(deltax, deltay);
		x2 += deltax;
		y2 += deltay;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int)x, (int)(y), (int)x2, (int)(y2));
		//g.drawLine(0, 450 - 100, 300, 450 - 100);
	}

	@Override
	public Line2D.Double getBounds() {
		return new Line2D.Double(x, y, x2, y2);
	}

}
