package clueTests;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.Player;
import clueGame.Solution;

public class PlayerTests {

	private ClueGame game;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	@Before
	public void setUp() throws Exception {
		game = new ClueGame();
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
			System.out.println(p.getHand().size());
			Assert.assertTrue(p.getHand().size()<=4);		//All players have at least three cards
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
}
