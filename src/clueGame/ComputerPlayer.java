package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private HashSet<String> seenlist;
	
	public ComputerPlayer(String string, Color blue, Integer x) {
		// TODO Auto-generated constructor stub
		super(string,blue,x);
		seenlist = new HashSet<String>();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		LinkedList<RoomCell> doors=new LinkedList<RoomCell>();
		LinkedList<BoardCell>tmpTargets=new LinkedList<BoardCell>(targets);
		for (BoardCell i : targets)
		{
			if (i.isDoorway()&&((RoomCell) i).getInitial()!=lastRoomVisited)
			{
				doors.add((RoomCell)i);
			}
			else if (i.isDoorway()&&((RoomCell) i).getInitial()==lastRoomVisited)
			{
				tmpTargets.remove(i);
			}
		}
		if (doors.size()!=0)
		{
			int x = (int) (Math.random()*doors.size());
			int i = 0;
			for(BoardCell b: doors)
			{
				if(i == x)
					return b;
				else i++;
			}
		}
		else{
			int x = (int) (Math.random()*tmpTargets.size());
			int i = 0;
			for(BoardCell b: tmpTargets)
			{
				if(i == x)
					return b;
				else i++;
			}
		}
		return null;
		
	}
	
	public Solution createSuggestion(ClueGame game) {
		String n=null, w=null, r=null;
		int count=1;
		for(Player p: game.players)
			if(!seenlist.contains(p.getName()))
			{
				if(Math.random()<(1.0/count))
					n = p.getName();
				count++;
			}
		count=1;
		for(String s: game.weapons)
			if(!seenlist.contains(s))
			{
				if(Math.random()<(1.0/count))
					w = s;
				count++;
			}
		count=1;
		for(String s: game.roomlist)
			if(!seenlist.contains(s))
			{
				if(Math.random()<(1.0/count))
					r = s;
				count++;
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
	
	public void draw(Graphics g, int x, int y, int ratio, int ratio2) {
		super.draw(g, x, y, ratio, ratio2);
	}
}
