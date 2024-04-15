import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

public class ScoreFrame extends JPanel {
	private Image background;
	private List<Player> players;
	private Player num1;
	private Player num2;
	private Player num3;
	private Player num4;
	private Player num5;
	private Player num6;
	private Player num7;
	private Player num8;
	private Player num9;
	private Player num10;

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public ScoreFrame() throws IOException {
		LoadPlayers();
		InitializeTop10();
		JFrame frame = new JFrame();
		JButton ok = new JButton("OK");
		frame.setSize(new Dimension(600, 400));
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(this);
		background = Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\El_Tiger\\workspace\\Excercise2\\src\\01(12940).jpg");
		frame.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 600, 400, this);
		g.setFont(new Font("serif", 100, 20));
		g.setColor(Color.yellow);
		g.drawString("name:" + "                     " + "lines:" + "                     " + "score:", 100, 50);
		g.drawString("1: " + num1.toString(), 80, 80);
		g.drawString("2: " + num2.toString(), 80, 110);
		g.drawString("3: " + num3.toString(), 80, 140);
		g.drawString("4: " + num4.toString(), 80, 170);
		g.drawString("5: " + num5.toString(), 80, 200);
		g.drawString("6: " + num6.toString(), 80, 230);
		g.drawString("7: " + num7.toString(), 80, 260);
		g.drawString("8: " + num8.toString(), 80, 290);
		g.drawString("9: " + num9.toString(), 80, 320);
		g.drawString("10: " + num10.toString(), 80, 350);
	}

	private void LoadPlayers() throws FileNotFoundException {
		File file = new File("C:\\Users\\El_Tiger\\workspace\\Excercise2\\src\\players.txt");
		List<Player> players = new ArrayList<Player>();
		Scanner scanner = new Scanner(file);
		String st = "";
		while (scanner.hasNext()) {
			st = st + scanner.nextLine();
		}
		Gson gson = new Gson();
		int i = 0;
		while (i < st.length()) {
			int j = i + 1 - 1;
			while (st.charAt(i) != '}') {
				i++;
			}
			i++;
			String player = st.substring(j, i);
			players.add(gson.fromJson(player, Player.class));
		}
		this.players = players;
	}

	private void InitializeTop10() {
		List<Integer> scores = new ArrayList<Integer>();
		for (Player player : players) {
			scores.add(player.getScore());
		}
		Collections.sort(scores);
		for (Player player : players) {
			if (player.getScore() == scores.get(scores.size() - 1))
				num1 = player;
			if (player.getScore() == scores.get(scores.size() - 2))
				num2 = player;
			if (player.getScore() == scores.get(scores.size() - 3))
				num3 = player;
			if (player.getScore() == scores.get(scores.size() - 4))
				num4 = player;
			if (player.getScore() == scores.get(scores.size() - 5))
				num5 = player;
			if (player.getScore() == scores.get(scores.size() - 6))
				num6 = player;
			if (player.getScore() == scores.get(scores.size() - 7))
				num7 = player;
			if (player.getScore() == scores.get(scores.size() - 8))
				num8 = player;
			if (player.getScore() == scores.get(scores.size() - 9))
				num9 = player;
			if (player.getScore() == scores.get(scores.size() - 10))
				num10 = player;
		}
	}
}
