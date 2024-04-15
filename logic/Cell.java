
public class Cell {
	private int x;
	private int y;
	private Square square;

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int gety() {
		return y;
	}
}
