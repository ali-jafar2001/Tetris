import java.util.ArrayList;
import java.util.List;

public class GameState {
	private List<Shape> Currentshapes;

	public static GameState gamestate;
	private Shape CurrentShape;
	private Shape NextShape;
	private ShapeFactory shapefactory;
	private List<Cell> ground;
	private boolean gamesize;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public List<Integer> getScores() {
		return scores;
	}

	private boolean pause;
	private int level;
	private int score;
	private int lines;
	private List<Integer> scores;

	public boolean getPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean getGameSize() {
		return gamesize;
	}

	public void setGameSize(boolean gamesize) {
		this.gamesize = gamesize;
	}

	public List<Cell> getGround() {
		return ground;
	}

	public Shape getCurrentShape() {
		return CurrentShape;
	}

	public Shape getNextShape() {
		return NextShape;
	}

	public void setCurrentShape(Shape shape) {
		this.CurrentShape = shape;
	}

	public void setNextShape(Shape shape) {
		this.NextShape = shape;
	}

	private GameState() {
		scores = new ArrayList<Integer>();
		scores.add(0);
		Currentshapes = new ArrayList<Shape>();
		ground = new ArrayList<Cell>();
		shapefactory = new ShapeFactory();
		CreateGround();
	}

	private void CreateGround() {
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 21; j++) {
				Cell cell = new Cell(i, j);
				ground.add(cell);
			}
		}
	}

	public List<Shape> getCurrentShapes() {
		return Currentshapes;
	}

	public static void getInstance() {
		if (gamestate == null)
			gamestate = new GameState();
	}

	public void MakeCurrentShape() {
		if (CurrentShape == null)
			CurrentShape = shapefactory.CreateShape();
	}

	public void MakeNextShape() {
		if (NextShape == null)
			NextShape = shapefactory.CreateShape();
	}

}
