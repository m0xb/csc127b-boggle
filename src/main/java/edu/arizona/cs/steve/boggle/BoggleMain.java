package edu.arizona.cs.steve.boggle;

import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoggleMain extends JFrame {

	private JButton[][] diceButtons = new JButton[4][4];
	private DiceTray dt;
	private SimpleList<String> submittedWords = new SimpleList<String>(
			"User Submitted Words");
	private HashMap wordList;

	private JTextField inputWord;
	private JButton submitWordButton;
	private JButton finishedButton;
	private JTextArea submittedWordsTA;
	private JTextArea unfoundWordsTA;
	private JFrame boggleFrame = this;

	private JMenuItem finished_mui;
	private JPanel resultsPanel;
	private int score;
	private JLabel scoreLabel;
	private JButton startNewGameButton;

	private Dimension lastDimensions;
	private ArrayList<String> correctWords;
	private ArrayList<String> incorrectWords;

	public static void main(String[] args) {
		JFrame window = new BoggleMain();
		window.setMinimumSize(new Dimension(325, 269));
		window.setVisible(true);
	}

	public BoggleMain() {
		// Step 1: Setup the dictionary
		// AGFQ: user should be able to start a 'new game' by remaking the
		// dicetray object, do we really need to remake the wordlist here?
		createWordList("words.txt");

		// Step 2: Setup the GUI in three substages (
		// -look and feel
		// -retrieve board labels from DiceTray object
		// -layout GUI elements
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		dt = new DiceTray();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				String buttonLabel = dt.getBoard()[i][j] + "";
				if (buttonLabel.equalsIgnoreCase("Q"))
					buttonLabel = "Qu";
				diceButtons[i][j] = new JButton(buttonLabel);

			}
		}
		layoutGUI();
	}

	private void layoutGUI() {

		// AGFQ: where should i initialize these?
		inputWord = new JTextField("");
		submitWordButton = new JButton("Submit word");
		submitWordButton.addActionListener(new SubmitButtonListener());
		finishedButton = new JButton("I'm Finished -- Score Me!");
		finishedButton.addActionListener(new FinishedButtonListener());
		finishedButton.setMaximumSize(new Dimension(2000, finishedButton
				.getMaximumSize().height));
		submittedWordsTA = new JTextArea("");
		submittedWordsTA.setEditable(false);
		// submittedWordsTA.setMaximumSize(new Dimension(1950,250));
		JScrollPane submittedWordsScrollPane = new JScrollPane(submittedWordsTA);

		setTitle("Boggle");
		setIconImage(new ImageIcon("icon3.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocation(375, 240);

		// Create the menubar and requisite menu items
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame_mui = new JMenuItem("New Boggle Game");
		newGame_mui.addActionListener(new NewGameButtonListener());
		fileMenu.add(newGame_mui);
		finished_mui = new JMenuItem("Finish Game");
		finished_mui.addActionListener(new FinishedButtonListener());
		fileMenu.add(finished_mui);
		fileMenu.addSeparator();

		JMenuItem exit_mui = new JMenuItem("Quit");
		exit_mui.addActionListener(new QuitMenuItem());
		fileMenu.add(exit_mui);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		Container mainCP = this.getContentPane();
		mainCP.setLayout(new BoxLayout(mainCP, BoxLayout.Y_AXIS));

		JPanel topCP = new JPanel();
		topCP.setAlignmentX(LEFT_ALIGNMENT);
		topCP.setLayout(new BoxLayout(topCP, BoxLayout.X_AXIS));

		JPanel boggleTrayPanel = new JPanel();
		boggleTrayPanel.setLayout(new GridLayout(4, 4));
		boggleTrayPanel.setMinimumSize(new Dimension(200, 170)); // 185,170
		boggleTrayPanel.setMaximumSize(new Dimension(200, 170));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				boggleTrayPanel.add(diceButtons[i][j]);
				diceButtons[i][j].setMinimumSize(new Dimension(45, 45));
				diceButtons[i][j].setPreferredSize(new Dimension(50, 50));
				diceButtons[i][j].addActionListener(new DieButtonListener());
			}
		}
		boggleTrayPanel.setBorder(BorderFactory
				.createTitledBorder("Boggle Tray"));
		boggleTrayPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		topCP.add(boggleTrayPanel);

		JPanel submittedWordsPanel = new JPanel();
		submittedWordsPanel.setLayout(new BoxLayout(submittedWordsPanel,
				BoxLayout.X_AXIS));
		submittedWordsPanel.setBorder(BorderFactory
				.createTitledBorder("Submitted Words"));
		submittedWordsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		submittedWordsPanel.add(submittedWordsScrollPane);
		topCP.add(submittedWordsPanel);
		mainCP.add(topCP);

		JPanel submitCP = new JPanel();
		submitCP.setLayout(new BoxLayout(submitCP, BoxLayout.X_AXIS));
		submitCP.setAlignmentX(LEFT_ALIGNMENT);
		submitCP.add(inputWord);
		submitCP.add(submitWordButton);
		inputWord.setMaximumSize(new Dimension(2000, 25));
		mainCP.add(submitCP);

		mainCP.add(finishedButton);

	}

	// AGFQ: good way to determine size for this hashmap?
	private void createWordList(String filePath) {
		wordList = new HashMap(90000);

		File theWordFile = new File(filePath);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(theWordFile);
		} catch (FileNotFoundException e) {
		}
		if (fileReader == null)
			System.out.println("ERROR: Could not make scanner with file...");
		else {
			while (fileReader.hasNextLine()) {
				wordList.put(fileReader.nextLine());
			}
		}
		fileReader.close();

	}

	public class DieButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			inputWord.setText(inputWord.getText()
					+ arg0.getActionCommand().toUpperCase());
		}

	}

	public class SubmitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (inputWord.getText().equals(""))
				return;
			submittedWordsTA.append(inputWord.getText().toUpperCase() + "\n");
			submittedWords.push(inputWord.getText().toUpperCase());
			System.out.println("Added user word: "
					+ inputWord.getText().toUpperCase());
			inputWord.setText("");
		}
	}

	public class FinishedButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("CALCULATING PLAYER'S SCORE: ");
			ArrayList<String> validWords = new ArrayList<String>();
			correctWords = new ArrayList<String>();
			incorrectWords = new ArrayList<String>();
			for (int i = 0; i < submittedWords.length; i++) {
				String word = submittedWords.get(i).toLowerCase();
				System.out.println("  Submitted Word " + i + ": " + word);
				if (!validWords.contains(word)) { // if the word is not already
					// added to validWords...
					System.out.println("    +-> word is unique");
					if (wordList.get(word) != null) {
						System.out.println("      +-> found in dictionary");
						if (dt.stringFound(word)) {
							correctWords.add(word);
							validWords.add(word);
							System.out
									.println("        +-> exists in the tray");
							System.out.println("  " + word.toUpperCase()
									+ " queued for scoring!\n");
						} else {
							incorrectWords.add(word);
							System.out.println("  " + word.toUpperCase()
									+ " Not On Board\n");
						}
					} else {
						incorrectWords.add(word);
						System.out.println("  " + word.toUpperCase()
								+ " Not A Word\n");
					}
				} else {
					System.out.println("  " + word.toUpperCase()
							+ " Not Unique\n");
				}
			}
			score = getScoreForWords(validWords);
			System.out.println("Points: " + score);
			inputWord.setText("");
			inputWord.setEnabled(false);
			submittedWordsTA.setEnabled(false);
			submitWordButton.setEnabled(false);
			finishedButton.setEnabled(false);
			finished_mui.setEnabled(false);
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					diceButtons[i][j].setEnabled(false);
				}
			}

			lastDimensions = boggleFrame.getSize();
			boggleFrame.setMinimumSize(new Dimension(325, 269 + 150));
			boggleFrame.setSize(lastDimensions.width,
					lastDimensions.height + 150);

			resultsPanel = new JPanel();
			resultsPanel
					.setLayout(new BoxLayout(resultsPanel, BoxLayout.X_AXIS));
			resultsPanel.setAlignmentX(LEFT_ALIGNMENT);
			JPanel usersCorrectWordsPanel = new JPanel();
			usersCorrectWordsPanel.setLayout(new BoxLayout(
					usersCorrectWordsPanel, BoxLayout.X_AXIS));
			usersCorrectWordsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			usersCorrectWordsPanel.setBorder(BorderFactory
					.createTitledBorder("Correct Words"));
			JTextArea usersCorrectWordsTA = new JTextArea("");
			for (String nextWord : correctWords) {
				usersCorrectWordsTA.append(nextWord + "\n");
			}
			usersCorrectWordsTA.setEditable(false);
			usersCorrectWordsTA.setMaximumSize(new Dimension(1000, 950));
			JScrollPane usersCorrectWordsScrollPane = new JScrollPane(
					usersCorrectWordsTA);
			usersCorrectWordsPanel.add(usersCorrectWordsScrollPane);
			resultsPanel.add(usersCorrectWordsPanel);

			JPanel usersIncorrectWordsPanel = new JPanel();
			usersIncorrectWordsPanel.setLayout(new BoxLayout(
					usersIncorrectWordsPanel, BoxLayout.X_AXIS));
			usersIncorrectWordsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			usersIncorrectWordsPanel.setBorder(BorderFactory
					.createTitledBorder("Incorrect Words"));
			JTextArea usersIncorrectWordsTA = new JTextArea("");
			for (String nextWord : incorrectWords) {
				usersIncorrectWordsTA.append(nextWord + "\n");
			}
			usersIncorrectWordsTA.setEditable(false);
			usersIncorrectWordsTA.setMaximumSize(new Dimension(1000, 950));
			JScrollPane usersIncorrectWordsScrollPane = new JScrollPane(
					usersIncorrectWordsTA);
			usersIncorrectWordsPanel.add(usersIncorrectWordsScrollPane);
			resultsPanel.add(usersIncorrectWordsPanel);

			JPanel unfoundWordsPanel = new JPanel();
			unfoundWordsPanel.setLayout(new BoxLayout(unfoundWordsPanel,
					BoxLayout.X_AXIS));
			unfoundWordsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
			unfoundWordsPanel.setBorder(BorderFactory
					.createTitledBorder("Unfound Words"));
			unfoundWordsTA = new JTextArea();
			findAllWords("words.txt");
			unfoundWordsTA.setEditable(false);
			unfoundWordsTA.setMaximumSize(new Dimension(1000, 950));
			JScrollPane unfoundWordsScrollPane = new JScrollPane(unfoundWordsTA);
			unfoundWordsPanel.add(unfoundWordsScrollPane);
			resultsPanel.add(unfoundWordsPanel);

			boggleFrame.getContentPane().add(resultsPanel);

			// AGF: could look better
			scoreLabel = new JLabel("Your Score: " + score + "  |  You found "+correctWords.size()+" out of "+(correctWords.size()+unfoundWordsTA.getText().split("\n").length)+" words.");
			boggleFrame.getContentPane().add(scoreLabel);

			startNewGameButton = new JButton("Start new game of Boggle");
			startNewGameButton.addActionListener(new NewGameButtonListener());
			startNewGameButton.setMaximumSize(new Dimension(2000,
					startNewGameButton.getMaximumSize().height));
			boggleFrame.getContentPane().add(startNewGameButton);

		}
	}

	// AGFQ: is this the correct way to exit?
	public class QuitMenuItem implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("File -> Quit");
			dispose();
			System.exit(0);
		}
	}

	// AGFQ: how exactly to go about re-starting the whole thing?
	public class NewGameButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("\nSTARTING NEW GAME");

			submittedWords = new SimpleList<String>("User Submitted Words");
			dt = new DiceTray();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					String buttonLabel = dt.getBoard()[i][j] + "";
					if (buttonLabel.equalsIgnoreCase("Q"))
						buttonLabel = "Qu";
					diceButtons[i][j].setText(buttonLabel);
					diceButtons[i][j].setEnabled(true);
				}
			}
			inputWord.setEnabled(true);
			submittedWordsTA.setText("");
			submittedWordsTA.setEnabled(true);
			submitWordButton.setEnabled(true);
			finishedButton.setEnabled(true);
			finished_mui.setEnabled(true);
			if (startNewGameButton != null) {
				boggleFrame.getContentPane().remove(startNewGameButton);
				boggleFrame.getContentPane().remove(scoreLabel);
				boggleFrame.getContentPane().remove(resultsPanel);
				boggleFrame.setMinimumSize(new Dimension(316, 269));
				int usersChangeInHeight = boggleFrame.getSize().height
						- (lastDimensions.height + 150);
				boggleFrame.setSize(boggleFrame.getSize().width,
						lastDimensions.height + usersChangeInHeight / 3);
			}
		}
	}

	private int getScoreForWords(ArrayList<String> words) {
		int score = 0;
		for (String nextWord : words) {
			score += getScoreForWord(nextWord);
		}
		return score;
	}

	// AGFQ: should this be seperate method?
	private int getScoreForWord(String word) {
		if (word.length() < 3) {
			System.out.println("  " + word.toUpperCase() + ": 0pts");
			return 0;
		} else if (word.length() <= 4) {
			System.out.println("  " + word.toUpperCase() + ": 1pts");
			return 1;
		} else if (word.length() == 5) {
			System.out.println("  " + word.toUpperCase() + ": 2pts");
			return 2;
		} else if (word.length() == 6) {
			System.out.println("  " + word.toUpperCase() + ": 3pts");
			return 3;
		} else if (word.length() == 7) {
			System.out.println("  " + word.toUpperCase() + ": 5pts");
			return 5;
		} else {
			System.out.println("  " + word.toUpperCase() + ": 11pts");
			return 11;
		}
	}

	private void findAllWords(String filePath) {

		String possible = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				possible += dt.getBoard()[i][j];

			}
		}

		File theWordFile = new File(filePath);
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(theWordFile);
		} catch (FileNotFoundException e) {
		}

		while (fileReader.hasNextLine()) {
			String dictWord = fileReader.next();

			if (possible.contains((""+dictWord.charAt(0)).toUpperCase()) && !correctWords.contains(dictWord) && dt.stringFound(dictWord)) {
				unfoundWordsTA.append(dictWord + "\n");
			}

		}
		
	}

}
