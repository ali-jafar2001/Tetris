import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

public class Player {
	private String name;
	private int score;
	private int lines;

	public Player(String name, int score, int lines) throws IOException {
		this.name = name;
		this.score = score;
		this.lines = lines;
		Save();
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		// TODO 
		return name + "                                " + lines + "                      " + score;
	}

	private void Save() throws IOException {
		List<String> words = new ArrayList<>();
		File file = new File("C:\\Users\\El_Tiger\\workspace\\Excercise2\\src\\players.txt");
		if (!file.exists())
			file.createNewFile();
		Scanner scanner = new Scanner(file);
		while (scanner.hasNext()) {
			words.add(scanner.nextLine());
		}
		scanner.close();
		FileWriter filewriter = new FileWriter(file);
		PrintWriter pw = new PrintWriter(filewriter);
		for (String sd : words) {
			pw.println(sd);
		}
		Gson gson = new Gson();
		String player = gson.toJson(this);
		for (int i = 0; i < player.length(); i++) {
			if (player.charAt(i) == '{') {
				pw.println('{');
				i++;
			}
			if (player.charAt(i) == '}') {
				pw.println('}');
			} else {
				int j = i + 1 - 1;
				while (player.charAt(i) != '}') {
					i++;
				}
				String s = player.substring(j, i);
				pw.println(s);
				i--;
			}
		}
		filewriter.close();
	}
}
