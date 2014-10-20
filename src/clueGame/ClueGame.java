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
	public Map<Character, String> rooms;
	private Board gameBoard;
	public ArrayList<Player> players;
	public ArrayList<String> weapons;
	public ArrayList<String> roomlist;
	private Solution solution;
	private Stack<Card> deck;
	private String sols[] = new String[3];
	
	//The CR tests initialize with no parameters and we can't change the tests she wrote. So we need to have this as well.
	public ClueGame(){
		gameBoard = new Board("ClueLayout2.csv", "ClueLegend2.txt");
		rooms = gameBoard.getRooms();
		
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		roomlist = new ArrayList<String>();
		
		try {
			Scanner sc = new Scanner(new FileReader("cards.txt"));
			ArrayList<String> n = new ArrayList<String>();
			
			while(sc.hasNextLine())
			{
				String line[] = sc.nextLine().split(":");
				if(line[1].equals("PERSON"))
					n.add(line[0]);
				if(line[1].equals("ROOM"))
					roomlist.add(line[0]);
				if(line[1].equals("WEAPON"))
					weapons.add(line[0]);
			}
			Collections.shuffle(n);
			Collections.shuffle(roomlist);
			players.add(new HumanPlayer(n.get(0),Color.BLUE,roomlist.get(0)));
			players.add(new ComputerPlayer(n.get(1),Color.GREEN,roomlist.get(1)));
			players.add(new ComputerPlayer(n.get(2),Color.RED,roomlist.get(2)));
			players.add(new ComputerPlayer(n.get(3),Color.DARK_GRAY,roomlist.get(3)));
			players.add(new ComputerPlayer(n.get(4),Color.MAGENTA,roomlist.get(4)));
			players.add(new ComputerPlayer(n.get(5),Color.WHITE,roomlist.get(5)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(roomlist.size());
		System.out.println(weapons.size());
		deck = new Stack<Card>();
		loadDeck();
		Collections.shuffle(deck);
	}
	
	//The file names have to include the folder name before the file, eclipse looks for the files in the root directory of the project which in our case they aren't.
	public ClueGame(String layout, String legend) {
		gameBoard = new Board(layout, legend);
		rooms = gameBoard.getRooms();
		
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		roomlist = new ArrayList<String>();
		
		try {
			Scanner sc = new Scanner(new FileReader("cards.txt"));
			ArrayList<String> n = new ArrayList<String>();
			
			while(sc.hasNextLine())
			{
				String line[] = sc.nextLine().split(":");
				if(line[1].equals("PERSON"))
					n.add(line[0]);
				if(line[1].equals("ROOM"))
					roomlist.add(line[0]);
				if(line[1].equals("WEAPON"))
					weapons.add(line[0]);
			}
			Collections.shuffle(n);
			Collections.shuffle(roomlist);
			players.add(new HumanPlayer(n.get(0),Color.BLUE,roomlist.get(0)));
			players.add(new ComputerPlayer(n.get(1),Color.GREEN,roomlist.get(1)));
			players.add(new ComputerPlayer(n.get(2),Color.RED,roomlist.get(2)));
			players.add(new ComputerPlayer(n.get(3),Color.DARK_GRAY,roomlist.get(3)));
			players.add(new ComputerPlayer(n.get(4),Color.MAGENTA,roomlist.get(4)));
			players.add(new ComputerPlayer(n.get(5),Color.WHITE,roomlist.get(5)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		deck = new Stack<Card>();
		loadDeck();
		Collections.shuffle(deck);
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
	
	public Card handleSuggestion(String person, String room, String weapon, Player accusor) {
		Card c = null;
		Player q = null;
		boolean boo = true;
		for(Player p: players)
			if(boo && !p.getName().equals(accusor))
			{
				c = p.disproveSuggestion(person, weapon, room);
				if(c!=null)
				{
					q = p;
					boo = false;
				}
			}
		return c;
		
		/*if(c==null)
			System.out.println("No one can prove the suggestion");
		else System.out.println(q+" can disprove the suggestion with " + c);*/
	}
	
	public void checkAccusation(String person, String room, String weapon, Player accusor) {
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
