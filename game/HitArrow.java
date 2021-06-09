package game;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import game.ImageLoader.Visual;

public class HitArrow extends Component{
	
	private double theta;
	private boolean shot;

	public HitArrow(double x, double y) {
		super(x, y);
		theta = Math.PI / 4.0;
	}

	@Override
	public void draw(Graphics g) {
		//BufferedImage intermediate = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		BufferedImage bimg = ImageLoader.getImage(Visual.ARROW);
		//intermediate.getGraphics().drawImage(bimg, 48, 0, null);
		//Graphics2D g2 = (Graphics2D)intermediate.getGraphics();
		//g2.rotate(theta, 64, 64);
		//g.drawImage(intermediate, (int)x - 8, Main.HEIGHT - (int)y - 56, null);
		//g.drawImage(bimg, (int)x - 24, Main.HEIGHT - (int)y - 56, null);
		
		AffineTransform tx = AffineTransform.getRotateInstance(-1 * theta + Math.PI / 2.0, 32, 32);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		//System.out.println("Arrow Drawn @ " + ((int)x - 16) + ", " + (Main.HEIGHT - (int)y - 64));
		g.drawImage(op.filter(bimg, null), (int)x - 24 + (int)(32 * Math.cos(theta)), (int)y - 24 - (int)(32 * Math.sin(theta)), null);
	}
	
	public double getTheta() {
		return theta;
	}
	
	public boolean getShot() {
		return shot;
	}
	
	public void setShot(boolean shot) {
		this.shot = shot;
	}
	
	public void updateTheta() {
		if(Keys.left) {
			theta += 0.01;
		}
		if(Keys.right) {
			theta -= 0.01;
		}
		if(theta > Math.PI * 2) {
			theta -= Math.PI * 2;
		}
		else if(theta < 0) {
			theta += Math.PI * 2;
		}
		//System.out.println(theta);
	}
}
