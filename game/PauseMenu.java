package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.MenuComponent.MenuCompType;

public class PauseMenu extends Menu {
	
	private boolean active;
	
	public PauseMenu() {
		super();
		active = true;
		components.add(new MenuComponent(320, 300, MenuCompType.RESUME));
		components.add(new MenuComponent(570, 300, MenuCompType.QUIT));
	}
	
	public void tick() {
		for(MenuComponent c : components) {
			if(c instanceof Clickable) {
				if(c.getClicked()) {
					if(c.getType() == MenuCompType.RESUME);{
						active = false;
					}
					if(c.getType() == MenuCompType.QUIT) {
						System.exit(0);
					}
				}
			}
				
		}
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(boolean b) {
		active = b;
	}
	
	public void reset() {
		active = true;
		for(MenuComponent c : components) {
			c.setClicked(false);
			c.setPressed(false);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 64));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", 415, 220);
	}

}
