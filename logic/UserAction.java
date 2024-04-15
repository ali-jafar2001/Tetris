import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class UserAction implements KeyListener {

	public static void HighScores(MainFrame frame) {
		frame.getHighScores().addActionListener(new ActionListener() {

			@Override
	public void actionPerformed(ActionEvent arg0) {
				try {
					new ScoreFrame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static void Exit(MainFrame frame) {
		frame.getExit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				System.exit(0);
			}
		});
	}

	public static void Pause(MainFrame frame) {
		frame.getPause().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (GameState.gamestate.getPause() == true) {
					if (GameState.gamestate.getCurrentShape() != null) {
						GameState.gamestate.getCurrentShape().setSpeed(Constants.ShapeSpeed);
						GameState.gamestate.setPause(false);
					}
				} else {
					if (GameState.gamestate.getCurrentShape() != null) {
						GameState.gamestate.getCurrentShape().setSpeed(0);
						GameState.gamestate.setPause(true);
					}
				}

			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: {
			if (GameState.gamestate.getPause() == false)
				GameCheck.gamecheck.Rotate();
			break;
		}
		case KeyEvent.VK_DOWN: {
			if (GameState.gamestate.getPause() == false)
				GameCheck.gamecheck.Jump();
			break;
		}
		case KeyEvent.VK_RIGHT: {
			if (GameState.gamestate.getPause() == false)
				GameCheck.gamecheck.MoveRight();
			break;
		}
		case KeyEvent.VK_LEFT: {
			if (GameState.gamestate.getPause() == false)
				GameCheck.gamecheck.MoveLeft();
			break;
		}
		case KeyEvent.VK_SPACE: {
			if (GameState.gamestate.getPause() == false)
				GameCheck.gamecheck.ComeBack();
			break;
		}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void Size(MainFrame frame) {
		frame.getSmall().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSize(new Dimension(Constants.MinLength, Constants.MinWidth));
				frame.setLocationRelativeTo(null);
				SetFrameSmallSize();
				SetLabelsSmallSize(frame);
				SetShapesSmall();
				GameState.gamestate.setGameSize(false);
				GameState.gamestate.setPause(false);
			}
		});
		frame.getBig().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setSize(new Dimension(Constants.MaxLength, Constants.MaxWidth));
				frame.setLocationRelativeTo(null);
				SetFrameBigSize();
				SetLabelsBigSize(frame);
				SetShapesBig();
				GameState.gamestate.setGameSize(true);
				GameState.gamestate.setPause(false);
			}
		});
	}

	public static void NewGame(MainFrame frame) {
		frame.getNewItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameCheck.gamecheck.Reset();
			}
		});
	}

	private static void SetFrameSmallSize() {
		Constants.stx = 10;
		Constants.sty = 5;
		Constants.enx = 221;
		Constants.eny = 426;
		Constants.ShapeX1 = 95;
		Constants.ShapeY1 = 6;
		Constants.ShapeSize = 20;
		Constants.ShapeSpeed = 21;
	}

	private static void SetFrameBigSize() {
		Constants.stx = 10;
		Constants.sty = 5;
		Constants.enx = 321;
		Constants.eny = 626;
		Constants.ShapeX1 = 135;
		Constants.ShapeY1 = 6;
		Constants.ShapeSize = 30;
		Constants.ShapeSpeed = 31;
	}

	private static void SetLabelsSmallSize(MainFrame frame) {
		frame.getScoreText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 20);
		frame.getScore().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 20,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 40);
		frame.getLevelText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 40,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 60);
		frame.getLevel().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 60,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 80);
		frame.getLinesText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 80,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 100);
		frame.getLines().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 100,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 120);
		frame.getHighScoresText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 120,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 140);
		frame.getHighScore().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 140,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 160);
		frame.getNextText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 160,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 180);
	}

	private static void SetLabelsBigSize(MainFrame frame) {
		frame.getScoreText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 7,
				frame.getBounds().width - 50, frame.getBounds().height / 7 + 20);
		frame.getScore().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 7 + 20,
				frame.getBounds().width - 30, frame.getBounds().height / 7 + 40);
		frame.getLevelText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 7 + 40,
				frame.getBounds().width - 50, frame.getBounds().height / 7 + 60);
		frame.getLevel().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 7 + 60,
				frame.getBounds().width - 30, frame.getBounds().height / 7 + 80);
		frame.getLinesText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 7 + 80,
				frame.getBounds().width - 50, frame.getBounds().height / 7 + 100);
		frame.getLines().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 7 + 100,
				frame.getBounds().width - 30, frame.getBounds().height / 7 + 120);
		frame.getHighScoresText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 7 + 120,
				frame.getBounds().width - 50, frame.getBounds().height / 7 + 140);
		frame.getHighScore().setBounds(frame.getBounds().width - 50, frame.getBounds().height / 7 + 140,
				frame.getBounds().width - 30, frame.getBounds().height / 7 + 160);
		frame.getNextText().setBounds(frame.getBounds().width - 70, frame.getBounds().height / 7 + 160,
				frame.getBounds().width - 50, frame.getBounds().height / 7 + 180);
	}

	private static void SetShapesBig() {
		for (Shape shape : GameState.gamestate.getCurrentShapes()) {
			for (Square square : shape.getSquares()) {
				if (square.getCell() != null) {
					int x = ((square.getCell().getX() - 1) * 31) + 11;
					int y = ((square.getCell().gety() - 1) * 31) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
		}
		if (GameState.gamestate.getGameSize() == false) {
			if (GameState.gamestate.getCurrentShape() != null) {
				GameState.gamestate.getCurrentShape().setSpeed(Constants.ShapeSpeed);
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					int x = (((square.getX() - 11) / 21) * 31) + 11;
					int y = (((square.getY() - 6) / 21) * 31) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
			if (GameState.gamestate.getNextShape() != null) {
				GameState.gamestate.getNextShape().setSpeed(Constants.ShapeSpeed);
				for (Square square : GameState.gamestate.getNextShape().getSquares()) {
					int x = (((square.getX() - 11) / 21) * 31) + 11;
					int y = (((square.getY() - 6) / 21) * 31) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
		}
	}

	private static void SetShapesSmall() {
		for (Shape shape : GameState.gamestate.getCurrentShapes()) {
			for (Square square : shape.getSquares()) {
				if (square.getCell() != null) {
					int x = ((square.getCell().getX() - 1) * 21) + 11;
					int y = ((square.getCell().gety() - 1) * 21) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
		}
		if (GameState.gamestate.getGameSize() == true) {
			if (GameState.gamestate.getCurrentShape() != null) {
				GameState.gamestate.getCurrentShape().setSpeed(Constants.ShapeSpeed);
				for (Square square : GameState.gamestate.getCurrentShape().getSquares()) {
					int x = (((square.getX() - 11) / 31) * 21) + 11;
					int y = (((square.getY() - 6) / 31) * 21) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
			if (GameState.gamestate.getNextShape() != null) {
				GameState.gamestate.getNextShape().setSpeed(Constants.ShapeSpeed);
				for (Square square : GameState.gamestate.getNextShape().getSquares()) {
					int x = (((square.getX() - 11) / 31) * 21) + 11;
					int y = (((square.getY() - 6) / 31) * 21) + 6;
					square.setX(x);
					square.setY(y);
					square.setSize(Constants.ShapeSize);
				}
			}
		}
	}
}
