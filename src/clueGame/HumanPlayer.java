package clueGame;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Card.CardType;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, Color color, String location) {
		super(name, color, location);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<CardType> needs;
	
	public boolean isHuman() {
		return true;
	}
}
