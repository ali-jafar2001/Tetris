import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private Timer timer;
	private Ticker ticker;
	private Drawer drawer;

	public GamePanel() {
		setLayout(null);
		setBackground(Color.BLACK);
		drawer = new Drawer();
		GameState.getInstance();
		GameCheck.GetInstance();
		timer = new Timer();
		ticker = new Ticker();
		timer.scheduleAtFixedRate(ticker, 1000, 500);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		drawer.DrawLine(g2D);
		if (GameCheck.gamecheck.getGameStart() == true) {
			GameState.gamestate.MakeCurrentShape();
			GameState.gamestate.MakeNextShape();
			drawer.DrawShape(g2D, GameState.gamestate.getCurrentShape());
			drawer.DrawReplica(g2D, GameState.gamestate.getNextShape());
		}
		for (Shape shape : GameState.gamestate.getCurrentShapes()) {
			drawer.DrawShape(g2D, shape);
		}
		drawer.setscores();
	}

	class Ticker extends TimerTask {
		@Override
		public void run() {

			if (GameState.gamestate.getCurrentShape() != null) {
				GameState.gamestate.getCurrentShape().move();
				GameCheck.gamecheck.strikeOtherShape();
				GameCheck.gamecheck.strikeDownBorder();
				GameCheck.gamecheck.CheckLines();
				try {
					GameCheck.gamecheck.CheckEndGame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			repaint();

		}

	}
}
