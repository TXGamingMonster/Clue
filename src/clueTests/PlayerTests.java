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
		//Checking to make sure no Players can disprove the solution
		Assert.assertNull(game.handleSuggestion(s.getPerson(), s.getRoom(), s.getWeapon(), new Player("", Color.RED, "")));
		
		for(Player p: game.getPlayers())
			for(String w: game.weapons)
				for(String r: game.roomlist)
				{
					if(!p.getName().equals(s.getPerson()) && !!r.equals(s.getRoom()) && !w.equals(s.getWeapon()))
						Assert.assertNotNull(game.handleSuggestion(p.getName(), r, w, new Player("", Color.RED, "")));
					//Asserting that at least one player can disprove a suggestion that is not the solution
				}

	}
	
	//Testing random ComputerPlayer target selection
	@Test
	public void targetTest() {
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
}
