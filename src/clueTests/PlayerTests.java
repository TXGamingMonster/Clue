package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class PlayerTests {

	private ClueGame game;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	@Before
	public void setUp() throws Exception {
		game = new ClueGame("ClueLayout2.csv", "ClueLegend2.txt");
		Scanner sc = new Scanner(new FileReader("cards.txt"));
		while(sc.hasNextLine())
		{
			String st[] = sc.nextLine().split(":");
			cards.add(new Card(st[0],st[1]));
		}
	}

	//Testing contents of deck were correctly added and shuffled
	@Test
	public void deckTests() {
		Assert.assertEquals(21,game.getDeck().size());		//Deck size matches cards.txt size
		int i=0;
		boolean shuffed = false;
		while(!game.getDeck().isEmpty())
		{
			if(!shuffed)
				if(!game.getDeck().peek().equals(cards.get(i)))
					shuffed = true;	
			Assert.assertTrue(cards.contains(game.getDeck().pop()));	//All cards in deck appear in cards.txt
			i++;
		}
		Assert.assertTrue(shuffed);		//Cards in deck are in different positions from their load spots
	}
	
	//Testing dealing of the deck and resulting player holdings
	@Test
	public void dealTests() {
		Assert.assertEquals(21,game.getDeck().size());		//All cards are in the deck
		game.deal();
		Assert.assertEquals(0,game.getDeck().size());		//All cards have been dealt from the deck
		for(Player p: game.getPlayers())
		{
			Assert.assertEquals(p.getHand().size(),3);		//All players have at least three cards
			for(Player q: game.getPlayers())
				if(!q.getName().equals(p.getName()))
					Assert.assertFalse(p.getHand().equals(q.getHand()));	//No two players have the same hand
		}
		Assert.assertTrue(game.getSolution().hasSolution());		//Solution was created as well
	}
	
	//Test initial player names, colours, and starting locations
	@Test
	public void playerStart() {
		Assert.assertEquals(6, game.getPlayers().size());		//Six players were created
		int i=0;
		int j=0;
		for(Player p: game.getPlayers())
		{
			if(p.isHuman())
				i++;
			else j++;
			for(Player q:game.getPlayers())
				if(!p.equals(q))
				{
					Assert.assertFalse(p.getName().equals(q.getName()));	
					Assert.assertFalse(p.getColor().equals(q.getColor()));					//No two players are the same
					Assert.assertFalse(p.getStlocation().equals(q.getStlocation()));
				}
		}
		Assert.assertEquals(1,i);			//Only one player is human
		Assert.assertEquals(5,j);			//the other players are computers
		
	}
	
	//Test for Solution matching
	@Test
	public void correctSoln()
	{
		Solution soln=new Solution("Mr. Green", "Candlestick", "Armory");
		Assert.assertTrue(soln.isSolution("Mr. Green", "Candlestick", "Armory"));
	}
	
	//Wrong person
	@Test
	public void wrongPerson()
	{
		Solution soln=new Solution("Mr. Green", "Candlestick", "Armory");
		Assert.assertFalse(soln.isSolution("Mrs. White", "Candlestick", "Armory"));
	}
	
	//Wrong Weapon
	@Test
	public void wrongWeapon()
	{
		Solution soln=new Solution("Mr. Green", "Candlestick", "Armory");
		Assert.assertFalse(soln.isSolution("Mr. Green", "Knife", "Armory"));
	}

	//Wrong Room
	@Test
	public void wrongRoom()
	{
		Solution soln=new Solution("Mr. Green", "Candlestick", "Armory");
		Assert.assertFalse(soln.isSolution("Mr. Green", "Candlestick", "Library"));
	}
	
	//Testing handleSuggestion method in ClueGame
	@Test
	public void handlingTest()
	{
		game.deal();
		Solution s = game.getSolution();
		Player dummy1 = new Player("dummy1", Color.RED, "");
		
		//Checking to make sure no Players can disprove the game solution
		Assert.assertNull(game.handleSuggestion(s.getPerson(), s.getRoom(), s.getWeapon(), dummy1));
		
		//Goes through Players in order as does handleSuggestion
		for(Player p: game.getPlayers())
			for(String w: game.weapons)
				for(String r: game.roomlist)
				{
					if(!p.getName().equals(s.getPerson()) && !!r.equals(s.getRoom()) && !w.equals(s.getWeapon()))
						Assert.assertNotNull(game.handleSuggestion(p.getName(), r, w, dummy1));
					//Asserting that at least one player can disprove a suggestion that is not the solution
				}
		
		for(String r: game.roomlist)
			if(!r.equals(s.getRoom()))
				s = new Solution(s.getPerson(), s.getRoom(), r);
		
		//Assert that at least one player can disprove a partly correct suggestion
		Assert.assertNotNull(game.handleSuggestion(s.getPerson(), s.getRoom(), s.getWeapon(), dummy1));

	}
	
	//Testing to make sure ComputerPlayers choose randomly based on seen cards
	@Test
	public void suggestionTest() {
		game.deal();
		ComputerPlayer comp = new ComputerPlayer("comp", Color.RED, game.roomlist.get(0));
		for(Player p: game.players)
			for(Card c: p.getHand())
				comp.updateSeen(c);
		
		//Checking created solution with game solution
		Assert.assertEquals(game.getSolution().getPerson(), comp.createSuggestion(game).getPerson());
		Assert.assertEquals(game.getSolution().getWeapon(), comp.createSuggestion(game).getWeapon());
		Assert.assertEquals(game.getSolution().getRoom(), comp.createSuggestion(game).getRoom());
		
	}
	
	//Testing random ComputerPlayer target selection with no room in question
	@Test
	public void targetnoroomTest() {
		ComputerPlayer player = new ComputerPlayer("", Color.RED, "");
		// Pick a location with no rooms in target, just three targets
		game.gameBoard.calcTargets(9, 25, 2);
		int a = 0;
		int b = 0;
		int c = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(game.gameBoard.getTargets());
			if (selected == game.gameBoard.getCellAt(11, 0))
				a++;
			else if (selected == game.gameBoard.getCellAt(8, 24))
				b++;
			else if (selected == game.gameBoard.getCellAt(9, 23))
				c++;
			else
				fail("Invalid target selected");
		}
		// Check that 100 choices were made
		assertEquals(100, a + b + c);
		// Check that all targets were selected multiple times
		assertTrue(a > 10);
		assertTrue(b > 10);
		assertTrue(c > 10);
	}
	
	//Testing random ComputerPlayer target selection with a room in question
	@Test
	public void targetroomTest() {
		ComputerPlayer player = new ComputerPlayer("", Color.RED, "");
		// Pick a location with no rooms in target, just three targets
		game.gameBoard.calcTargets(17, 9, 2);
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(game.gameBoard.getTargets());
			if (selected == game.gameBoard.getCellAt(18, 10))
				a++;
			else if (selected == game.gameBoard.getCellAt(16, 10))
				b++;
			else if (selected == game.gameBoard.getCellAt(17, 11))
				c++;
			else if (selected == game.gameBoard.getCellAt(16, 8))
				d++;
			else if (selected == game.gameBoard.getCellAt(17, 7))
				e++;
			else if (selected == game.gameBoard.getCellAt(18, 8))
				d++;
			else
				fail("Invalid target selected");
		}
		// Check that 100 choices were made
		assertEquals(100, a + b + c + d  + e + f);
		// Check that all targets were selected multiple times
		assertTrue(a > 10);
		assertTrue(b > 10);
		assertTrue(c > 10);
		assertTrue(d > 10);
		assertTrue(e > 10);
		assertTrue(f > 10);
	}

	//Testing random ComputerPlayer target selection with a room in question CONSIDERING LAST ROOM VISITED
	@Test
	public void targetroomconsiderTest() {
		ComputerPlayer player = new ComputerPlayer("", Color.RED, "");
		player.setLastVisited('N');
		// Pick a location with no rooms in target, just three targets
		game.gameBoard.calcTargets(17, 9, 2);
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(game.gameBoard.getTargets());
			if (selected == game.gameBoard.getCellAt(18, 10))
				a++;
			else if (selected == game.gameBoard.getCellAt(16, 10))
				b++;
			else if (selected == game.gameBoard.getCellAt(17, 11))
				c++;
			else if (selected == game.gameBoard.getCellAt(16, 8))
				d++;
			else if (selected == game.gameBoard.getCellAt(17, 7))
				e++;
			else if (selected == game.gameBoard.getCellAt(18, 8))
				d++;
			else
				fail("Invalid target selected");
		}
		// Check that 100 choices were made
		assertEquals(100, a + b + c + d  + e + f);
		
		// Check that all targets were selected multiple times except the room space
		assertTrue(a == 0);
		assertTrue(b > 10);
		assertTrue(c > 10);
		assertTrue(d > 10);
		assertTrue(e > 10);
		assertTrue(f > 10);
	}
	
	
}
