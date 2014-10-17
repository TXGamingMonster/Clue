package clueGame;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import clueGame.Card.CardType;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board gameBoard;
	private ArrayList<Player> players;
	private Solution solution;
	private Stack<Card> deck;
	private String sols[] = new String[3];
	
	//The CR tests initialize with no parameters and we can't change the tests she wrote. So we need to have this as well.
	public ClueGame(){
		gameBoard = new Board("ClueFilesCR/ClueLayout.csv", "ClueFilesCR/ClueLegend.txt");
		rooms = gameBoard.getRooms();
		
		players = new ArrayList<Player>();
		
		try {
			Scanner sc = new Scanner(new FileReader("cards.txt"));
			ArrayList<String> n = new ArrayList<String>();
			ArrayList<String> r = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				String line[] = sc.nextLine().split(":");
				if(line[1].equals("PERSON"))
					n.add(line[0]);
				if(line[1].equals("ROOM"))
					r.add(line[0]);
			}
			Collections.shuffle(n);
			Collections.shuffle(r);
			players.add(new HumanPlayer(n.get(0),Color.BLUE,r.get(0)));
			players.add(new ComputerPlayer(n.get(1),Color.GREEN,r.get(1)));
			players.add(new ComputerPlayer(n.get(2),Color.RED,r.get(2)));
			players.add(new ComputerPlayer(n.get(3),Color.DARK_GRAY,r.get(3)));
			players.add(new ComputerPlayer(n.get(4),Color.MAGENTA,r.get(4)));
			players.add(new ComputerPlayer(n.get(5),Color.WHITE,r.get(5)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
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

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void deal() {
		boolean boo1 = true, boo2 = true, boo3 = true;
		Stack<Card> deck2 = new Stack<Card>();
		while(!deck.isEmpty())
		{
			Card c = deck.pop();
			
			if(boo1 && c.getType().equals(CardType.PERSON))
				{
					sols[0] = c.getName();
					boo1 = false;
				}
			else if(boo2 && c.getType().equals(CardType.WEAPON))
				{
					sols[1] = c.getName();
					boo2 = false;
				}
			else if(boo3 && c.getType().equals(CardType.ROOM))
				{
					sols[2] = c.getName();
					boo3 = false;
				}
			else deck2.push(c);
			if(boo1 && boo2 && boo3)
				break;
		}
		solution = new Solution(sols[0],sols[1],sols[2]);
		while(!deck2.isEmpty())
			deck.push(deck2.pop());
		Collections.shuffle(deck);
		int i=0;
		while(!deck.isEmpty())
		{
			players.get(i%6).addCard(deck.pop()); 
			i++;
		}
		
	}
	
	public void selectAnswer() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusor) {
		
	}
	
	public void handleAccusation(String person, String room, String weapon, Player accusor) {
		if (solution.isSolution(person, weapon, room))
		{
			//Win
		}
		else
		{
			//Lose
		}
	}

	public Solution getSolution() {
		// TODO Auto-generated method stub
		return solution;
	}
	
}
