package game;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.RescaleOp;

import game.ImageLoader.Visual;

public class MenuComponent extends Component implements Clickable, Pressable {
	
	private boolean clicked, pressed;

	private Rectangle2D.Double bounds;
	private MenuCompType type;
	
	public enum MenuCompType{
		RESUME(0), QUIT(1);
		
		public Visual v;
		
		private MenuCompType(int num){
			switch(num) {
			case 0:
				v = Visual.RESUME;
				break;
			case 1:
				v = Visual.QUIT;
				break;
			}
		}
	}
	
	public MenuComponent(double x, double y, MenuCompType type) {
		super(x, y);
		this.type = type;
		bounds = new Rectangle2D.Double(x, y, ImageLoader.getImage(type.v).getWidth(), ImageLoader.getImage(type.v).getHeight());
		clicked = pressed = false;
	}

	@Override
	public void click() {
		clicked = true;
	}

	@Override
	public void release() {
		pressed = false;
	}

	@Override
	public void draw(Graphics g) {
		if(pressed) {
			RescaleOp op = new RescaleOp(.9f, 0, null);
			g.drawImage(op.filter(ImageLoader.getImage(type.v), null), (int)x, (int)y - 32, null);
		} else {
			g.drawImage(ImageLoader.getImage(type.v), (int)x, (int)y - 32, null);
		}
	}

	@Override
	public Rectangle2D.Double getBounds() {
		return bounds;
	}
	
	public MenuCompType getType() {
		return type;
	}
	
	public boolean getClicked() {
		return clicked;
	}
	
	public void setClicked(boolean b) {
		clicked = b;
	}

	@Override
	public void press() {
		pressed = true;
	}
	
	public void setPressed(boolean b) {
		pressed = b;
	}

}
