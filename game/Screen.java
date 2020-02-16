package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Screen extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static int xshift, yshift;
	private int deltax, deltay;
	private BufferedImage background;
	
	protected ArrayList<Component> components = new ArrayList<Component>();

	public Screen(BufferedImage background) {
		this.background = background;
	}
	
	public void redraw() {
		paintComponent(this.getGraphics());
	}
	
	public void add(Component c) {
		components.add(c);
	}
	
	public void moveComponents() {
		for(Component c : components) {
			c.move(deltax, deltay);
		}
	}
	
	public BufferedImage bufferFrame() {
		BufferedImage bimg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bimg.getGraphics();
		
		g.drawImage(background, 0, 0, this);
		
		for(Component c : components) {
			c.draw(g);
		}
		
		return bimg;
	}
	
	public void paintComponent(Graphics g) {
		BufferedImage bimg = bufferFrame();
		g.drawImage(bimg, 0, 0, this);
	}
}
