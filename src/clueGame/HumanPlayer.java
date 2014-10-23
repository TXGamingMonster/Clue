package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import clueGame.Card.CardType;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, Color color, Integer x) {
		super(name, color, x);
		// TODO Auto-generated constructor stub
	}

	private ArrayList<CardType> needs;
	
	public boolean isHuman() {
		return true;
	}
	
	public void draw(Graphics g, int x, int y, int ratio, int ratio2) {
		super.draw(g, x, y, ratio, ratio2);
	}
}
