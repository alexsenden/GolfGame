package game;

public class SSGUtils {
	public static int sgn(double x) {
		if(x < 0) {
			return -1;
		}
		return 1;
	}
	
	public static int[] getTerms(String s) {
		return getTerms(s, -1, 0, new int[getNumTerms(s, -1, 0)]);
		
	}
	
	private static int[] getTerms(String s, int index, int total, int[] array) {
		if(s.indexOf(' ', index + 1) >= 0) {
			array[total] = Integer.parseInt(s.substring(index + 1, s.indexOf(' ', index + 1)));
			total++;
			index = s.indexOf(' ', index + 1);
			return getTerms(s, index, total, array);
		} else { 
			//array[array.length - 1] = Integer.parseInt(s.substring(index + 1));
			for(int i : array) {
				System.out.println(i);
			}
			return array;
		}
	}
	
	private static int getNumTerms(String s, int index, int total) {
		//System.out.println(s + " " + (index + 1) + " " + s.indexOf(' ', index + 1));
		if(s.indexOf(' ', index + 1) < 0) {
			return total;
		} else {
			return getNumTerms(s, s.indexOf(' ', index + 1), total + 1);
		}
	}
	
	public static int[][] getCoordsForPolygon(int[] array) {
		int[] xpoints = new int[array.length / 4];
		int[] ypoints = new int[array.length / 4];
		for(int i = 0; i < array.length; i++) {
			if(i % 4 == 0) {
				xpoints[i / 4] = array[i];
			}
			else if(i % 4 == 1) {
				ypoints[i / 4] = array[i];
			}
		}
		return new int[][] {xpoints, ypoints};
	}
}
