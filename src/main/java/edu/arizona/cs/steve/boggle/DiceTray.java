package edu.arizona.cs.steve.boggle;

import java.awt.Point;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class DiceTray {
	
	boolean used[][];
	FileReader reader;
	private final char[][] board;
	
	private boolean prints = false;
	
	private class Die {
		public String possible;
		public char visibleChar;

		public Die(String chars) {
			possible = chars;
			this.roll();
		}

		private void roll() {
			Random rand = new Random();
			visibleChar = possible.charAt(rand.nextInt(possible.length()));
		}

	}
	
	public DiceTray(int width, int height) {

		board = new char[height][width];
		
		ArrayList<String> stringDice = new ArrayList<String>();
		stringDice.add("LRYTTE");
		stringDice.add("ANAEEG");
		stringDice.add("AFPKFS");
		stringDice.add("YLDEVR");
		stringDice.add("VTHRWE");
		stringDice.add("IDSYTT");
		stringDice.add("XLDERI");
		stringDice.add("ZNRNHL");
		stringDice.add("EGHWNE");
		stringDice.add("OATTOW");
		stringDice.add("HCPOAS");
		stringDice.add("NMIHUQ");
		stringDice.add("SEOTIS");
		stringDice.add("MTOICU");
		stringDice.add("ENSIEU");
		stringDice.add("OBBAOJ");

		//Loop that goes through each die slot and assigns it a random die
		//Perhaps the Die class is not needed?
		Random rand = new Random();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (stringDice.isEmpty()) {
					// The default boggle only supports 16 dice. If playing with a larger board, just make up random dice for the rest.
					board[i][j] = (char) ('A' + rand.nextInt(26));
				} else {
					int indexToAdd = rand.nextInt(stringDice.size());
					Die die = new Die(stringDice.get(indexToAdd));
					stringDice.remove(indexToAdd);
					board[i][j] = die.visibleChar;
				}
			}
		}
		
		//Loop that prints out a ASCI picture of the board; uneeded
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}	
	
	//Constructor for making a custom board, ignoring the offical dice
	public DiceTray(char[][] board) {
		this.board = board;
	}

	public int getWidth() {
		return board[0].length;
	}

	public int getHeight() {
		return board.length;
	}
	
	//Returns the board
	public char[][] getBoard(){
		return board;
	}
	
	public boolean stringFound(String str) {
		
		if (str.length() == 0) {
			return true;
		}
		str = str.toUpperCase();
		if (str.contains("QU")){
			str = str.replace("QU", "Q");
			if (prints)
				System.out.println(str);
		}
		
		if (prints)
			System.out.println("Searching for: '" + str + "'");
		boolean found = false;
		
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				used = new boolean[getHeight()][getWidth()];
				if (found)
					return true;
				else if (board[i][j] == str.charAt(0)) {
					used[i][j] = true;
					if (prints)
						System.out.println("  Possible start at: _" + board[i][j] + "_ ("+ j+","+i+")");
					found = stringFound(str.substring(1),j,i);
				}
			}
		}
		return found;
	}
	
	private boolean stringFound(String str, int x, int y) {
	
		// Base case: String length is 0, so we return that it was found
		if (str.length() == 0) {
			if (prints)
				System.out.println("  Final char was _"+board[y][x]+"_ ("+ x+","+y+"). Exit recurse.");
			return true;
		}

		Point undo = new Point(x,y);
		used[undo.y][undo.x] = true;

		String prefix = "    ";
		if (prints)
			System.out.println(prefix+"Looking for _"+str.charAt(0)+"_ around _"+board[y][x]+"_ ("+ x+","+y+")");
		SimpleList<Point> branchPts = findCharNextTo(str.charAt(0), x, y);

		boolean found = false;
		for (int i = 0; i < branchPts.length; i++) {
			Point pt = branchPts.get(i);
			if (found)
				return true;
			else {
				if (!used[pt.y][pt.x])
					found = stringFound(str.substring(1),pt.x,pt.y);
				else
					if (prints)
						System.out.println(prefix+"The char _"+board[pt.y][pt.x]+"_ ("+ pt.x+","+pt.y+") is used already.");
			}
		}

		if (prints)
			System.out.println("past loop. exit recurse (Success="+found+")");
		used[undo.y][undo.x] = false;
		return found;
	}
	
	private SimpleList<Point> findCharNextTo(char c, int x, int y) {
		SimpleList<Point> out = new SimpleList<Point>(c + "'s next to " + board[y][x] +" ("+ x+","+y+"):");

		String prefix = "      ";
		String prefix2 = "        ";
		if (prints)
			System.out.println(prefix+out.title);

		// This loop goes through the (at most) 8 chars around any X,Y
		for (int yMod = -1; yMod <= 1; yMod++) {
			for (int xMod = -1; xMod <= 1; xMod++) {
				// The next char that is near X,Y is at newX,newY
				int newX = x+xMod;
				int newY = y+yMod;
				// If newX,newY is a valid position, then push it onto our list 
				if (newX >= 0 &&
					newY >= 0 &&
					newX < getWidth() &&
					newY < getHeight() &&
					(xMod != 0 || yMod != 0) && // Skip the char at X,Y; this is the one we are looking around from
					board[newY][newX] == c
				) {
					if (prints)
						System.out.println(prefix2+(newX)+","+(newY) + ": " + board[newY][newX]);
					out.push(new Point(newX,newY));
				} else {
					//System.out.println(prefix+"Invalid: "+(newX)+","+(newY));
				}
			}
		}
		

		if (prints)
			System.out.println();
		// Return a list which has one x,y Point for each nearby matching char
		return out;
	}
	

	
}


