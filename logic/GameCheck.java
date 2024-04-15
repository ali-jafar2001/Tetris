import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GameCheck {
	public static GameCheck gamecheck;
	private boolean gamestart;

	public boolean getGameStart() {
		return gamestart;
	}

	public void SetGameStart(boolean gamestart) {
		this.gamestart = gamestart;
	}

	public static void GetInstance() {
		if (gamecheck == null)
			gamecheck = new GameCheck();
	}

	public void CheckEndGame() throws IOException {
		List<Cell> EndCells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == 5 && cell.gety() == 2)
				EndCells.add(cell);
			if (cell.getX() == 5 && cell.gety() == 1)
				EndCells.add(cell);
			if (cell.getX() == 6 && cell.gety() == 1)
				EndCells.add(cell);
			if (cell.getX() == 6 && cell.gety() == 2)
				EndCells.add(cell);
		}
		for (Cell cell : EndCells) {
			if (cell.getSquare() != null) {
				for (Cell c : GameState.gamestate.getGround()) {
					c.setSquare(null);
				}
				GameState.gamestate.getScores().add(GameState.gamestate.getScore());
				String name = JOptionPane.showInputDialog(Main.mainframe, "Enter your name :)))", "Save",
						JOptionPane.OK_CANCEL_OPTION | JOptionPane.QUESTION_MESSAGE);
				if (name != null) {
					new Player(name, GameState.gamestate.getScore(), GameState.gamestate.getLines());
				}
				GameState.gamestate.setLevel(0);
				GameState.gamestate.setScore(0);
				GameState.gamestate.setLines(0);
				GameState.gamestate.setCurrentShape(null);
				GameState.gamestate.setNextShape(null);
				GameState.gamestate.getCurrentShapes().clear();
				GameCheck.gamecheck.SetGameStart(false);
				break;
			}
		}
	}

	public void CheckLines() {
		for (int i = 20; i > 0; i--) {
			boolean line = true;
			for (Cell cell : GameState.gamestate.getGround()) {
				if (cell.gety() == i && cell.getSquare() == null) {
					line = false;
				}
			}
			if (line == true) {
				GameState.gamestate.setLines(GameState.gamestate.getLines() + 1);
				GameState.gamestate.setScore(GameState.gamestate.getScore() + 10);
				List<Square> Deletesquares = new ArrayList<Square>();
				for (Cell cell : GameState.gamestate.getGround()) {
					cell.setSquare(null);
				}
				for (Shape shape : GameState.gamestate.getCurrentShapes()) {
					for (Square square : shape.getSquares()) {
						if (square.getCell().gety() == i) {
							Deletesquares.add(square);
						}
					}
				}
				for (Shape shape : GameState.gamestate.getCurrentShapes()) {
					for (Square s : Deletesquares) {
						if (shape.getSquares().contains(s)) {
							shape.getSquares().remove(s);
						}
					}
				}
				for (Shape shape : GameState.gamestate.getCurrentShapes()) {
					for (Square square : shape.getSquares()) {
						if (square.getCell().gety() < i) {
							square.setY(square.getY() + Constants.ShapeSpeed);
						}
					}
				}
				for (Shape shape : GameState.gamestate.getCurrentShapes()) {
					for (Square square : shape.getSquares()) {
						int x = ((square.getX() - 11) / Constants.ShapeSpeed) + 1;
						int y = ((square.getY() - 6) / Constants.ShapeSpeed) + 1;
						for (Cell cell : GameState.gamestate.getGround()) {
							if (cell.getX() == x && cell.gety() == y) {
								square.setCell(cell);
								cell.setSquare(square);
							}
						}
					}
				}
				i++;
			}
		}

	}

	public void Jump() {
		if (GameState.gamestate.getCurrentShape() != null) {
			List<Cell> jumpcells = new ArrayList<Cell>();
			List<Cell> shapecells = new ArrayList<Cell>();
			shapecells.add(GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell());
			shapecells.add(GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell());
			shapecells.add(GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell());
			shapecells.add(GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell());
			for (Cell cell : GameState.gamestate.getGround()) {
				for (Cell c : shapecells) {
					if (cell.getX() == c.getX() && cell.gety() > c.gety() && cell.getSquare() != null) {
						jumpcells.add(cell);
					}
				}
			}
			if (jumpcells.size() == 0) {
				int y = -220;
				for (Cell cell : shapecells) {
					if (y < cell.gety()) {
						y = cell.gety();
					}
				}
				y = 20 - y;
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					square.setY(square.getY() + (y - 1) * Constants.ShapeSpeed);
				}
			} else {
				List<Integer> dis = new ArrayList<Integer>();
				for (Cell cell : shapecells) {
					for (Cell c : jumpcells) {
						if (c.getX() == cell.getX()) {
							dis.add(c.gety() - cell.gety());
						}
					}
				}
				int y = 220;
				for (int num : dis) {
					if (y > num) {
						y = num;
					}
				}
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					square.setY(square.getY() + (y - 2) * Constants.ShapeSpeed);
				}
			}
		}

	}

	public void strikeDownBorder() {
		if (GameState.gamestate.getCurrentShape().MaxY() >= Constants.eny - Constants.ShapeSize - 5) {
			GameState.gamestate.getCurrentShape().setSpeed(0);
			GameState.gamestate.getCurrentShape().FixSell();
			GameState.gamestate.getCurrentShapes().add(GameState.gamestate.getCurrentShape());
			GameState.gamestate.setCurrentShape(GameState.gamestate.getNextShape());
			GameState.gamestate.setLevel(GameState.gamestate.getLevel() + 1);
			GameState.gamestate.setScore(GameState.gamestate.getScore() + 1);
			GameState.gamestate.setNextShape(null);
		}
	}

	public void strikeOtherShape() {
		boolean strike = false;
		for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
			for (Shape shape : GameState.gamestate.getCurrentShapes()) {
				for (Square anothersquare : shape.getSquares()) {
					if (square.getX() == anothersquare.getX()
							&& square.getY() + square.getSize() >= anothersquare.getY() - 5) {
						strike = true;
					}
				}
			}
		}
		if (strike == true) {
			GameState.gamestate.getCurrentShape().setSpeed(0);
			GameState.gamestate.setLevel(GameState.gamestate.getLevel() + 1);
			GameState.gamestate.setScore(GameState.gamestate.getScore() + 1);
			GameState.gamestate.getCurrentShape().FixSell();
			GameState.gamestate.getCurrentShapes().add(GameState.gamestate.getCurrentShape());
			GameState.gamestate.setCurrentShape(GameState.gamestate.getNextShape());
			GameState.gamestate.setNextShape(null);
		}
	}

	public void MoveLeft() {
		if (GameState.gamestate.getCurrentShape() != null) {
			boolean left = true;
			List<Cell> LeftCells = new ArrayList<Cell>();
			for (Cell cell : GameState.gamestate.getGround()) {
				if (cell.getX() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().getX() - 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().gety())
					LeftCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() - 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety())
					LeftCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() - 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety())
					LeftCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().getX() - 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().gety())
					LeftCells.add(cell);
			}
			for (Cell cell : LeftCells) {
				if (cell.getSquare() != null) {
					left = false;
				}
			}
			if (left == true && GameState.gamestate.getCurrentShape().MinX() > Constants.stx + 10) {
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					square.setX(square.getX() - Constants.ShapeSpeed);
				}
			}
		}
	}

	public void Reset() {
		GameState.gamestate.getCurrentShapes().clear();
		GameState.gamestate.setScore(0);
		GameState.gamestate.setLevel(0);
		GameState.gamestate.setLines(0);
		GameState.gamestate.setCurrentShape(null);
		GameState.gamestate.setNextShape(null);
		for (Cell cell : GameState.gamestate.getGround()) {
			cell.setSquare(null);
		}
		GameCheck.gamecheck.SetGameStart(true);
	}

	public void MoveRight() {
		if (GameState.gamestate.getCurrentShape() != null) {
			boolean right = true;
			List<Cell> RightCells = new ArrayList<Cell>();
			for (Cell cell : GameState.gamestate.getGround()) {
				if (cell.getX() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().getX() + 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().gety())
					RightCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() + 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety())
					RightCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety())
					RightCells.add(cell);
				if (cell.getX() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().getX() + 1
						&& cell.gety() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().gety())
					RightCells.add(cell);
			}
			for (Cell cell : RightCells) {
				if (cell.getSquare() != null) {
					right = false;
				}
			}
			if (right == true && GameState.gamestate.getCurrentShape().MaxX() < Constants.enx - 35) {
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					square.setX(square.getX() + Constants.ShapeSpeed);
				}
			}
		}
	}

	public void ComeBack() {
		if (GameState.gamestate.getCurrentShape() != null) {
			switch (GameState.gamestate.getCurrentShape().getType()) {
			case 0: {
				ComeBackWood();
				break;
			}
			case 1: {
				ComeBackLeftFoot();
				break;
			}
			case 2: {
				ComeBackRightFoot();
				break;
			}
			case 3: {
				ComeBackWindow();
				break;
			}
			case 4: {
				ComeBackMountain();
				break;
			}
			case 5: {
				ComeBackRightDuck();
				break;
			}
			case 6: {
				ComeBackLeftDuck();
				break;
			}
			}
		}
	}

	private void ComeBackWood() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getSecondSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + 2 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + 3 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackLeftFoot() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getSecondSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + 2 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1 - Constants.ShapeSize - 1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + 2 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackRightFoot() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getSecondSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + 2 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + 2 * (Constants.ShapeSize + 1) - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackWindow() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getSecondSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackMountain() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1 - Constants.ShapeSize - 1);
		GameState.gamestate.getCurrentShape().getSecondSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackRightDuck() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getSecondSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1 - Constants.ShapeSize - 1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	private void ComeBackLeftDuck() {
		GameState.gamestate.getCurrentShape().getFirstSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getFirstSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getSecondSquare().setX(Constants.ShapeX1 - Constants.ShapeSize - 1);
		GameState.gamestate.getCurrentShape().getSecondSquare().setY(Constants.ShapeY1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getThirdSquare().setX(Constants.ShapeX1);
		GameState.gamestate.getCurrentShape().getThirdSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().getFourthSquare().setX(Constants.ShapeX1 + Constants.ShapeSize + 1);
		GameState.gamestate.getCurrentShape().getFourthSquare()
				.setY(Constants.ShapeY1 + Constants.ShapeSize + 1 - Constants.ShapeSpeed);
		GameState.gamestate.getCurrentShape().setEra(0);
	}

	public void Rotate() {
		if (GameState.gamestate.getCurrentShape() != null) {
			switch (GameState.gamestate.getCurrentShape().getType()) {
			case 0: {
				RotateWood();
				break;
			}
			case 1: {
				RotateLeftFoot();
				break;
			}
			case 2: {
				RotateRightFoot();
				break;
			}
			case 4: {
				RotateMountain();
				break;
			}
			case 5: {
				RotateRightDuck();
				break;
			}
			case 6: {
				RotateLeftDuck();
				break;
			}
			}
		}
	}

	private void RotateLeftDuck() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateLeftDuck();
			break;
		}
		case 1: {
			SecondRotateLeftDuck();
			break;
		}
		}

	}

	private void SecondRotateLeftDuck() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}

	}

	private void FirstRotateLeftDuck() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(1);
		}

	}

	private void RotateRightDuck() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateRightDuck();
			break;
		}
		case 1: {
			SecondRotateRightDuck();
			break;
		}
		}

	}

	private void SecondRotateRightDuck() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() + 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}
	}

	private void FirstRotateRightDuck() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(1);
		}
	}

	private void RotateMountain() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateMountain();
			break;
		}
		case 1: {
			SecondRotateMountain();
			break;
		}
		case 2: {
			ThirdRotateMountain();
			break;
		}
		case 3: {
			FourthRotateMountain();
			break;
		}
		}

	}

	private void FourthRotateMountain() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() + 2
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety())
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 1) {
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}
	}

	private void ThirdRotateMountain() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 1) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(3);
		}

	}

	private void SecondRotateMountain() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() - 1
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety())
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 1) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(2);
		}

	}

	private void FirstRotateMountain() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 1) {
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(1);
		}
	}

	private void RotateRightFoot() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateRightFoot();
			break;
		}
		case 1: {
			SecondRotateRightFoot();
			break;
		}
		case 2: {
			ThirdRotateRightFoot();
			break;
		}
		case 3: {
			FourthRotateRightFoot();
			break;
		}
		}

	}

	private void FourthRotateRightFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().gety() + 1
					&& cell.getX() == GameState.gamestate.getCurrentShape().getFourthSquareCurrentCell().getX() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}
	}

	private void ThirdRotateRightFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() - 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() - 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(3);
		}

	}

	private void SecondRotateRightFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 2
					&& cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX())
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() + (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(2);
		}

	}

	private void FirstRotateRightFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + 2 * Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(1);
		}

	}

	private void RotateLeftFoot() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateLeftFoot();
			break;
		}
		case 1: {
			SecondRotateLeftFoot();
			break;
		}
		case 2: {
			ThirdRotateLeftFoot();
			break;
		}
		case 3: {
			FourthRotateLeftFoot();
			break;
		}
		}

	}

	private void FourthRotateLeftFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}
	}

	private void ThirdRotateLeftFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() - 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(3);
		}
	}

	private void SecondRotateLeftFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.gety() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getFirstSquareCurrentCell().getX() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().setEra(2);
		}
	}

	private void FirstRotateLeftFoot() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX() - 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 2) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().setEra(1);
		}
	}

	private void RotateWood() {
		switch (GameState.gamestate.getCurrentShape().getEra()) {
		case 0: {
			FirstRotateWood();
			break;
		}
		case 1: {
			SecondRotateWood();
			break;
		}
		case 2: {
			ThirdRotateWood();
			break;
		}
		case 3: {
			FourthRotateWood();
			break;
		}
		}

	}

	private void FourthRotateWood() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() - 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 3) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(0);
		}
	}

	private void ThirdRotateWood() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() - 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() - 2)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 3) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setX(GameState.gamestate.getCurrentShape().getSecondSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(3);
		}

	}

	private void SecondRotateWood() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() - 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 1)
				rotatecells.add(cell);
			if (cell.getX() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().getX()
					&& cell.gety() == GameState.gamestate.getCurrentShape().getSecondSquareCurrentCell().gety() + 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 3) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() - Constants.ShapeSpeed);

			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setY(GameState.gamestate.getCurrentShape().getThirdSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() - 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().setEra(2);
		}

	}

	private void FirstRotateWood() {
		boolean rotate = true;
		List<Cell> rotatecells = new ArrayList<Cell>();
		for (Cell cell : GameState.gamestate.getGround()) {
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() - 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 1)
				rotatecells.add(cell);
			if (cell.gety() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().gety()
					&& cell.getX() == GameState.gamestate.getCurrentShape().getThirdSquareCurrentCell().getX() + 2)
				rotatecells.add(cell);
		}
		for (Cell cell : rotatecells) {
			if (cell.getSquare() != null) {
				rotate = false;
			}
		}
		if (rotate == true && rotatecells.size() == 3) {
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setX(GameState.gamestate.getCurrentShape().getFirstSquare().getX() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFirstSquare()
					.setY(GameState.gamestate.getCurrentShape().getFirstSquare().getY() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getSecondSquare()
					.setY(GameState.gamestate.getCurrentShape().getSecondSquare().getY() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getThirdSquare()
					.setX(GameState.gamestate.getCurrentShape().getThirdSquare().getX() + Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setX(GameState.gamestate.getCurrentShape().getFourthSquare().getX() + 2 * (Constants.ShapeSpeed));
			GameState.gamestate.getCurrentShape().getFourthSquare()
					.setY(GameState.gamestate.getCurrentShape().getFourthSquare().getY() - Constants.ShapeSpeed);
			GameState.gamestate.getCurrentShape().setEra(1);
		}
	}
}