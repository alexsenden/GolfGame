package game;

import java.awt.geom.Rectangle2D;

public interface Clickable {
	public abstract void click();
	public abstract Rectangle2D.Double getBounds();
}
