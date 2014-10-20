package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player{
	private char lastRoomVisited;
	
	public ComputerPlayer(String string, Color blue, String string2) {
		// TODO Auto-generated constructor stub
		super(string,blue,string2);
	}

	public void pickLocation(Set<BoardCell> targets) {
		
	}
	
	public void createSuggestion(ClueGame game) {
		game.handleSuggestion(game.players.get((int)Math.random()*game.players.size()).getName(),
				game.roomlist.get((int)Math.random()*game.roomlist.size()), game.weapons.get((int)Math.random()*game.weapons.size()), this);
	}
	
	public void updateSeen(Card seen) {
		
	}
	
	public boolean isHuman() {
		return false;
	}
}
