package clueGame;

import java.util.ArrayList;
import java.util.HashSet;

import clueGame.Card.CardType;

public class Player {
	private String name;
	private HashSet<Card> cards;
	private ArrayList<CardType> needs;
	
	public Player(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		cards = new HashSet<Card>();
		needs = new ArrayList<CardType>();
	}
	
	public Card disproveSuggestion(String person, String weapon, String room)
	{
		return null;
	}
	
	public boolean isHuman() {
		return false;
	}

	public HashSet<Card> getHand() {
		// TODO Auto-generated method stub
		return cards;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public boolean needsType(CardType type) {
		// TODO Auto-generated method stub
		return !needs.contains(type);
	}

	public void addCard(Card c) {
		// TODO Auto-generated method stub
		cards.add(c);
		needs.add(c.getType());
	}
}
