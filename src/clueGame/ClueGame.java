package clueGame;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board gameBoard;
	private HashSet<Player> players;
	private Solution solution;
	private Stack<Card> deck;
	
	//The CR tests initialize with no parameters and we can't change the tests she wrote. So we need to have this as well.
	public ClueGame(){
		gameBoard = new Board("ClueFilesCR/ClueLayout.csv", "ClueFilesCR/ClueLegend.txt");
		rooms = gameBoard.getRooms();
		players = new HashSet<Player>();
		deck = new Stack<Card>();
		loadDeck();
		Collections.shuffle(deck);
	}
	
	//The file names have to include the folder name before the file, eclipse looks for the files in the root directory of the project which in our case they aren't.
	public ClueGame(String layout, String legend) {
		gameBoard = new Board(layout, legend);
		rooms = gameBoard.getRooms();
	}
	
	public void loadConfigFiles() {
		try {
			gameBoard.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Board getBoard(){
		return gameBoard;
	}
	
	public void loadRoomConfig() {
		
	}
	
	public void loadDeck(){
		try {
			Scanner sc = new Scanner(new FileReader("cards.txt"));
			while(sc.hasNextLine()) {
				String get = sc.nextLine();
				String read[] = get.split(":");
				deck.push(new Card(read[0],read[1]));
			}
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public Stack<Card> getDeck() {
		return deck;
	}

	public HashSet<Player> getPlayers() {
		return players;
	}
	
	public void deal() {
		int dealt = 0;
		while(dealt < players.size()*3)
		{
			Card c = deck.pop();
			
		}
	}
	
	public void selectAnswer() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusor) {
		
	}
	
}
