package game;

import javax.swing.JFrame;

import game.ImageLoader.Visual;

public class Main {
	
	public static final int FPS = 60;
	public static final int TPS = 650;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 608;
	
	public static JFrame f = new JFrame("Superb Stickman Golf");
	
	public static void main(String[] args) {
		ImageLoader.loadSpriteSheet();
		f.setBounds(-10, 0, WIDTH, HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setFocusable(true);
		f.requestFocus();
		f.addKeyListener(new Keys());
		f.addMouseListener(new Mouse());
		f.setIconImage(ImageLoader.getImage(Visual.ICON));
		f.setVisible(true);
		
		playLevel(2);
	}
	
	public static void setScreen(Screen screen) {
		f.add((Level)screen);
		f.setVisible(true);
	}
	
	public static void playLevel(int id) {
		int tps = TPS;
		Level l = new Level(id);
		setScreen(l);
		boolean active = true;
		
		long lastTimeTick = System.nanoTime();
		long lastTimeFrame = System.nanoTime();
        long ctime;
        
        int ticks = 0;
        int frames = 0;
        
        long lastTimeMillis = System.currentTimeMillis();
        long ctimeMillis;
        while(active) {
        	if(Keys.ctrl) {
        		tps = TPS * 2; 
        	} else {
        		tps = TPS;
        	}
        	
            ctime = System.nanoTime();
            
            if(ctime - lastTimeTick > 1000000000.0 / tps) {
            //if(true) {
                lastTimeTick = ctime;
        		l.tick();
                ticks++;
            }
            
            if(ctime - lastTimeFrame > 1000000000.0 / FPS) {
                lastTimeFrame = ctime;
                l.redraw();
                frames++;
            }
            
            ctimeMillis = System.currentTimeMillis();
            if(ctimeMillis - lastTimeMillis > 1000) {
                lastTimeMillis = ctimeMillis;
            	//System.out.println("FPS: " + frames);
            	//System.out.println("TPS: " + ticks);
                ticks = frames = 0;
            }
        }
	}
}
