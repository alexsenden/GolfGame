package game;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	public static BufferedImage ballsheet, backgrounds, arrow, quit, resume, pausemenu, hole, powerbar, icon, polyGradient;
	public static BufferedImage ball, background, arrows;
	
	public enum Visual {
		BACKGROUND, BALL, ARROW, RESUME, QUIT, PAUSEMENU, HOLE, POWERBAR, ICON;
	}
	
	public static BufferedImage getImage(Visual visual) {
		switch(visual) {
		case BALL:
			if(ball == null) {
				ball = ballsheet.getSubimage(0, 0, 16, 16);
			}
			return ball;
		case BACKGROUND:
			if(background == null) {
				background = backgrounds.getSubimage(0, 0, 1080, 608);
			}
			return background;
		case ARROW:
			if(arrow == null) {
				arrow = arrows.getSubimage(0, 0, 64, 64);
			}
			return arrow;
		case QUIT:
			return quit;
		case RESUME:
			return resume;
		case HOLE:
			return hole;
		case POWERBAR:
			return powerbar;
		case ICON:
			return icon;
		default:
			return null;
		}
	}
	
	public static void setVariant(Visual v, int variant) {
		int x, y, perr;
		switch(v) {
		case BALL:
			x = y = 16;
			perr = 4;
		case BACKGROUND:
			x = 1080;
			y = 608;
			perr = 1;
			break;
		case ARROW:
			x = 64;
			y = 64;
			perr = 1;
		default:
			return;
			
		}
		int count = 0;
		int county = 0;
		while(count < variant) {
			count++;
			if(count % perr == 0) {
				county++;
			}
		}
		switch(v) {
		case BACKGROUND:
			background = backgrounds.getSubimage(x * (count % perr), y * county, x, y);
			return;
		case BALL:
			ball = ballsheet.getSubimage(x * (count % perr), y * county, x, y);
			return;
		case ARROW:
			arrow = arrows.getSubimage(x * (count % perr), y * county, x, y);
			return;
		default:
			return;
		}
	}
	
	public static BufferedImage cutOutPolygon(Polygon p) { 
		BufferedImage bimg = new BufferedImage((int)p.getBounds().getWidth(), (int)p.getBounds().getHeight(), BufferedImage.TYPE_INT_ARGB);
		System.out.println(p.getBounds().height);
		System.out.println(p.getBounds().width);
		System.out.println(p.getBounds().getMaxX());
		System.out.println(p.getBounds().getMinX());
		System.out.println(p.getBounds().getMaxY());
		System.out.println(p.getBounds().getMinY());
		
		for(int y = (int)p.getBounds().getMinY(); y < p.getBounds().getMaxY(); y++) {
			for(int x = (int)p.getBounds().getMinX(); x < p.getBounds().getMaxX(); x++) {
				if(x >= 0 && x < Main.WIDTH && y >= 0 && y < Main.HEIGHT) {
					if(p.contains(x, y)) {
						bimg.setRGB(x - (int)p.getBounds().getMinX(), y - (int)p.getBounds().getMinY(), polyGradient.getRGB(x, y));
					}
				}
			}
		}
		return bimg;
	}
	
	public static void loadSpriteSheet() {
		try {
			backgrounds = ImageIO.read(new File("src/images/bkgd2.png"));
			ballsheet = ImageIO.read(new File("src/images/ballSpriteSheet.png"));
			arrows = ImageIO.read(new File("src/images/arrow.png"));
			quit = ImageIO.read(new File("src/images/quit.png"));
			resume = ImageIO.read(new File("src/images/resume.png"));
			hole = ImageIO.read(new File("src/images/hole.png"));
			powerbar = ImageIO.read(new File("src/images/powerbar.png"));
			icon = ImageIO.read(new File("src/images/icon2.png"));
			polyGradient = ImageIO.read(new File("src/images/polyGradient3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}
