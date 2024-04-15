import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shape {
	private List<Square> squares;
	private Square firstsquare;
	private Square secondsquare;
	private Square thirdsquare;
	private Square fourthsquare;
	private int type;
	private int era;
	private Color color;
	private int speed;

	public Square getFirstSquare() {
		return firstsquare;
	}

	public Square getSecondSquare() {
		return secondsquare;
	}

	public Square getThirdSquare() {
		return thirdsquare;
	}

	public Square getFourthSquare() {
		return fourthsquare;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getType() {
		return type;
	}

	public int getEra() {
		return era;
	}

	public void setEra(int era) {
		this.era = era;
	}

	public Cell getFirstSquareCurrentCell() {
		Cell currentcell = null;
		int x = ((firstsquare.getX() - 11) / Constants.ShapeSpeed) + 1;
		int y = ((firstsquare.getY() - 6) / Constants.ShapeSpeed) + 1;
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == x && cell.gety() == y) {
				currentcell = cell;
			}
		}
		return currentcell;
	}

	public Cell getSecondSquareCurrentCell() {
		Cell currentcell = null;
		int x = ((secondsquare.getX() - 11) / Constants.ShapeSpeed) + 1;
		int y = ((secondsquare.getY() - 6) / Constants.ShapeSpeed) + 1;
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == x && cell.gety() == y) {
				currentcell = cell;
			}
		}
		return currentcell;
	}

	public Cell getThirdSquareCurrentCell() {
		Cell currentcell = null;
		int x = ((thirdsquare.getX() - 11) / Constants.ShapeSpeed) + 1;
		int y = ((thirdsquare.getY() - 6) / Constants.ShapeSpeed) + 1;
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == x && cell.gety() == y) {
				currentcell = cell;
			}
		}
		return currentcell;
	}

	public Cell getFourthSquareCurrentCell() {
		Cell currentcell = null;
		int x = ((fourthsquare.getX() - 11) / Constants.ShapeSpeed) + 1;
		int y = ((fourthsquare.getY() - 6) / Constants.ShapeSpeed) + 1;
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == x && cell.gety() == y) {
				currentcell = cell;
			}
		}
		return currentcell;
	}

	public Shape(int type, int era) {
		squares = new ArrayList<Square>();
		this.type = type;
		this.era = era;
		Initialize();
		setColor(RandomColor());
		setSpeed(Constants.ShapeSpeed);
	}

	public void move() {
		for (Square square : squares) {
			square.setY(square.getY() + speed);
		}
	}

	public int MinX() {
		int min = 1500;
		for (Square square : squares) {
			if (min > square.getX()) {
				min = square.getX();
			}
		}
		return min;
	}

	public int MaxX() {
		int max = -1500;
		for (Square square : squares) {
			if (max < square.getX()) {
				max = square.getX();
			}
		}
		return max;
	}

	public int MaxY() {
		int max = -1500;
		for (Square square : squares) {
			if (max < square.getY()) {
				max = square.getY();
			}
		}
		return max;
	}

	public Color RandomColor() {
		Random random = new Random();
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		return new Color(r, g, b);
	}

	public void Initialize() {
		firstsquare = new Square(Constants.ShapeX1, Constants.ShapeY1, Constants.ShapeSize);
		secondsquare = new Square(Constants.ShapeX1, Constants.ShapeY1, Constants.ShapeSize);
		thirdsquare = new Square(Constants.ShapeX1, Constants.ShapeY1, Constants.ShapeSize);
		fourthsquare = new Square(Constants.ShapeX1, Constants.ShapeY1, Constants.ShapeSize);

		switch (getType()) {
		case 0: {
			secondsquare.setY(firstsquare.getY() + secondsquare.getSize() + 1);
			thirdsquare.setY(secondsquare.getY() + thirdsquare.getSize() + 1);
			fourthsquare.setY(thirdsquare.getY() + thirdsquare.getSize() + 1);
			break;
		}
		case 1: {
			fourthsquare.setX(firstsquare.getX() - firstsquare.getSize() - 1);
			secondsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			thirdsquare.setY(secondsquare.getY() + secondsquare.getSize() + 1);
			fourthsquare.setY(thirdsquare.getY());
			break;
		}
		case 2: {
			fourthsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			secondsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			thirdsquare.setY(secondsquare.getY() + secondsquare.getSize() + 1);
			fourthsquare.setY(thirdsquare.getY());
			break;
		}
		case 3: {
			secondsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			fourthsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			thirdsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			fourthsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			break;
		}
		case 4: {
			secondsquare.setX(firstsquare.getX() - firstsquare.getSize() - 1);
			fourthsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			secondsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			thirdsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			fourthsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			break;
		}
		case 5: {
			secondsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			thirdsquare.setX(firstsquare.getX() - firstsquare.getSize() - 1);
			thirdsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			fourthsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			break;
		}
		case 6: {
			secondsquare.setX(firstsquare.getX() - firstsquare.getSize() - 1);
			fourthsquare.setX(firstsquare.getX() + firstsquare.getSize() + 1);
			thirdsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			fourthsquare.setY(firstsquare.getY() + firstsquare.getSize() + 1);
			break;
		}
		}
		squares.add(firstsquare);
		squares.add(secondsquare);
		squares.add(thirdsquare);
		squares.add(fourthsquare);
	}

	public void FixSell() {
		if (GameState.gamestate.getGameSize() == false) {
			for (Square square : squares) {
				int x = (int) ((square.getX() - 11) / 21) + 1;
				int y = (int) ((square.getY() - 6) / 21) + 1;
				for (Cell cell : GameState.gamestate.getGround()) {
					if (cell.getX() == x && cell.gety() == y) {
						square.setCell(cell);
						cell.setSquare(square);
					}
				}
			}
		} else {
			for (Square square : squares) {
				int x = (int) ((square.getX() - 11) / 31) + 1;
				int y = (int) ((square.getY() - 6) / 31) + 1;
				for (Cell cell : GameState.gamestate.getGround()) {
					if (cell.getX() == x && cell.gety() == y) {
						square.setCell(cell);
						cell.setSquare(square);
					}
				}
			}
		}
	}
}
