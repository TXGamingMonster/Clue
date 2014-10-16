package clueGame;

import java.util.ArrayList;

import clueGame.Card.CardType;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<CardType> needs;
	
	public boolean isHuman() {
		return true;
	}
}
