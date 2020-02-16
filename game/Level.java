package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import game.ImageLoader.Visual;

public class Level extends Screen {

	private static final long serialVersionUID = 734288568087462657L;
	private final int ID;
	private Ball ball;
	private HitArrow arrow;
	private boolean paused;
	private PauseMenu pausemenu;
	private int spawnx, spawny;
	private PowerBar powerbar;
	private Hole hole;

	public Level(int id) {
		super(ImageLoader.getImage(Visual.BACKGROUND));
		this.ID = id;
		ImageLoader.setVariant(Visual.BACKGROUND, 0);
		paused = false;
		pausemenu = new PauseMenu();
		
		Scanner s = null;
		try {
			s = new Scanner(new File("src/levels/level" + ID + ".golflevel"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		spawnx = s.nextInt();
		spawny = s.nextInt() + 56;
		ball = new Ball(spawnx, spawny);
		components.add(ball);
		hole = new Hole(s.nextInt(), s.nextInt());
		components.add(hole);
		int i1, i2, i3, i4;
		while(s.hasNext()) {
			i1 = s.nextInt();
			i2 = s.nextInt();
			i3 = s.nextInt();
			i4 = s.nextInt();
			System.out.println(i1 + " " + i2 + " " + i3 + " " + i4);
			components.add(new LevelGeometry(i1, i2 + 56, i3, i4 + 56));
		}
		powerbar = new PowerBar();
		components.add(powerbar);
		/*
		if(id == 0) {
			components.add(new LevelGeometry(0, 56, 0, 624));
			components.add(new LevelGeometry(0, 56, 1080, 56));
			components.add(new LevelGeometry(1080, 624, 0, 624));
			components.add(new LevelGeometry(1080, 624, 1080, 56));
			components.add(new LevelGeometry(0, 200, 300, 100));
			components.add(new LevelGeometry(300, 100, 400, 150));
			components.add(new LevelGeometry(700, 200, 800, 150));
			components.add(new LevelGeometry(800, 150, 900, 200));
			ball = new Ball(75, 250);
			components.add(ball);
			*/
	}
	
	public void addHitArrow() {
		arrow = new HitArrow(ball.getLocation().getX(), ball.getLocation().getY());
		components.add(arrow);
	}
	
	public void removeHitArrow() {
		components.remove(components.indexOf(arrow));
		arrow = null;
	}
	
	public void pause() {
		paused = true;
		components.add(pausemenu);
	}
	
	public void unpause() {
		paused = false;
		components.remove(pausemenu);
	}
	
	public void tick() {
		if(!paused) {
			if(hole.getBounds().contains(ball.getBounds())) {
				System.exit(0);
			}
			if(ball.getStalled()) {
				if(arrow == null) {
					addHitArrow();
					powerbar.show();
					//System.out.println("Arrow Added");
				}
				arrow.updateTheta();
				powerbar.tick();
				if(Keys.space) {
					arrow.setShot(true);
					//System.out.println("Hitting at angle " + arrow.getTheta());
					ball.hit(arrow.getTheta(), 3 * powerbar.getPower());
					powerbar.hide();
					removeHitArrow();
				}
			} else {
				if(ball.isAlive()) {
					ball.move();
					ball.applyForces();
					ball.checkCollision(components);
					ball.checkStalled();
				} else {
					components.remove(ball);
					ball = new Ball(spawnx, spawny);
					components.add(ball);
				}
			}
			if(Keys.p) {
				pause();
			}
		} else {
			for(MenuComponent c : pausemenu.components){
				if(c.getBounds().contains(Mouse.getPressedPos())){
		        	c.press();
		        	//System.out.println(c.getBounds().x + " " + c.getBounds().y + " " + (c.getBounds().width + c.getBounds().x) + " " + (c.getBounds().y + c.getBounds().height));
		        }
				else {
					c.release();
				}
			}
			if(System.currentTimeMillis() - Mouse.getClickTime() < 10) {
			    for(MenuComponent c : pausemenu.components){
			        if(c.getBounds().contains(Mouse.getClickPos())){
			        	c.click();
			        	//System.out.println(c.getBounds().x + " " + c.getBounds().y + " " + (c.getBounds().width + c.getBounds().x) + " " + (c.getBounds().y + c.getBounds().height));
			        }
			    }
			}
			pausemenu.tick();
			if(!pausemenu.getActive()) {
				unpause();
				pausemenu.reset();
			}
		}
	}
	
	public boolean getStalled() {
		return ball.getStalled();
	}
}
