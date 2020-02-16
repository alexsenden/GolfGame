package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.ImageLoader.Visual;

public class Hole extends Component{

	public Hole(double x, double y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(ImageLoader.getImage(Visual.HOLE), (int)x, (int)(Main.HEIGHT - y - 74), null);
		//g.setColor(Color.BLACK);
		//g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)(Main.HEIGHT - y - 40), 32, 32);
	}

}
