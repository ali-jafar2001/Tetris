import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class Drawer {

	public void setscores() {
		Main.mainframe.getLevel().setText(Integer.toString(GameState.gamestate.getLevel()));
		Main.mainframe.getScore().setText(Integer.toString(GameState.gamestate.getScore()));
		Main.mainframe.getLines().setText(Integer.toString(GameState.gamestate.getLines()));
		int s = -200;
		for (int score : GameState.gamestate.getScores()) {
			if (score > s) {
				s = score;
			}
		}
		Main.mainframe.getHighScore().setText(Integer.toString(s));
	}

	public void DrawLine(Graphics2D g2D) {
		g2D.setColor(Color.white);
		g2D.drawLine(Constants.stx - 1, Constants.sty, Constants.enx, Constants.sty);
		g2D.drawLine(Constants.stx - 1, Constants.sty, Constants.stx - 1, Constants.eny);
		g2D.drawLine(Constants.stx - 1, Constants.eny, Constants.enx, Constants.eny);
		g2D.drawLine(Constants.enx, Constants.sty, Constants.enx, Constants.eny);
	}

	public void DrawShape(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		for (Square square : shape.getSquares()) {
			g2D.fillRect(square.getX(), square.getY(), square.getSize(), square.getSize());
		}
	}

	public void DrawReplica(Graphics2D g2D, Shape shape) {
		switch (shape.getType()) {
		case 0: {
			DrawWoodReplica(g2D, shape);
			break;
		}
		case 1: {
			DrawLeftFootReplica(g2D, shape);
			break;
		}
		case 2: {
			DrawRightFootReplica(g2D, shape);
			break;
		}
		case 3: {
			DrawWindowReplica(g2D, shape);
			break;
		}
		case 4: {
			DrawMountainReplica(g2D, shape);
			break;
		}
		case 5: {
			DrawRightDuckReplica(g2D, shape);
			break;
		}
		case 6: {
			DrawLeftDuckReplica(g2D, shape);
			break;
		}
		}
	}

	private void DrawLeftDuckReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 9,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
	}

	private void DrawRightDuckReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 9,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
	}

	private void DrawMountainReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 9,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
	}

	private void DrawWindowReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
	}

	private void DrawRightFootReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 42, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 31,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 42, 10, 10);
	}

	private void DrawLeftFootReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 42, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 9,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 42, 10, 10);
	}

	private void DrawWoodReplica(Graphics2D g2D, Shape shape) {
		g2D.setColor(shape.getColor());
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 20, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 31, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 42, 10, 10);
		g2D.fillRect(Main.mainframe.getNextText().getBounds().x + 20,
				(int) Main.mainframe.getNextText().getBounds().getCenterY() + 53, 10, 10);
	}
}
