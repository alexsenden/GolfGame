package game;

import java.awt.Color;
import java.awt.Graphics;

import game.ImageLoader.Visual;

public class PowerBar extends Component {
	
	private static final double INCREMENT = 0.5;
	
	private boolean increasing;
	private boolean visible;
	private double value;

	public PowerBar() {
		super(50, 50);
		visible = false;
		value = 0;
	}

	@Override
	public void draw(Graphics g) {
		if(visible) {
			g.drawImage(ImageLoader.getImage(Visual.POWERBAR), (int)x, (int)(Main.HEIGHT - y - 32), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect((int)(x + value), (int)(Main.HEIGHT - y - 35), 6, 38);
		}
	}
	
	public void tick() {
		if(increasing) {
			value += INCREMENT;
		} else {
			value -= INCREMENT;
		}
		if(value < 1 + INCREMENT) {
			increasing = true;
			value = 1 + INCREMENT;
		} else if(value > 250 - INCREMENT) {
			increasing = false;
			value = 250 - INCREMENT;
		}
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
		value = 0;
	}
	
	public double getPower() {
		return value / 250;
	}
	
}
