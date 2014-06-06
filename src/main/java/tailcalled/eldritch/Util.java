package tailcalled.eldritch;

public class Util {
	
	private Util() {}

	public static int dx(int side) {
		switch (side) {
		case 0:
		case 1:
		case 2:
		case 3:
			return  0;
		case 4:
			return -1;
		case 5:
			return  1;
		default:
			throw new RuntimeException();
		}
	}
	public static int dy(int side) {
		switch (side) {
		case 0:
			return -1;
		case 1:
			return 1;
		case 2:
		case 3:
		case 4:
		case 5:
			return  0;
		default:
			throw new RuntimeException();
		}
	}
	public static int dz(int side) {
		switch (side) {
		case 0:
		case 1:
			return  0;
		case 2:
			return  1;
		case 3:
			return -1;
		case 4:
		case 5:
			return  0;
		default:
			throw new RuntimeException();
		}
	}
	public static int side(int dx, int dy, int dz) {
		assert (dx+dy+dz)*(dx+dy+dz) == 1;
		if (dx != 0) {
			return 4 + (dx+1)/2;
		}
		if (dy != 0) {
			return (dy+1)/2;
		}
//		if (dz != 0) {
			return 2 + (dz+1)/2;
//		}
	}
	public static int opposite(int side) {
		return side(-dx(side), -dy(side), -dz(side));
	}
	
}
