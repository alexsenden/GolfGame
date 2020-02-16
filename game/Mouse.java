package game;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    private static long clicktime;
    private static int clickx, clicky, pressedx, pressedy;
    
    public Mouse(){
        clickx = clicky = (int) (clicktime = 0);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
    	clickx = e.getXOnScreen();
    	clicky = e.getYOnScreen();
    	clicktime = System.currentTimeMillis();
        //System.out.println("X: " + clickx + " Y: " + clicky);
    }
    
    public static Point getClickPos(){
        return new Point(clickx, clicky);
    }
    
    public static long getClickTime(){
        return clicktime;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	pressedx = e.getXOnScreen();
    	pressedy = e.getYOnScreen();
    }
    
    public static Point getPressedPos(){
        return new Point(pressedx, pressedy);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    	pressedx = -100;
    	pressedy = -100;
    }
}
