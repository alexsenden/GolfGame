package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Menu extends Component {

	private static final int X = 200;
	private static final int Y = 100;
	private static final int W = Main.WIDTH - (2 * X);
	private static final int H = Main.HEIGHT - (2 * Y);
	
	protected ArrayList<MenuComponent> components = new ArrayList<MenuComponent>();
	
	public Menu() {
		super(X, Y);
	}
	
	public void addMenuComponent(MenuComponent c) {
		components.add(c);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRoundRect(X, Y, W, H, 50, 50);
		
		for(MenuComponent c : components) {
			c.draw(g);
		}
	}
	
}
