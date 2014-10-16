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
		Assert.assertEquals(22,game.getDeck().size());		//Deck size matches cards.txt size
		int i=0;
		boolean shuffed = false;
		while(!game.getDeck().isEmpty())
		{
			if(!shuffed)
				if(!game.getDeck().peek().equals(cards.get(i)))
					shuffed = true;
					
			Assert.assertTrue(cards.contains(game.getDeck().pop()));	//All cards appear in cards.txt
			i++;
		}
		Assert.assertTrue(shuffed);		//Cards in deck are in different positions from there load spots
	}
	
	//Testing dealing of the deck and resulting player holdings
	@Test
	public void dealTests() {
		Assert.assertEquals(22,game.getDeck().size());
		game.deal();
		Assert.assertEquals(0,game.getDeck().size());
		for(Player p: game.getPlayers())
		{
			ArrayList<CardType> list = new ArrayList<CardType>();
			Assert.assertEquals(3,p.getHand().size());
			for(Card c: p.getHand())
				list.add(c.getType());
			Assert.assertTrue(list.contains(CardType.PERSON));
			Assert.assertTrue(list.contains(CardType.WEAPON));
			Assert.assertTrue(list.contains(CardType.ROOM));
				
		}
			
	}

}
