package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player{
	private char lastRoomVisited;
	private HashSet<String> seenlist;
	
	public ComputerPlayer(String string, Color blue, String string2) {
		// TODO Auto-generated constructor stub
		super(string,blue,string2);
		seenlist = new HashSet<String>();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		int x = (int)Math.random()*targets.size();
		int i = 0;
		for(BoardCell b: targets)
		{
			if(i == x)
				return b;
			else i++;
		}
		return null;
		
	}
	
	public Solution createSuggestion(ClueGame game) {
		String n=null, w=null, r=null;
		
		for(Player p: game.players)
			if(!seenlist.contains(p.getName()))
			{
				n = p.getName();
				break;
			}
		for(String s: game.weapons)
			if(!seenlist.contains(s))
			{
				w = s;
				break;
			}
		for(String s: game.roomlist)
			if(!seenlist.contains(s))
			{
				r = s;
				break;
			}
		
		if(n != null && w !=null && r !=null)
			return new Solution(n,w,r);
		return null;
	}
	
	public void updateSeen(Card seen) {
		seenlist.add(seen.getName());
	}
	public void setLastVisited(char r) {
		lastRoomVisited = r;
	}
	
	public boolean isHuman() {
		return false;
	}
}
