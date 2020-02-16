package game;

import java.awt.geom.Rectangle2D;

public interface Pressable {
	public abstract void press();
	public abstract void release();
	public abstract Rectangle2D.Double getBounds();
}
