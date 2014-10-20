package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import clueGame.Card.CardType;

public class Player {
	private String name,stlocation;
	Color color;
	private HashSet<Card> cards;
	private ArrayList<CardType> needs;
	
	public Player(String name, Color color, String location) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.color = color;
		this.stlocation = location;
		cards = new HashSet<Card>();
		needs = new ArrayList<CardType>();
	}
	
	public Card disproveSuggestion(String person, String weapon, String room)
	{
		for(Card c: getHand())
			if(person.equals(c.getName()) || weapon.equals(c.getName()) || room.equals(c.getName())) 
				return c;
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
	
	public Color getColor() {
		return color;
	}
	
	public String getStlocation() {
		return stlocation;
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
