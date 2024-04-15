import java.util.Random;

public class ShapeFactory {
	private Random random;

	public ShapeFactory() {
		random = new Random(System.nanoTime());
	}

	public Shape CreateShape() {
		int type = random.nextInt(7);
		return new Shape(type, 0);
	}
}
