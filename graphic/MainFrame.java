
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MainFrame extends JFrame {

	private GamePanel gamepanel;
	private JMenu gamemenu;
	private JMenu OptinosMenu;
	private JMenu SizeMenu;
	private JMenuItem NewItem;
	private JMenuItem Pause;
	private JMenuItem HighScores;
	private JMenuItem Exit;
	private JRadioButtonMenuItem Small;
	private JRadioButtonMenuItem Big;
	private JLabel ScoreText;
	private JLabel Score;
	private JLabel LinesText;
	private JLabel Lines;
	private JLabel LevelText;
	private JLabel Level;
	private JLabel HighScoresText;
	private JLabel HighScore;
	private JLabel NextText;

	public JMenuItem getPause() {
		return Pause;
	}

	public GamePanel getGamePanel() {
		return gamepanel;
	}

	public JLabel getLinesText() {
		return LinesText;
	}

	public JLabel getLevelText() {
		return LevelText;
	}

	public JLabel getHighScoresText() {
		return HighScoresText;
	}

	public JLabel getNextText() {
		return NextText;
	}

	public JLabel getScoreText() {
		return ScoreText;
	}

	public JLabel getScore() {
		return Score;
	}

	public JLabel getLines() {
		return Lines;
	}

	public JLabel getLevel() {
		return Level;
	}

	public JLabel getHighScore() {
		return HighScore;
	}

	public JRadioButtonMenuItem getSmall() {
		return Small;
	}

	public JRadioButtonMenuItem getBig() {
		return Big;
	}

	public JMenuItem getNewItem() {
		return NewItem;
	}

	public JMenuItem getHighScores() {
		return HighScores;
	}

	public JMenuItem getExit() {
		return Exit;
	}

	public MainFrame() {
		Initialize();
		UserAction.Exit(this);
		UserAction.Size(this);
		UserAction.NewGame(this);
		UserAction.Pause(this);
		UserAction.HighScores(this);
	}

	private void Initialize() {
		setSize(new Dimension(Constants.MinLength, Constants.MinWidth));
		setTitle("Tetris");
		setResizable(false);
		setLocationRelativeTo(null);
		JMenuBar Menubar = CreateMenuBar();
		setJMenuBar(Menubar);
		gamepanel = new GamePanel();
		setContentPane(gamepanel);
		SetLabels();
		addKeyListener(new UserAction());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JMenuBar CreateMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu gamemenu = GameMenu();
		JMenu OptinosMenu = OptinosMenu();
		menubar.add(gamemenu);
		menubar.add(OptinosMenu);
		return menubar;
	}

	private JMenu GameMenu() {
		JMenu gamemenu = new JMenu("Game");
		this.gamemenu = gamemenu;
		gamemenu.setMnemonic(KeyEvent.VK_G);
		JMenuItem NewItem = new JMenuItem("New");
		this.NewItem = NewItem;
		NewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		Pause = new JMenuItem("Pause");
		this.Pause = Pause;
		Pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		JMenuItem HighScoreItem = new JMenuItem("HighScores...");
		this.HighScores = HighScoreItem;
		HighScoreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		JMenuItem ExitItem = new JMenuItem("Exit");
		this.Exit = ExitItem;
		ExitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		gamemenu.add(NewItem);
		gamemenu.add(Pause);
		gamemenu.add(HighScoreItem);
		gamemenu.addSeparator();
		gamemenu.add(ExitItem);
		return gamemenu;
	}

	private JMenu OptinosMenu() {
		JMenu OptinosMenu = new JMenu("Optinos");
		OptinosMenu.setMnemonic(KeyEvent.VK_O);
		this.OptinosMenu = OptinosMenu;
		JMenu SizeMenu = new JMenu("Size");
		this.SizeMenu = SizeMenu;
		ButtonGroup bg = new ButtonGroup();
		JRadioButtonMenuItem SmallItem = new JRadioButtonMenuItem("Small");
		this.Small = SmallItem;
		JRadioButtonMenuItem BigItem = new JRadioButtonMenuItem("Big");
		this.Big = BigItem;
		SmallItem.setSelected(true);
		bg.add(SmallItem);
		bg.add(BigItem);
		SizeMenu.add(SmallItem);
		SizeMenu.add(BigItem);
		OptinosMenu.add(SizeMenu);
		return OptinosMenu;
	}

	private void SetLabels() {
		SetScoreText(this);
		SetScore(this);
		SetLevelText(this);
		SetLevel(this);
		SetLines(this);
		SetLinesText(this);
		SetNext(this);
		SetHighScore(this);
		SetHighScoresText(this);
	}

	private void SetScoreText(MainFrame frame) {
		ScoreText = new JLabel("Score");
		ScoreText.setFont(new Font("serif", 10, 20));
		ScoreText.setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15, frame.getBounds().width - 50,
				frame.getBounds().height / 15 + 20);
		frame.add(ScoreText);
	}

	private void SetScore(MainFrame frame) {
		this.Score = new JLabel("0");
		this.Score.setFont(new Font("serif", 10, 20));
		this.Score.setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 20,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 40);
		frame.add(Score);
	}

	private void SetLevelText(MainFrame frame) {
		this.LevelText = new JLabel("Level");
		this.LevelText.setFont(new Font("serif", 10, 20));
		this.LevelText.setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 40,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 60);
		frame.add(LevelText);
	}

	private void SetLevel(MainFrame frame) {
		this.Level = new JLabel("0");
		this.Level.setFont(new Font("serif", 10, 20));
		this.Level.setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 60,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 80);
		frame.add(Level);
	}

	private void SetLinesText(MainFrame frame) {
		this.LinesText = new JLabel("Lines");
		this.LinesText.setFont(new Font("serif", 10, 20));
		this.LinesText.setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 80,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 100);
		frame.add(LinesText);
	}

	private void SetLines(MainFrame frame) {
		this.Lines = new JLabel("0");
		this.Lines.setFont(new Font("serif", 10, 20));
		this.Lines.setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 100,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 120);
		frame.add(Lines);
	}

	private void SetHighScoresText(MainFrame frame) {
		this.HighScoresText = new JLabel("High Score");
		this.HighScoresText.setFont(new Font("serif", 10, 15));
		this.HighScoresText.setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 120,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 140);
		frame.add(HighScoresText);
	}

	private void SetHighScore(MainFrame frame) {
		this.HighScore = new JLabel("0");
		this.HighScore.setFont(new Font("serif", 10, 20));
		this.HighScore.setBounds(frame.getBounds().width - 50, frame.getBounds().height / 15 + 140,
				frame.getBounds().width - 30, frame.getBounds().height / 15 + 160);
		frame.add(HighScore);
	}

	private void SetNext(MainFrame frame) {
		this.NextText = new JLabel("Next");
		this.NextText.setFont(new Font("serif", 10, 20));
		this.NextText.setBounds(frame.getBounds().width - 70, frame.getBounds().height / 15 + 160,
				frame.getBounds().width - 50, frame.getBounds().height / 15 + 180);
		frame.add(NextText);
	}
}
