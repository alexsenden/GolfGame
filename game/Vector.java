package game;

public class Vector {
	
	private final double DAMPENING = 0.8;
	private final double G = -0.005;
	
	private double x, y;
	private boolean stalled;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void reflect(double slope) {
		if(slope < 0.0001 && slope > -0.0001) {
			slope = 0;
		}
		double theta = getTheta();
		if(theta < 0) {
			theta += Math.PI;
		} else {
			theta -= Math.PI;
		}
		//System.out.println("theta " + theta);
		double slopetheta = Math.atan2(slope, 1);
		//System.out.println("slopetheta " + slopetheta);
		double outdeltatheta = Math.abs(slopetheta - theta);
		//System.out.println("outdeltatheta " + outdeltatheta);
		//System.out.println("PI / 2        " + Math.PI / 2.0);
		//System.out.println(outdeltatheta > Math.PI / 2.0);
		if(outdeltatheta > Math.PI / 2.0) {
			slopetheta += Math.PI;
			outdeltatheta = Math.abs(slopetheta - theta);
		}
		if(outdeltatheta > Math.PI / 2.0) {
			slopetheta += Math.PI;
			outdeltatheta = Math.abs(slopetheta - theta);
		}
		if(outdeltatheta > Math.PI / 2.0) {
			slopetheta -= 3 * Math.PI;
			outdeltatheta = Math.abs(slopetheta - theta);
		}
		if(outdeltatheta > Math.PI / 2.0) {
			slopetheta -= Math.PI;
			outdeltatheta = Math.abs(slopetheta - theta);
		}
		double indeltatheta = Math.PI / 2.0 - outdeltatheta;
		double newTheta;
		if(theta > slopetheta) {
			newTheta = theta + indeltatheta * 2;
		} else {
			newTheta = theta - indeltatheta * 2;
		}
		//System.out.println("newTheta " + newTheta);
		//System.out.println("theta " + theta);
		double hypotenuse = DAMPENING * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		x = hypotenuse * Math.cos(newTheta);
		y = hypotenuse * Math.sin(newTheta);
		
		if(x < 0.05 && hypotenuse < 0.05) {
			x = 0;
		}
		if(y < 0.05 && hypotenuse < 0.05) {
			y = 0;
		}
		if(y == 0 && x == 0) {
			stalled = true;
		}
	}
	
	public void checkStalled() {
		if(y == 0 && x == 0) {
			stalled = true;
		}
	}
	
	public double getTheta() {
		return Math.atan2(y, x);
	}
	
	public void applyFriction(double coefficient) {
		double hypotenuse = (1 - coefficient) * Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double theta = getTheta();
		x = hypotenuse * Math.cos(theta);
		y = hypotenuse * Math.sin(theta);
	}
	
	public void applyGravity() {
		//System.out.println(x + ", " + y);
		y -= G;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public boolean getStalled() {
		return stalled;
	}
}
